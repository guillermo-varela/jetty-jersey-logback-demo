package com.blogspot.nombre_temp.jetty.jersey.logback.demo.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.nombre_temp.jetty.jersey.logback.demo.model.DemoResponse;
import com.blogspot.nombre_temp.jetty.jersey.logback.demo.model.User;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private static Logger logger = LoggerFactory.getLogger(UsersResource.class);

    private static final Set<User> USERS = new HashSet<User>();

    @POST
    public DemoResponse create(User user) {
        logger.info("Creating user: {}", user);
        DemoResponse response = new DemoResponse();

        try {
            Validate.notNull(user, "The user cannot be null");
            logger.debug("Trying to add user with id: {}", user.getId());

            if (USERS.add(user)) {
                logger.info("User created");
            } else {
                logger.warn("User repeated");
                response.setError(true);
                response.setMessage("User repeated");
            }
        } catch (Exception e) {
            logger.error("Error creating the user: {}", user, e);
            response.setError(true);
            response.setMessage(e.getMessage());
        }

        logger.info("User processed with response: {}", response);
        return response;
    }
}
