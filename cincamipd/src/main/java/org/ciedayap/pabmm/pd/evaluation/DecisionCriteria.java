/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It represents a set of decision criterion jointly analyzed.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DecisionCriteria")
@XmlType(propOrder={"criteria"})
public class DecisionCriteria {
    private ArrayList<DecisionCriterion> criteria;
    
    /**
     * Default constructor
     */
    public DecisionCriteria()
    {
        criteria=new ArrayList<>();
    }

    /**
     * @return the criteria
     */
    @XmlElement(name="DecisionCriterion", required=true)
    public ArrayList<DecisionCriterion> getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(ArrayList<DecisionCriterion> criteria) {
        if(!check(criteria)) return;
        criteria.sort(null);
        this.criteria = criteria;
    }
    
    /**
     * It is an auxiliar function for verificating the orden in the decision criteria
     * @param pcriteria The list with the decision criteria to be analyzed
     * @return Boolean is all in all the decision crition are not overlapped, FALSE otherwise.
     */
    private static boolean check(ArrayList<DecisionCriterion> pcriteria)
    {
        if(pcriteria==null || pcriteria.isEmpty() || pcriteria.size()==1) return true;
        pcriteria.sort(null);
        
        for(int i=1;i<pcriteria.size();i++)
        {
            DecisionCriterion previous=pcriteria.get(i-1);
            DecisionCriterion current=pcriteria.get(i);
            
            if(previous.getLowerThreshold().compareTo(current.getLowerThreshold())>=0) return false;//overlaping
            if(previous.getLowerThreshold().compareTo(current.getUpperThreshold())>=0) return false;//containing
            if(previous.getUpperThreshold().compareTo(current.getLowerThreshold())>=0) return false;//overlaping
            if(previous.getUpperThreshold().compareTo(current.getUpperThreshold())>=0) return false;//containing
        }
        
        return true;
    }    
}
