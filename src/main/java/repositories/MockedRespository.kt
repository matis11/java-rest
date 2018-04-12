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

    private val grades = mutableListOf(
            Grade(subject = subjects[0], value = 5.0F, creationDate = Date.from(Instant.now())),
            Grade(subject = subjects[1], value = 2.0F, creationDate = Date.from(Instant.now()))
    )

    val students = mutableListOf(
            Student(name = "Mateusz", surname = "Bartos", birthday = Date.from(Instant.now()), grades = grades),
            Student(name = "Bartosz", surname = "Mat", birthday = Date.from(Instant.now()), grades = grades)
    )

}