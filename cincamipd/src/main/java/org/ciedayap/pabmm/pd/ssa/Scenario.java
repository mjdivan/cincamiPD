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

/**
 * It defines an analysis scenario
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Scenario")
@XmlType(propOrder={"scenarioProperties"})
public class Scenario extends State implements Serializable, SingleConcept{
    private ScenarioProperties scenarioProperties;
    
    public Scenario()
    {
        super();
        scenarioProperties=null;
    }

    public Scenario(String id, String pname, BigDecimal elikelihood,BigDecimal tlikelihood,
            ScenarioProperties sce,StateTransitionModel tm) throws SSAException
    {
        super(id,pname,elikelihood,tlikelihood,tm);
        
        if(sce==null || sce.isEmpty()) throw new SSAException("The Scenario Properties are not defined");

        scenarioProperties=sce;     
    }
    
    /**
     * Default factory method
     * @return A new Scenario instance
     */
    public synchronized static Scenario create()
    {
        return new Scenario();
    }
    
    /**
     * An Alternative factory method
     * @param id The Scenario ID
     * @param pname The Scenario Name
     * @param elikelihood The empirical likelihood related to the Scenario
     * @param tlikelihood The Theoretical likelihood related to the Scenario
     * @param sce The Scenario Properties
     * @param tm The Transition Model associated with the Scenario
     * @return A new Scenario Instance
     * @throws SSAException It is raised when some component is incomplete of not-well defined
     */
    public synchronized static Scenario create(String id, String pname, BigDecimal elikelihood,BigDecimal tlikelihood,
            ScenarioProperties sce,StateTransitionModel tm) throws SSAException
    {
        return new Scenario(id,pname,elikelihood,tlikelihood,sce,tm);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(scenarioProperties==null || !scenarioProperties.isEmpty()); 
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
     * @return the scenarioProperties
     */
    @XmlElement(name="scenarioProperties", required=true) 
    public ScenarioProperties getScenarioProperties() {
        return scenarioProperties;
    }

    /**
     * @param scenarioProperties the scenarioProperties to set
     */
    public void setScenarioProperties(ScenarioProperties scenarioProperties) {
        this.scenarioProperties = scenarioProperties;
    }
}
