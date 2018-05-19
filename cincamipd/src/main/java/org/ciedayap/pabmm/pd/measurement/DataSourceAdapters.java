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
 * It represents a set of data source adapter.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceAdapters")
@XmlType(propOrder={"adapters"})
public class DataSourceAdapters implements Serializable {
    private ArrayList<DataSourceAdapter> adapters;
    
    /**
     * Default constructor
     */
    public DataSourceAdapters()
    {
        adapters=new ArrayList<>();
    }

    /**
     * @return the adapters
     */
    @XmlElement(name="DataSourceAdapter", required=true)
    public ArrayList<DataSourceAdapter> getAdapters() {
        return adapters;
    }

    /**
     * @param adapters the adapters to set
     */
    public void setAdapters(ArrayList<DataSourceAdapter> adapters) {
        this.adapters = adapters;
    }
}
