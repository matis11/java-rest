package repositories

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import models.Grade
import models.Student
import models.Subject
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import java.time.Instant
import java.util.*


object MongoDBRepository : Repository {
    private lateinit var database: MongoDatabase
    private lateinit var datastore: Datastore

    override val grades: MutableList<Grade>
        get() = datastore.find(Grade::class.java)
                .retrieveKnownFields()
                .asList()

    override val subjects: MutableList<Subject>
        get() = datastore.find(Subject::class.java)
                .retrieveKnownFields()
                .asList()

    override val students: MutableList<Student>
        get() = datastore.find(Student::class.java)
                .retrieveKnownFields()
                .asList()

    fun saveStudent(student: Student) = datastore.save(student)

    fun saveSubject(subject: Subject) = datastore.save(subject)

    fun saveGrade(grade: Grade) = datastore.save(grade)

    fun deleteStudent(student: Student) = datastore.delete(student)

    fun deleteSubject(subject: Subject) = datastore.delete(subject)

    fun deleteGrade(grade: Grade) = datastore.delete(grade)


    init {
        try {
            val mongoClient = MongoClient("localhost", 27017)
            database = mongoClient.getDatabase("database")
            val morphia = Morphia()
            morphia.mapPackage("models")
            datastore = morphia.createDatastore(MongoClient(), "database")
            datastore.ensureIndexes()

            initMockedDatabase()

        } catch (e: Exception) {
            println("Connection to mongodb@localhost:27017 cannot be estabilished")
            println(e)
        }
    }

    private fun initMockedDatabase() {
        database.drop()

        val mockedSubjects = mutableListOf(
                Subject(name = "TPAL", lecturer = "T.Pawlak"),
                Subject(name = "Metody Probabilistyczne", lecturer = "J.Carbon")
        )

        val mockedGrades = mutableListOf(
                Grade(subject = mockedSubjects[0], value = 5.0F, creationDate = Date.from(Instant.now())),
                Grade(subject = mockedSubjects[1], value = 2.0F, creationDate = Date.from(Instant.now()))
        )

        val mockedStudents = mutableListOf(
                Student(name = "Mateusz", surname = "Bartos", birthday = Date.from(Instant.now()), grades = mockedGrades),
                Student(name = "Bartosz", surname = "Mat", birthday = Date.from(Instant.now()), grades = mockedGrades)
        )

        datastore.save(mockedSubjects)
        datastore.save(mockedStudents)
    }

}