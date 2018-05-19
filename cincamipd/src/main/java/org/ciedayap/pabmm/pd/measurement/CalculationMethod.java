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
 * This class represents the calculation method related to a metric or indicator
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="CalculationMethod")
@XmlType(propOrder={"type"})
public class CalculationMethod extends Method{
    /**
     * Type of Calculation method e.g. estimated, deterministic, etc.
     */
    private String type;
    
    /**
     * Default constructor
     */
    public CalculationMethod()
    {
        super();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The calculation method id
     * @param name  The calculation metho name
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException It is raised when some mandatory attribute is not defined
     */
    public CalculationMethod(String id,String name) throws EntityPDException
    {
        super(id,name);
    }
    
    /**
     * A basic factory method
     * @param id The calculation method id
     * @param name The calculation method name
     * @return A new CalculationMethod´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not raised
     */
    public static CalculationMethod create(String id, String name) throws EntityPDException
    {
        return new CalculationMethod(id,name);
    }

    /**
     * @return the type
     */
    @XmlElement(name="type")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
}
