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
 * It defines a variation range by a state or context property. 
 * @author mjdivan
 */
@XmlRootElement(name="Range")
@XmlType(propOrder={"expectedMinValue","includedMinValue","expectedMaxValue","includedMaxValue"})
public class Range implements Serializable, SingleConcept{
    private BigDecimal expectedMinValue;
    private boolean includedMinValue;
    private BigDecimal expectedMaxValue;
    private boolean includedMaxValue;

    public Range()
    {
        includedMinValue=false;
        includedMaxValue=false;
        expectedMinValue=null;
        expectedMaxValue=null;
    }
    
    public Range(BigDecimal min, boolean includedMin, BigDecimal max, boolean includedMax) throws SSAException
    {
        if(min==null && max==null) throw new SSAException("The Uppest and Lowest Values for the variation range cannot be null. At least one of them must be defined.");
        if(min!=null && max!=null && max.compareTo(min)<0)
            throw new SSAException("The max parameter must be upper than the min parameter.");
        
        expectedMinValue=min;
        expectedMaxValue=max;
        includedMinValue=includedMin;
        includedMaxValue=includedMax;
    }
    
    public synchronized static Range create()
    {
        return new Range();
    }
    
    public synchronized static Range create(BigDecimal min, boolean includedMin, BigDecimal max, boolean includedMax) throws SSAException
    {
        return new Range(min,includedMin,max,includedMax);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !((getExpectedMinValue()==null && getExpectedMaxValue()==null) ||
                (expectedMinValue!=null && expectedMaxValue!=null && expectedMaxValue.compareTo(expectedMinValue)<0));       
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
        return "["+getExpectedMinValue()+"; "+getExpectedMaxValue()+"]";
    }

    /**
     * @return the expectedMinValue
     */
    @XmlElement(name="expectedMinValue")
    public BigDecimal getExpectedMinValue() {
        return expectedMinValue;
    }

    /**
     * @param expectedMinValue the expectedMinValue to set
     */
    public void setExpectedMinValue(BigDecimal expectedMinValue) {
        this.expectedMinValue = expectedMinValue;
    }

    /**
     * @return the includedMinValue
     */
    @XmlElement(name="includedMinValue")
    public boolean isIncludedMinValue() {
        return includedMinValue;
    }

    /**
     * @param includedMinValue the includedMinValue to set
     */
    public void setIncludedMinValue(boolean includedMinValue) {
        this.includedMinValue = includedMinValue;
    }

    /**
     * @return the expectedMaxValue
     */
    @XmlElement(name="expectedMaxValue")
    public BigDecimal getExpectedMaxValue() {
        return expectedMaxValue;
    }

    /**
     * @param expectedMaxValue the expectedMaxValue to set
     */
    public void setExpectedMaxValue(BigDecimal expectedMaxValue) {
        this.expectedMaxValue = expectedMaxValue;
    }

    /**
     * @return the includedMaxValue
     */
    @XmlElement(name="includedMaxValue")
    public boolean isIncludedMaxValue() {
        return includedMaxValue;
    }

    /**
     * @param includedMaxValue the includedMaxValue to set
     */
    public void setIncludedMaxValue(boolean includedMaxValue) {
        this.includedMaxValue = includedMaxValue;
    }
    
    
}
