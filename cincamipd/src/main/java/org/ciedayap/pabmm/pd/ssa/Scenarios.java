/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author mjdivan
 */
@XmlRootElement(name="Scenarios")
@XmlType(propOrder={"scenarios"})
public class Scenarios implements Serializable{
    private ArrayList<Scenario> scenarios;
    
    public Scenarios()
    {
        scenarios=new ArrayList();
    }

    public synchronized static Scenarios create()
    {
        return new Scenarios();
    }
    
    /**
     * @return the scenarios
     */
    @XmlElement(name="Scenario", required=true)
    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    /**
     * @param scenarios the scenarios to set
     */
    public void setScenarios(ArrayList<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    
    public boolean isEmpty()
    {
        if(scenarios==null) return true;
        return scenarios.isEmpty();
    }
}
