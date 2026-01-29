package cifrar

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import java.util.Base64

fun generarAESclave(): Key = KeyGenerator.getInstance("AES").apply{init(128)}.generateKey()

fun cifrar(texto: String, clave: Key): ByteArray{
    val cifrado = Cipher.getInstance("AES/CBC/PKCS5Padding")

    cifrado.init(Cipher.ENCRYPT_MODE,clave)

    val iv = cifrado.iv
    val textoCifrado = cifrado.doFinal(texto.toByteArray())
    return  iv + textoCifrado
}

fun desCifrar(textoCifrado: ByteArray, clave: Key): String{
    val cifrado = Cipher.getInstance("AES/CBC/PKCS5Padding")

    val iv = textoCifrado.sliceArray(0 until 16)
    val textoBytes = textoCifrado.sliceArray(16 until textoCifrado.size)

    val spec = javax.crypto.spec.IvParameterSpec(iv)

    cifrado.init(Cipher.DECRYPT_MODE, clave, spec)

    val textoDescifrado = cifrado.doFinal(textoBytes)

    return String(textoDescifrado)
}


fun main() {
    val clave = generarAESclave()
    val textoCifrado = cifrar("hola", clave)
    val cifrado = (Base64.getEncoder().encodeToString(textoCifrado))
    val textoDescifrado = desCifrar(textoCifrado, clave)
    println(cifrado)
    println(textoDescifrado)

}