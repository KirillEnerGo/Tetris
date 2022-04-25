package com.example.tetrisnoactivity.helper

/*
 *  генерирует и возвращает массив с указанными свойствами
 * sizeOuter - номер строки создаваемого массива
 * sizeInner - номер столбца
 */
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> =
	Array(sizeOuter){ByteArray(sizeInner)}
