/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2020-2021.
 */
package dev.galasa.zos.internal.properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dev.galasa.ICredentials;
import dev.galasa.ICredentialsUsernamePassword;
import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.IConfigurationPropertyStoreService;
import dev.galasa.framework.spi.cps.CpsProperties;
import dev.galasa.zos.ZosManagerException;
import dev.galasa.zos.internal.ZosProvisionedImageImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ZosPropertiesSingleton.class, CpsProperties.class})
public class TestRunUNIXPathPrefix {
    
    @Mock
    private IConfigurationPropertyStoreService configurationPropertyStoreServiceMock;
    
    @Mock
    private ZosProvisionedImageImpl zosProvisionedImageMock;
    
    @Mock
    private ICredentialsUsernamePassword credentialsUsernamePasswordMock;
    
    private static final String IMAGE_ID = "image";
    
    private static final String RUN_UNIX_PATH_PREFIX = "/run/path/prefix";

    private static final String USERID = "USERID";

    private static final String DEFAUT_RUN_UNIX_PATH_PREFIX = "/u/userid/Galasa";
    
    @Before
    public void setup() throws ZosManagerException {
        Mockito.doReturn(IMAGE_ID).when(zosProvisionedImageMock).getImageID();
        Mockito.doReturn(credentialsUsernamePasswordMock).when(zosProvisionedImageMock).getDefaultCredentials();
        Mockito.doReturn(USERID).when(credentialsUsernamePasswordMock).getUsername();
    }    
    
    @Test
    public void testConstructor() {
        RunUNIXPathPrefix runUNIXPathPrefix = new RunUNIXPathPrefix();
        Assert.assertNotNull("Object was not created", runUNIXPathPrefix);
    }
    
    @Test
    public void testNull() throws Exception {
        Assert.assertEquals("Unexpected value returned from RunUNIXPathPrefix.get()", DEFAUT_RUN_UNIX_PATH_PREFIX, getProperty(zosProvisionedImageMock, null));
    }
    
    @Test
    public void testValid() throws Exception {
        Assert.assertEquals("Unexpected value returned from RunUNIXPathPrefix.get()", RUN_UNIX_PATH_PREFIX, getProperty(zosProvisionedImageMock, RUN_UNIX_PATH_PREFIX));
    }
    
    @Test
    public void testException1() throws Exception {
        ICredentials credentialsMock = Mockito.mock(ICredentials.class);
        Mockito.doReturn(credentialsMock).when(zosProvisionedImageMock).getDefaultCredentials();
        String expectedMessage = "Unable to get the run username for image " + IMAGE_ID;
        ZosManagerException expectedException = Assert.assertThrows("expected exception should be thrown", ZosManagerException.class, ()->{
            getProperty(zosProvisionedImageMock, null);
        });
    	Assert.assertEquals("exception should contain expected message", expectedMessage, expectedException.getMessage());
    }
    
    @Test
    public void testException2() throws Exception {
        String expectedMessage = "Problem asking the CPS for the zOS run UNIX path prefix for image "  + IMAGE_ID;
        ZosManagerException expectedException = Assert.assertThrows("expected exception should be thrown", ZosManagerException.class, ()->{
        	getProperty(zosProvisionedImageMock, null, true);
        });
    	Assert.assertEquals("exception should contain expected message", expectedMessage, expectedException.getMessage());
    }

    private String getProperty(ZosProvisionedImageImpl arg, String value) throws Exception {
        return getProperty(arg, value, false);
    }
    
    private String getProperty(ZosProvisionedImageImpl arg, String value, boolean exception) throws Exception {
        PowerMockito.spy(ZosPropertiesSingleton.class);
        PowerMockito.doReturn(configurationPropertyStoreServiceMock).when(ZosPropertiesSingleton.class, "cps");
        PowerMockito.spy(CpsProperties.class);
        
        if (!exception) {
            PowerMockito.doReturn(value).when(CpsProperties.class, "getStringNulled", Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        } else {
            PowerMockito.doThrow(new ConfigurationPropertyStoreException()).when(CpsProperties.class, "getStringNulled", Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        }
        
        return RunUNIXPathPrefix.get(arg);
    }
}
