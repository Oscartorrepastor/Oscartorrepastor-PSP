import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.sin

fun main() {
    runBlocking {
        val job = launch {
            println("Incio de la corrutina")
            for (i in 1..10) {
                println("Corrutina en ejecución")
                delay(1000L)
            }
        }
        job.join()
        for (i in 1..10) {
            println("Corrutina en ejecución principal")
            Thread.sleep(1000)
        }
    }

}