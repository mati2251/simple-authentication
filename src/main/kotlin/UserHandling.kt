import java.io.FileInputStream
import java.io.IOException
import java.util.*


class UserHandling() {

    init {
        try {
            FileInputStream("path/to/config.properties").use { input ->
                val prop = Properties()
                prop.load(input);
                println(prop)
            }
        } catch (io: IOException) {
            io.printStackTrace()
        }

    }

    fun createUser(name: String, surname: String, mail: String, isAdmin: Boolean) {
        
    }

}