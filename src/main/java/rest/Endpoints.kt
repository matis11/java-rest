package rest

import models.Grade
import models.Student
import models.Subject
import org.bson.types.ObjectId
import repositories.MongoDBRepository
import java.net.URI
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("students")
class ListStudentsEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Response? {
        val students = repository.students
        return Response.status(if (students == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(students).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(student: Student): Response {
        repository.saveStudent(student)

        val message = "Student ${student.name} ${student.surname} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
    }
}

@Path("students/{index}")
class StudentEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String): Response {
        val student = repository.students
                .firstOrNull { it.index == index }

        return Response.status(if (student == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(student).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("index") index: String, student: Student): Response {
        val students = repository.students
        val oldStudent = students
                .firstOrNull { it.index == index }

        if (oldStudent == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val i = students.indexOf(oldStudent)
        student.index = index
        repository.saveStudent(student)

        val message = "Student ${student.name} ${student.surname} has been updated"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("index") index: String): Response {
        val students = repository.students
        val student = students
                .firstOrNull { it.index == index }

        if (student == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }


        repository.deleteStudent(student)

        val message = "Student ${student?.name} ${student?.surname} has been removed"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }
}

@Path("students/{index}/grades")
class ListGradesForStudentEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String): Response {
        val grades = repository.students
                .firstOrNull { it.index == index }
                ?.grades

        return Response.status(if (grades == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(grades).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(grade: Grade, @PathParam("index") index: String): Response {

        repository.saveGrade(grade)

        val message = "Grade ${grade.value} has been added"
        return Response.created(URI.create("/students/$index")).build()
    }
}

@Path("students/{index}/grades/{id}")
class GradeForStudentEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String, @PathParam("id") id: String): Response {
        val grade = repository.students
                .firstOrNull { it.index == index }
                ?.grades
                ?.firstOrNull { it.id.toString() == id }

        return Response.status(if (grade == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(grade).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("index") index: String, @PathParam("id") id: String, grade: Grade): Response {
        val grades = repository.students
                .firstOrNull { it.index == index }
                ?.grades

        if (grades == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val oldGrade = grades
                .firstOrNull { it.id.toString() == id }

        if (oldGrade == null) {
            val message = "Grade $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val i = grades.indexOf(oldGrade)
        grade.id = ObjectId(id)
        repository.saveGrade(grade)

        val message = "Grade ${grade.value} has been updated"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("index") index: String, @PathParam("id") id: String): Response {
        val grades = repository.students
                .firstOrNull { it.index == index }
                ?.grades

        if (grades == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val grade = grades
                .firstOrNull { it.id.toString() == id }

        if (grade == null) {
            val message = "Grade $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        repository.deleteGrade(grade)

        val message = "Grade ${grade.value} has been removed"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }
}

@Path("subjects")
class ListSubjectsEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON )
    fun get(): Response {
        val subjects = repository.subjects

        return Response.status(if (subjects == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(subjects).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(subject: Subject): Response {
        repository.saveSubject(subject)

        val message = "Subject ${subject.name} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
    }
}

@Path("subjects/{id}")
class SubjectEndpoint {

    private val repository = MongoDBRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("id") id: String): Response {
        val subject = repository.subjects
                .firstOrNull { it.id.toString() == id }

        return Response.status(if (subject == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(subject).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("id") id: String, subject: Subject): Response {
        val subjects = repository.subjects
        val oldSubject = subjects
                .firstOrNull { it.id.toString() == id }

        if (oldSubject == null) {
            val message = "Subject ${subject.name} not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val index = subjects.indexOf(oldSubject)
        subject.id = ObjectId(id)
        repository.saveSubject(subject)

        val message = "Subject ${subject.name} has been updated"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") id: String): Response {
        val subjects = repository.subjects
        val subject = subjects
                .firstOrNull { it.id.toString() == id }

        if (subject == null) {
            val message = "Subject with id $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        repository.deleteSubject(subject)

        val message = "Subject ${subject.name} has been removed"
        return Response.status(Response.Status.NO_CONTENT).entity(message).build()
    }
}