package models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.glassfish.jersey.linking.InjectLink
import org.glassfish.jersey.linking.InjectLinks
import org.mongodb.morphia.annotations.*
import javax.ws.rs.core.Link
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter


@Entity("subject")
@Indexes(
        Index(value = "index", fields = [(Field("id"))])
)
@XmlRootElement(name = "subject")
@XmlAccessorType(XmlAccessType.FIELD)
data class Subject(
        @Id
        @JsonIgnore
        @field:XmlElement(name = "id")
        var id: ObjectId = ObjectId(),
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