package com.study.quarkus.resource;

import com.study.quarkus.dto.ErrorResponse;
import com.study.quarkus.dto.ClassRequest;
import com.study.quarkus.service.ClassService;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/classes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ClassResource {
    private final ClassService service;

    @GET
    public Response listClasses() {
        return Response.ok(service.listClasses())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getClass(@PathParam("id") int classId) {
        return Response.ok(service.getClass(classId))
                .build();
    }

    @POST
    public Response saveClass(ClassRequest request) {
        try {
            final var response = service.saveClass(request);
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
    public Response updateClass(@PathParam("id") int classId, ClassRequest request) {
        return Response.ok(service.updateClass(classId, request))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteClass(@PathParam("id") int classId) {
        service.deleteClass(classId);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @PATCH
    @Path("/{id}/titular/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idClass, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTitular(idClass, idProfessor);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}