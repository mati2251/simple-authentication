import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.util.*

class ServerPropsTest {

    private val prop = Properties()

    init {
        FileInputStream("src/main/resources/server.properties").use { input ->
            prop.load(input)
        }
    }

    @Test
    fun `server properties exist`() {
        val file = File("src/main/resources/server.properties")
        val exists = file.exists()
        Assertions.assertEquals(true, exists)
    }

    @Test
    fun `url prop`() {
        Assertions.assertEquals(true, prop["db.url"].toString().isNotEmpty())
    }

    @Test
    fun `user properties`() {
        Assertions.assertEquals(true, prop["user"].toString().isNotEmpty())
    }

    @Test
    fun `port properties`() {
        Assertions.assertEquals(true, prop["db.port"].toString().isNotEmpty())
    }

    @Test
    fun `password properties`() {
        Assertions.assertEquals(true, prop["password"].toString().isNotEmpty())
    }

    @Test
    fun `ssl properties`() {
        Assertions.assertEquals(true, prop["ssl"].toString().isNotEmpty())
    }

}