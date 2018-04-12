package models

import java.time.Instant
import java.util.*
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
data class Grade(
        var id: String = java.util.UUID.randomUUID().toString(),
        val subject: Subject,
        var value: Float,
        val creationDate: Date = Date.from(Instant.now())
) {
    init {
        when (value) {
            2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f -> print("Valid grade")
            else -> value = 2.0f
        }
    }
}