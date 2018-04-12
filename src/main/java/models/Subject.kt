package models

import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement
data class Subject(val id: String = java.util.UUID.randomUUID().toString(), val name: String, val lecturer: String)