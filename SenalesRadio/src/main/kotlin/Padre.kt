import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.random.Random

fun main() {
    val proceso = ProcessBuilder("C:\\Users\\delat\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=50172\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\delat\\Documents\\GitHub\\Oscartorrepastor-PSP\\SenalesRadio\\out\\production\\SenalesRadio;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.10\\kotlin-stdlib-2.2.10.jar;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar HijoKt")

    val procesoArranque = proceso.start()
/*
    val array = arrayOf("Hola" , "desde", "el" , "proceso" , "padre")

    val out = OutputStreamWriter(procesoArranque.outputStream)
    out.write(array.joinToString(",") + "\n")
    //out.write(array[0] + "\n" )
    out.flush()

    val input = procesoArranque.inputStream
    val leer = BufferedReader(InputStreamReader(input))
    print(leer.readLine())
 */

    val escritor = BufferedWriter(OutputStreamWriter(procesoArranque.outputStream))
    val lector = BufferedReader(InputStreamReader(procesoArranque.inputStream))

    // Padre pregunta saldo
    println("Cliente: ¿Cuánto dinero tengo?")
    escritor.write("saldo\n")
    escritor.flush()
    println("Cajero: " + lector.readLine())

    // Padre pide retirar
    println("Cliente: Quiero sacar dinero, ¿cuánto puedo sacar?")
    escritor.write("retirar\n")
    escritor.flush()
    println("Cajero: " + lector.readLine())

    // Padre indica cantidad
    val cantidad = Random.nextInt(100, 1001)
    println("Cliente: Deseo sacar $cantidad €")
    escritor.write("$cantidad\n")
    escritor.flush()
    println("Cajero: " + lector.readLine())

    escritor.close()
    lector.close()
}