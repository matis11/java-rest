package models

import java.time.Instant
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement(name = "grade")
@XmlAccessorType(XmlAccessType.FIELD)
data class Grade(
        @field:XmlElement(name = "id")
        var id: String = java.util.UUID.randomUUID().toString(),
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