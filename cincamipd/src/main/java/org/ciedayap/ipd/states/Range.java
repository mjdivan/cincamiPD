/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It describes a interval to be considered in restrictions applied to data sources.
 * @author Mario Divan
 * @Version 1.0
 */
@XmlRootElement(name="Range")
@XmlType(propOrder={"IDRange","minValue","minValueIncluded","maxValue","maxValueIncluded"})
public class Range implements Serializable, Level{
    /**
     * ID associated with the range. It is  a friendly visualization, for example, [10;15]
     */
    private String IDRange;
    /**
     * The min value contemplated in the interval
     */
    private BigDecimal minValue;
    /**
     * It indicates whether the min value is included or not in the interval
     */
    private Boolean minValueIncluded;
    /**
     * The max value contemplated in the interval
     */
    private BigDecimal maxValue;
    /**
     * It indicates whether the max valuue is included or not in the interval
     */
    private Boolean maxValueIncluded;
    
    public Range(){}
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        IDRange=null;
        minValue=null;
        minValueIncluded=null;
        maxValue=null;
        maxValueIncluded=null;
        
        return true;
    }
    
    public static synchronized Range create(String id,BigDecimal min, Boolean minv, BigDecimal max, Boolean maxv)
    {
        Range r=new Range();
                
        r.setIDRange(id);
        r.setMaxValue(max);
        r.setMaxValueIncluded(maxv);
        r.setMinValue(min);
        r.setMinValueIncluded(minv);
        
        return r;
    }

    @Override
    public int getLevel() {
        return 11;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Range);
        //IDRange *mandatory*
        sb.append(Tokens.removeTokens(this.getIDRange().trim())).append(Tokens.fieldSeparator);
        //minValue  *mandatory*
        sb.append(getMinValue()).append(Tokens.fieldSeparator);
        //minValueIncluded *mandatory*
        sb.append(this.getMinValueIncluded()).append(Tokens.fieldSeparator);
        //axValue  *mandatory*
        sb.append(getMaxValue()).append(Tokens.fieldSeparator);
        //maxValueIncluded *mandatory*
        sb.append(this.getMaxValueIncluded());
        
        sb.append(Tokens.Class_ID_Range).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Range readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Range);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Range+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Range item=new Range();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Range.length()+1, idx_en);        
        
        //"IDRange" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The IDRange field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The IDRange field is not present and it is mandatory.");
        item.setIDRange(value);
        
        //"minValue" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The minValue field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The minValue field is not present and it is mandatory");
        item.setMinValue(new BigDecimal(value));

        //"minValueIncluded" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The minValueIncluded field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The minValueIncluded field is not present and it is mandatory");
        item.setMinValueIncluded(Boolean.valueOf(value));

        //"maxValue" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The maxValue field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The maxIncluded field is not present and it is mandatory");
        item.setMaxValue(new BigDecimal(value));
        
        //"maxValueIncluded" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        
        item.setMaxValueIncluded(Boolean.valueOf(value));
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(IDRange) || minValue==null ||  maxValue==null || minValueIncluded==null || maxValueIncluded==null);
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
        return this.getIDRange();
    }

    /**
     * @return the IDRange
     */
    @XmlElement(name="IDRange", required=true)
    public String getIDRange() {
        return IDRange;
    }

    /**
     * @param IDRange the IDRange to set
     */
    public void setIDRange(String IDRange) {
        this.IDRange = IDRange;
    }

    /**
     * @return the minValue
     */
    @XmlElement(name="minValue", required=true)
    public BigDecimal getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the minValueIncluded
     */
    @XmlElement(name="minValueIncluded", required=true)
    public Boolean getMinValueIncluded() {
        return minValueIncluded;
    }

    /**
     * @param minValueIncluded the minValueIncluded to set
     */
    public void setMinValueIncluded(Boolean minValueIncluded) {
        this.minValueIncluded = minValueIncluded;
    }

    /**
     * @return the maxValue
     */
    @XmlElement(name="maxValue", required=true)
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the maxValueIncluded
     */
    @XmlElement(name="maxValueIncluded", required=true)
    public Boolean getMaxValueIncluded() {
        return maxValueIncluded;
    }

    /**
     * @param maxValueIncluded the maxValueIncluded to set
     */
    public void setMaxValueIncluded(Boolean maxValueIncluded) {
        this.maxValueIncluded = maxValueIncluded;
    }
    
    public static Range test()
    {
        Range r=new Range();
        r.setIDRange("(10;20)");
        r.setMinValue(new BigDecimal("10.0"));
        r.setMinValueIncluded(Boolean.FALSE);
        r.setMaxValue(new BigDecimal("20.0"));
        r.setMaxValueIncluded(Boolean.FALSE);

        return r;
    }
    
    @Override
    public String computeFingerprint(){
        if(!this.isDefinedProperties()) return null;
        
        String original;
        try {
            original = this.writeTo();
        } catch (ProcessingException ex) {
            return null;
        }
        
        return (original==null)?null:Tokens.getFingerprint(original);
    }    
    
    @Override
    public int hashCode()
    {
        String ret=computeFingerprint();
        return (ret==null)?0:ret.hashCode();
    }
    
    @Override
    public boolean equals(Object target)
    {
        if(target==null) return false;
        if(!(target instanceof Range)) return false;
        
        Range cp=(Range)target;
        String ft=cp.computeFingerprint();
        
        String or;
        try{
            or=Tokens.getFingerprint(this.writeTo());
        }catch(ProcessingException pe)
        {
            return false;
        }
        
        return (ft==null)?false:(ft.equalsIgnoreCase(or));
    }
    
    @Override
    public ArrayList<TokenDifference> findDifferences(Object ptr) throws ProcessingException {
        if(ptr==null) throw new ProcessingException("The instance to compared is null");
        if(!(ptr instanceof Range)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Range comp=(Range)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(Range.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Range r=test();
        
        String xml=TranslateXML.toXml(r);
        String json=TranslateJSON.toJSON(r);
        String brief=r.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Range r2=(Range) Range.readFromSt(brief);
        System.out.println("ID: "+r2.getIDRange());
        System.out.println("Min: "+r2.getMinValue());
        System.out.println("minIncluded: "+r2.getMinValueIncluded());
        System.out.println("Max: "+r2.getMaxValue());
        System.out.println("maxIncluded: "+r2.getMaxValueIncluded());   
        
        System.out.println("Equal: "+r.equals(r2));
    }
}
