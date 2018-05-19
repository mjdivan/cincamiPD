/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It represents a set of Unit´s instances
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Units")
@XmlType(propOrder={"units"})
public class Units implements Serializable{
    private ArrayList<Unit> units;
    
    /**
     * Default constructor
     */
    public Units()
    {
        units=new ArrayList<>();
    }

    /**
     * @return the units
     */
    @XmlElement(name="Unit", required=true)
    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }
    
    
}
