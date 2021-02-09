/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.measurement;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;

/**
 * It represents a constraint acting on a data source
 * @author mjdivan
 */
@XmlRootElement(name="Constraint")
@XmlType(propOrder={"ID","name","filterAlgorithm","filterType"})
public class Constraint implements Serializable, Level{
    protected String ID;
    protected String name;
    protected String filterAlgorithm;
    protected String filterType;
    
    public Constraint(){}
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        filterAlgorithm=null;
        filterType=null;
        
        return true;
    }
    
    public static synchronized Constraint create(String id, String na)
    {
        Constraint c=new Constraint();
        c.setID(id);
        c.setName(na);
        
        return c;
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
     * @return the filterAlgorithm
     */
    @XmlElement(name="filterAlgorithm", required=false)        
    public String getFilterAlgorithm() {
        return filterAlgorithm;
    }

    /**
     * @param filterAlgorithm the filterAlgorithm to set
     */
    public void setFilterAlgorithm(String filterAlgorithm) {
        this.filterAlgorithm = filterAlgorithm;
    }

    /**
     * @return the filterType
     */
    @XmlElement(name="filterType", required=false)     
    public String getFilterType() {
        return filterType;
    }

    /**
     * @param filterType the filterType to set
     */
    public void setFilterType(String filterType) {
        this.filterType = filterType;
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
        sb.append((StringUtils.isEmpty(this.filterType))?"":Tokens.removeTokens(this.getFilterType().trim()));
        
        sb.append(Tokens.Class_ID_Constraint).append(Tokens.endLevel);
        
        return  sb.toString();               

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Constraint readFromSt(String text) throws ProcessingException {
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
        value=cleanedText.substring(0);        
        if(!StringUtils.isEmpty(value)) item.setFilterType(value);
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name));
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
        if(!(target instanceof Constraint)) return false;
        
        Constraint cp=(Constraint)target;
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
        if(!(ptr instanceof Constraint)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Constraint comp=(Constraint)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(Constraint.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
}
