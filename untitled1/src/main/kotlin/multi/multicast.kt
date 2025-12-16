@file:Suppress("DEPRECATION")

package multi

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

fun main() {
    try {
        val socketMulti = MulticastSocket(5000)
        val direcion = InetAddress.getByName("224.0.0.3")

        socketMulti.joinGroup(direcion)

        val mensaje = "Hola Tochas".toByteArray()
        val paquete = DatagramPacket(mensaje, mensaje.size, direcion, 5000)
        socketMulti.send(paquete)

        while (true){

            val byte = ByteArray(1024)
            val paqueteRecibido = DatagramPacket(byte, byte.size)
            socketMulti.receive(paqueteRecibido)
            println(String(paqueteRecibido.data, paqueteRecibido.offset, paqueteRecibido.length))
        }
    }catch (e : Exception){
        println(e)
    }
}