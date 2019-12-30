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
        server.createContext("/verification", ActivateUserHandler())
        println("SERVER STARTED")
        server.start()
    }
}