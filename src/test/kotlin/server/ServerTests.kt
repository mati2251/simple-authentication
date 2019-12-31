package server

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import user.UserHandling
import java.io.FileInputStream
import java.net.URL
import java.util.*


class ServerTests{
    @Test
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
    fun `active user`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        var r = UserHandling.getUserDetails(prop["testMail"].toString())
        r.first()
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
    fun `create second the same user request`(){
        val prop = Properties()
        FileInputStream("src/main/resources/mail.properties").use { input ->
            prop.load(input)
        }
        val url = URL("http://localhost:8000/createUser?name=test&password=test&mail=${prop["testMail"]}&surname=test&isAdmin=true")
        val httpPost = HttpPost(url.toURI())
        HttpClients.createDefault().use { httpClient ->
            httpClient.execute(httpPost).use { response -> println(EntityUtils.toString(response.entity)) }
        }
        val r = UserHandling.getUserDetails("${prop["testMail"]}")
        r.next()
        UserHandling.deleteUser(r.getInt("id"))
    }

}