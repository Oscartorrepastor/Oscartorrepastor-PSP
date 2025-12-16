import java.net.ServerSocket

fun main() {
    val servidor = ServerSocket(12345)
    val cliente = servidor.accept()

    val entrada = cliente.getInputStream().bufferedReader()
    println(entrada.readLine())
    cliente.close()
    servidor.close()
}