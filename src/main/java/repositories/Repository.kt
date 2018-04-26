package repositories

import models.Grade
import models.Student
import models.Subject

interface Repository {
    val grades: MutableList<Grade>
    val subjects: MutableList<Subject>
    val students: MutableList<Student>
}