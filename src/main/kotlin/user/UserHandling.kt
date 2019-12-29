package user

import com.google.common.hash.Hashing
import database.DatabaseConnection
import java.nio.charset.StandardCharsets
import java.sql.ResultSet
import javax.mail.Session
import javax.mail.internet.MimeMessage


class UserHandling {

    private val statement = DatabaseConnection.connection.createStatement()

    fun createUser(name: String, surname: String, mail: String, isAdmin: Boolean, password: String) {
        val id = howManyUsersExist()
        val hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_16).toString()
        val activationKey = Hashing.sha256().hashString(mail, StandardCharsets.UTF_16).toString()
        val sqlInsert = "INSERT INTO public.users values (${(id+1)}, '${surname}', '${name}', '${mail}', ${isAdmin}, false,'${activationKey}', '${hashedPassword}');"
        statement.executeUpdate(sqlInsert)
    }

    fun deleteUser(id: Int){
        val statement = DatabaseConnection.connection.createStatement()
        val sqlDelete = "DELETE FROM public.users WHERE id=${id}"
        statement.executeUpdate(sqlDelete)
    }

    fun howManyUsersExist (): Int {
        val sqlSelect = "SELECT count(\"id\") from public.users;"
        val howManyColumn :  ResultSet = statement.executeQuery(sqlSelect)
        while (howManyColumn.next()){
            return howManyColumn.getInt("count")
        }
        return 0
    }

    fun sendActivationMail (to: String) {
        val from = "mateusz.karlowski@zoho.com"
        val properties = System.getProperties()
        properties.setProperty("mail.smtp.host", "localhost")
        val session = Session.getDefaultInstance(properties)
        try{
            val message = MimeMessage(session)
        }
    }
}