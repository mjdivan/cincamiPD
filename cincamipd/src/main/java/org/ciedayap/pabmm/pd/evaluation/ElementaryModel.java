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
 * It represents a set of decision criterion used for interpreting a metric´s value
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ElementaryModel")
@XmlType(propOrder={"idEM","name","criteria"})
public class ElementaryModel implements Serializable,SingleConcept{
    /**
     * The Elementary Model´s ID
     */
    private String idEM;
    /**
     * The Elementary Model´s name
     */
    private String name;
    /**
     * The set of decision criterion organized under a DecisionCriteria instance
     */
    private DecisionCriteria criteria;
    
    /**
     * Default Constructor
     */
    public ElementaryModel()
    {
        criteria=new DecisionCriteria();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The Elementary Model´s ID
     * @param name The Elementary Model´s name
     * @param dc The DecisionCriteria instance
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException It is raised when some mandatory attribute is not defined
     */
    public ElementaryModel(String id, String name, DecisionCriteria dc) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The Elementary Model´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The Elementary Model´s name is not defined");
        criteria=dc;
        this.idEM=id;
        this.name=name;
    }

    /**
     * A basic factory method 
     * @param id The Elementary Model´s ID
     * @param name The Elementary Model´s name
     * @param dc The DecisionCriteria instance
     * @return A new Elementary Model´s instance
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static ElementaryModel create(String id, String name, DecisionCriteria dc) throws EntityPDException
    {
        return new ElementaryModel(id,name,dc);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(idEM) || StringUtils.isEmpty(this.name) || criteria==null);
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
        return this.getIdEM();
    }

    /**
     * @return the idEM
     */
    @XmlElement(name="idEM", required=true)
    public String getIdEM() {
        return idEM;
    }

    /**
     * @param idEM the idEM to set
     */
    public void setIdEM(String idEM) {
        this.idEM = idEM;
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
     * @return the criteria
     */
    @XmlElement(name="DecisionCriteria", required=true)
    public DecisionCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(DecisionCriteria criteria) {
        this.criteria = criteria;
    }
    
    public ElementaryDecision evaluate(BigDecimal value)
    {
        if(value==null) return ElementaryDecision.createError(value,"The value for evaluation is null");
        if(criteria==null || criteria==null || criteria.getCriteria().isEmpty())
            return ElementaryDecision.createError(value,"There not exist decision criteria");
        
        for(DecisionCriterion dc:criteria.getCriteria())
        {
            if(value.compareTo(dc.getLowerThreshold())<0)
            {//lower than
                if(dc.isNotifiableUnderLowerThreshold())
                {
                    return ElementaryDecision.createAnswer(value, this.getIdEM(), true, dc.getNult_message(), true);
                }
            }
            else
            {
                if(value.compareTo(dc.getUpperThreshold())>0)
                {//upper than
                    if(dc.isNotifiableAboveUpperThreshold())
                    {
                       return ElementaryDecision.createAnswer(value, this.getIdEM(), true, dc.getNaut_message(), true); 
                    }
                }
                else
                {//between
                    if(dc.isNotifiableBetweenThreshold())
                    {
                        return ElementaryDecision.createAnswer(value, this.getIdEM(), true, dc.getNbt_message(), true);
                    }
                    else
                    {//This decision is not notifiable
                        return ElementaryDecision.createAnswer(value, this.getIdEM(), true, dc.getNbt_message(), false); 
                    }
                }
            }
        }
        
        return ElementaryDecision.createError(value,"There are not an applicable decision criterion");
    }
}
