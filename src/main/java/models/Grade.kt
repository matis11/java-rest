package models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.*
import java.time.Instant
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@Entity("grade")
@Indexes(
        Index(value = "index", fields = [(Field("id"))])
)
@XmlRootElement(name = "grade")
@XmlAccessorType(XmlAccessType.FIELD)
data class Grade(
        @Id
        @JsonIgnore
        @field:XmlElement(name = "id")
        @XmlJavaTypeAdapter(ObjectIdJaxbAdapter::class)
        var id: ObjectId = ObjectId(),
        @Reference
        @field:XmlElement(name = "subject")
        val subject: Subject? = null,
        @field:XmlElement(name = "value")
        var value: Float = 0.0F,
        @field:XmlElement(name = "creationDate")
        val creationDate: Date = Date.from(Instant.now())
) {
    init {
        when (value) {
            2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f -> print("Valid grade")
            else -> value = 2.0f
        }
    }
}