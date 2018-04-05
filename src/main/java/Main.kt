import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI


object Main : ResourceConfig() {
    private const val BASE_URI = "http://localhost:9998/"

    private fun startServer(): HttpServer {
        val resourceConfig = ResourceConfig().packages("rest")

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startServer()
    }

}
