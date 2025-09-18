import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    /*
    val nombre = "arp -a"
    val proceso = ProcessBuilder("cmd.exe", "/c", "arp -a")
    val procesotemporal = proceso.start()
    proceso.start()
    //print(procesotemporal.inputStream.bufferedReader().readText())
    //print(procesotemporal.isAlive)
    //procesotemporal.waitFor()
    val lector =
        BufferedReader(InputStreamReader(procesotemporal.inputStream)) // te conecta y lo lee a la vez en una sola linea
*/
    //var linea: String?
    var dar =  BufferedReader(InputStreamReader(ProcessBuilder("C:\\Users\\delat\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=57328\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\delat\\Documents\\GitHub\\Oscartorrepastor-PSP\\untitled\\out\\production\\untitled;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar LlamarKt").start().inputStream)).readLine()
    var linea =  BufferedReader(InputStreamReader(ProcessBuilder("C:\\Users\\delat\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=57328\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\delat\\Documents\\GitHub\\Oscartorrepastor-PSP\\untitled\\out\\production\\untitled;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\delat\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar LlamarKt").start().inputStream)).readLine()
    println(linea.uppercase())



    /*
    while (lector.readLine().also { linea = it } != null) {
        print(linea)
    }

*/
}