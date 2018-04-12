package models

import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement
data class Subject(
        var id: String = java.util.UUID.randomUUID().toString(),
        val name: String,
        val lecturer: String
)