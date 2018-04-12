import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import javax.ws.rs.ext.ContextResolver


object Main : ResourceConfig() {
    private const val BASE_URI = "http://localhost:9998/"

    private fun startServer(): HttpServer {
        val resourceConfig = ResourceConfig()
                .register(ContextResolver<ObjectMapper> { ObjectMapper().registerModule(KotlinModule()) })
                .packages("rest")

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startServer()
    }

}
