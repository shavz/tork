package io.shiveenp

import java.io.EOFException
import java.io.File
import java.io.PushbackInputStream

private const val INPUT_STREAM_LOOK_AHEAD = 1

fun readBencodedFile(file: File) {
    val dataBytes = PushbackInputStream(file.inputStream(), INPUT_STREAM_LOOK_AHEAD)
    dataBytes.use {

    }
}

fun readNextByte(input: PushbackInputStream) {
    when (val data = input.lookAhead().toString()) {
        "i " -> readInteger(input)
        "l" -> readList(input)
        "d" -> readDictionary(input)
        else -> readString(input)
    }
}

fun readInteger(input:) {
    input.reader().read()
}


fun readString(input: PushbackInputStream) {

}

fun readDictionary(input: PushbackInputStream) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun readList(input: PushbackInputStream) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

/**
 * Looks ahead at the next value in the stream without consuming it permanently
 */
fun PushbackInputStream.lookAhead(): Byte {
    val byte = this.read()
    if (byte == -1) throw EOFException()
    this.unread(byte)
    return byte.toByte()
}

fun PushbackInputStream.safeRead(): Byte {
    val byte = this.read()
    if (byte == -1) throw EOFException()
    return byte.toByte()
}

