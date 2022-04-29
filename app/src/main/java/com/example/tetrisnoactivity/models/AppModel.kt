package com.example.tetrisnoactivity.models

import android.graphics.Point
import com.example.tetrisnoactivity.constants.FieldConstants
import com.example.tetrisnoactivity.helper.array2dOfByte
import com.example.tetrisnoactivity.storage.AppPreferences
import com.example.tetrisnoactivity.constants.CellConstants

class AppModel {
	var score: Int = 0 // значение очков
	private var preferences: AppPreferences? = null

	var currentBlock: Block? = null
	/*
	 *	currentState == соответствующее состояние
	 */
	var currentState: String = Statuses.AWAITING_START.name
	private var field: Array<ByteArray> = array2dOfByte(
		FieldConstants.ROW_COUNT.value,
		FieldConstants.COLUMN_COUNT.value
	)
	enum class Statuses {
		AWAITING_START, ACTIVE, INACTIVE, OVER
	}
	enum class  Motions{
		LEFT, RIGHT, DOWN, ROTATE
	}
	fun setPreferences(preferences: AppPreferences?){
		// устанавливает свойство предпочтений
		this.preferences = preferences
	}
	fun getCellStatus(row: Int, column: Int): Byte?{
		// возвращает состояние ячейки в указанной позиции в 2 мерном массиве
		return field[row][column]
	}
	private fun setCellStatus(row: Int, column: Int, status: Byte?){
		// устанавливает состояние ячейки в поле равным указанному байту
		if (status != null){
			field[row][column] = status
		}
	}
	fun isGameOver(): Boolean{
		return currentState == Statuses.OVER.name
	}
	fun isGameActive(): Boolean{
		return currentState == Statuses.ACTIVE.name
	}
	fun isGameAwaitingStart(): Boolean{
		return currentState == Statuses.AWAITING_START.name
	}
	private fun boostScore(){
		// функция для увеличения значения очков
		score += 10
		// проверка не превышает ли текущий счет рекорд установленный в файле настроек
		if (score > preferences?.getHighScore() as Int)
			//рекорд перезаписывается
			preferences?.saveHighScore(score)
	}
	private fun generateNextBlock(){
		// создает новый экземпляр блока и устанавливает значение текущего блока равным ему
		currentBlock = Block.createBlock()
	}
	//validTranslation - допустимость поступательного движения тетрамино
	private fun validTranslation(position: Point, shape:Array<ByteArray>):
			Boolean{
		// 3 условия для проверки находится ли в поле позиция в которую переводится тетрамино
		return if(position.y < 0 || position.x < 0){
			false
		}   else if(position.y + shape.size > FieldConstants.ROW_COUNT.value){
			false
		}else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value){
			false
		} else {
			for (i in 0 until shape.size){
				for (j in 0 until shape[i].size){
					val y = position.y + i
					val x = position.x + j
					if (CellConstants.Empty.value != shape[i][j] &&
							CellConstants.Empty.value != field[y][x]){
						return false
					}
				}
			}
			true //если трансляция корректна
		}
	}
	private fun moveValid(position: Point, frameNumber: Int?): Boolean
	{
		val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber as Int)
		// разрешен ли выполненый игроком ход
		return validTranslation(position, shape as Array<ByteArray>)
	}

	fun generateField(action: String){
		//генерирует обновление поля, action - действие для обновления
		if(isGameActive()) {
			resetField()
			// извлекаются номер фрейма
			var frameNumber: Int? = currentBlock?.frameNumber
			// и координаты блока
			val coordinate: Point? = Point()
			coordinate?.x = currentBlock?.position?.x
			coordinate?.y = currentBlock?.position?.y
			// определяется запрашиваемое действие
			when (action) {
				Motions.LEFT.name -> {
					coordinate?.x = currentBlock?.position?.x?.minus(1)
				}
				Motions.RIGHT.name -> {
					coordinate?.x = currentBlock?.position?.x?.plus(1)
				}
				Motions.DOWN.name -> {
					coordinate?.y = currentBlock?.position?.y?.minus(1)
				}
				Motions.ROTATE.name -> {
					/* для вращения значение frameNumber изменяется
					 * с учетом соответствующего фрейма
					 */
					frameNumber = frameNumber?.plus(1)

					if (frameNumber != null) {
						if (frameNumber >= currentBlock?.frameCount as Int) {
							frameNumber = 0
						}
					}
				}
			}
			// проверяется является ли запрошенное движение действительным
			if(!moveValid(coordinate as Point, frameNumber)){
				// если нет, то текущий блок фиксируется в текущей позиции
				translateBlock(currentBlock?.position as Point,
					currentBlock?.frameNumber as Int)
				if(Motions.DOWN.name == action){
					boostScore()
					persistCellData()
					assessField()
					generateNextBlock()
					if (!blockAdditionPossible()){
						currentState = Statuses.OVER.name;
						currentBlock = null;
						resetField(false);
					}
				}
			} else {
				if (frameNumber != null){
					translateBlock(coordinate, frameNumber)
					currentBlock?.setState(frameNumber, coordinate)
				}
			}
		}
	}
}