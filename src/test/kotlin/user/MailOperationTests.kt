package user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.FileInputStream
import java.lang.Exception
import java.util.*

class MailOperationTests {
    @Test
    fun `send mail`() {
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        try {
            MailOperation.sendActivationMail(prop["testMail"].toString(), "Test", "Test")
        }
        catch (e: Exception){
            fail(e)
        }
    }
}