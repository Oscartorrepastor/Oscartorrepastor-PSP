package y

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.Socket

fun main() {
    val cliente = Socket("127.0.0.1", 123456)
    val fichero = File(" ")

    val output: OutputStream = cliente.getOutputStream()
    val file = FileInputStream(fichero)
    val bufferInput = BufferedInputStream(file)

    val temporal = ByteArray(4000)
    var byte: Int

    while (bufferInput.read(temporal).also { byte= it } !=-1){
        output.write(temporal, 0 , byte)
    }
    output.flush()
    output.close()
    bufferInput.close()
    cliente.close()
}