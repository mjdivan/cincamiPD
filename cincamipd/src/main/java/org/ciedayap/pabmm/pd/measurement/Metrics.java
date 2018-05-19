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
 * It represents a set of Direct and Indirect metrics used for computing a given indirect metric
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Metrics")
@XmlType(propOrder={"related"})
public class Metrics implements Serializable{
    private ArrayList<Metric> related;
    
    /**
     * Default constructor
     */
    public Metrics()
    {
        related=new ArrayList<>();
    }

    /**
     * @return the related
     */
    @XmlElement(name="Metric", required=true)
    public ArrayList<Metric> getRelated() {
        return related;
    }

    /**
     * @param related the related to set
     */
    public void setRelated(ArrayList<Metric> related) {
        this.related = related;
    }
    
}
