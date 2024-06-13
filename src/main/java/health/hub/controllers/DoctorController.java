package health.hub.controllers;

import health.hub.entities.Doctor;
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
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @POST
    public Response addDoctor(@Valid Doctor doctor) {
        doctorService.addDoctor(doctor);
        return Response.ok("Doctor added successfully").build();
    }

    @GET
    @Path("/{id}")
    public Response getDoctorById(@PathParam("id") Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return Response.ok(doctor).build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateDoctor(@PathParam("id") Long id, Doctor doctorDetails) {

        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
            return Response.ok(updatedDoctor).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDoctorById(@PathParam("id") Long id) {
        doctorService.deleteDoctorById(id);
        return Response.ok().build();
    }

    @GET
    @Path("{id}/doctorsbycabinet")
    public List<Doctor> getDoctorsByCabinet(@PathParam("id") Long id) {
        return doctorService.getDoctorsByCabinet(id);
    }
}
