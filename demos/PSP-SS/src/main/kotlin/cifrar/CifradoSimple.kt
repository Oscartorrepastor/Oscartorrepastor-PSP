package cifrar

fun cifrar(palabra: String, desplazamiento: Int): String {
    return palabra.map {
        if (it.isLetter()) {
            val base = if (it.isLowerCase()) 'a' else 'A'
            ((it - base + desplazamiento) % 26 + base.code).toChar()
        } else {
            it
        }
    }.joinToString("")
}

fun desCifrar(palabra: String, desplazamiento: Int): String{

    return palabra.map {
        if (it.isLetter()) {
            val base = if (it.isLowerCase()) 'a' else 'A'
            ((it - base - desplazamiento + 26) % 26 + base.code).toChar()
        } else {
            it
        }
    }.joinToString("")
}

fun main() {
    println(cifrar("hola que tal estas", 2))
    println(desCifrar(cifrar("hola que tal estas", 2), 2))
}