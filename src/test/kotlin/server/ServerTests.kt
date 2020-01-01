package server

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import user.UserHandling
import java.io.FileInputStream
import java.net.URL
import java.util.*


class ServerTests{
    @Test
    @Order(1)
    fun `create user request`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        val url = URL("http://localhost:8000/createUser?name=test&password=test&mail=${prop["testMail"]}&surname=test&isAdmin=true")
        val httpPost = HttpPost(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpPost).use { response -> println(EntityUtils.toString(response.entity)) }
        }
    }

    @Test
    @Order(2)
    fun `active user`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
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
    @Order(3)
    fun `login user`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        val url = URL("http://localhost:8000/login?mail=${prop["testMail"]}&password=test")
        println(url)
        val httpGet = HttpGet(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpGet).use { response -> println(EntityUtils.toString(response.entity)) }
        }
    }

}