package models

import java.util.*
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
data class Grade(val id: String = java.util.UUID.randomUUID().toString(), val subject: Subject, val value: Float, val creationDate: Date)