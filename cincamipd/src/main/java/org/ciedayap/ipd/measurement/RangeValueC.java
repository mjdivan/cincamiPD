/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.measurement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.states.Range;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It  implements a restriction based on a range for data sources
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="RangeValueC")
@XmlType(propOrder={"range"})
public class RangeValueC extends Constraint implements Serializable, Level{
    private Range range;
    
    public RangeValueC()
    {        
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        super.realeaseResources();
        if(range!=null)
        {
            range.realeaseResources();
            range=null;
        }
        
        return true;
    }
    
    public static synchronized RangeValueC create(String id, String na, Range r2)
    {
        RangeValueC r=new RangeValueC();
        r.setID(id);
        r.setName(na);
        r.setRange(r2);
        
        return r;
    }
    
    @Override
    public int getLevel() {
        return 10;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Constraint);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //filterAlgorithm *optional*
        sb.append((StringUtils.isEmpty(this.filterAlgorithm))?"":Tokens.removeTokens(this.getFilterAlgorithm().trim())).append(Tokens.fieldSeparator);
        //filterType  *optional*
        sb.append((StringUtils.isEmpty(this.filterType))?"":Tokens.removeTokens(this.getFilterType().trim())).append(Tokens.fieldSeparator);
        //range *mandatory*
        sb.append(range.writeTo());
        
        sb.append(Tokens.Class_ID_Constraint).append(Tokens.endLevel);
        
        return  sb.toString();               

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static RangeValueC readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Constraint);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Constraint+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        RangeValueC item=new RangeValueC();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Constraint.length()+1, idx_en);        
        
        //ID *mandatory*
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"name" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);

        //"filterAlgorithm" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The filterAlgorithm field is not present.");
        value=cleanedText.substring(0, idx_en);
        
        if(!StringUtils.isEmpty(value)) item.setFilterAlgorithm(value);

        //"filterType" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The filterType field is not present.");
        value=cleanedText.substring(0, idx_en);
        
        if(!StringUtils.isEmpty(value)) item.setFilterType(value);
        
        //"range" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);        
        Range r=(Range) Range.readFromSt(value);
        if(r==null) throw new ProcessingException("The range field is not present and it is mandatory.");
        item.setRange(r);
        
        return item;        
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
        return this.ID;
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
        if(this.range!=null){
            try{
                this.range.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.range = range;
    }

    public static RangeValueC test(int i)
    {
        Range r=Range.create("(1020)", BigDecimal.TEN, Boolean.FALSE, new BigDecimal("20"), Boolean.FALSE);
        RangeValueC rvc=new RangeValueC();
        rvc.setID("rvc"+i);
        rvc.setName("rvc_name"+i);
        rvc.setFilterAlgorithm("algorithm"+i);
        rvc.setFilterType("filterType"+i);
        rvc.setRange(r);
        
        return rvc;
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
        if(!(target instanceof RangeValueC)) return false;
        
        RangeValueC cp=(RangeValueC)target;
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
        if(!(ptr instanceof RangeValueC)) throw new ProcessingException("The instance to be compared is not of the expected type");
        RangeValueC comp=(RangeValueC)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(RangeValueC.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        RangeValueC rvc=test(1);
        
        String xml=TranslateXML.toXml(rvc);
        String json=TranslateJSON.toJSON(rvc);
        String brief=rvc.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        RangeValueC r2=(RangeValueC) RangeValueC.readFromSt(brief);
        
        System.out.println("ID: "+r2.getID());
        System.out.println("Name: "+r2.getName());
        System.out.println("Algorithm: "+r2.getFilterAlgorithm());
        System.out.println("Type: "+r2.getFilterType());
        System.out.println("ID: "+r2.getRange().getIDRange());
        System.out.println("Min: "+r2.getRange().getMinValue());
        System.out.println("minIncluded: "+r2.getRange().getMinValueIncluded());
        System.out.println("Max: "+r2.getRange().getMaxValue());
        System.out.println("maxIncluded: "+r2.getRange().getMaxValueIncluded());        
        
        System.out.println("Equal: "+rvc.equals(r2));
    }
    
    
}
