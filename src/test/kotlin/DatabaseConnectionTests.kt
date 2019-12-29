import database.DatabaseConnection
import org.junit.jupiter.api.Test

class DatabaseConnectionTests {

    @Test
    fun `database connection check `(){
        val databaseConnection = DatabaseConnection.connection
    }

}