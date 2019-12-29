package user

import database.DatabaseConnection
import java.sql.ResultSet

class UserHandling {

    fun createUser(name: String, surname: String, mail: String, isAdmin: Boolean) {
        val statement = DatabaseConnection.connection.createStatement()
        val sqlSelect = "SELECT count(\"id\") from public.users;"
        val howManyColumn :  ResultSet = statement.executeQuery(sqlSelect)
        var id : Int = 0;
        while (howManyColumn.next()){
            id = howManyColumn.getInt("id")
        }
        val sqlInsert = "INSERT INTO public.users values (${id.toString()}, '${surname}', '${name}', '${mail}', ${isAdmin}, false,'activation code');"
        statement.executeQuery(sqlInsert)
    }

}