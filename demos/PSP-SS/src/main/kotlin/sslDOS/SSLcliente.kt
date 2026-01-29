package sslDOS

import java.io.DataInputStream
import java.io.DataOutputStream
import javax.net.ssl.SSLSocketFactory

fun main() {
    System.setProperty("javax.net.ssl.trustStore", "Almacen")
    System.setProperty("javax.net.ssl.trustStorePassword", "1234567")

    val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
    val socket = socketFactory.createSocket("localhost", 2000) as javax.net.ssl.SSLSocket

    val entrada = DataInputStream(socket.getInputStream())
    val salida = DataOutputStream(socket.getOutputStream())

    salida.writeUTF("Hola desde el cliente SSL")
    salida.flush()
    println(entrada.readUTF())
    socket.close()
}