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
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It represents each characteristic of a data source
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceProperty")
@XmlType(propOrder={"propertyID","name","description","value","relevance"})
public class DataSourceProperty implements Serializable, Level{
    /**
     * The property ID. It is Mandatory.
     */
    private String propertyID;
    /**
     * The name of the property. It is Mandatory.
     */
    private String name;
    /**
     * A brief description of the property. It is optional
     */    
    private String description;
    /**
     * A value related to the property. It is mandatory.
     */
    private BigDecimal value;
    /**
     * It is a relevance related to the value  of the property. It is optional.
     */
    private BigDecimal relevance;
    
    public DataSourceProperty(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        propertyID=null;
        name=null;
        description=null;
        value=null;
        relevance=null;
        
        return true;
    }
    
    public static synchronized DataSourceProperty create(String id, String na, BigDecimal va)
    {
        DataSourceProperty dsp=new DataSourceProperty();
        dsp.setPropertyID(id);
        dsp.setName(na);
        dsp.setValue(va);
        
        return dsp;
    }
    
    @Override
    public int getLevel() {
        return 12;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        
        //"propertyID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getPropertyID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);
        
        //"description" *Optional*
        sb.append((StringUtils.isEmpty(this.getDescription()))?"":Tokens.removeTokens(this.getDescription())).append(Tokens.fieldSeparator);
        
        //"value", *Mandatory*
        sb.append(this.getValue()).append(Tokens.fieldSeparator);

        //"relevance" *Optional*
        sb.append((this.relevance==null)?"":this.relevance);
        
        sb.append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DataSourceProperty readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSourceProperty item=new DataSourceProperty();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        
        
        //"propertyID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The propertyID field is not present and it is mandatory.");
        item.setPropertyID(value);
        
        //"name" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);
                
        //"description" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setDescription(value);
        
        //"value" *Mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The value field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setValue(new BigDecimal(value));
        
        //"relevance" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(!StringUtils.isEmpty(value)) item.setRelevance(new BigDecimal(value));
                        
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(this.propertyID) || StringUtils.isEmpty(this.name) || this.value==null);
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
        return this.propertyID;
    }

    /**
     * @return the propertyID
     */
    @XmlElement(name="propertyID", required=true)  
    public String getPropertyID() {
        return propertyID;
    }

    /**
     * @param propertyID the propertyID to set
     */
    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
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
     * @return the description
     */
    @XmlElement(name="description", required=false)  
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the value
     */
    @XmlElement(name="value", required=true)  
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return the relevance
     */
    @XmlElement(name="relevance", required=false)  
    public BigDecimal getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    public void setRelevance(BigDecimal relevance) {
        this.relevance = relevance;
    }

    public static DataSourceProperty test(int i)
    {
        DataSourceProperty u=new DataSourceProperty();
        u.setPropertyID("ID"+i);
        u.setName("DSA"+i);       
        u.setDescription("some description"+i);
        u.setValue(BigDecimal.ONE);        
        u.setRelevance(BigDecimal.TEN);
        
        return u;
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
        if(!(target instanceof DataSourceProperty)) return false;
        
        DataSourceProperty cp=(DataSourceProperty)target;
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
        if(!(ptr instanceof DataSourceProperty)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSourceProperty comp=(DataSourceProperty)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(DataSourceProperty.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        DataSourceProperty u=DataSourceProperty.test(1);
        
        String xml=TranslateXML.toXml(u);
        String json=TranslateJSON.toJSON(u);
        String brief=u.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSourceProperty tg=(DataSourceProperty) u.readFrom(brief);
        System.out.println("ID: "+tg.getPropertyID());
        System.out.println("Name: "+tg.getName());
        System.out.println("Description: "+tg.getDescription());
        System.out.println("Value: "+tg.getValue());
        System.out.println("Relevance: "+tg.getRelevance());
        
        System.out.println("Equal: "+u.equals(tg));
    }    
    
}
