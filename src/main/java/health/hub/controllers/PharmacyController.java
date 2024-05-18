package health.hub.controllers;

import health.hub.requests.PharmacyRequest;
import health.hub.responses.PharmacyResponse;
import health.hub.services.PharmacyService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pharmacies")
public class PharmacyController {
    private static final long serialVersionUID = 1L;

    public PharmacyController() {
        super();
    }

    PharmacyService pharmacyService = new PharmacyService();


    @GET
    public List<PharmacyResponse> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

    @POST
    public Response addPharmacy(@Valid PharmacyRequest request) {
        pharmacyService.addPharmacy(request);
        return Response.ok("Pharmacy added successfully").build();
    }


}
