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
@XmlRootElement(name="WeightedIndicators")
@XmlType(propOrder={"weightedIndicators"})
public class WeightedIndicators implements Serializable{
    private ArrayList<WeightedIndicator> weightedIndicators;
    
    public WeightedIndicators()
    {
        weightedIndicators=new ArrayList();
    }
    
    public synchronized static WeightedIndicators create()
    {
        return new WeightedIndicators();
    }

    /**
     * @return the weightedIndicators
     */
    @XmlElement(name="WeightedIndicators", required=true)
    public ArrayList<WeightedIndicator> getWeightedIndicators() {
        return weightedIndicators;
    }

    /**
     * @param weightedIndicators the weightedIndicators to set
     */
    public void setWeightedIndicators(ArrayList<WeightedIndicator> weightedIndicators) {
        this.weightedIndicators = weightedIndicators;
    }
    
    public boolean isEmpty()
    {
        if(weightedIndicators==null) return true;
        return weightedIndicators.isEmpty();
    }       
}
