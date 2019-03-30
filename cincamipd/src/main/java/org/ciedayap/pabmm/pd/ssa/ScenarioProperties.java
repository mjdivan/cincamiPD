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
@XmlRootElement(name="ScenarioProperties")
@XmlType(propOrder={"properties"})
public class ScenarioProperties implements Serializable{
    private ArrayList<ScenarioProperty> properties;
 
    public ScenarioProperties()
    {
        properties=new ArrayList();
    }

    public synchronized static ScenarioProperties create()
    {
        return new ScenarioProperties();
    }
    
    /**
     * @return the properties
     */
    @XmlElement(name="property", required=true)
    public ArrayList<ScenarioProperty> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(ArrayList<ScenarioProperty> properties) {
        this.properties = properties;
    }
    
    public boolean isEmpty()
    {
        if(properties==null) return true;
        return properties.isEmpty();
    }
}
