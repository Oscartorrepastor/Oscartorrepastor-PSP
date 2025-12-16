package udp

import java.net.DatagramPacket
import java.net.DatagramSocket

fun main() {
    val datagrama = DatagramSocket(12345)
    while (true) {
        val buffer = ByteArray(1024)
        val paqueteRespuesta = DatagramPacket(buffer, buffer.size)
        datagrama.receive(paqueteRespuesta)
        println(paqueteRespuesta.address.hostAddress)
        val cadenaFinal = String(paqueteRespuesta.data, 0 , paqueteRespuesta.length)
        println(cadenaFinal)
    }
}