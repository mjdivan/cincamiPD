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

/**
 * It represents each decision criterion for interpreting a metric´s value
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DecisionCriterion")
@XmlType(propOrder={"idDecisionCriterion","name","description","lowerThreshold","upperThreshold","notifiableUnderLowerThreshold",
    "nult_message","notifiableBetweenThreshold","nbt_message","notifiableAboveUpperThreshold",
    "naut_message"})
public class DecisionCriterion implements Serializable, SingleConcept, Comparable{
    /**
     * The decision criterion´s ID. It is mandatory
     */
    private String idDecisionCriterion; 
    /**
     * The decision criterion´s name. It is mandatory
     */
    private String name;
    /**
     * The decision criterion´s desription. It is optional. However, it could be useful
     * for explaining the associated thresholds. 
     */
    private String description;
    /**
     * The lower threshold related to the decision criterion. It is mandatory
     */
    private BigDecimal lowerThreshold;
    /**
     * The upper threshold related to the decision criterion. It is mandatory
     */
    private BigDecimal upperThreshold;
    /**
     * It indicates whether it is necessary notify when the indicator´s value falls under the lower threshold or not.
     */
    private boolean notifiableUnderLowerThreshold;
    /**
     * It represents the message to be sent jointly with the indicator´s value when the notification is required
     * in case of the indicator´s value fall under the lower threshold.
     */
    private String nult_message;
    /**
     * It indicates whether it is necessary notify when the indicator´s value falls between the thresholds or not.
     */
    private boolean notifiableBetweenThreshold;
    /**
     * It represents the message to be sent jointly with the indicator´s value when the notification is required in case of
     * the indicator´s value fall between the thresholds.
     */
    private String nbt_message;
    /**
     * It indicates whether it is necessary notify when the indicator´s value falls above the upper threshold.
     */
    private boolean notifiableAboveUpperThreshold;
    /**
     * It represents the message to be sent jointly with the indicator´s value when the notification is required in case of
     * the indicator´s value fall above the upper threshold.
     */
    private String naut_message;
    
    /**
     * Default constructor
     */
    public DecisionCriterion(){}
    
    /**
     * A constructor related to the mandatory attributes
     * @param id The decision criterion´s ID
     * @param name The decision criterion´s name
     * @param lowerT The lower threshold related to the decision criterion
     * @param upperT The upper threshold related to the decision criterion
     * @throws EntityPDException It is raised when some mandatory attribute is not defined or it is contradictory in relation its own definition.
     */
    public DecisionCriterion(String id,String name,BigDecimal lowerT, BigDecimal upperT) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The decision criterion´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The decision criterion´s name is not defined");
        if(lowerT==null) throw new EntityPDException("The lower threshold related to the decision criterion is not defined");
        if(upperT==null) throw new EntityPDException("The upper threshold related to the decision criterion is not defined");
        if(lowerT.compareTo(upperT)>0) throw new EntityPDException("The lower threshold must be lesser than the upper threshold");
        
        this.idDecisionCriterion=id;
        this.name=name;
        this.lowerThreshold=lowerT;
        this.upperThreshold=upperT;
    }

    /**
     * A basic factory method
     * @param id The decision criterion´s ID
     * @param name The decision criterion´s name
     * @param lowerT The lower threshold related to the decision criterion
     * @param upperT The upper threshold related to the decision criterion
     * @return A new DecisionCriterion´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined or it is contradictory in relation its own definition.
     */
    public static synchronized DecisionCriterion create(String id,String name,BigDecimal lowerT, BigDecimal upperT) throws EntityPDException
    {
        return new DecisionCriterion(id,name,lowerT,upperT);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.idDecisionCriterion) || 
                this.lowerThreshold==null || this.upperThreshold==null);
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
        return this.getIdDecisionCriterion();
    }

    /**
     * @return the idDecisionCriterion
     */
    @XmlElement(name="idDecisionCriterion", required=true)
    public String getIdDecisionCriterion() {
        return idDecisionCriterion;
    }

    /**
     * @param idDecisionCriterion the idDecisionCriterion to set
     */
    public void setIdDecisionCriterion(String idDecisionCriterion) {
        this.idDecisionCriterion = idDecisionCriterion;
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
     * @return the lowerThreshold
     */
    @XmlElement(name="lowerThreshold", required=true)
    public BigDecimal getLowerThreshold() {
        return lowerThreshold;
    }

    /**
     * @param lowerThreshold the lowerThreshold to set
     */
    public void setLowerThreshold(BigDecimal lowerThreshold) {
        if(this.upperThreshold!=null && lowerThreshold!=null)
        {
         if(lowerThreshold.compareTo(this.upperThreshold)>0) return;   
        }
        
        this.lowerThreshold = lowerThreshold;
    }

    /**
     * @return the upperThreshold
     */
    @XmlElement(name="upperThreshold", required=true)
    public BigDecimal getUpperThreshold() {
        return upperThreshold;
    }

    /**
     * @param upperThreshold the upperThreshold to set
     */
    public void setUpperThreshold(BigDecimal upperThreshold) {
        if(this.lowerThreshold!=null && upperThreshold!=null)
        {
            if(upperThreshold.compareTo(this.lowerThreshold)<0) return;
        }
        this.upperThreshold = upperThreshold;
    }

    /**
     * @return the notifiableUnderLowerThreshold
     */
    @XmlElement(name="notifiableUnderLowerThreshold")
    public boolean isNotifiableUnderLowerThreshold() {
        return notifiableUnderLowerThreshold;
    }

    /**
     * @param notifiableUnderLowerThreshold the notifiableUnderLowerThreshold to set
     */
    public void setNotifiableUnderLowerThreshold(boolean notifiableUnderLowerThreshold) {
        this.notifiableUnderLowerThreshold = notifiableUnderLowerThreshold;
    }

    /**
     * @return the nult_message
     */
    @XmlElement(name="nult_message")
    public String getNult_message() {
        return nult_message;
    }

    /**
     * @param nult_message the nult_message to set
     */
    public void setNult_message(String nult_message) {
        this.nult_message = nult_message;
    }

    /**
     * @return the notifiableBetweenThreshold
     */
    @XmlElement(name="notifiableBetweenThreshold")
    public boolean isNotifiableBetweenThreshold() {
        return notifiableBetweenThreshold;
    }

    /**
     * @param notifiableBetweenThreshold the notifiableBetweenThreshold to set
     */
    public void setNotifiableBetweenThreshold(boolean notifiableBetweenThreshold) {
        this.notifiableBetweenThreshold = notifiableBetweenThreshold;
    }

    /**
     * @return the nbt_message
     */
    @XmlElement(name="nbt_message")
    public String getNbt_message() {
        return nbt_message;
    }

    /**
     * @param nbt_message the nbt_message to set
     */
    public void setNbt_message(String nbt_message) {
        this.nbt_message = nbt_message;
    }

    /**
     * @return the notifiableAboveUpperThreshold
     */
    @XmlElement(name="notifiableAboveUpperThreshold")
    public boolean isNotifiableAboveUpperThreshold() {
        return notifiableAboveUpperThreshold;
    }

    /**
     * @param notifiableAboveUpperThreshold the notifiableAboveUpperThreshold to set
     */
    public void setNotifiableAboveUpperThreshold(boolean notifiableAboveUpperThreshold) {
        this.notifiableAboveUpperThreshold = notifiableAboveUpperThreshold;
    }

    /**
     * @return the nuut_message
     */
    @XmlElement(name="naut_message")
    public String getNaut_message() {
        return naut_message;
    }

    /**
     * @param naut_message the nuat_message to set
     */
    public void setNaut_message(String naut_message) {
        this.naut_message = naut_message;
    }

    @Override
    public int compareTo(Object o) {
        if(o==null) return 1;
        if(!(o instanceof DecisionCriterion)) return 1;
        
        DecisionCriterion come=(DecisionCriterion)o;
        return this.lowerThreshold.compareTo(come.getLowerThreshold());
    }
    
}
