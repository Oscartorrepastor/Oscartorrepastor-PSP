package SS

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun sha512(input: String): String {
    val bytes = input.toByteArray()
    val md = java.security.MessageDigest.getInstance("SHA-512")
    val digest = md.digest(bytes)
    return digest.joinToString("") { "%02x".format(it) }
}

fun hmac(key: String, message: String): String {
    val hmac = Mac.getInstance("HmacMD5")
    hmac.init(SecretKeySpec(key.toByteArray(), "HmacMD5"))
    return hmac.doFinal(message.toByteArray()).joinToString("") { "%02x".format(it) }

}

fun main() {
    val texto = "hola"
    val hash = sha512(texto)
    println("Texto: $texto")
    println("SHA-512: $hash")
    println("--------------------------HMAC-----------------------------------")
    val clave = "1234"
    val mensaje = "hola"
    val hmacResult = hmac(clave, mensaje)
    println("Mensaje: $mensaje")
    println("HMAC-MD5: $hmacResult")
}