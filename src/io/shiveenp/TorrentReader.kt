package io.shiveenp

import java.io.EOFException
import java.io.File
import java.io.InputStream
import java.io.PushbackInputStream

private const val INPUT_STREAM_LOOK_AHEAD = 1

fun readBencodedFile(file: File) {
    val dataBytes = PushbackInputStream(file.inputStream(), INPUT_STREAM_LOOK_AHEAD)
    dataBytes.use {

    }
}

fun readNextByte(input: PushbackInputStream) {
    when (input.lookAhead().toString()) {
        "i " -> readInteger(input)
        "l" -> readList(input)
        "d" -> readDictionary(input)
        else -> readString(input)
    }
}

/**
 * Reads an integer type value `i3e` or `i-25e` from an bencoded stream
 */
@Throws(BencodeReaderException::class)
fun readInteger(input: PushbackInputStream): Int {
    var digitRead = false
    var isNegative = false
    var stillReading = true

    val integerString = ""
    var integerToReturn = 0

    try {
        while (stillReading) {
            val tmp = input.lookAhead().toString()
            if (tmp == "-" && !isNegative && !digitRead) {
                isNegative = true
            } else if (tmp.isNumber() && tmp.toInt() >= 0 && tmp.toInt() <= 9) {
                integerString.plus(tmp)
                digitRead = true
            } else if (tmp == "e") {
                if (digitRead) {
                    integerToReturn = if (isNegative) {
                        0 - integerString.toInt()
                    } else {
                        integerString.toInt()
                    }
                    stillReading = false
                } else {
                    throw BencodeReaderException("Unable to read integer for the given byte value $tmp")
                }
            }
        }
    } catch (e: Exception) {
        throw BencodeReaderException("Encountered exception while processing integer value ${e.localizedMessage}")
    }
    return integerToReturn
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

fun String.isNumber(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: Exception) {
        false
    }
}
