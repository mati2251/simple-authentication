package server

import com.google.common.hash.Hashing
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import user.UserHandling
import java.io.OutputStream
import java.nio.charset.StandardCharsets

class LoginUserHandler : HttpHandler {
    override fun handle(httpHandler: HttpExchange?) {
        if (httpHandler != null) {
            val s = httpHandler.requestURI.query
            val values = s?.split("&")
            val userData = mutableMapOf<String, String>()
            values?.forEach { it ->
                val tmp = it.split("=")
                userData[tmp[0]] = tmp[1]
            }
            val charset = Charsets.UTF_8
            if (userData["mail"].isNullOrEmpty()) {
                val info = "TOO ENOUGH DATA"
                httpHandler.sendResponseHeaders(200, info.length.toLong())
                val os: OutputStream = httpHandler.responseBody
                os.write(info.toByteArray(charset))
                os.close()
            } else {
                val result = UserHandling.getUserDetails(userData["mail"].toString())
                if (!result.next()) {
                    val info = "BAD USER OR PASSWORD"
                    httpHandler.sendResponseHeaders(200, info.length.toLong())
                    val os: OutputStream = httpHandler.responseBody
                    os.write(info.toByteArray(charset))
                    os.close()
                } else if (!userData["password"].isNullOrEmpty()) {
                    val password = Hashing.sha256().hashString(userData["password"], StandardCharsets.UTF_16).toString()
                    result.next()
                    if (result.getString("password") == password) {
                        val info = "SUCCESS?"
                        httpHandler.sendResponseHeaders(200, info.length.toLong())
                        val os: OutputStream = httpHandler.responseBody
                        os.write(info.toByteArray(charset))
                        os.close()
                    } else {
                        val info = "BAD USER OR PASSWORD"
                        httpHandler.sendResponseHeaders(200, info.length.toLong())
                        val os: OutputStream = httpHandler.responseBody
                        os.write(info.toByteArray(charset))
                        os.close()
                    }
                }
            }
        }
    }

}