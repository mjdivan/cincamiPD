/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.pabmm.pd.measurement.CalculationMethod;
import org.ciedayap.pabmm.pd.measurement.Scale;

/**
 * It represents the base class related to the indicator.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Indicator")
@XmlType(propOrder={"indicatorID","name","weight","author","version","scale","method"})
public class Indicator implements Serializable, SingleConcept{
    /**
     * The indicator´s ID
     */
    private String indicatorID;
    /**
     * The indicator´s name
     */
    private String name;
    /**
     * The relative importance of the indicator along the project. It is a value
     * between 0 and 1.
     */
    private BigDecimal weight;
    /**
     * The indicator´s author
     */
    private String author;
    /**
     * The indicator´s version
     */
    private String version; 
    /**
     * The indicator´s Scale
     */
    private Scale scale;
    /**
     * The indicator´s method for obtaining its value
     */
    private CalculationMethod method;

    /**
     * Default constructor
     */
    public Indicator()
    {
        
    }
    
    /**
     * A constructor oriented to the mandatory attributes
     * @param id The unique ID related to the indicator
     * @param name The indicator´s name
     * @param weight The indicator´s weight
     * @param scale The indicator´s scale
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public Indicator(String id, String name, BigDecimal weight, Scale scale) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The indicator´s id is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The indicator´s name is not defined");
        if(weight==null || weight.compareTo(BigDecimal.ZERO)<0 || weight.compareTo(BigDecimal.ONE)>0)
            throw new EntityPDException("The Indicator´s weight is not defined");
        
        this.indicatorID=id;
        this.name=name;
        this.weight=weight;                
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.indicatorID) ||
                 weight==null || scale==null || !scale.isDefinedProperties());
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
        return this.getIndicatorID();
    }

    /**
     * @return the indicatorID
     */
    @XmlElement(name="indicatorID", required=true)
    public String getIndicatorID() {
        return indicatorID;
    }

    /**
     * @param indicatorID the indicatorID to set
     */
    public void setIndicatorID(String indicatorID) {
        this.indicatorID = indicatorID;
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
     * @return the weight
     */
    @XmlElement(name="weight", required=true)
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set. The value must be between 0 and 1
     */
    public void setWeight(BigDecimal weight) {
        if(weight==null) return;
        if(weight.compareTo(BigDecimal.ZERO)<0) return;
        if(weight.compareTo(BigDecimal.ONE)>0) return;
        
        this.weight = weight;
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
     * @return the method
     */
    @XmlElement(name="CalculationMethod")
    public CalculationMethod getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(CalculationMethod method) {
        this.method = method;
    }
    
}
