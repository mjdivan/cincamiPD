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
 * It defines each particular state
 * 
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="ECStateProperties")
@XmlType(propOrder={"stateItems"})
public class ECStateProperties implements Serializable{
    private ArrayList<ECMetadata> stateItems;
    
    public ECStateProperties()
    {
        stateItems=new ArrayList();
    }
    
    public synchronized static ECStateProperties create()
    {
        return new ECStateProperties();
    }
    
    public boolean isEmpty() {
        if(stateItems==null) return true;
        return stateItems.isEmpty();
    }

    /**
     * @return the stateItems
     */
    @XmlElement(name="stateItem", required=true)
    public ArrayList<ECMetadata> getStateItems() {
        return stateItems;
    }

    /**
     * @param stateItems the stateItems to set
     */
    public void setStateItems(ArrayList<ECMetadata> stateItems) {
        this.stateItems = stateItems;
    }
}
