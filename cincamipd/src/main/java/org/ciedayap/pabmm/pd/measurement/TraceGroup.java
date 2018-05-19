/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * This class represents the grouping strategy of data sources oriented to joint analysis of the measures.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="TraceGroup")
@XmlType(propOrder={"traceGroupID","name","definition"})
public class TraceGroup implements Serializable, SingleConcept {
    /**
     * TraceGroup´s ID
     */
    private String traceGroupID;
    /**
     * TraceGroup´s name
     */
    private String name;
    /**
     * TraceGroup´s definition
     */
    private String definition;

    /**
     * Default Constructor
     */
    public TraceGroup()
    {
        
    }
    
    /**
     * Constructor associated wiith the mandatory attributes
     * @param id TraceGroup´s ID
     * @param pname TraceGroup´s name
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public TraceGroup(String id,String pname) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The Trace Group ID is not defined");
        if(StringUtils.isEmpty(pname)) throw new EntityPDException("The name related to the TraceGroup is not defined");
        
        this.traceGroupID=id;
        this.name=pname;
    }
    
    /**
     * A basic factory method
     * @param id TraceGroup´s ID
     * @param name TraceGroup´s name
     * @return A new TraceGroup´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static TraceGroup create(String id,String name) throws EntityPDException
    {
        return new TraceGroup(id,name);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(this.traceGroupID));
    }

    @Override
    public String getCurrentClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getQualifiedCurrentClassName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public String getUniqueID() {
        return this.getTraceGroupID();
    }

    /**
     * @return the traceGroupID
     */
    @XmlElement(name="traceGroupID", required=true)
    public String getTraceGroupID() {
        return traceGroupID;
    }

    /**
     * @param traceGroupID the traceGroupID to set
     */
    public void setTraceGroupID(String traceGroupID) {
        this.traceGroupID = traceGroupID;
    }

    /**
     * @return the name
     */
    @XmlElement(name="name", required=true)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the definition
     */
    @XmlElement(name="definition")
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
}
