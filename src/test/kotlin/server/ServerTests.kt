package server

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import user.UserHandling
import java.io.FileInputStream
import java.net.URL
import java.util.*

@TestMethodOrder(OrderAnnotation::class)
class ServerTests {

    val prop = Properties()

    init {
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
    }


    @Test
    @Order(1)
    fun `create user request`() {
        val url =
            URL("http://localhost:8000/createUser?name=test&password=test&mail=${prop["testMail"]}&surname=test&isAdmin=true")
        val httpPost = HttpPost(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpPost).use { response -> println(EntityUtils.toString(response.entity)) }
        }
        val r = UserHandling.getUserDetails("${prop["testMail"]}")
        Assertions.assertEquals(true, r.next())
    }


    @Test
    @Order(2)
    fun `login user without activation`() {
        val url = URL("http://localhost:8000/login?mail=${prop["testMail"]}&password=test")
        println(url)
        val httpGet = HttpGet(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpGet).use { response ->
                val tmp = EntityUtils.toString(response.entity)
                Assertions.assertEquals(tmp.slice(IntRange(0, 2)), "BAD")
                println(tmp)
            }
        }
    }

    @Test
    @Order(3)
    fun `active user`() {
        var r = UserHandling.getUserDetails("${prop["testMail"]}")
        r.next()
        val url = URL("http://localhost:8000/verification?key=${r.getString("verification_key")}")
        val httpPost = HttpPost(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpPost).use { response -> println(EntityUtils.toString(response.entity)) }
        }
        r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.next()
        Assertions.assertEquals(true, r.getBoolean("is_active"))
    }

    @Test
    @Order(4)
    fun `login user`() {
        val url = URL("http://localhost:8000/login?mail=${prop["testMail"]}&password=test")
        println(url)
        val httpGet = HttpGet(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpGet).use { response ->
                val tmp = EntityUtils.toString(response.entity)
                Assertions.assertEquals(tmp.slice(IntRange(0, 6)), "SUCCESS")
                println(tmp)
            }
        }
    }

    @Test
    @Order(5)
    fun `login user with bad password`() {
        val url = URL("http://localhost:8000/login?mail=${prop["testMail"]}&password=bad")
        println(url)
        val httpGet = HttpGet(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpGet).use { response ->
                val tmp = EntityUtils.toString(response.entity)
                Assertions.assertEquals(tmp.slice(IntRange(0, 2)), "BAD")
                println(tmp)
            }
        }
    }

    @Test
    @Order(6)
    fun `login user with bad mail`() {
        val url = URL("http://localhost:8000/login?mail=test@test.test&password=bad")
        println(url)
        val httpGet = HttpGet(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpGet).use { response ->
                val tmp = EntityUtils.toString(response.entity)
                Assertions.assertEquals(tmp.slice(IntRange(0, 2)), "BAD")
                println(tmp)
            }
        }
    }

    @AfterAll
    fun clean (){
        UserHandling.deleteUser(UserHandling.howManyUsersExist())
    }
}