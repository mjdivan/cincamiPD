/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents the particular relevance of one data source property in a given
 * data source.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceProperties")
@XmlType(propOrder={"ID","property","value","relevance"})
public class DataSourceProperties implements Serializable, SingleConcept{
    /**
     * A unique ID for this property and data source
     */
    private String ID;
    /**
     * A particular data source property in a given data source
     */
    private DataSourceProperty property;
    /**
     * the value for the property in a given data source
     */
    private BigDecimal value;
    /**
     * The property relevance for a given data source. It should be a value contained
     * between 0 and 1.
     */
    private BigDecimal relevance;
    
    /**
     * Default constructor
     */
    public DataSourceProperties()
    {
        
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param ID The unique ID for this property and data source
     * @param property The property related to the data source
     * @param value The property´s value for the indicated data source
     * @param relevance The property´s relevance for the indicated data source
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public DataSourceProperties(String ID,DataSourceProperty property,BigDecimal value, BigDecimal relevance) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The ID is not defined");
        if(property==null || !property.isDefinedProperties()) throw new EntityPDException("The property is not defined");
        if(value==null) throw new EntityPDException("The value is not defined");
        if(relevance==null || relevance.compareTo(BigDecimal.ZERO)<0 || relevance.compareTo(BigDecimal.ONE)>0)
            throw new EntityPDException("The relevance is not defined or out of range");
        
        this.ID=ID;
        this.property=property;
        this.value=value;
        this.relevance=relevance;
    }
    
    /**
     * It is a basic factory method
     * @param ID The unique ID for this property and data source
     * @param property The property related to the data source
     * @param value The property´s value for the indicated data source
     * @param relevance The property´s relevance for the indicated data source
     * @return A new DataSourceProperties instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static DataSourceProperties create(String ID,DataSourceProperty property,BigDecimal value, BigDecimal relevance) throws EntityPDException
    {
        return new DataSourceProperties(ID,property,value,relevance);
    }
    
    @Override
    public boolean isDefinedProperties() {
        if(StringUtils.isEmpty(getID())) return false;
        if(getProperty()==null || !property.isDefinedProperties()) return false;
        if(getValue()==null) return false;
        return !(relevance==null || relevance.compareTo(BigDecimal.ZERO)<0 || relevance.compareTo(BigDecimal.ONE)>0);
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
        return this.getID();
    }

    /**
     * @return the ID
     */
    @XmlElement(name="ID", required=true)
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the property
     */
    @XmlElement(name="DataSourceProperty", required=true)
    public DataSourceProperty getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(DataSourceProperty property) {
        this.property = property;
    }

    /**
     * @return the value
     */
    @XmlElement(name="value", required=true)
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return the relevance
     */
    @XmlElement(name="relevance", required=true)
    public BigDecimal getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set. It should be between 0 and 1
     */
    public void setRelevance(BigDecimal relevance) {
        if(relevance==null || relevance.compareTo(BigDecimal.ZERO)<0 || relevance.compareTo(BigDecimal.ONE)>0)
            return;
        
        this.relevance = relevance;
    }
    
}
