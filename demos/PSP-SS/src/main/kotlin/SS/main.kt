package SS

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient

fun main() {
    val ftp = FTPClient()
    try{
        ftp.connect("ftp.rediris.es", 21)
        ftp.login("anonymous", "123456")
        ftp.enterLocalPassiveMode()
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
        val ficheros = ftp.listFiles("/sites")
        for (fichero in ficheros) {
            if (fichero.isFile){
                println("File: ${fichero.name} Size: ${fichero.size}")
            } else if (fichero.isDirectory){
                println("Directory: ${fichero.name}")
            }
        }

    } catch (e: Exception){
        println("Connection failed: ${e.message}")
        return
    }

}