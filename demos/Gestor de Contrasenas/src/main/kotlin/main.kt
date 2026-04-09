import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.thread
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * SISTEMA DE BACKUP SEGURO
 *
 * CIBERSEGURIDAD:
 * - Cifrado AES-256
 * - Hash SHA-256 para integridad
 * - Autenticación de usuarios
 *
 * PROGRAMACIÓN DE PROCESOS Y SERVICIOS:
 * - Hilos concurrentes
 * - Corrutinas
 * - Sincronización
 * - Monitoreo en tiempo real
 */
fun main() {
    val sistema = SistemaBackupSeguro()
    sistema.iniciar()
}

class SistemaBackupSeguro {
    // Configuración
    private val carpetaOrigen = File("C:/Backup/Origen")
    private val carpetaDestino = File("C:/Backup/Destino")
    private val carpetaCifrados = File("C:/Backup/Cifrados")

    // Seguridad
    private lateinit var claveMaestra: SecretKey
    private val usuarios = mutableMapOf<String, String>()

    // Estadísticas
    private val archivosProcesados = AtomicInteger(0)
    private val bytesTransferidos = AtomicInteger(0)
    private val alertasSeguridad = ConcurrentHashMap.newKeySet<String>()

    // Control de procesos
    private var monitoreoActivo = true
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun iniciar() {
        // Configuración inicial
        configurarSistema()

        // Menú principal
        while (true) {
            mostrarMenu()
            when (readlnOrNull()?.trim() ?: "") {
                "1" -> iniciarMonitoreo()
                "2" -> realizarBackupManual()
                "3" -> verEstadisticas()
                "4" -> verAlertas()
                "5" -> gestionarUsuarios()
                "6" -> verificarIntegridad()
                "7" -> {
                    println("\n👋 ¡Hasta pronto!")
                    monitoreoActivo = false
                    scope.cancel()
                    return
                }
                else -> println("❌ Opción no válida")
            }
        }
    }

    private fun configurarSistema() {
        println("⚙️  CONFIGURACIÓN INICIAL DEL SISTEMA")
        println("======================================")

        // Crear carpetas
        carpetaOrigen.mkdirs()
        carpetaDestino.mkdirs()
        carpetaCifrados.mkdirs()

        // Generar clave de cifrado
        println("🔐 Generando clave de cifrado AES-256...")
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        claveMaestra = keyGen.generateKey()

        // Usuario por defecto
        usuarios["admin"] = hashPassword("admin123")

        println("✅ Sistema configurado correctamente")
        println("📁 Carpeta origen: ${carpetaOrigen.absolutePath}")
        println("📁 Carpeta destino: ${carpetaDestino.absolutePath}")
        println("🔒 Carpeta cifrados: ${carpetaCifrados.absolutePath}")
        println("\nPresiona ENTER para continuar...")
        readlnOrNull()
    }

    private fun mostrarMenu() {
        println("""
            
            ╔══════════════════════════════════════════════════════════╗
            ║     🛡️  SISTEMA DE BACKUP SEGURO v1.0 - DAM2            ║
            ║     Ciberseguridad + Procesos Concurrentes              ║
            ╚══════════════════════════════════════════════════════════╝
            
            📋 MENÚ PRINCIPAL:
            ═══════════════════════════════════════════════════════════
            1️⃣  🚀 INICIAR MONITOREO AUTOMÁTICO (Procesos en tiempo real)
            2️⃣  📂 REALIZAR BACKUP MANUAL
            3️⃣  📊 VER ESTADÍSTICAS DEL SISTEMA
            4️⃣  ⚠️  VER ALERTAS DE SEGURIDAD
            5️⃣  👥 GESTIONAR USUARIOS
            6️⃣  🔍 VERIFICAR INTEGRIDAD DE ARCHIVOS
            7️⃣  ❌ SALIR
            ═══════════════════════════════════════════════════════════
            Opción: 
        """.trimIndent())
    }

