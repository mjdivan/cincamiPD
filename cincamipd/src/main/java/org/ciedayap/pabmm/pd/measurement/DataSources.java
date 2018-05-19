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
 * A set of data source
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSources")
@XmlType(propOrder={"sources"})
public class DataSources implements Serializable{
    /**
     * A set of data sources
     */
    private ArrayList<DataSource> sources;
    
    /**
     * Default constructor
     */
    public DataSources()
    {
        sources=new ArrayList<>();        
    }

    /**
     * @return the sources
     */
    @XmlElement(name="DataSource", required=true)
    public ArrayList<DataSource> getSources() {
        return sources;
    }

    /**
     * @param sources the sources to set
     */
    public void setSources(ArrayList<DataSource> sources) {
        this.sources = sources;
    }
}
