fun tablaMultiplicar(numero: Int) {
    for (i in 1..10) {
        println("$numero x $i = ${numero * i}")
        Thread.sleep(100)
    }
}

fun main() {
    val hilos = mutableListOf<Thread>()
    for (i in 1..10) {
        val hilo = Thread {
            tablaMultiplicar(i)
        }
        hilos.add(hilo)
        hilo.start()
        hilo.join()
    }
}