    /**
     * 🚀 PROCESO 1: Monitoreo en tiempo real
     * Demuestra: Hilos, corrutinas, procesos concurrentes
     */
    private fun iniciarMonitoreo() {
        println("\n🔄 INICIANDO MONITOREO EN TIEMPO REAL")
        println("======================================")

        monitoreoActivo = true

        // PROCESO 1: Hilo que monitorea cambios en carpeta
        thread(name = "Monitor-Carpeta") {
            while (monitoreoActivo) {
                try {
                    val archivos = carpetaOrigen.listFiles() ?: emptyArray()
                    archivos.forEach { archivo ->
                        if (archivo.isFile && !archivo.name.startsWith(".")) {
                            // Procesar nuevo archivo
                            scope.launch {
                                procesarArchivoConcurrente(archivo)
                            }
                        }
                    }
                    Thread.sleep(3000) // Esperar 3 segundos
                } catch (e: Exception) {
                    println("❌ Error en monitor: ${e.message}")
                }
            }
        }

        // PROCESO 2: Hilo que muestra estadísticas cada 10 segundos
        thread(name = "Monitor-Estadisticas") {
            while (monitoreoActivo) {
                try {
                    Thread.sleep(10000)
                    println("\n📊 [${obtenerHora()}] Estadísticas en tiempo real:")
                    println("   • Archivos procesados: ${archivosProcesados.get()}")
                    println("   • MB transferidos: ${bytesTransferidos.get() / (1024 * 1024)}")
                    println("   • Alertas activas: ${alertasSeguridad.size}")
                } catch (e: Exception) {
                    // Ignorar
                }
            }
        }

        println("✅ Monitoreo activado - Revisa la carpeta: ${carpetaOrigen.absolutePath}")
        println("⏺️  Presiona ENTER para volver al menú (el monitoreo continúa en segundo plano)")
        readlnOrNull()
    }

    /**
     * 🔄 Procesamiento concurrente de archivos
     * Demuestra: Corrutinas, async/await, procesamiento paralelo
     */
    private suspend fun procesarArchivoConcurrente(archivo: File) {
        coroutineScope {
            val backupJob = async { realizarBackup(archivo) }
            val cifradoJob = async { cifrarArchivo(archivo) }
            val hashJob = async { calcularHash(archivo) }

            // Esperar a que terminen todos
            val resultadoBackup = backupJob.await()
            val resultadoCifrado = cifradoJob.await()
            val hash = hashJob.await()

            // Actualizar estadísticas
            archivosProcesados.incrementAndGet()
            bytesTransferidos.addAndGet(archivo.length().toInt())

            println("✅ [${obtenerHora()}] Archivo procesado: ${archivo.name}")
            println("   • Backup: ${resultadoBackup}")
            println("   • Cifrado: ${resultadoCifrado}")
            println("   • Hash: ${hash.take(16)}...")
        }
    }

    /**
     * 📂 Backup manual
     */
    private fun realizarBackupManual() {
        println("\n📂 REALIZANDO BACKUP MANUAL")
        println("==========================")

        runBlocking {
            val archivos = carpetaOrigen.listFiles()?.filter { it.isFile } ?: emptyList()

            if (archivos.isEmpty()) {
                println("❌ No hay archivos en la carpeta origen")
                return@runBlocking
            }

            println("Procesando ${archivos.size} archivos de forma concurrente...")

            val jobs = archivos.map { archivo ->
                async {
                    procesarArchivoConcurrente(archivo)
                }
            }

            jobs.awaitAll()
            println("\n✅ Backup manual completado!")
        }

        println("\nPresiona ENTER para continuar...")
        readlnOrNull()
    }

    /**
     * 🔐 Cifrado AES-256
     * Demuestra: Ciberseguridad, cifrado simétrico
     */
    private fun cifrarArchivo(archivo: File): String {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, claveMaestra)

            val archivoCifrado = File(carpetaCifrados, "${archivo.name}.encrypted")

            FileInputStream(archivo).use { input ->
                FileOutputStream(archivoCifrado).use { output ->
                    val buffer = ByteArray(1024)
                    var bytesLeidos: Int

                    while (input.read(buffer).also { bytesLeidos = it } != -1) {
                        val datosCifrados = cipher.update(buffer.copyOfRange(0, bytesLeidos))
                        datosCifrados?.let { output.write(it) }
                    }

                    val final = cipher.doFinal()
                    output.write(final)
                }
            }

