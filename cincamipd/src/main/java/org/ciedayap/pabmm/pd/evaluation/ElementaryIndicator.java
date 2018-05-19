/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.pabmm.pd.measurement.Scale;

/**
 * It represents the elementary indicator
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ElementaryIndicator")
@XmlType(propOrder={"modeledBy"})
public class ElementaryIndicator extends Indicator implements Serializable, SingleConcept{
    private ElementaryModel modeledBy;
    
    /**
     * Default constructor
     */
    public ElementaryIndicator()
    {
        super();
        modeledBy=new ElementaryModel();
    }
    
    /**
     * A constructor oriented to the mandatory attributes
     * @param id The unique ID related to the indicator
     * @param name The indicator´s name
     * @param weight The indicator´s weight
     * @param scale The indicator´s scale
     * @param em The elementary model used for interpretation of each metric´s value
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public ElementaryIndicator(String id, String name, BigDecimal weight, Scale scale,ElementaryModel em) throws EntityPDException
    {
        super(id,name,weight,scale);
        if(em== null || !em.isDefinedProperties()) throw new EntityPDException("The Elementary model is not correctly defined");
        
        this.modeledBy=em;
    }
    
    /**
     * A basic factory method
     * @param id The unique ID related to the indicator
     * @param name The indicator´s name
     * @param weight The indicator´s weight
     * @param scale The indicator´s scale
     * @param em The elementary model used for interpretation of each metric´s value
     * @return A new ElementaryIndicator´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static ElementaryIndicator create(String id, String name, BigDecimal weight, Scale scale,ElementaryModel em) throws EntityPDException
    {
        return new ElementaryIndicator(id,name,weight,scale,em);
    }

    /**
     * @return the modeledBy
     */
    @XmlElement(name="ElementaryModel", required=true)
    public ElementaryModel getModeledBy() {
        return modeledBy;
    }

    /**
     * @param modeledBy the modeledBy to set
     */
    public void setModeledBy(ElementaryModel modeledBy) {
        this.modeledBy = modeledBy;
    }
    
}
