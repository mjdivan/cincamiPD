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
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It is oriented to implement trace groups
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="TraceGroup")
@XmlType(propOrder={"traceGroupID","name","definition"})
public class TraceGroup implements Serializable, Level{
    /**
     * It is the concept ID.
     */
    private String traceGroupID;
    /**
     * The name of the trace group.
     */
    private String name;
    /**
     * A brief definition related to the trace group. It is optional.
     */
    private String definition;

    /**
     * Default constructor
     */
    public TraceGroup()
    {
        
    }
        
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        traceGroupID=null;
        name=null;
        definition=null;
        
        return true;
    }
    
    /**
     * A factory method 
     * @param ID The ID of the trace group
     * @param name The name of the trace group
     * @return It returns an instance when the parameters are defined properly, null otherwise.
     */
    public static synchronized TraceGroup create(String ID, String name)
    {
        if(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name)) return null;
        
        TraceGroup tg=new TraceGroup();
        tg.setTraceGroupID(ID);
        tg.setName(name);
        
        return tg;
    }
    
    @Override
    public String toString()
    {
        try {
            return this.writeTo();
        } catch (ProcessingException ex) {
            return "Exception: "+ex.getMessage();
        }
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
        //traceGroupID *mandatory*
        sb.append(Tokens.removeTokens(this.getTraceGroupID().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //definition *optional*
        sb.append((StringUtils.isEmpty(this.getDefinition()))?"":Tokens.removeTokens(this.getDefinition().trim()));
        
        sb.append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException 
    {
        return readFromSt(text);
    }
    
    public static TraceGroup readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        TraceGroup item=new TraceGroup();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        
        
        //traceGroupID *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The traceGroupID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The traceGroupID field is not present and it is mandatory.");
        item.setTraceGroupID(value);
        
        //name *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);

        //definition *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        
        item.setDefinition((StringUtils.isEmpty(value))?null:value.trim());
        
        return item;        
    }

    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(this.getTraceGroupID()) || StringUtils.isEmpty(this.getName()));
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
    
    public static TraceGroup test(int i)
    {
        TraceGroup u=new TraceGroup();
        u.setTraceGroupID("ID"+i);
        u.setName("Peter"+i);
        u.setDefinition("Some description"+i);
        
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
        if(!(target instanceof TraceGroup)) return false;
        
        TraceGroup cp=(TraceGroup)target;
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
        if(!(ptr instanceof TraceGroup)) throw new ProcessingException("The instance to be compared is not of the expected type");
        TraceGroup comp=(TraceGroup)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(TraceGroup.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        TraceGroup u=test(1);
        
        String xml=TranslateXML.toXml(u);
        String json=TranslateJSON.toJSON(u);
        String brief=u.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        TraceGroup tg=(TraceGroup) u.readFrom(brief);
        System.out.println("ID: "+tg.getTraceGroupID());
        System.out.println("Name: "+tg.getName());
        System.out.println("Definition: "+tg.getDefinition());
        
        System.out.println("Equal: "+u.equals(tg));
    }
    
    
}
