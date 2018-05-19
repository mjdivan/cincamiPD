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
 * It represents a set of attribute which describe the characteristics associated
 * with a given entity category.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Attributes")
@XmlType(propOrder={"characteristics"})
public class Attributes implements Serializable
{
    private ArrayList<Attribute> characteristics;
    
    public Attributes()
    {
        characteristics=new ArrayList<>();
    }

    /**
     * @return the characteristics
     */
    @XmlElement(name="Attribute", required=true)
    public ArrayList<Attribute> getCharacteristics() {
        return characteristics;
    }

    /**
     * @param characteristics the characteristics to set
     */
    public void setCharacteristics(ArrayList<Attribute> characteristics) {
        this.characteristics = characteristics;
    }
}
