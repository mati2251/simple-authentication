package database

import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object DatabaseConnection {

    private val url: String
    private val prop = Properties()
    val connection: Connection

    init {
        try {
            FileInputStream("src/main/resources/server.properties").use { input ->
                prop.load(input)
            }
        } catch (io: IOException) {
            io.printStackTrace()
        }
        url = "jdbc:postgresql://${prop["db.url"]}:${prop["db.port"]}/postgres"
        try {
            connection = DriverManager.getConnection(
                url,
                prop
            )
        }
        catch (e: Exception){
            throw e;
        }
    }
}