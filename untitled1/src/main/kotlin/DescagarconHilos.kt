import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis


fun descagar2() {
    println("comienzo a descargar")
    TimeUnit.SECONDS.sleep(1)
    println("Fin de descargar")
}


fun main() {
    val tiempo = measureTimeMillis {
        val hilos = listOf(
            Thread {descagar2()}, Thread {descagar2()} , Thread {descagar2()}
        )
        hilos.forEach { it.start() }
        hilos.forEach { it.join()

        }
    }
    println(tiempo)
}