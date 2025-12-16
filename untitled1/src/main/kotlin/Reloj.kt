import java.util.Calendar
import kotlin.concurrent.thread

fun main() {
    thread {
        val horario = Calendar.getInstance().time
        println(horario)
    }
    for (hora in 0..24) {
        for (minuto in 0..59) {
            for (segundo in 0..59) {
                Thread.sleep(1000)
                println("$hora:$minuto:$segundo")
            }
        }
    }
}