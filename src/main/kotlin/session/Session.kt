package session

import com.google.common.hash.Hashing
import database.DatabaseConnection
import user.UserHandling
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class Session (private var userId: Int){
    val sessionId: String
    private val statement = DatabaseConnection.connection.createStatement()
    init {
        val array = ByteArray(7)
        val data = UserHandling.getUserDetails(userId);
        data.next()
        val mail = data.getString(4);
        val password = data.getString(7)
        sessionId = Hashing.sha256().hashString(mail+password+Math.random(), StandardCharsets.UTF_16).toString()
        addToDataBaseSession()
    }

    private fun addToDataBaseSession (){
        val sqlInsert = "INSERT INTO public.session values ('${sessionId}', ${this.userId});"
        statement.executeUpdate(sqlInsert)
    }
}