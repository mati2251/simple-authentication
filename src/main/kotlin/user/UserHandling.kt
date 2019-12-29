package user

import database.DatabaseConnection
import java.sql.ResultSet

class UserHandling (){

    private val statement = DatabaseConnection.connection.createStatement()

    fun createUser(name: String, surname: String, mail: String, isAdmin: Boolean) {
        val id = howManyUsersExist()
        val sqlInsert = "INSERT INTO public.users values (${(id+1).toString()}, '${surname}', '${name}', '${mail}', ${isAdmin}, false,'activation code');"
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
}