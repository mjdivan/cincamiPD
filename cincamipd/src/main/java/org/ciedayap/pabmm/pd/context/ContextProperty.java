

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
import org.ciedayap.pabmm.pd.measurement.Metrics;
import org.ciedayap.pabmm.pd.requirements.Attribute;


/**
 * It represents a context characteristic
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ContextProperty")
@XmlType(propOrder={"relevance","related"})
public class ContextProperty extends Attribute implements Serializable, SingleConcept{
    /**
     * The relevance related to the context property in terms of the entity under analysis
     */
    private String relevance;
    private ContextProperties related;
    
    /**
     * Default constructor
     */
    public ContextProperty()
    {
        super();
        related=new ContextProperties();
    }
    
    /**
     * Constructor associated with the mandatory attributes.
     * @param ID The identification related to context property
     * @param name The descriptive name related to the context property
     * @param quantifiedBy The metric responsible for the context property quantification 
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public ContextProperty(String ID, String name, Metrics quantifiedBy) throws EntityPDException
    {
        super(ID,name,quantifiedBy);
        related=new ContextProperties();
    }
    
    /**
     * A Basic factory method.
     * @param ID The context property id
     * @param name The context property name
     * @param quantifiedBy The metric responsible for the context property quantification
     * @return A new Context property instance, null otherwise
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public static ContextProperty create(String ID, String name, Metrics quantifiedBy) throws EntityPDException
    {
        return new ContextProperty(ID,name,quantifiedBy);
    }
    
    /**
     * @return the relevance
     */
    @XmlElement(name="relevance")
    public String getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    /**
     * @return the related
     */
    @XmlElement(name="ContextProperties")
    public ContextProperties getRelated() {
        return related;
    }

    /**
     * @param related the related to set
     */
    public void setRelated(ContextProperties related) {
        this.related = related;
    }
   
}
