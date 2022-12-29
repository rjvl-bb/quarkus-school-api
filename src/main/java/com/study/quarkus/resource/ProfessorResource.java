package com.study.quarkus.resource;

import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.service.StudentService;
import com.study.quarkus.service.ClassService;
import com.study.quarkus.service.ProfessorService;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/professors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProfessorResource {
    private final ProfessorService service;

    private final ClassService classService;

    private final StudentService studentService;

    @GET
    public Response listProfessors() {
        return Response.ok(service.listProfessors())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") int professorId) {
        return Response.ok(service.getProfessor(professorId))
                .build();
    }

    @POST
    public Response saveProfessor(final ProfessorRequest request) {
        System.out.println(request);
        try {
            System.out.println("1");
            final var response = service.saveProfessor(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch (ConstraintViolationException e) {
            System.out.println("2");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProfessor(@PathParam("id") int professorId, ProfessorRequest request) {
        try {
            var response = service.updateProfessor(professorId, request);
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
    public Response deleteProfessor(@PathParam("id") int professorId) {
        service.deleteProfessor(professorId);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/{id}/class")
    public Response getClass(@PathParam("id") int id) {
        final var response = classService.getClassByProfessorId(id);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}/tutorados")
    public Response getTutorados(@PathParam("id") int id) {
        final var response = studentService.getTutoredByProfessorId(id);
        return Response.ok(response).build();
    }
}