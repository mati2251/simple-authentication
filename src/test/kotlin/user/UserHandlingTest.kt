package user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.util.*

class UserHandlingTest {


    @Test
    fun `fun how many user exist`(){
        Assertions.assertEquals(true, UserHandling.howManyUsersExist()>=0)
    }

    @Test
    fun `insert new user to database`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        UserHandling.createUser("Active your account", "Active link: ", prop["testMail"].toString() , true, "test")
    }

    @Test
    fun `delete new user`(){
        UserHandling.deleteUser(UserHandling.howManyUsersExist())
    }

}