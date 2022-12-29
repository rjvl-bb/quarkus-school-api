package com.study.quarkus.resource;

import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.dto.StudentRequest;
import com.study.quarkus.service.StudentService;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class StudentResource {
    private final StudentService service;

    @GET
    public Response listStudents() {
        return Response.ok(service.listStudents())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") int studentId) {
        return Response.ok(service.getStudent(studentId))
                .build();
    }

    @POST
    public Response saveStudent(final StudentRequest request) {
        try {
            final var response = service.saveStudent(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") int studentId, StudentRequest request) {
        try {
            var response = service.updateStudent(studentId, request);
            return Response
                    .ok(response)
                    .build();
        } catch (ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") int studentId) {
        service.deleteStudent(studentId);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @PATCH
    @Path("/{id}/tutor/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idStudent, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTutor(idStudent, idProfessor);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}