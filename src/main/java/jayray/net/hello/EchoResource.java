package jayray.net.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("echo")
public class EchoResource {

	@GET
	@Produces("application/json")
	public String echo() {
		return "echo: hest jullanebest" ;
	}
/*	@GET
	@Produces("application/json")
	public String echo(@QueryParam("m") String message) {
		return "echo: hest jullanebest" + message;
	}*/
}
