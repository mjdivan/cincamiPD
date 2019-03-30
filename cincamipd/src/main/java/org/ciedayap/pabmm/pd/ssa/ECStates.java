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
@XmlRootElement(name="ECStates")
@XmlType(propOrder={"ecstates"})
public class ECStates implements Serializable{
    private ArrayList<ECState> ecstates;
    
    public ECStates()
    {
        ecstates=new ArrayList();
    }

    public synchronized static ECStates create()
    {
        return new ECStates();
    }
    
    /**
     * @return the ecstates
     */
    @XmlElement(name="ECState", required=true)
    public ArrayList<ECState> getEcstates() {
        return ecstates;
    }

    /**
     * @param ecstates the ecstates to set
     */
    public void setEcstates(ArrayList<ECState> ecstates) {
        this.ecstates = ecstates;
    }
    
    public boolean isEmpty()
    {
        if(ecstates==null) return true;
        return ecstates.isEmpty();
    }    
}
