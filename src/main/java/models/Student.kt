package models

import java.util.*
import javax.ws.rs.Path
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@Path("students")
data class Student(val index: Int, val name: String, val surname: String, val birthday: Date, val grades: MutableList<Grade>)