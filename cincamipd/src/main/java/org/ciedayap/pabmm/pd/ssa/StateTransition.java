/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.utils.StringUtils;

/**
 * It represents each transition state
 *
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="StateTransition")
@XmlType(propOrder={"sourceID","targetID","cost","income","likelihood","similarity"})
public class StateTransition implements Serializable, SingleConcept{
    private String sourceID;
    private String targetID;
    private BigDecimal cost;
    private BigDecimal income;
    private BigDecimal likelihood;
    private BigDecimal similarity;   
    
    /**
     * Default Constructor
     */
    public StateTransition()
    {
        sourceID=targetID=null;
        cost=BigDecimal.ZERO;
        income=BigDecimal.ZERO;
        likelihood=null;
        similarity=null;        
    }

    /**
     * Constructor 
     * @param sid The Source State ID
     * @param tid The Target State ID
     * @param pcost The cost related to the transition from the c
     * @param pincome The income eventually related to the transitio from the source state to the target state
     * @param plikelihood The likelihood related to the transition itself
     * @param psimilarity The similarity between the source and target states
     * @throws org.ciedayap.pabmm.pd.ssa.SSAException It is raised when the source or target state is not defined
     */
    public StateTransition(String sid,String tid,BigDecimal pcost, BigDecimal pincome, BigDecimal plikelihood, BigDecimal psimilarity) throws SSAException
    {
        if(StringUtils.isEmpty(sid)) throw new SSAException("The Source ID is not established");
        if(StringUtils.isEmpty(tid)) throw new SSAException("The Target ID is not established");
        
        sourceID=sid;
        targetID=tid;
        cost=pcost;
        income=pincome;
        likelihood=plikelihood;
        similarity=psimilarity;
    }

    public synchronized static StateTransition create()
    {
        return new StateTransition();
    }
    
    public synchronized static StateTransition create(String sid,String tid,BigDecimal pcost, BigDecimal pincome, BigDecimal plikelihood, BigDecimal psimilarity) throws SSAException
    {
        return new StateTransition(sid,tid,pcost,pincome,plikelihood,psimilarity);
    }

    /**
     * @return the cost
     */
    @XmlElement(name="cost")
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * @return the income
     */
    @XmlElement(name="income")
    public BigDecimal getIncome() {
        return income;
    }

    /**
     * @param income the income to set
     */
    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    /**
     * @return the likelihood
     */
    @XmlElement(name="likelihood")
    public BigDecimal getLikelihood() {
        return likelihood;
    }

    /**
     * @param likelihood the likelihood to set
     */
    public void setLikelihood(BigDecimal likelihood) {
        this.likelihood = likelihood;
    }

    /**
     * @return the similarity
     */
    @XmlElement(name="similarity")
    public BigDecimal getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(BigDecimal similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the targetID
     */
    @XmlElement(name="targetID", required=true)
    public String getTargetID() {
        return targetID;
    }

    /**
     * @param targetID the targetID to set
     */
    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    /**
     * @return the sourceID
     */
    @XmlElement(name="sourceID", required=true)
    public String getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(targetID) || StringUtils.isEmpty(sourceID));
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
        return this.getSourceID()+"."+this.getTargetID();
    }
}
