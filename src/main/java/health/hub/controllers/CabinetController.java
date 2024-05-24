package health.hub.controllers;

import health.hub.requests.CabinetRequest;
import health.hub.responses.CabinetResponse;
import health.hub.services.CabinetService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/cabinets")
public class CabinetController {
    private static final long serialVersionUID = 1L;

    public CabinetController() {
        super();
    }

    CabinetService cabinetService = new CabinetService();


    @GET
    public List<CabinetResponse> getAllCabinets() {
        return cabinetService.getAllCabinets();
    }

    @POST
    public Response addCabinet(@Valid CabinetRequest request) {
        cabinetService.addCabinet(request);
        return Response.ok("Cabinet added successfully").build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteCabinetBy(@PathParam("id") Long id) {
        try {
            cabinetService.deleteCabinet(id);
            return Response.ok("Cabinet deleted successfully").build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
