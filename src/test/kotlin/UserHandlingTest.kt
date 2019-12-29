import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import user.UserHandling

class UserHandlingTest {

    private val userHandling = UserHandling();

    @Test
    fun `check fun how many user exist`(){
        Assertions.assertEquals(true, userHandling.howManyUsersExist()>=0)
    }

    @Test
    fun `insert new user to database`(){
        userHandling.createUser("Test", "Test", "test@test.com", true, "test")
    }

    @Test
    fun `delete new user`(){
        userHandling.deleteUser(userHandling.howManyUsersExist())
    }

}