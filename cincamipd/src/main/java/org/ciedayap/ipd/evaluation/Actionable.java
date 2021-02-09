/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
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
 * It represents a mode in which a a given data source is actionable
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Actionable")
@XmlType(propOrder={"dataSourceID","mode","message","action"})
public class Actionable implements Serializable, Level{
    private String dataSourceID;
    private String mode;
    private String message;
    private String action;
    
    public Actionable(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        dataSourceID=null;
        mode=null;
        message=null;
        action=null;
        
        return true;
    }
    
    public static synchronized Actionable create(String ds,String mo, String mes, String ac)
    {
        Actionable act=new Actionable();
        act.setAction(ac);
        act.setDataSourceID(ds);
        act.setMessage(mes);
        act.setMode(mo);
        
        return act;
    }
    
    @Override
    public int getLevel() {
        return 11;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Actionable);
        //dataSourceID *mandatory*
        sb.append(Tokens.removeTokens(this.getDataSourceID().trim())).append(Tokens.fieldSeparator);
        //mode  *mandatory*
        sb.append(Tokens.removeTokens(this.getMode().trim())).append(Tokens.fieldSeparator);
        //message *mandatory*
        sb.append(Tokens.removeTokens(this.getMessage().trim())).append(Tokens.fieldSeparator);

        //Action *mandatory*
        sb.append(Tokens.removeTokens(this.getAction().trim()));
        
        sb.append(Tokens.Class_ID_Actionable).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Actionable readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Actionable);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Actionable+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Actionable item=new Actionable();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Actionable.length()+1, idx_en);        
        
        //"dataSourceID" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The dataSourceID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The dataSourceID field is not present and it is mandatory.");
        item.setDataSourceID(value);
        
        //"mode" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The mode field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The mode field is not present and it is mandatory");
        item.setMode(value);

        //"message" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The message field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The message field is not present and it is mandatory");
        item.setMessage(value);
                        
        //"action" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The action field is not present.");
        item.setAction(value);
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(dataSourceID) || StringUtils.isEmpty(mode) || 
                StringUtils.isEmpty(message) || StringUtils.isEmpty(action));
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
        sb.append(dataSourceID).append("-").append(mode).append("-").append(action);
        
        return sb.toString();
    }

    /**
     * @return the dataSourceID
     */
    public String getDataSourceID() {
        return dataSourceID;
    }

    /**
     * @param dataSourceID the dataSourceID to set
     */
    public void setDataSourceID(String dataSourceID) {
        this.dataSourceID = dataSourceID;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
     
    public static Actionable test(int k)
    {
        Actionable a=new Actionable();
        a.setAction("Run"+k);
        a.setDataSourceID("DS"+k);
        a.setMessage("It will go to exploit"+k);
        a.setMode("Silentious"+k);

        return a;
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
        if(!(target instanceof Actionable)) return false;
        
        Actionable cp=(Actionable)target;
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
        if(!(ptr instanceof Actionable)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Actionable comp=(Actionable)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(Actionable.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Actionable a=test(3);
        
        String xml=TranslateXML.toXml(a);
        String json=TranslateJSON.toJSON(a);
        String brief=a.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Actionable a2=(Actionable)Actionable.readFromSt(brief);
        System.out.println("Action: "+a2.getAction());
        System.out.println("On: "+a2.getDataSourceID());
        System.out.println("Mode: "+a2.getMode());
        System.out.println("Message: "+a2.getMessage());
        System.out.println("Equal: "+a.equals(a2));
    }    
}
