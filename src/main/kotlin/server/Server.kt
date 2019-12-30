package server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import user.UserHandling
import java.io.OutputStream
import java.lang.Exception
import java.net.InetSocketAddress


class Server {

    private val server: HttpServer = HttpServer.create(InetSocketAddress(8000), 0)

    init {
        server.createContext("/createUser", CreateUserHandler())
        println("SERVER STARTED")
        server.start()
    }

    inner class CreateUserHandler : HttpHandler {
        override fun handle(httpHandler: HttpExchange?) {
            if (httpHandler != null) {
                val s = httpHandler.requestURI.query
                val values = s?.split("&")
                val newUserData = mutableMapOf<String, String>()
                values?.forEach { it ->
                    val tmp = it.split("=")
                    newUserData[tmp[0]] = tmp[1]
                }
                val charset = Charsets.UTF_8
                if (newUserData["name"].isNullOrEmpty() || newUserData["surname"].isNullOrEmpty() || newUserData["password"].isNullOrEmpty() || newUserData["mail"].isNullOrEmpty() || newUserData["isAdmin"].isNullOrEmpty()) {
                    val info = "TOO NOT ENOUGH DATA"
                    httpHandler.sendResponseHeaders(200, info.length.toLong())
                    val os: OutputStream = httpHandler.responseBody
                    os.write(info.toByteArray(charset))
                    os.close()
                } else {
                    try {
                        UserHandling.createUser(
                            name = newUserData["name"].toString(),
                            mail = newUserData["mail"].toString(),
                            surname = newUserData["mail"].toString(),
                            password = newUserData["password"].toString(),
                            isAdmin = newUserData["isAdmin"]?.toBoolean()
                        )
                        val info = "CREATE USER SUCCESSFUL"
                        httpHandler.sendResponseHeaders(200, info.length.toLong())
                        val os: OutputStream = httpHandler.responseBody
                        os.write(info.toByteArray(charset))
                        os.close()
                    } catch (e: Exception) {
                        val mess = e.message?.slice(IntRange(0, 20))
                        if (mess == "ERROR: duplicate key ") {
                            val info = "MAIL IS EXISTS"
                            httpHandler.sendResponseHeaders(200, info.length.toLong())
                            val os: OutputStream = httpHandler.responseBody
                            os.write(info.toByteArray(charset))
                            os.close()
                        } else {
                            val info = "SERVICE IS UNAVAILABLE"
                            httpHandler.sendResponseHeaders(503, info.length.toLong())
                            val os: OutputStream = httpHandler.responseBody
                            os.write(info.toByteArray(charset))
                            os.close()
                        }
                    }

                }

            }
        }
    }
}