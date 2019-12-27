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
    fun `check server properties exist`() {
        val file = File("src/main/resources/server.properties")
        val exists = file.exists()
        Assertions.assertEquals(true, exists)
    }

    @Test
    fun `check server properties url is exist`() {
        Assertions.assertEquals(true, prop["db.url"].toString().isNotEmpty())
    }

    @Test
    fun `check server properties user is exist`() {
        Assertions.assertEquals(true, prop["user"].toString().isNotEmpty())
    }

    @Test
    fun `check server properties password is exist`() {
        Assertions.assertEquals(true, prop["db.port"].toString().isNotEmpty())
    }

    @Test
    fun `check server properties port is exist`() {
        Assertions.assertEquals(true, prop["password"].toString().isNotEmpty())
    }

    @Test
    fun `check server properties ssl is exist`() {
        Assertions.assertEquals(true, prop["ssl"].toString().isNotEmpty())
    }

}