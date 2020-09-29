package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("web")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(rest.AccountAPI.class);
        resources.add(rest.FileUploadAPI.class);
        resources.add(rest.HelperResourceAPI.class);
        resources.add(rest.OrderAPI.class);
        resources.add(rest.ServiceAPI.class);
    }
    
}
