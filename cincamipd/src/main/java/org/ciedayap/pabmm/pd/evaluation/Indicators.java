/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It represents a set of Indicator useful for the global evaluation
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Indicators")
@XmlType(propOrder={"indicators"})
public class Indicators implements Serializable{
    private ArrayList<Indicator> indicators;
    
    /**
     * Default constructor
     */
    public Indicators()
    {
        indicators=new ArrayList<>();
    }

    /**
     * @return the indicators
     */
    @XmlElement(name="Indicator", required=true)
    public ArrayList<Indicator> getIndicators() {
        return indicators;
    }

    /**
     * @param indicators the indicators to set
     */
    public void setIndicators(ArrayList<Indicator> indicators) {
        this.indicators = indicators;        
    }
}
