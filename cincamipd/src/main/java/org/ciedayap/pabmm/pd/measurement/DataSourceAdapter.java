/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents the concept related to the data source adapter.
 * It would be the intermediary among the data sources and the gathering
 * function in PAbMM. The aim of the measurement adapter is to convert
 * from a native data source format to CINCAMI/MIS stream.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceAdapter")
@XmlType(propOrder={"dsAdapterID","name","deviceDescription","supportedTypes","supportedFormats"})
public class DataSourceAdapter implements Serializable, SingleConcept{
    /**
     * The Measurement Adatper´s ID
     */
    private String dsAdapterID;
    /**
     * A representative name related to the measurement adapter
     */
    private String name;
    /**
     * A brief description related to the measurement adapter
     */
    private String deviceDescription;
    /**
     * A comma-separated list with each mime type supported as translating´s origin.
     * An updated list is available on http://www.iana.org/assignments/media-types/media-types.xhtml
     */
    private String supportedTypes;
    /**
     * Each kind of Supported Data format as a comma-separated list
     */
    private String supportedFormats;

    /**
     * Default constructors
     */
    public DataSourceAdapter()
    {
        
    }
    /**
     * Constructor related to the mandatory attributes
     * @param id The unique measurement adapter´s ID along all the Measurement projects
     * @param name The measurement adapter´s name 
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public DataSourceAdapter(String id,String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The data source adapter ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The data source adapter name is not defined");
        
        this.dsAdapterID=id;
        this.name=name;
    }
    
    /**
     * A basic static method for creating a new data source adapter (known as measurement adapter as well)
     * @param id The measurement adapter´s ID
     * @param name The measurement adapter´s name
     * @return A new Data source adapter´s instance, null otherwise
     * @throws EntityPDException 
     */
    public static DataSourceAdapter create(String id,String name) throws EntityPDException
    {
        return new DataSourceAdapter(id,name);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.dsAdapterID));
    }

    @Override
    public String getCurrentClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getQualifiedCurrentClassName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public String getUniqueID() {
        return this.getDsAdapterID();
    }

    /**
     * @return the dsAdapterID
     */
    @XmlElement(name="dsAdapterID", required=true)
    public String getDsAdapterID() {
        return dsAdapterID;
    }

    /**
     * @param dsAdapterID the dsAdapterID to set
     */
    public void setDsAdapterID(String dsAdapterID) {
        this.dsAdapterID = dsAdapterID;
    }

    /**
     * @return the name
     */
    @XmlElement(name="name", required=true)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the deviceDescription
     */
    @XmlElement(name="deviceDescription")
    public String getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * @param deviceDescription the deviceDescription to set
     */
    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    /**
     * @return the supportedTypes
     */
    @XmlElement(name="supportedTypes")
    public String getSupportedTypes() {
        return supportedTypes;
    }

    /**
     * @param supportedTypes the supportedTypes to set
     */
    public void setSupportedTypes(String supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    /**
     * @return the supportedFormats
     */
    @XmlElement(name="supportedFormats")
    public String getSupportedFormats() {
        return supportedFormats;
    }

    /**
     * @param supportedFormats the supportedFormats to set
     */
    public void setSupportedFormats(String supportedFormats) {
        this.supportedFormats = supportedFormats;
    }
    
}
