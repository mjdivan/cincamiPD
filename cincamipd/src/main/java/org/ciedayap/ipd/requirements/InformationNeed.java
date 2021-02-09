/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.requirements;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.context.Context;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It contains the information need jointly with the entity definition and the context.
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="InformationNeed")
@XmlType(propOrder={"ID","purpose","shortTitle","userViewpoint","entityCategory","context"})
public class InformationNeed implements Serializable, Level{
    private String ID;//mandatory
    private String purpose;//mandatory
    private String shortTitle;//mandatory
    private String userViewpoint;//optional
    private EntityCategory entityCategory;//mandatory
    private Context context;//optional
    
    public InformationNeed()
    {
        
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        purpose=null;
        shortTitle=null;
        userViewpoint=null;
        if(entityCategory!=null)
        {
            entityCategory.realeaseResources();
            entityCategory=null;
        }
        
        if(context!=null)
        {
            context.realeaseResources();
            context=null;
        }

        return true;
    }
    
    public static synchronized InformationNeed create(String id, String pur,String sh,EntityCategory ec)
    {
        InformationNeed in=new InformationNeed();
        
        in.setEntityCategory(ec);
        in.setID(id);
        in.setPurpose(pur);
        in.setShortTitle(sh);
        
        return in;
    }
    
    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_InformationNeed);
        
        //"ID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getID())).append(Tokens.fieldSeparator);
        //"purpose" *Mandatory*
        sb.append(Tokens.removeTokens(this.getPurpose())).append(Tokens.fieldSeparator);        
        //"shortTitle" *Mandatory*
        sb.append(Tokens.removeTokens(this.getShortTitle())).append(Tokens.fieldSeparator);
        //"userViewPoint" *optional*
        sb.append((StringUtils.isEmpty(userViewpoint))?"":Tokens.removeTokens(this.getUserViewpoint())).append(Tokens.fieldSeparator);
        //entityCategory *Mandatory*
        sb.append(entityCategory.writeTo()).append(Tokens.fieldSeparator);
        //context *optional*
        if(context.isDefinedProperties()) sb.append(context.writeTo());
        
        sb.append(Tokens.Class_ID_InformationNeed).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static InformationNeed readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_InformationNeed);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_InformationNeed+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        InformationNeed item=new InformationNeed();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_InformationNeed.length()+1, idx_en);                
        
        //"ID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"purpose" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The purpose field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setPurpose(value);

        //"shortTitle" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The shortTitle field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The shortTitle field is not present and it is mandatory.");
        item.setShortTitle(value);
        
        //"userViewPoint" *optional*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The userViewPoint field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setUserViewpoint(value);
        
        
        //entityCategory
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_EntityCategory+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The entityCategory field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_EntityCategory.length()+Tokens.endLevel.length());
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The entityCategory field is not present and it is mandatory.");
        item.setEntityCategory(EntityCategory.readFromSt(value));        

        //entityCategory
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_EntityCategory.length()+Tokens.endLevel.length()+1);
        value=cleanedText.substring(0);
        if(!StringUtils.isEmpty(value)) 
        {
            item.setContext(Context.readFromSt(value));
        }        
                                        
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(purpose) ||  StringUtils.isEmpty(shortTitle) ||
                entityCategory==null || !entityCategory.isDefinedProperties());
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
        return ID;
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
     * @return the shortTitle
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * @param shortTitle the shortTitle to set
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * @return the userViewpoint
     */
    public String getUserViewpoint() {
        return userViewpoint;
    }

    /**
     * @param userViewpoint the userViewpoint to set
     */
    public void setUserViewpoint(String userViewpoint) {
        this.userViewpoint = userViewpoint;
    }

    /**
     * @return the entityCategory
     */
    public EntityCategory getEntityCategory() {
        return entityCategory;
    }

    /**
     * @param entityCategory the entityCategory to set
     */
    public void setEntityCategory(EntityCategory entityCategory) {
        if(this.entityCategory!=null){
            try{
                this.entityCategory.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.entityCategory = entityCategory;
    }

    /**
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Context context) {
        if(this.context!=null){
            try{
                this.context.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.context = context;
    }
    
    public static InformationNeed test(int k)
    {
        InformationNeed in=new InformationNeed();
        in.setID("ID"+k);
        in.setShortTitle("Outpatient monitoring"+k);
        in.setPurpose("To monitor..."+k);
        in.setUserViewpoint("point of view"+k);
        in.setEntityCategory(EntityCategory.test(1));
        in.setContext(Context.test(1));

        return in;        
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
        if(!(target instanceof InformationNeed)) return false;
        
        InformationNeed cp=(InformationNeed)target;
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
        if(!(ptr instanceof InformationNeed)) throw new ProcessingException("The instance to be compared is not of the expected type");
        InformationNeed comp=(InformationNeed)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(InformationNeed.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  Attributes *mandatory
        ArrayList<TokenDifference> result;
        result=this.entityCategory.findDifferences(comp.getEntityCategory());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }

        //  Context *optional*
        if(context!=null && context.isDefinedProperties() &&
                comp.getContext()!=null && comp.getContext().isDefinedProperties())
        {
            result=this.context.findDifferences(comp.getContext());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }            
        }
        else
        {
            if((context!=null && context.isDefinedProperties()))
            {
                global.add(TokenDifference.create(Context.class, context.getUniqueID(), 
                        context.getLevel(), context.computeFingerprint(), null));                
            }
            
            if(comp.getContext()!=null && comp.getContext().isDefinedProperties())
            {
                global.add(TokenDifference.create(Context.class, comp.getContext().getUniqueID(), 
                        comp.getContext().getLevel(), null, comp.getContext().computeFingerprint()));                
            }            
        }
                
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {
        InformationNeed in=test(1);
        String xml=TranslateXML.toXml(in);
        String json=TranslateJSON.toJSON(in);
        String brief=in.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        InformationNeed in2=InformationNeed.readFromSt(brief);
        String brief2=in2.writeTo();
        
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));
        System.out.println("Equal2: "+in.equals(in2));
        System.out.println("XML: "+xml.getBytes().length);
        System.out.println("JSON: "+json.getBytes().length);
        System.out.println("Brief: "+brief.getBytes().length);
    }
}
