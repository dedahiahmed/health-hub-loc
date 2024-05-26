package health.hub.controllers;

import health.hub.annotations.RoleRequired;
import health.hub.requests.PharmacyRequest;
import health.hub.responses.PharmacyResponse;
import health.hub.services.PharmacyService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pharmacies")
public class PharmacyController {


    @Inject
    PharmacyService pharmacyService;


    @GET
    public List<PharmacyResponse> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

    @POST
    public Response addPharmacy(@Valid PharmacyRequest request) {
        pharmacyService.addPharmacy(request);
        return Response.ok("Pharmacy added successfully").build();
    }

    @GET
    @Path("/{id}")
    @RoleRequired("ADMIN")
    public Response getPharmacyById(@PathParam("id") Long id) {
        PharmacyResponse pharmacyResponse = pharmacyService.getPharmacyById(id);

        return Response.ok(pharmacyResponse).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deletePharmacyById(@PathParam("id") Long id) {
        pharmacyService.deletePharmacyById(id);
        return Response.ok().build();

    }
}
