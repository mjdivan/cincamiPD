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
import org.ciedayap.pabmm.pd.evaluation.DecisionCriteria;
import org.ciedayap.utils.StringUtils;

/**
 * It represents the weighted indicator with its corresponding interpretation in a given scenario and entity property
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="WeightedIndicator")
@XmlType(propOrder={"indicatorID","scenarioID","ecstateID","weight","parameter","criteria"})     
public class WeightedIndicator implements Serializable, SingleConcept{
    private String indicatorID;
    private String scenarioID;
    private String ecstateID;
    private BigDecimal weight;
    private BigDecimal parameter;
    private DecisionCriteria criteria;
    
    public WeightedIndicator()
    {
        indicatorID="1.0";
        scenarioID=null;
        ecstateID=null;
        weight=BigDecimal.ZERO;
        parameter=BigDecimal.ZERO;
        criteria=null;
    }
    
    public WeightedIndicator(String iid,String sid,String ecid, BigDecimal w, BigDecimal p, DecisionCriteria cri) throws SSAException
    {
        if(!StringUtils.isEmpty(iid)) throw new SSAException("The Indicator ID is not defined");
        if(w==null) throw new SSAException("The weighting is not defined");
        if(p==null) throw new SSAException("The parameter is not defined");
        if(cri==null || cri.getCriteria()==null || cri.getCriteria().isEmpty()) throw new SSAException("The criteria are not defined");

        indicatorID=iid;
        scenarioID=sid;
        ecstateID=ecid;
        weight=w;
        parameter=p;
        criteria=cri;        
    }
    
    
    public synchronized static WeightedIndicator create()
    {
        return new WeightedIndicator();
    }
    
    public synchronized static WeightedIndicator create(String iid,String sid,String ecid, BigDecimal w, BigDecimal p, DecisionCriteria cri) throws SSAException
    {
        return new WeightedIndicator(iid,sid,ecid,w,p,cri);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return (!StringUtils.isEmpty(indicatorID) || getWeight()==null || getParameter()==null || getCriteria()==null ||
                getCriteria().getCriteria()==null || getCriteria().getCriteria().isEmpty());
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
        StringBuilder sb=new StringBuilder();
        
        sb.append("IID_").append(getIndicatorID());
        
        if(!StringUtils.isEmpty(scenarioID))
        {
            sb.append(".SID_").append(getScenarioID());
        }
        
        if(!StringUtils.isEmpty(ecstateID))
        {
            sb.append(".EID_").append(getEcstateID());
        }
        
        return sb.toString();
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
     * @return the scenarioID
     */
    @XmlElement(name="scenarioID")
    public String getScenarioID() {
        return scenarioID;
    }

    /**
     * @param scenarioID the scenarioID to set
     */
    public void setScenarioID(String scenarioID) {
        this.scenarioID = scenarioID;
    }

    /**
     * @return the ecstateID
     */
    @XmlElement(name="ecstateID")
    public String getEcstateID() {
        return ecstateID;
    }

    /**
     * @param ecstateID the ecstateID to set
     */
    public void setEcstateID(String ecstateID) {
        this.ecstateID = ecstateID;
    }

    /**
     * @return the weight
     */
    @XmlElement(name="weight", required=true)
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the parameter
     */
    @XmlElement(name="parameter", required=true)
    public BigDecimal getParameter() {
        return parameter;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(BigDecimal parameter) {
        this.parameter = parameter;
    }

    /**
     * @return the criteria
     */
    @XmlElement(name="criteria", required=true)
    public DecisionCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(DecisionCriteria criteria) {
        this.criteria = criteria;
    }
    
}
