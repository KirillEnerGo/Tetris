package com.example.tetrisnoactivity.models

import android.graphics.Point
import com.example.tetrisnoactivity.constants.FieldConstants
import com.example.tetrisnoactivity.helper.array2dOfByte
import com.example.tetrisnoactivity.storage.AppPreferences

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

}