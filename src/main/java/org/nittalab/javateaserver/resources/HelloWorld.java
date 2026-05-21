package org.nittalab.javateaserver.resources;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/hello")
public class HelloWorld {
    @GET
    public String getHello() {
        return "Hello World";
    }
}