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
 * It represents a Global Indicator for evaluating the weighted results associated
 * with a calculable concept using others global and elementary indicator. 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="GlobalIndicator")
@XmlType(propOrder={"operator","related","modeledBy"})
public class GlobalIndicator extends Indicator implements Serializable, SingleConcept{
    /**
     * The set of Indicators used for computing the GlobalIndicator´s value
     */
    private Indicators related;
    /**
     * The operation which is used for relationship each Indicator´s value along
     * the set of Indicators belonging to the GlobalIndicator
     */
    private OperatorType operator;
    /**
     * The model responsible for interpreting the Global Indicator´s value
     */
    private GlobalModel modeledBy;
    
    /**
     * Default Constructor
     */
    public GlobalIndicator()
    {
        super();
        related=new Indicators();
        operator=OperatorType.ADDITIVE;
    }
    
    /**
     * A constructor oriented to the mandatory attributes
     * @param id The Indicator´s ID
     * @param name The Indicator´s name
     * @param weight The indicator´s weight
     * @param scale The Indicator scale
     * @param rel The Related Indicators
     * @param kind The kind of operation for summarizing the related indicators
     * @param gm The global model for interpreting the indicator´s value
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public GlobalIndicator(String id, String name, BigDecimal weight, Scale scale,
            Indicators rel, OperatorType kind, GlobalModel gm) throws EntityPDException
    {
        super(id,name,weight,scale);
        
        if(rel==null || rel.getIndicators()==null || rel.getIndicators().isEmpty())
            throw new EntityPDException("The indicator list is empty");
        
        if(kind==null)
            throw new EntityPDException("The Operator Kind is not indicated");
        
        if(gm==null || !gm.isDefinedProperties())
            throw new EntityPDException("The Global Model is not defined");
        
        this.related=rel;
        this.operator=kind;
        this.modeledBy=gm;
    }
    /**
     * A basic factory method
     * @param id The Indicator´s ID
     * @param name The Indicator´s name
     * @param weight The indicator´s weight
     * @param scale The Indicator scale
     * @param rel The Related Indicators
     * @param kind The kind of operation for summarizing the related indicators
     * @param gm The global model for interpreting the indicator´s value
     * @return A new GlobalIndicator´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static GlobalIndicator create(String id, String name, BigDecimal weight, Scale scale,
            Indicators rel, OperatorType kind, GlobalModel gm) throws EntityPDException
    {
        return new GlobalIndicator(id,name,weight,scale,rel,kind,gm);
    }

    /**
     * @return the related
     */
    @XmlElement(name="Indicators", required=true)
    public Indicators getRelated() {
        return related;
    }

    /**
     * @param related the related to set
     */
    public void setRelated(Indicators related) {
        this.related = related;
    }

    /**
     * @return the operator
     */
    @XmlElement(name="OperatorType", required=true)
    public OperatorType getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(OperatorType operator) {
        this.operator = operator;
    }

    /**
     * @return the modeledBy
     */
    @XmlElement(name="GlobalModel", required=true)
    public GlobalModel getModeledBy() {
        return modeledBy;
    }

    /**
     * @param modeledBy the modeledBy to set
     */
    public void setModeledBy(GlobalModel modeledBy) {
        this.modeledBy = modeledBy;
    }
    
}
