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
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.utils.StringUtils;

/**
 * It represents the data source related to the measures.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSource")
@XmlType(propOrder={"dataSourceID","name","description","type","dataFormmats","groups","adapters"})
public class DataSource implements Serializable, SingleConcept{
    /**
     * The data source´s ID
     */
    private String dataSourceID;
    /**
     * The data source´s name
     */
    private String name;
    /**
     * A brief description related to the data source
     */
    private String description;
    /**
     * The data source´s type
     */
    private String type;
    /**
     * A comma-separated list with the supported data formats by the data source
     */
    private String dataFormmats;
    /**
     * A set of TraceGroup instances which this data source is a member.
     */
    private TraceGroups groups;
    /**
     * A set of DataSourceAdapte instances which is used for this data source for converting
     * the original data format in cincamimis streams. At least one measurement adapter is 
     * required for a given data source.
     */
    private DataSourceAdapters adapters;
    
    /**
     * Default constructor
     */
    public DataSource()
    {
        groups=new TraceGroups();
        adapters=new DataSourceAdapters();
    }
    
    /**
     * A constructor oriented to the mandatory attributes
     * @param id The data source´s ID
     * @param name The data source´s name
     * @param adap The measurement adapter useful for this data source
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public DataSource(String id,String name,DataSourceAdapters adap) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The data source id is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The data source name is not defined");
        if(adap==null || adap.getAdapters()==null || adap.getAdapters().isEmpty())
            throw new EntityPDException("The measurement adapter for the data source is not defined");
        
        this.dataSourceID=id;
        this.name=name;  
        
        groups=new TraceGroups();
        adapters=adap;        
    }
    
    /**
     * A basic static factory method
     * @param id The data source´s ID
     * @param name The data source´s name
     * @param adap The measurement adapter for this data source
     * @return A new DataSource´s instance, null otherwise
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public static DataSource create(String id,String name,DataSourceAdapters adap) throws EntityPDException
    {
        return new DataSource(id,name,adap);
    }

    /**
     * @return the dataSourceID
     */
    @XmlElement(name="dataSourceID", required=true)
    public String getDataSourceID() {
        return dataSourceID;
    }

    /**
     * @param dataSourceID the dataSourceID to set
     */
    public void setDataSourceID(String dataSourceID) {
        this.dataSourceID = dataSourceID;
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

    /**
     * @return the type
     */
    @XmlElement(name="type")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the dataFormmats
     */
    @XmlElement(name="dataFormmats")
    public String getDataFormmats() {
        return dataFormmats;
    }

    /**
     * @param dataFormmats the dataFormmats to set
     */
    public void setDataFormmats(String dataFormmats) {
        this.dataFormmats = dataFormmats;
    }

    /**
     * @return the groups
     */
    @XmlElement(name="TraceGroups")
    public TraceGroups getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(TraceGroups groups) {
        this.groups = groups;
    }

    /**
     * @return the adapters
     */
    @XmlElement(name="DataSourceAdapters", required=true)
    public DataSourceAdapters getAdapters() {
        return adapters;
    }

    /**
     * @param adapters the adapters to set. At least one measurement adapter is required in the list.
     */
    public void setAdapters(DataSourceAdapters adapters) {
        this.adapters = adapters;
    }

    @Override
    public boolean isDefinedProperties() 
    {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.dataSourceID) ||
                this.adapters==null || adapters.getAdapters()==null || adapters.getAdapters().isEmpty());     
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
        return this.dataSourceID;
    }

}
