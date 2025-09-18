import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val proceso = ProcessBuilder("C:\\Users\\delat\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=53541\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\delat\\Documents\\GitHub\\Oscartorrepastor-PSP\\untitled\\out\\production\\untitled;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar HijoKt")
    val ejecutar = proceso.start()

    val output = OutputStreamWriter(ejecutar.outputStream)

    output.write("Hola hijo\n")
    output.flush()
    val input = BufferedReader(InputStreamReader( ejecutar.inputStream)).readLine()
    print(input)
}