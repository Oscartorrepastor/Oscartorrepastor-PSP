package cifrar

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import java.util.Base64

fun main() {

    //  TU COMPI PEGA AQUÍ EL MENSAJE CIFRADO
    val mensajeCifrado = "RwQYXZEAzdHtbQHrq/qz2qEX/ZMt4ZK1uCN2GS2vIUayvcsvy7SSp7oMaHUMqzkMKGidWollluzAuOKoi3ZmnLokV2fZlZ2PCQ2FCMmh4mHe48uP5PWfhI2SCmiA73jWPpjxS0H3Rdj/T4vIkMiFYVG21ZBt6jzesc+2jFLAI/+U7guNAFfC15u81TmoGqxCSKIbgqwPcahdpvC+n+IXgTRM2kqNie2eE5mN/lFk2gFdo5pxmWpUviwRHcVK2xwyIvdktGhG9u4yqd1I8wpiIMq1uy2WL07KyWTAqmi5yj4gD5ORE5fgiDknqYlbv71iooKVtVagn3Xp3UF5VR3Wfg=="

    //  TU COMPI PEGA AQUÍ TU CLAVE PUBLICA
    val clavePublicaBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlaq+ZvZ8bDoOrGqeBQKo2ubUJv5jo42Fwejn3LWuhSlexy044wcPrqllROu8E/OPj30Bmif9tvjLT/mdMV9Do5povFlaEboDRGUhRqsmniALr99tbiaBsSRVdjLRUBk8VAuy5kCzclkd5ooB0/Rf25Tczs9fxvuozZPMkC/wpKHZxD3csLLhH+YcfgvLqu8DGjWvjwfWtgbHS9z/TwuFLJ04x5OOk4bbIjTpjLWHxmyZWLV0vO7j35wG7ECDnqVIP4EwA6RNsYcXIuaoVUCR+vROu2T2OdGuxPBUlEWR4lJSqfCJ/xsxdlBfrOUCnO9vGf/ex12BXxt4qwUXzanUNQIDAQAB"

    // Reconstruir clave pública
    val keyBytes = Base64.getDecoder().decode(clavePublicaBase64)
    val keySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance("RSA")
    val clavePublica: PublicKey = keyFactory.generatePublic(keySpec)

    // Descifrar con la clave pública
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.DECRYPT_MODE, clavePublica)
    val mensajeDescifradoBytes =
        cipher.doFinal(Base64.getDecoder().decode(mensajeCifrado))

    val mensajeDescifrado = String(mensajeDescifradoBytes)

    println("MENSAJE DESCIFRADO:")
    println(mensajeDescifrado)
}