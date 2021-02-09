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
import org.ciedayap.ipd.Containers;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It contains a set of modes
 * @author Mario Divan
 * 
 */
@XmlRootElement(name="Values")
@XmlType(propOrder={"values"})
public class Values implements Serializable, Containers, Level{
    private ArrayList<BigDecimal> values;
    
    public Values()
    {
        values=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(values==null)  return true;
        
        values.clear();
        values=null;

        return true;
    }
    
    public static synchronized Values create(ArrayList<BigDecimal> v)
    {
        Values va=new Values();
        va.setValues(v);
        
        return va;
    }
    
    @Override
    public int length() {
        return (getValues()==null)?0:getValues().size();
    }

    @Override
    public boolean isEmpty() {
        if(getValues()==null)return true;
        
        return getValues().isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return BigDecimal.class;
    }

    @Override
    public int getLevel() {
        return 7;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Values);
        for(int i=0;i<getValues().size();i++)
        {
            BigDecimal item=getValues().get(i);
            sb.append(item);
            if(i!=(getValues().size()-1))
                sb.append(Tokens.fieldSeparator);

        }
        sb.append(Tokens.Class_ID_Values).append(Tokens.endLevel);
        
        return sb.toString();        
    }
    
    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Values readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) {
            System.out.println("Null text");
            return null;
        }
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Values);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Values+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) {
            return null;
        }
        
        Values item=new Values();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Values.length()+1, idx_en);        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        while(idx_en>=0 || (cleanedText!=null && cleanedText.length()>0))
        {
            String segment=null;
            if(idx_en<0)
            {
                segment=cleanedText;
                cleanedText=null;
            }
            else
            {
                segment=cleanedText.substring(0, idx_en);            
                //It retrieves the rest of the string
                cleanedText=cleanedText.substring(idx_en+1);

                idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
                
            }
            
            BigDecimal bd=null;
            try{
                bd=(BigDecimal) new BigDecimal(segment);
            }catch(Exception pe)
            {
                bd=null;
            }
            
            if(bd!=null) item.getValues().add(bd);
            
        }

        return item;
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
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        for (BigDecimal item : this.getValues()) 
        {
            sb.append(item).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public boolean isDefinedProperties() {
        return !(this.values==null || this.values.isEmpty());
    }

    /**
     * @return the values
     */
    @XmlElement(name="Value", required=true)
    public ArrayList<BigDecimal> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(ArrayList<BigDecimal> values) {
        if(this.values!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.values = values;
    }
    

    public static Values test(int k)
    {
        Values v=new Values();
        for(int i=0;i<k;i++) v.getValues().add(BigDecimal.TEN.multiply(new BigDecimal(""+i)));
        
        return v;
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
        if(!(target instanceof Values)) return false;
        
        Values cp=(Values)target;
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
        if(!(ptr instanceof Values)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Values comp=(Values)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(Values.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }    
    
    public static void main(String args[]) throws ProcessingException
    {
        Values v=test(4);
        
        String xml=TranslateXML.toXml(v);
        String json=TranslateJSON.toJSON(v);
        String brief=v.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Values v2=(Values) Values.readFromSt(brief);
        for(BigDecimal val:v2.getValues())
        {
            System.out.println(val);
        }
        
        System.out.println("Equal: "+v.equals(v2));
    }    
}
