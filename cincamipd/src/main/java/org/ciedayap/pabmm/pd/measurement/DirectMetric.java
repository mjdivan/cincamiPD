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
 * It represents the metric which each value is obtained through direct observation.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DirectMetric")
@XmlType(propOrder={"observation","method"})
public class DirectMetric extends Metric{
    /**
     * Optional observations about the direct metric
     */
    private String observation;
    /**
     * The used measurement method 
     */
    private MeasurementMethod method;
    
    /**
     * Default constructor
     */
    public DirectMetric()
    {
        super();
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The metric ID
     * @param name The metric name
     * @param attrid The attribute ID associated with the metric
     * @param scale The scale related to the metric
     * @param sources The possible data sources related to the metric
     * @param method The used measurement method
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public DirectMetric(String id,String name, String attrid, Scale scale, DataSources sources,MeasurementMethod method) throws EntityPDException
    {
        super(id,name,attrid,scale,sources);
        if(method==null || !method.isDefinedProperties()) throw new EntityPDException("The method is not defined");       
        
        this.method=method;
    }
    
    /**
     * A basic factory method
     * @param id The metric ID
     * @param name The metric name
     * @param attrid The attribute ID associated with the metric
     * @param scale The scale related to the metric
     * @param sources The possible data sources related to the metric
     * @param method The used measurement method
     * @return A new DirectMetric´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static DirectMetric create(String id,String name, String attrid, Scale scale, DataSources sources, MeasurementMethod method) throws EntityPDException
    {
        return new DirectMetric(id,name,attrid,scale, sources,method);
    }

    /**
     * @return the observation
     */
    @XmlElement(name="observation")
    public String getObservation() {
        return observation;
    }

    /**
     * @param observation the observation to set
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    /**
     * @return the method
     */
    @XmlElement(name="MeasurementMethod", required=true)
    public MeasurementMethod getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(MeasurementMethod method) {
        this.method = method;
    }
    
   @Override
   public boolean isDefinedProperties() {
       if(!super.isDefinedProperties()) return false;
       
       return !(method==null || !method.isDefinedProperties());
   }    
}
