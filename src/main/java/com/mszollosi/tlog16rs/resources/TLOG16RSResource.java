package com.mszollosi.tlog16rs.resources;

import com.mszollosi.tlog16rs.core.Greeting;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TLOG16RSResource {

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String getGreeting() {
       return "Hello World!";
   }

   @Path("/{name}")
   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String getGreetingWithParam(@PathParam(value = "name") String name) {
       return "Hello " + name + "!";
   }

   @Path("/query_param")
   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String getGreetingWithQuery(@DefaultValue("world") @QueryParam(value = "name") String name) {
       return "Hello " + name + "!";
   }

   @Path("/hello_json")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Greeting getJSONGreeting() {
       return new Greeting("Hello world");
   }
}
