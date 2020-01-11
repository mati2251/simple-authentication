package session

import database.DatabaseConnection
import java.nio.charset.Charset
import java.util.*


class Session (private var userId: Int){
    val sessionId: String
    private val statement = DatabaseConnection.connection.createStatement()
    init {
        val array = ByteArray(7)
        Random().nextBytes(array)
        sessionId = String(array, Charset.forName("UTF-8"))
        addToDataBaseSession()
    }

    private fun addToDataBaseSession (){
        val sqlInsert = "INSERT INTO public.session values ('${sessionId}', ${this.userId});"
        statement.executeUpdate(sqlInsert)
    }
}