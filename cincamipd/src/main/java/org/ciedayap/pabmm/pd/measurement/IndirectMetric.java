/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents the metric in where each value is obtained from the combination 
 * of ohter metric´s values.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="IndirectMetric")
@XmlType(propOrder={"formula","method","related"})
public class IndirectMetric extends Metric{
    /**
     * Formula for computing the metric´s value
     */
    private String formula;
    /**
     * The used measurement method 
     */
    private CalculationMethod method;
    /**
     * The definition of the related metrics which are present in the formula
     */
    private Metrics related;
    
    /**
     * Default constructor
     */
    public IndirectMetric()
    {
        super();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The metric ID
     * @param name The metric name
     * @param attrid The attribute ID associated with the metric
     * @param scale The scale related to the metric
     * @param formula formula for computing the metric´s value
     * @param sources The possible data sources related to the metric
     * @param method The used measurement method
     * @param related The metric definition related to the formula
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public IndirectMetric(String id,String name, String attrid, Scale scale,String formula,DataSources sources, CalculationMethod method,Metrics related) throws EntityPDException
    {
        super(id,name,attrid,scale,sources);
        
        if(method==null || !method.isDefinedProperties()) throw new EntityPDException("The method is not defined");
        if(StringUtils.isEmpty(formula)) throw new EntityPDException("The formula is not defined");
        if(related==null || related.getRelated()==null || 
                related.getRelated().size()<2) throw new EntityPDException("The method is not defined or incomplete (< 2 metrics)");
        
        this.method=method;
        this.formula=formula;
        this.related=related;
    }
    
    /**
     * A basic factory method
     * @param id The metric ID
     * @param name The metric name
     * @param attrid The attribute ID associated with the metric
     * @param scale The scale related to the metric
     * @param formula The formula for computing the metric´s value
     * @param sources The possible data sources related to the metric
     * @param method The used measurement method
     * @param related The metric definition related to the formula
     * @return A new DirectMetric´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static IndirectMetric create(String id,String name, String attrid, Scale scale,String formula, DataSources sources, CalculationMethod method,
            Metrics related) throws EntityPDException
    {
        return new IndirectMetric(id,name,attrid,scale, formula, sources, method,related);
    }

    /**
     * @return the observation
     */
    @XmlElement(name="formula", required=true)
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the method
     */
    @XmlElement(name="CalculationMethod", required=true)
    public CalculationMethod getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(CalculationMethod method) {
        this.method = method;
    }
    
   @Override
   public boolean isDefinedProperties() {
       if(!super.isDefinedProperties()) return false;
       
       return !(method==null || !method.isDefinedProperties() || StringUtils.isEmpty(formula) ||
               related==null || related.getRelated()==null || related.getRelated().size()<2);
   }    

    /**
     * @return the related
     */
    @XmlElement(name="Metrics", required=true)
    public Metrics getRelated() {
        return related;
    }

    /**
     * @param related the related to set
     */
    public void setRelated(Metrics related) {
        this.related = related;
    }
    
}
