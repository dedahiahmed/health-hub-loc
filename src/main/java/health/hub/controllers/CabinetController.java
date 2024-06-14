package health.hub.controllers;

import health.hub.entities.Cabinet;
import health.hub.services.CabinetService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/cabinets")
public class CabinetController {

    @Inject

            
    CabinetService cabinetService;


    @GET
    public List<Cabinet> getAllCabinets() {
        return cabinetService.getAllCabinets();
    }

    @GET
    @Path("/{id}")
    public Response getCabbinetById(@PathParam("id") Long id) {
        Cabinet cabinetResponse = cabinetService.getCabinetByID(id);

        return Response.ok(cabinetResponse).build();
    }


    @POST
    public Response addCabinet(@Valid Cabinet request) {
        return Response.ok(cabinetService.addCabinet(request)).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteCabinetBy(@PathParam("id") Long id) {
        cabinetService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response updatePharmacy(@PathParam("id") Long id, Cabinet cabinet) {
        return Response.ok(cabinetService.updateCabinet(id, cabinet)).build();
    }
}
