package user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterAll;
import java.io.FileInputStream
import java.util.*

class UserHandlingTest {

    private val prop = Properties()

    init {
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
    }

    @Order (1)
    @Test
    fun `fun how many user exist`() {
        Assertions.assertEquals(true, UserHandling.howManyUsersExist() >= 0)
    }

    @Order (1)
    @Test
    fun `insert new user to database`() {

        UserHandling.createUser("Active your account", "Active link: ", prop["testMail"].toString(), true, "test")
    }

    @Order (1)
    @Test
    fun `user details`() {
        val r = UserHandling.getUserDetails(prop["testMail"].toString())
        Assertions.assertEquals(true, r.next())
    }

    @Order (1)
    @Test
    fun `activate user`() {
        var r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.next()
        UserHandling.activateUser(r.getString("verification_key"))
        r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.next()
        Assertions.assertEquals(true, r.getBoolean("is_active"))
    }

    @Order (2)
    @Test
    fun `delete new user`() {
        UserHandling.deleteUser(UserHandling.howManyUsersExist())
    }

    @AfterAll
    fun clean (){
        UserHandling.deleteUser(UserHandling.howManyUsersExist())
    }

}