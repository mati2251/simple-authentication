package database

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.lang.Exception

class DatabaseConnectionTests {

    @Test
    fun `database connection check `() {
        try {
            val databaseConnection = DatabaseConnection.connection
        }
        catch(e: Exception){
            fail(e)
        }
    }

}