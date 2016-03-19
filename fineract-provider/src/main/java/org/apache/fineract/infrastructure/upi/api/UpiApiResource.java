package org.apache.fineract.infrastructure.upi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/upi")
@Component
@Scope("singleton")
public class UpiApiResource {
    private final PlatformSecurityContext platformSecurityContext;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    
    @Autowired
    public UpiApiResource(final PlatformSecurityContext platformSecurityContext,
            final ApiRequestParameterHelper apiRequestParameterHelper) {
        this.platformSecurityContext = platformSecurityContext;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
    }

    @GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveTemplate(@Context final UriInfo uriInfo) {
        this.platformSecurityContext.authenticatedUser();
        return null ;
    }
    
}