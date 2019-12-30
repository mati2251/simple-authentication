import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.util.*

class MailPropsTest {

    private val prop = Properties()

    init {
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
    }

    @Test
    fun `mail properties exist`() {
        val file = File("src/main/resources/server.properties")
        val exists = file.exists()
        Assertions.assertEquals(true, exists)
    }

    @Test
    fun `mail prop`() {
        Assertions.assertEquals(true, prop["mail"].toString().isNotEmpty())
    }

    @Test
    fun `password prop`() {
        Assertions.assertEquals(true, prop["password"].toString().isNotEmpty())
    }

    @Test
    fun `hop`() {
        Assertions.assertEquals(true, prop["url"].toString().isNotEmpty())
    }

    @Test
    fun `check server properties port is exist`() {
        Assertions.assertEquals(true, prop["port"].toString().isNotEmpty())
    }

}