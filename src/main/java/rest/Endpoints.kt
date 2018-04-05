package rest

import models.Grade
import models.Student
import models.Subject
import repositories.MockedRespository
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
                .firstOrNull { it.index.toString() == index }
                ?.grades
                ?.add(grade)

        val message = "Grade ${grade.value} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
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
}

@Path("subjects")
class ListSubjectsEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("index") index: String): Response {
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
        val student = MockedRespository.subjects
                .firstOrNull { it.id == id }




        val message = "Subject ${subject.name} has been added"
        return Response.status(Response.Status.CREATED).entity(message).build()
    }
}