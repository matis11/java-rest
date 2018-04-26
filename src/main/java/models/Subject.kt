package models

import org.glassfish.jersey.linking.InjectLink
import org.glassfish.jersey.linking.InjectLinks
import javax.ws.rs.core.Link
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter


@XmlRootElement(name = "subject")
@XmlAccessorType(XmlAccessType.FIELD)
data class Subject(
        @field:XmlElement(name = "id")
        var id: String = java.util.UUID.randomUUID().toString(),
        @field:XmlElement(name = "name")
        val name: String = "",
        @field:XmlElement(name = "lecturer")
        val lecturer: String = ""
) {

    @field:InjectLinks(value = [InjectLink(resource = rest.SubjectEndpoint::class, rel = "parent"),
        InjectLink(value = "/subjects/{id}", rel = "self")])
    @field:XmlElement(name = "links")
    @field:XmlElementWrapper(name = "links")
    @field:XmlJavaTypeAdapter(Link.JaxbAdapter::class)
    lateinit var links: List<Link>
}