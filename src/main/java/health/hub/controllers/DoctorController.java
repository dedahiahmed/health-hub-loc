package health.hub.controllers;

import health.hub.requests.DoctorRequest;
import health.hub.responses.DoctorResponse;
import health.hub.services.DoctorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/doctors")
public class DoctorController {
    @Inject
    DoctorService doctorService;

    @GET
    public List<DoctorResponse> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @POST
    public Response addDoctor(@Valid DoctorRequest request) {
        doctorService.addDoctor(request);
        return Response.ok("Doctor added successfully").build();
    }

    @GET
    @Path("/{id}")
    public Response getDoctorById(@PathParam("id") Long id) {
        DoctorResponse doctorResponse = doctorService.getDoctorById(id);
        return Response.ok(doctorResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDoctorById(@PathParam("id") Long id) {
        doctorService.deleteDoctorById(id);
        return Response.ok().build();
    }
}