            "✅ Cifrado AES-256"
        } catch (e: Exception) {
            alertasSeguridad.add("Fallo cifrado: ${archivo.name}")
            "❌ Error: ${e.message}"
        }
    }

    /**
     * 💾 Realizar backup simple
     */
    private fun realizarBackup(archivo: File): String {
        return try {
            val destino = File(carpetaDestino, archivo.name)
            archivo.copyTo(destino, overwrite = true)
            "✅ Copiado a destino"
        } catch (e: Exception) {
            "❌ Error backup: ${e.message}"
        }
    }

    /**
     * 🔍 Calcular hash SHA-256
     * Demuestra: Integridad de datos
     */
    private fun calcularHash(archivo: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(archivo).use { input ->
            val buffer = ByteArray(8192)
            var bytesLeidos: Int

            while (input.read(buffer).also { bytesLeidos = it } != -1) {
                digest.update(buffer, 0, bytesLeidos)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    /**
     * 🔐 Hash de contraseñas
     */
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    /**
     * 📊 Ver estadísticas
     */
    private fun verEstadisticas() {
        println("""
            
            📊 ESTADÍSTICAS DEL SISTEMA
            ════════════════════════════
            🕐 Hora: ${obtenerHora()}
            
            📦 PROCESAMIENTO:
            • Archivos procesados: ${archivosProcesados.get()}
            • Datos transferidos: ${bytesTransferidos.get() / 1024} KB
            • Alertas de seguridad: ${alertasSeguridad.size}
            
            🔐 SEGURIDAD:
            • Algoritmo cifrado: AES-256
            • Hash integridad: SHA-256
            • Usuarios registrados: ${usuarios.size}
            
            ⚙️  PROCESOS:
            • Monitoreo activo: ${if (monitoreoActivo) "✅ SÍ" else "❌ NO"}
            • Hilos activos: ${Thread.activeCount()}
            • Corrutinas activas: ${scope.coroutineContext.job.children.count()}
            ════════════════════════════
            
        """.trimIndent())

        println("Presiona ENTER para continuar...")
        readlnOrNull()
    }

    /**
     * ⚠️ Ver alertas de seguridad
     */
    private fun verAlertas() {
        println("""
            
            ⚠️  ALERTAS DE SEGURIDAD
            ════════════════════════
        """.trimIndent())

        if (alertasSeguridad.isEmpty()) {
            println("✅ No hay alertas activas - Sistema seguro")
        } else {
            alertasSeguridad.forEachIndexed { index, alerta ->
                println("${index + 1}. 🚨 $alerta")
            }
        }

        println("\nPresiona ENTER para continuar...")
        readlnOrNull()
    }

    /**
     * 👥 Gestión de usuarios
     */
    private fun gestionarUsuarios() {
        while (true) {
            println("""
                
                👥 GESTIÓN DE USUARIOS
                ═══════════════════════
                1. Ver usuarios
                2. Añadir usuario
                3. Eliminar usuario
                4. Cambiar contraseña
                5. Volver
                
                Opción: 
            """.trimIndent())

            when (readlnOrNull()?.trim() ?: "") {
                "1" -> {
                    println("\n📋 USUARIOS REGISTRADOS:")
                    usuarios.forEach { (user, _) ->
                        println("   • $user")
                    }
                }
                "2" -> {
                    print("Nuevo usuario: ")
                    val user = readlnOrNull() ?: continue
                    if (usuarios.containsKey(user)) {
                        println("❌ El usuario ya existe")
                    } else {
                        print("Contraseña: ")
                        val pass = readlnOrNull() ?: continue
                        usuarios[user] = hashPassword(pass)
                        println("✅ Usuario añadido")
                    }
                }
                "3" -> {
                    print("Usuario a eliminar: ")
                    val user = readlnOrNull() ?: continue
                    if (user == "admin") {
                        println("❌ No se puede eliminar el administrador")
                    } else {
                        usuarios.remove(user)
                        println("✅ Usuario eliminado")
                    }
                }
                "4" -> {
                    print("Usuario: ")
                    val user = readlnOrNull() ?: continue
                    if (usuarios.containsKey(user)) {
                        print("Nueva contraseña: ")
                        val pass = readlnOrNull() ?: continue
                        usuarios[user] = hashPassword(pass)
                        println("✅ Contraseña actualizada")
                    } else {
                        println("❌ Usuario no encontrado")
                    }
                }
                "5" -> return
                else -> println("❌ Opción no válida")
            }

            println("\nPresiona ENTER para continuar...")
            readlnOrNull()
        }
    }

    /**
     * 🔍 Verificar integridad
     */
    private fun verificarIntegridad() {
        println("\n🔍 VERIFICANDO INTEGRIDAD DE ARCHIVOS")
        println("======================================")

        val archivosOrigen = carpetaOrigen.listFiles()?.filter { it.isFile } ?: emptyList()
        val archivosDestino = carpetaDestino.listFiles()?.filter { it.isFile } ?: emptyList()

        println("Comparando ${archivosOrigen.size} archivos originales con sus copias...")

        archivosOrigen.forEach { original ->
            val copia = File(carpetaDestino, original.name)
            if (copia.exists()) {
                val hashOriginal = calcularHash(original)
                val hashCopia = calcularHash(copia)

                if (hashOriginal == hashCopia) {
                    println("✅ ${original.name}: ÍNTEGRO")
                } else {
                    println("❌ ${original.name}: CORRUPTO")
                    alertasSeguridad.add("Integridad comprometida: ${original.name}")
                }
            } else {
                println("⚠️ ${original.name}: Sin copia de seguridad")
            }
        }

        println("\nPresiona ENTER para continuar...")
        readlnOrNull()
    }

    /**
     * ⏰ Obtener hora actual
     */
    private fun obtenerHora(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    }
}