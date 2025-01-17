/*
 * Copyright contributors to the Galasa project
 */
package dev.galasa.cicsts;

import dev.galasa.ProductVersion;
import dev.galasa.cicsts.cicsresource.ICicsResource;
import dev.galasa.zos.IZosImage;
import dev.galasa.zosbatch.IZosBatchJob;
import dev.galasa.zosfile.IZosUNIXFile;

public interface ICicsRegion {

    /***
     * Retrieve the CICS TS Region tag
     * @return the tag of the CICS TS Region
     */
    String getTag();

    /***
     * Retrieve the CICS TS Region applid
     * @return the applid of the CICS TS Region
     * @throws CicstsManagerException If the applid is not available
     */
    String getApplid();

    /***
     * Retrieve the CICS TS Region version
     * @return the version of the CICS TS Region
     * @throws CicstsManagerException If the version is not available
     */
    ProductVersion getVersion() throws CicstsManagerException;
    
    /***
     * Retrieve the zOS Image the CICS TS region resides on
     * @return the zOS Image the CICS TS region resides on
     */
    IZosImage getZosImage();

    /**
     * Describes the type of CICS region
     * 
     * @return The type of CICS Region
     */
    MasType getMasType();
    
    //TODO
    ICemt cemt() throws CicstsManagerException;    
    ICeda ceda() throws CicstsManagerException;    
    ICeci ceci() throws CicstsManagerException;
    
    /**
     * Provides a CICS resource instance that can then be used to create a specific CICS resource 
     * @return a {@link ICicsResource} instance associated with this CICS region
     * @throws CicstsManagerException
     */
    public ICicsResource cicsResource() throws CicstsManagerException;
    
    void startup() throws CicstsManagerException;
    void shutdown() throws CicstsManagerException;
    
    boolean isProvisionStart();

    public String getUssHome() throws CicstsManagerException;

    public String getJvmProfileDir() throws CicstsManagerException;
    
	public String getJavaHome() throws CicstsManagerException;

    /**
     * Return the CICS region {@link IZosBatchJob}
     * @return the CICS region job
     * @throws CicstsManagerException
     */
	public IZosBatchJob getRegionJob() throws CicstsManagerException;
	
	/**
	 * 
	 * @return the Run Temporary UNIX Directory for this CICS Region
	 * @throws CicstsManagerException
	 */
	public IZosUNIXFile getRunTemporaryUNIXDirectory() throws CicstsManagerException;
}