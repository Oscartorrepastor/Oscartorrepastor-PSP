package SS

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val ftp = FTPClient()
    try{
        ftp.connect("127.0.0.1", 21)
        ftp.login("dam2026", " ")
        ftp.enterLocalPassiveMode()
        ftp.setFileType(FTP.BINARY_FILE_TYPE)


        val ouput = FileOutputStream("C:\\Users\\delat\\Pictures\\Screenshots\\cocheverde.png")
        ftp.retrieveFile("/carpeta/cocheverde56.png", ouput)
        ouput.close()

        /*  PARA SUBIR FICHERO
        val input = FileInputStream("C:\\Users\\delat\\Desktop\\DAM2\\apunte.txt")
        ftp.storeFile("/carpeta/apunte2.txt", input)
        input.close()
        */

        /*  SOlO PARA CONSULTA A FICHERO EXISTENTE
        val ficheros = ftp.listFiles("/carpeta")
        for (fichero in ficheros) {
            if (fichero.isFile){
                println("File: ${fichero.name} Size: ${fichero.size}")
            } else if (fichero.isDirectory){
                println("Directory: ${fichero.name}")
            }
        }*/

    } catch (e: Exception){
        println("Connection failed: ${e.message}")
        return
    } finally {
        ftp.logout()
        ftp.disconnect()
    }

}