import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException
/*
fun main() {
    try {
        while (true) {
            val cliente = Socket("192.168.2.1", 80)
            println(cliente.getOutputStream())
            println("Puerto 80 está abierto")
        }
    }catch (e: UnknownHostException)
    {
        println("Host desconocido")
    }catch (e: Exception) {
        println("Puerto 80 está cerrado")
    }
}*/


// --- utilidades para convertir IP <-> Long ---
fun ipToLong(ip: String): Long {
    val parts = ip.split('.').map { it.toInt() }
    require(parts.size == 4) { "IP inválida: $ip" }
    return (parts[0].toLong() shl 24) or
            (parts[1].toLong() shl 16) or
            (parts[2].toLong() shl 8)  or
            (parts[3].toLong())
}

fun longToIp(l: Long): String {
    val b1 = (l shr 24) and 0xFF
    val b2 = (l shr 16) and 0xFF
    val b3 = (l shr 8 ) and 0xFF
    val b4 = l and 0xFF
    return listOf(b1, b2, b3, b4).joinToString(".") { it.toString() }
}

// --- main similar al tuyo pero sobre rango ---
fun main() {
    // Cambia aquí el rango deseado:
    val startIp = "192.168.2.1"
    val endIp = "192.168.2.254"

    val start = ipToLong(startIp)
    val end = ipToLong(endIp)
    require(start <= end) { "startIp debe ser <= endIp" }

    val timeoutMs = 800 // timeout para la conexión (ms)

    for (ipNum in start..end) {
        val ip = longToIp(ipNum)
        try {
            // versión "similar" a la tuya pero con timeout (constructor Socket bloqueante no tiene timeout)
            val cliente = Socket()
            try {
                cliente.connect(InetSocketAddress(ip, 80), timeoutMs)
                println(cliente.getOutputStream())
                println("$ip -> Puerto 80 está abierto")
            } finally {
                try { cliente.close() } catch (_: Exception) {}
            }
        } catch (e: UnknownHostException) {
            println("$ip -> Host desconocido")
        } catch (e: Exception) {
            println("$ip -> Puerto 80 está cerrado (${e.javaClass.simpleName})")
        }
    }
}
