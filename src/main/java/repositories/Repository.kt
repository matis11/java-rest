package repositories

interface Repository {
    val grades: MutableList<Grade>
    val subjects: MutableList<Subject>
    val students: MutableList<Student>
}