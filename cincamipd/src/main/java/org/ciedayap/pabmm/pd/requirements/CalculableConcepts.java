/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.requirements;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It is responsible for the managing of a set of CalculableConcept instances
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="CalculableConcepts")
@XmlType(propOrder={"calculableConcepts"})
public class CalculableConcepts implements Serializable{
    private ArrayList<CalculableConcept> calculableConcepts;
    
    public CalculableConcepts()
    {
        calculableConcepts=new ArrayList<>();
    }

    /**
     * @return the calculableConcepts
     */
    @XmlElement(name="CalculableConcept", required=true)
    public ArrayList<CalculableConcept> getCalculableConcepts() {
        return calculableConcepts;
    }

    /**
     * @param calculableConcepts the calculableConcepts to set
     */
    public void setCalculableConcepts(ArrayList<CalculableConcept> calculableConcepts) {
        this.calculableConcepts = calculableConcepts;
    }
 
}
