package server

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress


class Server {

    private val server: HttpServer = HttpServer.create(InetSocketAddress(8000), 0)

    init {
        server.createContext("/createUser", CreateUserHandler())
        server.createContext("/verification", ActivateUserHandler())
        server.createContext("/login", LoginUserHandler())
        println("SERVER STARTED")
        server.start()
    }
}