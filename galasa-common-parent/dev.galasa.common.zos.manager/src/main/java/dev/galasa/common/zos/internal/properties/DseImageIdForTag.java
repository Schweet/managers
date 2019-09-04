package dev.galasa.common.zos.internal.properties;

import javax.validation.constraints.NotNull;

import dev.galasa.common.zos.ZosManagerException;
import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.cps.CpsProperties;

/**
 * Developer Supplied Environment zOS Image
 * <p>
 * The image ID of the Developer Supplied Environment for the specified tag 
 * </p><p>
 * The property is:<br>
 * {@code zos.dse.tag.[tag].imageid=SYSA} 
 * </p>
 * <p>
 * There is no default
 * </p>
 *
 */
public class DseImageIdForTag extends CpsProperties {
	
	public static String get(@NotNull String tag) throws ZosManagerException {
		try {
			return getStringNulled(ZosPropertiesSingleton.cps(), "dse.tag", "imageid", tag);
		} catch (ConfigurationPropertyStoreException e) {
			throw new ZosManagerException("Problem asking the CPS for the DSE image id for tag '"  + tag + "'", e);
		}
	}

}
