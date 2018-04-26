package models

import org.bson.types.ObjectId
import org.glassfish.jersey.linking.InjectLink
import org.glassfish.jersey.linking.InjectLinks
import org.mongodb.morphia.annotations.*
import rest.GradeForStudentEndpoint
import rest.StudentEndpoint
import java.util.*
import javax.ws.rs.core.Link
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@Entity("student")
@Indexes(
        Index(value = "index", fields = [Field("id")])
)
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
data class Student(
        @Id
        @field:XmlElement(name = "id")
        @XmlTransient
        var id: ObjectId = ObjectId(),
        @field:XmlElement(name = "index")
        var index: String = java.util.UUID.randomUUID().toString(),
        @field:XmlElement(name = "name")
        val name: String,
        @field:XmlElement(name = "surname")
        val surname: String,
        @field:XmlElement(name = "birthday")
        val birthday: Date,
        @field:XmlElement(name = "grades")
        val grades: MutableList<Grade>
) {
    @field:InjectLinks(value = [(InjectLink(resource = StudentEndpoint::class, rel = "parent")),
        (InjectLink(value = "/students/{index}", rel = "self")),
        InjectLink(resource = GradeForStudentEndpoint::class, rel = "grades")])
    @field:XmlElement(name = "links")
    @field:XmlElementWrapper(name = "links")
    @field:XmlJavaTypeAdapter(Link.JaxbAdapter::class)
    lateinit var links: List<Link>
}