package repositories

import models.Grade
import models.Student
import models.Subject
import java.time.Instant
import java.util.*

object MockedRespository {
    val subjects = mutableListOf(
            Subject(name = "TPAL", lecturer = "T.Pawlak"),
            Subject(name = "Metody Probabilistyczne", lecturer = "J.Carbon")
    )

    val grades = mutableListOf(
            Grade(subject = subjects[0], value = 5.0F, creationDate = Date.from(Instant.now())),
            Grade(subject = subjects[1], value = 2.0F, creationDate = Date.from(Instant.now()))
    )

    val students = mutableListOf(
            Student(122437, "Mateusz", "Bartos", Date.from(Instant.now()), grades),
            Student(437122, "Bartosz", "Mat", Date.from(Instant.now()), grades)
    )

}