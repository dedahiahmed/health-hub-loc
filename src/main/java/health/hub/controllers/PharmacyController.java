package health.hub.controllers;

import health.hub.entities.Pharmacy;
import health.hub.services.PharmacyService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pharmacies")
public class PharmacyController {


    @Inject
    PharmacyService pharmacyService;


    @GET
    @Path("/all")
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

    @GET
    public Response getPharmacies() {
        try {
            List<Pharmacy> pharmacies = pharmacyService.Listepharmacie();
            return Response.ok(pharmacies).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching pharmacies")
                    .build();
        }
    }

    @PATCH
    @Path("/isOpenTonight")
    public Response updateIsOpenTonight(List<Map<String, Object>> updates) {
        try {
            for (Map<String, Object> update : updates) {
                Long id = ((Number) update.get("id")).longValue();
                boolean isOpenTonight = (boolean) update.get("isOpenTonight");
                pharmacyService.updateIsOpenTonight(id, isOpenTonight);
            }
            return Response.ok("Pharmacies updated successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while updating pharmacies")
                    .build();
        }
    }

    @POST
    public Response addPharmacy(@Valid Pharmacy request) {
        return Response.ok(pharmacyService.addPharmacy(request)).build();
    }

    @GET
    @Path("/{id}")
    public Response getPharmacyById(@PathParam("id") Long id) {
        Pharmacy pharmacyResponse = pharmacyService.getPharmacyById(id);
        return Response.ok(pharmacyResponse).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deletePharmacyById(@PathParam("id") Long id) {
        pharmacyService.deletePharmacyById(id);
        return Response.noContent().build();

    }

    @PATCH
    @Path("/{id}")
    public Response updatePharmacy(@PathParam("id") Long id, Pharmacy pharmacy) {
        return Response.ok(pharmacyService.updatePharmacy(id, pharmacy)).build();
    }
}
