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
@XmlRootElement(name="StateTransitionModel")
@XmlType(propOrder={"transitions"})     
public class StateTransitionModel implements Serializable{
    private ArrayList<StateTransition> transitions;
    
    public StateTransitionModel()
    {
        transitions=new ArrayList();
    }

    public synchronized static StateTransitionModel create()
    {
        return new StateTransitionModel();
    }
    
    /**
     * @return the transitions
     */
    @XmlElement(name="transition", required=true)
    public ArrayList<StateTransition> getTransitions() {
        return transitions;
    }

    /**
     * @param transitions the transitions to set
     */
    public void setTransitions(ArrayList<StateTransition> transitions) {
        this.transitions = transitions;
    }
    
    public boolean isEmpty()
    {
        if(transitions==null) return true;
        return transitions.isEmpty();
    }
    
}
