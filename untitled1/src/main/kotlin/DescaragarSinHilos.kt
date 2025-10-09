import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

fun descagar() {
    println("comienzo a descargar")
    TimeUnit.SECONDS.sleep(1)
    println("Fin de descargar")
}


fun main() {
    val tiempo = measureTimeMillis {
        for (i in 1.. 10){
        descagar()
    }
    }
    println(tiempo)
}

