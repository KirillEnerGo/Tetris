package com.example.tetrisnoactivity.models

import com.example.tetrisnoactivity.helper.array2dOfByte

class Frame (private val width: Int){
	// width - число столбцов в байтовом массиве фрейма (ширина)
	// data - список элементов массива в пространстве значений ByteArray
	val data: ArrayList<ByteArray> = ArrayList()

	/*
	 *  addRow() обрабатывает строку,
	 * преобразуя отдельный символ строки в байтовое представление
	 * и добавляет байтовое представление в байтовый массив
	 * после чего байтовый массив добавляется в список данных
	 */
	fun addRow(byteStr: String): Frame {
		// создает пустой массив, длинной в строку, заполненный '0'
		val row = ByteArray(byteStr.length)
		// перебирает каждый элемент в массиве
		for (index in byteStr.indices){
			/*
			 *  записывает каждый символ в строке,
			 *  конвертированный в байт в новый массив байтов
			 */
			row[index] = "${byteStr[index]}".toByte()
		}
		data.add(row)
		return this
	}
	fun as2dByteArray(): Array<ByteArray>{
		val bytes = array2dOfByte(data.size, width)
		return data.toArray(bytes)
	}
}