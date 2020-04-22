/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2020.
 */
package dev.galasa.zostso.ssh.manager.internal.properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import dev.galasa.framework.spi.IConfigurationPropertyStoreService;
import dev.galasa.zostso.ZosTSOCommandManagerException;

@Component(service=ZosTSOCommandSshPropertiesSingleton.class, immediate=true)
public class ZosTSOCommandSshPropertiesSingleton {
    
    private static ZosTSOCommandSshPropertiesSingleton singletonInstance;
    private static void setInstance(ZosTSOCommandSshPropertiesSingleton instance) {
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
    
    public static IConfigurationPropertyStoreService cps() throws ZosTSOCommandManagerException {
        if (singletonInstance != null) {
            return singletonInstance.cps;
        }
        
        throw new ZosTSOCommandManagerException("Attempt to access manager CPS before it has been initialised");
    }
    
    public static void setCps(IConfigurationPropertyStoreService cps) throws ZosTSOCommandManagerException {
        if (singletonInstance != null) {
            singletonInstance.cps = cps;
            return;
        }
        
        throw new ZosTSOCommandManagerException("Attempt to set manager CPS before instance created");
    }
}