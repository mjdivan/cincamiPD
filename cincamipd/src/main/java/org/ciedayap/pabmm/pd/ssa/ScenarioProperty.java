/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.context.ContextProperty;
import org.ciedayap.utils.StringUtils;

/**
 * It characterizes a property for a given scenario
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ScenarioProperty")
@XmlType(propOrder={"ID","name","range"})
public class ScenarioProperty implements Serializable, SingleConcept{
    private String ID;
    private String name;
    private Range range;

    public ScenarioProperty()
    {
        ID=null;
        name=null;
        range=new Range();
    }
    
    public ScenarioProperty(String pid, String pname, Range r) throws SSAException
    {
        if(StringUtils.isEmpty(pid)) throw new SSAException("The ID is not defined");
        if(StringUtils.isEmpty(pname)) throw new SSAException("The name is not defined");
        if(r==null || !r.isDefinedProperties()) throw new SSAException("The Range is not adequately defined");
        
        ID=pid;
        name=pname;
        range=r;        
    }
    
    public ScenarioProperty(ContextProperty cp, Range r) throws SSAException
    {
        if(!cp.isDefinedProperties()) throw new SSAException("The indicated context property is incomplete");        
        if(range==null || !range.isDefinedProperties()) throw new SSAException("The Range is not adequately defined");

        ID=cp.getID();
        name=cp.getName();
        range=r;        
    }
    
    public synchronized static ScenarioProperty create()
    {
        return new ScenarioProperty();
    }
    
    public synchronized static ScenarioProperty create(String pid, String pname, Range range) throws SSAException
    {
        return new ScenarioProperty(pid,pname,range);
    }
    
    public synchronized static ScenarioProperty create(ContextProperty cp, Range r) throws SSAException
    {
        return new ScenarioProperty(cp,r);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || range==null || !range.isDefinedProperties());
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
        return getID();
    }

    /**
     * @return the ID
     */
    @XmlElement(name="ID", required=true)
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
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
     * @return the range
     */
    @XmlElement(name="range", required=true)
    public Range getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(Range range) {
        this.range = range;
    }
    
}
