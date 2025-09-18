import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val input = BufferedReader(InputStreamReader(System.`in`))
    val ouput = OutputStreamWriter(System.out)

    ouput.write(input.readLine().uppercase())
    ouput.flush()


}