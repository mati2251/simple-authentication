package user

import com.google.common.hash.Hashing
import database.DatabaseConnection
import java.nio.charset.StandardCharsets
import java.sql.ResultSet


object UserHandling {

    private val statement = DatabaseConnection.connection.createStatement()

    fun createUser(name: String, surname: String, mail: String, isAdmin: Boolean?, password: String) {
        val id = howManyUsersExist()
        val hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_16).toString()
        val activationKey = Hashing.sha256().hashString(mail, StandardCharsets.UTF_16).toString()
        val sqlInsert = "INSERT INTO public.users values (${(id+1)}, '${surname}', '${name}', '${mail}', ${isAdmin}, false,'${activationKey}', '${hashedPassword}');"
        try {
            statement.executeUpdate(sqlInsert)
            MailOperation.sendActivationMail(mail, activationKey)
            println("USER CREATED")
        }
        catch (exc: Exception){
            throw exc
        }
    }

    fun deleteUser(mail: String){
        val sqlDelete = "DELETE FROM public.users WHERE mail=${mail}"
        statement.executeUpdate(sqlDelete)
    }

    fun deleteUser(id: Int){
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

    fun activateUser(key: String){
        val sqlUpdate = "UPDATE public.users SET is_active=true WHERE verification_key = '$key'"
        statement.executeUpdate(sqlUpdate)
        println("USER ACTIVATED")
    }

    fun getUserDetails(mail: String) : ResultSet{
        val sqlSelect = "SELECT * from public.users WHERE mail = '$mail';"
        return statement.executeQuery(sqlSelect)
    }

    fun getUserDetails(userId: Int) : ResultSet{
        val sqlSelect = "SELECT * from public.users WHERE id = '$userId';"
        return statement.executeQuery(sqlSelect)
    }
}