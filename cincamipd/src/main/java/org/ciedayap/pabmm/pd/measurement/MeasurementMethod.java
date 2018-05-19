/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents the measurement method concept
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="MeasurementMethod")
@XmlType(propOrder={"technique"})
public class MeasurementMethod extends Method{
    /**
     * The technique associated with the measurement method
     */
    private String technique;
    
    /**
     * Default constructor
     */
    public MeasurementMethod()
    {
        super();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The method id
     * @param name The method name
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public MeasurementMethod(String id, String name) throws EntityPDException
    {
        super(id,name);
    }

    /**
     * A basic factory method
     * @param id The method id
     * @param name The method name
     * @return A new MeasurementMethod´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static MeasurementMethod create(String id,String name) throws EntityPDException
    {
        return new MeasurementMethod(id,name);
    }
    
    /**
     * @return the technique
     */
    @XmlElement(name="technique")
    public String getTechnique() {
        return technique;
    }

    /**
     * @param technique the technique to set
     */
    public void setTechnique(String technique) {
        this.technique = technique;
    }
}
