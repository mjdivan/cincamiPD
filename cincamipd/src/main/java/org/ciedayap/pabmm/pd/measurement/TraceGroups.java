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
 * It represents a set of TraceGroup instances
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="TraceGroups")
@XmlType(propOrder={"groups"})
public class TraceGroups implements Serializable{
    private ArrayList<TraceGroup> groups;
    
    /**
     * Default constructor
     */
    public TraceGroups()
    {
        groups=new ArrayList<>();
    }

    /**
     * @return the groups
     */
    @XmlElement(name="TraceGroup", required=true)
    public ArrayList<TraceGroup> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(ArrayList<TraceGroup> groups) {
        this.groups = groups;
    }
}
