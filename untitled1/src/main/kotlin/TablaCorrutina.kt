import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.Color

fun main() {

    // Jetpack para interfaz gráfica y que se vean las tablas de multiplicar

    runBlocking {
        val jobs = mutableListOf<Job>()
        for (tabla in 1..10){
            val job = launch {
                println("Tabla del $tabla")
                for (i in 1..10){
                    println("$tabla x $i = ${tabla * i}")
                    kotlinx.coroutines.delay(1000L)
                }
            }
            jobs.add(job)
        }
    }
}
