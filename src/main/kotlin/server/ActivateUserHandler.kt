package server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import user.UserHandling
import java.io.OutputStream

class ActivateUserHandler : HttpHandler {
    override fun handle(httpHandler: HttpExchange?) {
        val charset = Charsets.UTF_8
        if (httpHandler != null) {
            val s = httpHandler.requestURI.query
            val values = s?.split("&")
            var key : String = ""
            values?.forEach { it ->
                val tmp = it.split("=")
                if (tmp[0] == "key") {
                    key = tmp[1]
                }
            }
            UserHandling.activateUser(key)
            val info = "ACTIVATION USER SUCCESSFUL"
            httpHandler.sendResponseHeaders(200, info.length.toLong())
            val os: OutputStream = httpHandler.responseBody
            os.write(info.toByteArray(charset))
            os.close()
        }
    }
}