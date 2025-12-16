package clienteServer

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class Server {
    fun startServer() {
        try {
            val server = ServerSocket(5000)
            val cliente = server.accept()
            val leer = BufferedReader(InputStreamReader(cliente.getInputStream()))
            val escribir = PrintWriter(cliente.getOutputStream(), true)
            var mensaje: String?
            do {
                mensaje = leer.readLine()
                println(mensaje)
                escribir.println("Mensaje recibido Servidor: $mensaje")

            } while (mensaje != null || mensaje == "fin")
            leer.close()
            escribir.close()
            cliente.close()
            server.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
fun main() {
    val server = Server().startServer()

}