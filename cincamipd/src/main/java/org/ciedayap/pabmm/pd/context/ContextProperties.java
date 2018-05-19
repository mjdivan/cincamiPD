/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.context;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It is responsible for managing a set of ContextProperty instances
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ContextProperties")
@XmlType(propOrder={"contextProperties"})
public class ContextProperties implements Serializable{
    /**
     * List of ContextPropery instances
     */
    private ArrayList<ContextProperty> contextProperties;
    /**
     * Default constructor
     */
    public ContextProperties()
    {
        contextProperties=new ArrayList<>();
    }

    /**
     * @return the contextProperties
     */
    @XmlElement(name="ContextProperty", required=true)
    public ArrayList<ContextProperty> getContextProperties() {
        return contextProperties;
    }

    /**
     * @param contextProperties the contextProperties to set
     */
    public void setContextProperties(ArrayList<ContextProperty> contextProperties) {
        this.contextProperties = contextProperties;
    }
    
}
