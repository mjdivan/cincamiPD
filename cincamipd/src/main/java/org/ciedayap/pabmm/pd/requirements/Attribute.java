/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.requirements;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.evaluation.ElementaryIndicator;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.pabmm.pd.measurement.Metrics;

/**
 * It is responsible for describing a characteristic associated with an entity category
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Attribute")
@XmlType(propOrder={"ID","name","definition","objective","weight","minEstimatedValue","maxEstimatedValue",
"avgEstimatedValue","sdEstimatedValue","kurtosisEstimatedValue","skewnessEstimatedValue",
"medianEstimatedValue","modeEstimatedValues","quantifiedBy","indicator"})
public class Attribute implements Serializable, SingleConcept{
    /**
     * The identification related to the Attribute
     */
    private String ID;
    /**
     * The attribute´s name
     */
    private String name;
    /**
     * The narrative definition related to the attribute
     */
    private String definition;
    /**
     * The attribute aim
     */
    private String objective;
    /**
     * The attribute weighting of this attribute in relation to the associated entity category.
     * The value must be contained between 0 and 1
     */
    private BigDecimal weight;
    /**
     * When it is known, the estimated min value related to the attribute
     */
    private BigDecimal minEstimatedValue;
    /**
     * When it is known, the estimated max value related to the attribute
     */
    private BigDecimal maxEstimatedValue;
    /**
     * When it is known, the estimated mean value related to the attribute
     */
    private BigDecimal avgEstimatedValue;
    /**
     * When it is known, the estimated standard deviation value related to the attribute
     */
    private BigDecimal sdEstimatedValue;
    /**
     * When it is known, the estimated kurtosis value related to the attribute
     */
    private BigDecimal kurtosisEstimatedValue;
    /**
     * When it is known, the estimated min value related to the attribute
     */
    private BigDecimal skewnessEstimatedValue;
    /**
     * When it is known, the estimated median value related to the attribute
     */
    private BigDecimal medianEstimatedValue;
    /**
     * When it is known, the estimated mode values related to the attribute
     */
    private ArrayList<BigDecimal> modeEstimatedValues;
    /**
     * The metrics responsible for quantifying the attribute
     */
    private Metrics quantifiedBy;
    /**
     * The ElementaryIndicator responsible for interpreting each metric´s value
     */
    private ElementaryIndicator indicator;
            
    /**
     * Default constructor
     */
    public Attribute()
    {
        
    }
    /**
     * Constructor associated with the mandatory attributes
     * @param ID Attribute´s id
     * @param name Attribute´s name
     * @param quantifiedBy Metric responsible for the attribute quantification
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public Attribute(String ID, String name, Metrics quantifiedBy) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The ID is not defined for the attribute");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The name is not defined for the attribute");
        if(quantifiedBy==null || quantifiedBy.getRelated()==null || quantifiedBy.getRelated().isEmpty())
            throw new EntityPDException("The metric responsible for the quantification is not defined");
        
        this.ID=ID;
        this.name=name;
        this.quantifiedBy=quantifiedBy;
    }
    
    /**
     * a basic Factory method.
     * 
     * @param ID The attribute´s id
     * @param name The attribut´s name
     * @param quantifiedBy The metric responsible for the quantification of the attribute
     * @return a new Attribute´s instance
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static Attribute create(String ID, String name,Metrics quantifiedBy) throws EntityPDException
    {
        return new Attribute(ID,name,quantifiedBy);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || weight==null || quantifiedBy==null
                || quantifiedBy.getRelated()==null || quantifiedBy.getRelated().isEmpty());
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
        return getID();
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
     * @return the definition
     */
    @XmlElement(name="definition")
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
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
     * @return the weight
     */
    @XmlElement(name="weight", required=true)
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set. Just values in [0; 1]
     */
    public void setWeight(BigDecimal weight) {
        if(weight==null || weight.compareTo(BigDecimal.ZERO)<0 ||
                weight.compareTo(BigDecimal.ONE)>0) return;
        
        this.weight = weight;
    }

    /**
     * @return the minEstimatedValue
     */
    @XmlElement(name="minEstimatedValue")
    public BigDecimal getMinEstimatedValue() {
        return minEstimatedValue;
    }

    /**
     * @param minEstimatedValue the minEstimatedValue to set
     */
    public void setMinEstimatedValue(BigDecimal minEstimatedValue) {
        this.minEstimatedValue = minEstimatedValue;
    }

    /**
     * @return the maxEstimatedValue
     */
    @XmlElement(name="maxEstimatedValue")
    public BigDecimal getMaxEstimatedValue() {
        return maxEstimatedValue;
    }

    /**
     * @param maxEstimatedValue the maxEstimatedValue to set
     */
    public void setMaxEstimatedValue(BigDecimal maxEstimatedValue) {
        this.maxEstimatedValue = maxEstimatedValue;
    }

    /**
     * @return the avgEstimatedValue
     */
    @XmlElement(name="avgEstimatedValue")
    public BigDecimal getAvgEstimatedValue() {
        return avgEstimatedValue;
    }

    /**
     * @param avgEstimatedValue the avgEstimatedValue to set
     */
    public void setAvgEstimatedValue(BigDecimal avgEstimatedValue) {
        this.avgEstimatedValue = avgEstimatedValue;
    }

    /**
     * @return the sdEstimatedValue
     */
    @XmlElement(name="sdEstimatedValue")
    public BigDecimal getSdEstimatedValue() {
        return sdEstimatedValue;
    }

    /**
     * @param sdEstimatedValue the sdEstimatedValue to set
     */
    public void setSdEstimatedValue(BigDecimal sdEstimatedValue) {
        this.sdEstimatedValue = sdEstimatedValue;
    }

    /**
     * @return the kurtosisEstimatedValue
     */
    @XmlElement(name="kurtosisEstimatedValue")
    public BigDecimal getKurtosisEstimatedValue() {
        return kurtosisEstimatedValue;
    }

    /**
     * @param kurtosisEstimatedValue the kurtosisEstimatedValue to set
     */
    public void setKurtosisEstimatedValue(BigDecimal kurtosisEstimatedValue) {
        this.kurtosisEstimatedValue = kurtosisEstimatedValue;
    }

    /**
     * @return the skewnessEstimatedValue
     */
    @XmlElement(name="skewnessEstimatedValue")
    public BigDecimal getSkewnessEstimatedValue() {
        return skewnessEstimatedValue;
    }

    /**
     * @param skewnessEstimatedValue the skewnessEstimatedValue to set
     */
    public void setSkewnessEstimatedValue(BigDecimal skewnessEstimatedValue) {
        this.skewnessEstimatedValue = skewnessEstimatedValue;
    }

    /**
     * @return the medianEstimatedValue
     */
    @XmlElement(name="medianEstimatedValue")
    public BigDecimal getMedianEstimatedValue() {
        return medianEstimatedValue;
    }

    /**
     * @param medianEstimatedValue the medianEstimatedValue to set
     */
    public void setMedianEstimatedValue(BigDecimal medianEstimatedValue) {
        this.medianEstimatedValue = medianEstimatedValue;
    }

    /**
     * @return the modeEstimatedValues
     */
    @XmlElement(name="modeEstimatedValue")
    public ArrayList<BigDecimal> getModeEstimatedValues() {
        return modeEstimatedValues;
    }

    /**
     * @param modeEstimatedValues the modeEstimatedValues to set
     */
    public void setModeEstimatedValues(ArrayList<BigDecimal> modeEstimatedValues) {
        this.modeEstimatedValues = modeEstimatedValues;
    }

    /**
     * @return the quantifiedBy
     */
    @XmlElement(name="Metrics", required=true)
    public Metrics getQuantifiedBy() {
        return quantifiedBy;
    }

    /**
     * @param quantifiedBy the quantifiedBy to set
     */
    public void setQuantifiedBy(Metrics quantifiedBy) {
        this.quantifiedBy = quantifiedBy;
    }

    /**
     * @return the indicator
     */
    @XmlElement(name="ElementaryIndicator")
    public ElementaryIndicator getIndicator() {
        return indicator;
    }

    /**
     * @param indicator the indicator to set
     */
    public void setIndicator(ElementaryIndicator indicator) {
        this.indicator = indicator;
    }
}
