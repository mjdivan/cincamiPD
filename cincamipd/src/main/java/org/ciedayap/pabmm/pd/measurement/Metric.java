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
 * It represents the abstract class for the metric
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Metric")
@XmlType(propOrder={"IDmetric","name","objective","author","version","IDAttribute","scale","sources"})
public class Metric implements Serializable, SingleConcept{
    /**
     * The unique identifier for the metric along the projects
     */
    private String IDmetric;
    /**
     * The metric´s name
     */
    private String name;
    /**
     * The metric objective
     */
    private String objective;
    /**
     * The metric author
     */
    private String author;
    /**
     * The version related to the metric
     */
    private String version;
    /**
     * The attribute which is quantified by this metric. Here, just we keep the ID 
     * for avoiding a circular reference at the moment in where the XML is generated.
     * The navigation will be driven from the attribute instance to the metric. However,
     * the metric keeps the attribute´s id.
     */
    private String IDAttribute;
    /**
     * The scale associated with the metric
     */
    private Scale scale;
    /**
     * The possible data sources related to the metric
     */
    private DataSources sources;
    
    /**
     * Default constructor
     */
    public Metric()
    {
     scale=new Scale();
     sources=new DataSources();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The metric ID
     * @param name The metric name
     * @param attrid The attribute ID associated with the metric
     * @param scale The scale associated with the metric. It contains the associated unit to the metric value.
     * @param sources The possible data sources related to the metric. At least one is required.
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public Metric(String id,String name, String attrid,Scale scale, DataSources sources) throws EntityPDException
    {
       if(StringUtils.isEmpty(id)) throw new EntityPDException("The Metric ID is not defined"); 
       if(StringUtils.isEmpty(name)) throw new EntityPDException("The Metric name is not defined"); 
       if(StringUtils.isEmpty(attrid)) throw new EntityPDException("The Attribute ID associated with the metric is not defined"); 
       if(scale==null || !scale.isDefinedProperties()) throw new EntityPDException("The scale associated with the metric is not defined"); 
       if(sources==null || sources.getSources()==null || sources.getSources().isEmpty())
           throw new EntityPDException("The data sources are not defined for this metric");
       
       this.IDmetric=id;
       this.IDAttribute=attrid;
       this.name=name;
       this.version="1.0";
       this.scale = scale;
       this.sources = sources;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(IDmetric) || StringUtils.isEmpty(this.IDAttribute) 
                || scale==null || !scale.isDefinedProperties());
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
        return this.getIDmetric();
    }

    /**
     * @return the IDmetric
     */
    @XmlElement(name="IDmetric", required=true)
    public String getIDmetric() {
        return IDmetric;
    }

    /**
     * @param IDmetric the IDmetric to set
     */
    public void setIDmetric(String IDmetric) {
        this.IDmetric = IDmetric;
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
     * @return the objective
     */
    @XmlElement(name="objective")
    public String getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * @return the author
     */
    @XmlElement(name="author")
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the version
     */
    @XmlElement(name="version")
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the IDAttribute
     */
    @XmlElement(name="IDAttribute", required=true)
    public String getIDAttribute() {
        return IDAttribute;
    }

    /**
     * @param IDAttribute the IDAttribute to set
     */
    public void setIDAttribute(String IDAttribute) {
        this.IDAttribute = IDAttribute;
    }

    /**
     * @return the scale
     */
    @XmlElement(name="Scale", required=true)
    public Scale getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    /**
     * @return the sources
     */
    @XmlElement(name="DataSources", required=true)
    public DataSources getSources() {
        return sources;
    }

    /**
     * @param sources the sources to set
     */
    public void setSources(DataSources sources) {
        this.sources = sources;
    }
}
