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
 *
 * @author mjdivan
 */
@XmlRootElement(name="Scenario")
@XmlType(propOrder={"ID","name","empiricalLikelihood","theoreticalLikelihood","stateTransitionModel"})
public class State implements Serializable, SingleConcept{
    private String ID;
    private String name;
    private BigDecimal empiricalLikelihood;
    private BigDecimal theoreticalLikelihood;
    private StateTransitionModel stateTransitionModel;

    public State()
    {
        ID="1.0";
        name="noname";
    }
    
    public State(String id,String pname, BigDecimal el,BigDecimal tl,StateTransitionModel stm) throws SSAException
    {
       if(StringUtils.isEmpty(id) || StringUtils.isEmpty(pname)) throw new SSAException("ID or name contains null");        
       if(stm==null || stm.isEmpty()) throw new SSAException("The Transition Model is empty");
       
       ID=id;
       name=pname;
       empiricalLikelihood=el;
       theoreticalLikelihood=tl;
       stateTransitionModel=stm;
    }
    
    public synchronized static State create()
    {
        return new State();
    }
    
    public synchronized static State create(String id,String pname, BigDecimal el,BigDecimal tl,StateTransitionModel stm) throws SSAException
    {
        return new State(id,pname,el,tl,stm);
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
     * @return the empiricalLikelihood
     */
    @XmlElement(name="empiricalLikelihood")   
    public BigDecimal getEmpiricalLikelihood() {
        return empiricalLikelihood;
    }

    /**
     * @param empiricalLikelihood the empiricalLikelihood to set
     */
    public void setEmpiricalLikelihood(BigDecimal empiricalLikelihood) {
        this.empiricalLikelihood = empiricalLikelihood;
    }

    /**
     * @return the theoreticalLikelihood
     */
    @XmlElement(name="theoreticalLikelihood") 
    public BigDecimal getTheoreticalLikelihood() {
        return theoreticalLikelihood;
    }

    /**
     * @param theoreticalLikelihood the theoreticalLikelihood to set
     */
    public void setTheoreticalLikelihood(BigDecimal theoreticalLikelihood) {
        this.theoreticalLikelihood = theoreticalLikelihood;
    }

    @Override
    public boolean isDefinedProperties() {
         return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name));
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
        return ID;
    }

    /**
     * @return the stateTransitionModel
     */
    @XmlElement(name="stateTransitionModel", required=true)    
    public StateTransitionModel getStateTransitionModel() {
        return stateTransitionModel;
    }

    /**
     * @param stateTransitionModel the stateTransitionModel to set
     */
    public void setStateTransitionModel(StateTransitionModel stateTransitionModel) {
        this.stateTransitionModel = stateTransitionModel;
    }
    
}
