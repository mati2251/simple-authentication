package user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.util.*

class UserHandlingTest {

    private val prop = Properties()

    init {
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
    }

    @Test
    fun `fun how many user exist`() {
        Assertions.assertEquals(true, UserHandling.howManyUsersExist() >= 0)
    }

    @Test
    fun `insert new user to database`() {

        UserHandling.createUser("Active your account", "Active link: ", prop["testMail"].toString(), true, "test")
    }

    @Test
    fun `user details`() {
        val r = UserHandling.getUserDetails(prop["testMail"].toString())
        Assertions.assertEquals(true, r.next())
    }

    @Test
    fun `activate user`() {
        var r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.next()
        UserHandling.activateUser(r.getString("verification_key"))
        r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.next()
        Assertions.assertEquals(true, r.getBoolean("is_active"))
    }

    @Test
    fun `delete new user`() {
        UserHandling.deleteUser(UserHandling.howManyUsersExist())
    }

}