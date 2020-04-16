/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2020.
 */
package dev.galasa.zosunix.ssh.manager.internal.properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import dev.galasa.framework.spi.IConfigurationPropertyStoreService;
import dev.galasa.zosunix.ZosUNIXCommandManagerException;

@Component(service=ZosUNIXCommandSshPropertiesSingleton.class, immediate=true)
public class ZosUNIXCommandSshPropertiesSingleton {
    
    private static ZosUNIXCommandSshPropertiesSingleton singletonInstance;
    private static void setInstance(ZosUNIXCommandSshPropertiesSingleton instance) {
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
    
    public static IConfigurationPropertyStoreService cps() throws ZosUNIXCommandManagerException {
        if (singletonInstance != null) {
            return singletonInstance.cps;
        }
        
        throw new ZosUNIXCommandManagerException("Attempt to access manager CPS before it has been initialised");
    }
    
    public static void setCps(IConfigurationPropertyStoreService cps) throws ZosUNIXCommandManagerException {
        if (singletonInstance != null) {
            singletonInstance.cps = cps;
            return;
        }
        
        throw new ZosUNIXCommandManagerException("Attempt to set manager CPS before instance created");
    }
}
