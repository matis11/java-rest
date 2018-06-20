import java.io.IOException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter

class CustomHeaders : ContainerResponseFilter {
    @Throws(IOException::class)
    override fun filter(containerRequestContext: ContainerRequestContext, containerResponseContext: ContainerResponseContext) {
        containerResponseContext.headers.add("Access-Control-Allow-Origin", "*")
        containerResponseContext.headers.add("Access-Control-Allow-Headers", "Content-Type")
        containerResponseContext.headers.add("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE")
        containerResponseContext.headers.add("Access-Control-Expose-Headers", "Location")
    }
}