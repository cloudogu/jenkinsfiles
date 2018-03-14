package org.jboss.as.quickstarts.kitchensink.rest;

import de.triology.versionname.VersionNames;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("version")
public class VersionRESTService {
    private static final String VERSION_NAME = VersionNames.getVersionNameFromProperties();

    @GET
    public String getVersion() {
        return VERSION_NAME;
    }
}