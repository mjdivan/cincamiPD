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
 * It represents a characteristic associated with a data source
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceProperty")
@XmlType(propOrder={"propertyDSID","name","description"})
public class DataSourceProperty implements Serializable, SingleConcept{
    /**
     * property ID 
     */
    private String propertyDSID;
    /**
     * property name
     */
    private String name;
    /**
     * A brief description related to the data source´s property
     */
    private String description;

    /**
     * Default constructor
     */
    public DataSourceProperty()
    {
        
    }
    
    /**
     * A constructor related to the mandatory attribute
     * @param id The property id associated with the data source
     * @param name The property name associated with the data source
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public DataSourceProperty(String id,String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The  property id of the data source is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The property name of the data source is not defined");
        this.propertyDSID=id;
        this.name=name;
    }
    
    /**
     * A basic factory method
     * @param id The property id associated with the data source
     * @param name The property name associated with the data source
     * @return A new DataSourceProperty´s instance 
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public static DataSourceProperty create(String id,String name) throws EntityPDException
    {
        return new DataSourceProperty(id,name);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.propertyDSID));
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
        return this.getPropertyDSID();
    }

    /**
     * @return the propertyDSID
     */
    @XmlElement(name="propertyDSID", required=true)
    public String getPropertyDSID() {
        return propertyDSID;
    }

    /**
     * @param propertyDSID the propertyDSID to set
     */
    public void setPropertyDSID(String propertyDSID) {
        this.propertyDSID = propertyDSID;
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
     * @return the description
     */
    @XmlElement(name="description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
