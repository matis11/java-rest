package rest

import models.Grade
import models.Student
import models.Subject
import repositories.MockedRespository
import java.net.URI
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("students")
class ListStudentsEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Response? {
        val students = MockedRespository.students
        return Response.status(if (students == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(students).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(student: Student): Response {
        MockedRespository.students
                .add(student)

        val message = "Student ${student.name} ${student.surname} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
    }
}

@Path("students/{index}")
class StudentEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String): Response {
        val student = MockedRespository.students
                .firstOrNull { it.index.toString() == index }

        return Response.status(if (student == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(student).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("index") index: String, student: Student): Response {
        val students = MockedRespository.students
        val oldStudent = students
                .firstOrNull { it.index == index }

        if (oldStudent == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val i = students.indexOf(oldStudent)
        student.index = index
        students[i] = student

        val message = "Student ${student.name} ${student.surname} has been updated"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("index") index: String): Response {
        val students = MockedRespository.students
        val student = students
                .firstOrNull { it.index == index }

        if (student == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }


        students.remove(student)

        val message = "Student ${student?.name} ${student?.surname} has been removed"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }
}

@Path("students/{index}/grades")
class ListGradesForStudentEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String): Response {
        val grades = MockedRespository.students
                .firstOrNull { it.index.toString() == index }
                ?.grades

        return Response.status(if (grades == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(grades).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(grade: Grade, @PathParam("index") index: String): Response {
        MockedRespository.students
                .firstOrNull { it.index == index }
                ?.grades
                ?.add(grade)

        val message = "Grade ${grade.value} has been added"
        return Response.created(URI.create("/students/$index")).build()
    }
}

@Path("students/{index}/grades/{id}")
class GradeForStudentEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String, @PathParam("id") id: String): Response {
        val grade = MockedRespository.students
                .firstOrNull { it.index.toString() == index }
                ?.grades
                ?.firstOrNull { it.id == id }

        return Response.status(if (grade == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(grade).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("index") index: String, @PathParam("id") id: String, grade: Grade): Response {
        val grades = MockedRespository.students
                .firstOrNull { it.index == index }
                ?.grades

        if (grades == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val oldGrade = grades
                .firstOrNull { it.id == id }

        if (oldGrade == null) {
            val message = "Grade $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val i = grades.indexOf(oldGrade)
        grade.id = id
        grades[i] = grade

        val message = "Grade ${grade.value} has been updated"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("index") index: String, @PathParam("id") id: String): Response {
        val grades = MockedRespository.students
                .firstOrNull { it.index == index }
                ?.grades

        if (grades == null) {
            val message = "Student with index $index not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val grade = grades
                .firstOrNull { it.id == id }

        if (grade == null) {
            val message = "Grade $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        grades.remove(grade)

        val message = "Grade ${grade.value} has been removed"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }
}

@Path("subjects")
class ListSubjectsEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON )
    fun get(): Response {
        val subjects = MockedRespository.subjects

        return Response.status(if (subjects == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(subjects).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(subject: Subject): Response {
        MockedRespository.subjects.add(subject)

        val message = "Subject ${subject.name} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
    }
}

@Path("subjects/{id}")
class SubjectEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("id") id: String): Response {
        val subject = MockedRespository.subjects
                .firstOrNull { it.id == id }

        return Response.status(if (subject == null) Response.Status.NOT_FOUND else Response.Status.OK).entity(subject).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun put(@PathParam("id") id: String, subject: Subject): Response {
        val subjects = MockedRespository.subjects
        val oldSubject = subjects
                .firstOrNull { it.id == id }

        if (oldSubject == null) {
            val message = "Subject ${subject.name} not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        val index = subjects.indexOf(oldSubject)
        subject.id = id
        subjects[index] = subject

        val message = "Subject ${subject.name} has been updated"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun delete(@PathParam("id") id: String): Response {
        val subjects = MockedRespository.subjects
        val subject = subjects
                .firstOrNull { it.id == id }

        if (subject == null) {
            val message = "Subject with id $id not found"
            return Response.status(Response.Status.NOT_FOUND).entity(message).build()
        }

        subjects.remove(subject)

        val message = "Subject ${subject.name} has been removed"
        return Response.status(Response.Status.ACCEPTED).entity(message).build()
    }
}