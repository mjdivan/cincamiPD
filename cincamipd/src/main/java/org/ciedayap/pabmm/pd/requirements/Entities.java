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
 * This class is responsible for managing a set of Entity instances
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Entities")
@XmlType(propOrder={"entitiesList"})
public class Entities implements Serializable
{
    /**
     * container for a set of Entity instances
     */
    private ArrayList<Entity> entitiesList;
    
    /**
     * Default constructor
     */
    public Entities()
    {
        entitiesList=new ArrayList<>();
    }

    /**
     * @return the entitiesList
     */
    @XmlElement(name="Entity", required=true)
    public ArrayList<Entity> getEntitiesList() {
        return entitiesList;
    }

    /**
     * @param entitiesList the entitiesList to set
     */
    public void setEntitiesList(ArrayList<Entity> entitiesList) {
        this.entitiesList = entitiesList;
    }   
}
