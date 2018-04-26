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

    init {
        try {
            val mongoClient = MongoClient("localhost", 27017)
            database = mongoClient.getDatabase("database")
            val morphia = Morphia()
            morphia.mapPackage("models")
            datastore = morphia.createDatastore(MongoClient(), "database")
            datastore.ensureIndexes()

        } catch (e: Exception) {
            println("Connection to mongodb@localhost:27017 cannot be estabilished")
        }
    }

    override val subjects = mutableListOf(
            Subject(name = "TPAL", lecturer = "T.Pawlak"),
            Subject(name = "Metody Probabilistyczne", lecturer = "J.Carbon")
    )

    override val grades = mutableListOf(
            Grade(subject = subjects[0], value = 5.0F, creationDate = Date.from(Instant.now())),
            Grade(subject = subjects[1], value = 2.0F, creationDate = Date.from(Instant.now()))
    )

    override val students = mutableListOf(
            Student(name = "Mateusz", surname = "Bartos", birthday = Date.from(Instant.now()), grades = grades),
            Student(name = "Bartosz", surname = "Mat", birthday = Date.from(Instant.now()), grades = grades)
    )

}