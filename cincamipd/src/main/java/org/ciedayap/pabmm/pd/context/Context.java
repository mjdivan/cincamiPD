/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.context;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.pabmm.pd.requirements.Entity;

/**
 * It represents the entity´s context
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Context")
@XmlType(propOrder={"describedBy"})
public class Context extends Entity implements Serializable,SingleConcept{
    private ContextProperties describedBy;
   /**
    * Default constructor
    */
    public Context()
    {
        super();
        describedBy=new ContextProperties();
    }
    
    /**
     * Constructor associated with the mandatory attributes
     * @param ID The identification related to the context
     * @param name A descriptive name related to the context
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public Context(String ID,String name) throws EntityPDException
    {
        super(ID,name);
        describedBy=new ContextProperties();
    }
    
    /**
     * A basic factory method
     * @param ID The identification related to the context
     * @param name A descriptive name related to the context
     * @return A new Context´s instance
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public static Context create(String ID, String name) throws EntityPDException
    {
        return new Context(ID, name);
    }

    /**
     * @return the describedBy
     */
    @XmlElement(name="ContextProperties")
    public ContextProperties getDescribedBy() {
        return describedBy;
    }

    /**
     * @param describedBy the describedBy to set
     */
    public void setDescribedBy(ContextProperties describedBy) {
        this.describedBy = describedBy;
    }
}
