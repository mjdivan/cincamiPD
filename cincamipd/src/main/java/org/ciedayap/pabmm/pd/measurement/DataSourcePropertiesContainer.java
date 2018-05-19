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
 * It represents a set of DataSourceProperties instances
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourcePropertiesContainer")
@XmlType(propOrder={"characteristics"})
public class DataSourcePropertiesContainer implements Serializable{
    private ArrayList<DataSourceProperties> characteristics;
    
    /**
     * Default constructor
     */
    public DataSourcePropertiesContainer()
    {
        characteristics=new ArrayList<>();
    }

    /**
     * @return the characteristics
     */
    @XmlElement(name="DataSourceProperties", required=true)
    public ArrayList<DataSourceProperties> getCharacteristics() {
        return characteristics;
    }

    /**
     * @param characteristics the characteristics to set
     */
    public void setCharacteristics(ArrayList<DataSourceProperties> characteristics) {
        this.characteristics = characteristics;
    }
    
}
