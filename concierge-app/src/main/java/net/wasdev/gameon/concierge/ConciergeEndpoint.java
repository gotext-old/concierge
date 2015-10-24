package net.wasdev.gameon.concierge;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.wasdev.gameon.room.common.EndpointCollection;
import net.wasdev.gameon.room.common.Room;

@Path("/")
public class ConciergeEndpoint {

	@Inject
	Concierge c;

	@GET
	@Path("startingRoom")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStartingRoom() {
		Room startingRoom = c.getStartingRoom();

		if ( startingRoom == null )
			return Response.status(404).build();

		return Response.ok(startingRoom).build();
	}

	@GET
	@Path("rooms/{roomId}/{exitName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response exitRoom(@PathParam("roomId") String roomId, @PathParam("roomID") String exitName) {
		EndpointCollection ec = c.exitRoom(roomId, exitName);

		if ( ec.getEndpoints().isEmpty() )
			return Response.status(404).build();

		return Response.ok(ec).build();
	}

	@POST
	@Path("registerRoom")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerRoom(Room room) {
		return Response.ok(c.registerRoom(room)).build();
	}


}