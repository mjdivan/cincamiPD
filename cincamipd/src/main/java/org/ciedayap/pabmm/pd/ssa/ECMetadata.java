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
import org.ciedayap.pabmm.pd.requirements.Attribute;
import org.ciedayap.utils.StringUtils;

/**
 * It implementes the ECStateProperty interface, allowing a masquerade for the Attribute class too
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ECMetadata")
@XmlType(propOrder={"ID","name","range"})
public class ECMetadata implements Serializable, SingleConcept{
    private String ID;
    private String name;
    private Range range;
    
    public ECMetadata()
    {
        ID="1.0";
        name="noname";
        range=new Range();
    }
    
    public ECMetadata(String pid, String pname, Range r) throws SSAException
    {
        if(StringUtils.isEmpty(pid)) throw new SSAException("The ID is not defined");
        if(StringUtils.isEmpty(pname)) throw new SSAException("The name is not defined");
        if(r==null || !r.isDefinedProperties()) throw new SSAException("The Range is not adequately defined");
        
        ID=pid;
        name=pname;
        range=r;
    }
    
    public ECMetadata(Attribute a, Range r) throws SSAException
    {
        if(!a.isDefinedProperties()) throw new SSAException("The indicated attribute is incomplete");
        if(range==null || !range.isDefinedProperties()) throw new SSAException("The Range is not adequately defined");
        
        ID=a.getID();
        name=a.getName();
        range=r;
    }
    
    public static ECMetadata create()
    {
        return new ECMetadata();
    }
    
    public synchronized static ECMetadata create(String pid, String pname, Range r) throws SSAException
    {
        return new ECMetadata(pid,pname,r);
    }
    
    public synchronized static ECMetadata create(Attribute a, Range r) throws SSAException
    {
        return new ECMetadata(a,r);
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
        return this.getID();
    }

    @XmlElement(name="ID", required=true)
    public String getID() {
        return ID;
    }

    public String getDescription() {
        return getName();
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
