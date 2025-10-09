fun main() {
    val hilo = Thread {
        println("Hilo empezado")
        for (i in 1..5) {
            println("Hilo en ejecución $i")
            Thread.sleep(1000)
        }
        println("Hilo terminado")
    }
    println(hilo.state)
    hilo.start()
    hilo.join()
    println(hilo.state)


}