package dev.galasa.common.zos.internal.properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import dev.galasa.common.zos.ZosManagerException;
import dev.galasa.framework.spi.IConfigurationPropertyStoreService;

@Component(service=ZosPropertiesSingleton.class, immediate=true)
public class ZosPropertiesSingleton {
	
	private static ZosPropertiesSingleton singletonInstance;
	private static void setInstance(ZosPropertiesSingleton instance) {
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
	
	public static IConfigurationPropertyStoreService cps() throws ZosManagerException {
		if (singletonInstance != null) {
			return singletonInstance.cps;
		}
		
		throw new ZosManagerException("Attempt to access manager CPS before it has been initialised");
	}
	
	public static void setCps(IConfigurationPropertyStoreService cps) throws ZosManagerException {
		if (singletonInstance != null) {
			singletonInstance.cps = cps;
			return;
		}
		
		throw new ZosManagerException("Attempt to set manager CPS before instance created");
	}
}
