/*
 * Copyright contributors to the Galasa project
 */
package dev.galasa.zosliberty.angel.internal.properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import dev.galasa.framework.spi.IConfigurationPropertyStoreService;
import dev.galasa.zosliberty.angel.ZosLibertyAngelManagerException;

@Component(service=ZosLibertyAngelPropertiesSingleton.class, immediate=true)
public class ZosLibertyAngelPropertiesSingleton {
    
    private static ZosLibertyAngelPropertiesSingleton singletonInstance;
    private static void setInstance(ZosLibertyAngelPropertiesSingleton instance) {
        singletonInstance = instance;
    }
    
    private IConfigurationPropertyStoreService cps;
    
    @Activate
    public void activate() {
        setInstance(this);
    }
    
    @Deactivate
    public void deacivate() {
        setInstance(null);
    }
    
    public static IConfigurationPropertyStoreService cps() throws ZosLibertyAngelManagerException {
        if (singletonInstance != null) {
            return singletonInstance.cps;
        }
        
        throw new ZosLibertyAngelManagerException("Attempt to access manager CPS before it has been initialised");
    }
    
    public static void setCps(IConfigurationPropertyStoreService cps) throws ZosLibertyAngelManagerException {
        if (singletonInstance != null) {
            singletonInstance.cps = cps;
            return;
        }
        
        throw new ZosLibertyAngelManagerException("Attempt to set manager CPS before instance created");
    }
}
