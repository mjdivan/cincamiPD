/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.SingleConcept;

/**
 * It represents the state related to an entity under monitoring
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ECState")
@XmlType(propOrder={"properties"})
public class ECState extends State implements Serializable, SingleConcept{
    private ECStateProperties properties;
    
    /**
     * Default constructor
     */
    public ECState()
    {
        properties=null;
    }
    
    public ECState(String id,String pname, BigDecimal el,BigDecimal tl,StateTransitionModel stm,ECStateProperties p) throws SSAException
    {
        super(id,pname,el,tl,stm);
        
        if(p==null || p.isEmpty()) throw new SSAException("There are not defined properties");
        properties=p;
    }

    public synchronized static ECState create()
    {
        return new ECState();
    }
    
    public synchronized static ECState create(String id,String pname, BigDecimal el,BigDecimal tl,StateTransitionModel stm,ECStateProperties p) throws SSAException
    {
        return new ECState(id,pname,el,tl,stm,p);
    }
    
    /**
     * @return the properties
     */
    @XmlElement(name="properties", required=true)
    public ECStateProperties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(ECStateProperties properties) {
        this.properties = properties;
    }
    
}
