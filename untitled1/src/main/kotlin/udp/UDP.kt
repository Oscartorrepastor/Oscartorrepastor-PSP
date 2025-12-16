package udp

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

fun main() {
    val socket = DatagramSocket()
    val direcion = InetAddress.getByName("localhost")
    val paquete = DatagramPacket("Hola Mundo".toByteArray(), "Hola Mundo".toByteArray().size, direcion, 1234)
    socket.send(paquete)
    socket.close()

}