package session

import com.google.common.hash.Hashing
import database.DatabaseConnection
import user.UserHandling
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Session(private var userId: Int) {
    val sessionId: String
    private val statement = DatabaseConnection.connection.createStatement()

    init {
        val array = ByteArray(7)
        val data = UserHandling.getUserDetails(userId);
        data.next()
        val mail = data.getString(4);
        val password = data.getString(7)
        sessionId = Hashing.sha256().hashString(mail + password + Math.random(), StandardCharsets.UTF_16).toString()
        addToDataBaseSession()
    }

    private fun addToDataBaseSession() {
        val sqlInsert = "INSERT INTO public.session values ('${sessionId}', ${this.userId});"
        val executor = Executors.newSingleThreadScheduledExecutor();
        val sqlDelete = "DELETE FROM public.session WHERE id=${sessionId}"
        executor.schedule(Callable {
            statement.executeUpdate(sqlDelete)
        }, 60, TimeUnit.MINUTES)
        statement.executeUpdate(sqlInsert)
    }
}