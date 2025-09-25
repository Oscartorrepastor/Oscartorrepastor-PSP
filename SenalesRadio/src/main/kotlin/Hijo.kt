import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.random.Random

fun main() {
    /*
    val input = BufferedReader(InputStreamReader(System.`in`))
    val array = input.readLine().split(",").toTypedArray()


    print(array.joinToString(",") + "\n")
    print("ya he ralizado la operacion y has ganado")
     */

    val input = BufferedReader(InputStreamReader(System.`in`))
    val output = BufferedWriter(OutputStreamWriter(System.out))

    // Saldo inicial aleatorio entre 100 y 1000
    var saldo = Random.nextInt(100, 1001)

    while (true) {
        val linea = input.readLine() ?: break
        when {
            linea.equals("saldo", ignoreCase = true) -> {
                output.write("Tienes $saldo €\n")
                output.flush()
            }
            linea.equals("retirar", ignoreCase = true) -> {
                output.write("Puedes sacar hasta $saldo €\n")
                output.flush()
            }
            linea.toIntOrNull() != null -> {
                val cantidad = linea.toInt()
                if (cantidad <= saldo) {
                    saldo -= cantidad
                    output.write("Has sacado $cantidad €. Te quedan $saldo €\n")
                } else {
                    output.write("No puedes sacar $cantidad €, saldo insuficiente.\n")
                }
                output.flush()
            }
            else -> {
                output.write("No entiendo tu petición.\n")
                output.flush()
            }
        }
    }
}