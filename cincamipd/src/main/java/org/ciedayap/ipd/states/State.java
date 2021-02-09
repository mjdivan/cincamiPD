/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * The base class related to the state
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="State")
@XmlType(propOrder={"ID","name","version","empiricalLikelihood","theoreticalLikelihood"})
public class State implements Serializable, Level{
    protected String ID;
    protected String name;
    protected String version;
    protected BigDecimal empiricalLikelihood;
    protected BigDecimal theoreticalLikelihood;
    
    public State(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        empiricalLikelihood=null;
        theoreticalLikelihood=null;
        ID=null;
        version=null;
        name=null;

        return true;
    }
    
    public static synchronized State create(String id, String name, String version, BigDecimal eLikelihood, BigDecimal tLikelihood)            
    {
        State s=new State();
        s.setID(id);
        s.setEmpiricalLikelihood(eLikelihood);
        s.setName(name);
        s.setTheoreticalLikelihood(tLikelihood);
        s.setVersion(version);
        
        return s;
    }

    public static synchronized State create(String id, String name, String version)            
    {
        State s=new State();
        s.setID(id);
        s.setName(name);
        s.setVersion(version);
        
        return s;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //Name *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //Version *mandatory*
        sb.append(Tokens.removeTokens(this.getVersion().trim())).append(Tokens.fieldSeparator);
        //empiricalLikelihood *optional*
        sb.append((this.getEmpiricalLikelihood()==null)?"":this.getEmpiricalLikelihood()).append(Tokens.fieldSeparator);
        //theoreticalLikelihood *optional*
        sb.append((this.getTheoreticalLikelihood()==null)?"":this.getTheoreticalLikelihood());
        
        sb.append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static State readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        State item=new State();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        
        
        //"ID" *mandatory*        
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

        //"version" *mandatory*        
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name version is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The version field is not present and it is mandatory.");
        item.setVersion(value);
        
        //empiricalLikelihood *optional*   
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The empiricalLikelihood field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(!StringUtils.isEmpty(value)) item.setEmpiricalLikelihood(new BigDecimal(value));
        
        //theoreticalLikelihood *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
       
        if(!StringUtils.isEmpty(value)) item.setTheoreticalLikelihood(new BigDecimal(value));        
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || StringUtils.isEmpty(version));
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the empiricalLikelihood
     */
    public BigDecimal getEmpiricalLikelihood() {
        return empiricalLikelihood;
    }

    /**
     * @param empiricalLikelihood the empiricalLikelihood to set
     */
    public void setEmpiricalLikelihood(BigDecimal empiricalLikelihood) {
        this.empiricalLikelihood = empiricalLikelihood;
    }

    /**
     * @return the theoreticalLikelihood
     */
    public BigDecimal getTheoreticalLikelihood() {
        return theoreticalLikelihood;
    }

    /**
     * @param theoreticalLikelihood the theoreticalLikelihood to set
     */
    public void setTheoreticalLikelihood(BigDecimal theoreticalLikelihood) {
        this.theoreticalLikelihood = theoreticalLikelihood;
    }
    
    public static State test(int i)
    {
        State r=new State();
        r.setEmpiricalLikelihood(BigDecimal.ZERO);
        r.setTheoreticalLikelihood(BigDecimal.ONE);
        r.setID("ID"+i);
        r.setName("name"+i);
        r.setVersion("1.0");
        
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
        if(!(target instanceof State)) return false;
        
        State cp=(State)target;
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
        if(!(ptr instanceof State)) throw new ProcessingException("The instance to be compared is not of the expected type");
        State comp=(State)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(State.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        State r=State.test(1);
        
        String xml=TranslateXML.toXml(r);
        String json=TranslateJSON.toJSON(r);
        String brief=r.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        State r2=(State) State.readFromSt(brief);
        System.out.println("ID: "+r2.getID());
        System.out.println("Name: "+r2.getName());
        System.out.println("Version: "+r2.getVersion());
        System.out.println("eL: "+r2.getEmpiricalLikelihood());
        System.out.println("tL: "+r2.getTheoreticalLikelihood()); 
        
        System.out.println("Equal: "+r.equals(r2));
        
        ArrayList<TokenDifference> list=r.findDifferences(r2);
        if(list==null) System.out.println("**No difference**");
        else
        {
            for(TokenDifference td:list)
            {
                System.out.println("Level: "+td.getLevel());
                System.out.println("ID: "+td.getID());
                System.out.println("Class: "+td.getTheclass());
            }
        }
        
    }
}
