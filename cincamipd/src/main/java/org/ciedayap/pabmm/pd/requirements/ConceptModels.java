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
 * It represents a manager for many ConceptModel Instances
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ConceptModels")
@XmlType(propOrder={"representedList"})
public class ConceptModels implements Serializable
{
    /**
     * The list of Concept Model which are used as representation for a given 
     * calculable concept
     */
    private ArrayList<ConceptModel> representedList;
    
    /**
     * Default constructor
     */
    public ConceptModels()
    {
        representedList=new ArrayList();
    }

    /**
     * @return the representedList
     */
    @XmlElement(name="ConceptModel", required=true)
    public ArrayList<ConceptModel> getRepresentedList() {
        return representedList;
    }

    /**
     * @param representedList the representedList to set
     */
    public void setRepresentedList(ArrayList<ConceptModel> representedList) {
        this.representedList = representedList;
    }
}
