package y

import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.ServerSocket

fun main() {
    val servidor = ServerSocket(123456)
    println("Servidor Y esperando conexiones en el puerto 123456...")
    val cliente = servidor.accept()
    println("Cliente conectado desde ${cliente.inetAddress.hostAddress}")

    val input: InputStream = cliente.getInputStream()
    val fichero = FileOutputStream("imagen.png")
    val buffer = BufferedOutputStream(fichero)

    val temporal = ByteArray(4000)
    var byte: Int

    while (input.read(temporal).also { byte = it } != -1) {
        buffer.write(temporal, 0, byte)
    }
    buffer.flush()
    buffer.close()
    input.close()
    cliente.close()
    servidor.close()
    println("Imagen recibida y guardada como imagen.png")
}