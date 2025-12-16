import java.net.Socket

fun main() {
    val cliete = Socket("192.168.2.143", 80)
    cliete.getOutputStream().write("Hola desde el cliente".toByteArray())

    cliete.close()
}

//192.168.2.189
//192.168.2.216
//192.168.2.143