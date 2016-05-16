package com.blogspot.nombre_temp.jetty.jersey.logback.demo.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.blogspot.nombre_temp.jetty.jersey.logback.demo.model.Status;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    public Status health() {
        return new Status();
    }
}
