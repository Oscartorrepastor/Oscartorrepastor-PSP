package clienteServer

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Cliente {
    fun startCliente() {
        try {
            val cliente = Socket("127.0.0.1", 5000)
            val leer = BufferedReader(InputStreamReader(cliente.getInputStream()))
            val escribir = PrintWriter(cliente.getOutputStream(), true)
            var mensaje: String?
            do {
                println("Escribe un mensaje para el servidor: ")
                mensaje = readLine()
                escribir.println(mensaje)
                println(leer.readLine())

            }while (mensaje != null || mensaje == "fin")
            leer.close()
            escribir.close()
            cliente.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}
fun main() {
    val cliente = Cliente().startCliente()
}