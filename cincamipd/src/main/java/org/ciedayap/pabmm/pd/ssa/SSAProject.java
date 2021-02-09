/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.utils.StringUtils;

/**
 * It contains the definition of scenarios and states for a given project
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="SSAProject")
@XmlType(propOrder={"projectID","scenarios","ecstates","weightedindicators"})
public class SSAProject implements Serializable, SingleConcept {
    /**
     * It is the unique identification for the M and E project
     */
    private String projectID;
    /**
     * It contains the scenario definition
     */
    private Scenarios scenarios;
    /**
     * It contains the definition of each Entity Category State
     */
    private ECStates ecstates;
    /**
     * It defines the weighting for each combination between Scenario and/OR ECState
     */
    private WeightedIndicators weightedindicators;
    
    public SSAProject()
    {
        projectID="1";
    }
    
    public SSAProject(String pid, Scenarios sce,ECStates ecs, WeightedIndicators wi) throws SSAException
    {
        if(StringUtils.isEmpty(pid)) throw new SSAException("The Project id is not defined");
        if(sce==null || sce.isEmpty()) throw new SSAException("There is not scenario definition");
        if(ecs==null || ecs.isEmpty()) throw new SSAException("There is not ECStates definition");
        if(wi==null || wi.isEmpty()) throw new SSAException("There is not Indicator definition");
        projectID=pid;
        scenarios=sce;
        ecstates=ecs;
        weightedindicators=wi;        
    }
    
    public synchronized static SSAProject create()
    {
        return new SSAProject();
    }
    
    public synchronized static SSAProject create(String pid, Scenarios sce,ECStates ecs, WeightedIndicators wi) throws SSAException
    {
        return new SSAProject(pid,sce,ecs,wi);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(projectID) ||
                    scenarios==null || scenarios.isEmpty() ||
                    ecstates==null || ecstates.isEmpty() ||
                    weightedindicators==null || weightedindicators.isEmpty()); 
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
        return getProjectID();
    }

    /**
     * @return the projectID
     */
    @XmlElement(name="ID", required=true)    
    public String getProjectID() {
        return projectID;
    }

    /**
     * @param projectID the projectID to set
     */
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    /**
     * @return the scenarios
     */
    @XmlElement(name="Scenarios", required=true)
    public Scenarios getScenarios() {
        return scenarios;
    }

    /**
     * @param scenarios the scenarios to set
     */
    public void setScenarios(Scenarios scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * @return the ecstates
     */
    @XmlElement(name="ECStates", required=true)
    public ECStates getEcstates() {
        return ecstates;
    }

    /**
     * @param ecstates the ecstates to set
     */
    public void setEcstates(ECStates ecstates) {
        this.ecstates = ecstates;
    }

    /**
     * @return the weightedindicators
     */
    @XmlElement(name="WeightedIndicators", required=true)
    public WeightedIndicators getWeightedindicators() {
        return weightedindicators;
    }

    /**
     * @param weightedindicators the weightedindicators to set
     */
    public void setWeightedindicators(WeightedIndicators weightedindicators) {
        this.weightedindicators = weightedindicators;
    }
    
}
