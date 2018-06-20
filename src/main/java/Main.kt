import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.linking.DeclarativeLinkingFeature
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import javax.ws.rs.ext.ContextResolver


object Main : ResourceConfig() {
    private const val BASE_URI = "http://localhost:9998/"

    private fun startServer(): HttpServer {
        val dateParamConverterProvider = DateParamConverterProvider("yyyy-MM-dd")

        val resourceConfig = ResourceConfig(rest.ListSubjectsEndpoint::class.java, rest.SubjectEndpoint::class.java,
                rest.GradeForStudentEndpoint::class.java, rest.ListGradesForStudentEndpoint::class.java,
                rest.ListStudentsEndpoint::class.java, rest.StudentEndpoint::class.java)
                .register(ContextResolver<ObjectMapper> { ObjectMapper().registerModule(KotlinModule()) })
                .register(DeclarativeLinkingFeature())
                .register(DeclarativeLinkingFeature::class.java).register(dateParamConverterProvider).register(CustomHeaders::class.java)
                .packages("rest")

        val l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler")
        l.level = Level.FINE
        l.useParentHandlers = false
        val ch = ConsoleHandler()
        ch.level = Level.ALL
        l.addHandler(ch)

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startServer()
    }

}
