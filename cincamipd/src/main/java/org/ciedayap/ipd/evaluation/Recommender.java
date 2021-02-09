/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
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
 * It contains a set of actionables appleable based on a given priority
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Recommender")
@XmlType(propOrder={"ID","name","priority","url","actionables"})
public class Recommender implements Serializable, Level{
    private String ID;
    private String name;
    private Integer priority;
    private String url;
    private Actionables actionables;
    
    public Recommender(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        priority=null;
        url=null;
        if(actionables!=null)
        {
            actionables.realeaseResources();
            actionables=null;
        }

        return true;
    }
    
    public static synchronized Recommender create(String id, String na, Integer prio, String url,Actionables act)
    {
        Recommender rec=new Recommender();
        rec.setID(id);
        rec.setName(na);
        rec.setPriority(prio);
        rec.setUrl(url);
        rec.setActionables(act);
        
        return rec;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Recommender);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //priority *mandatory*
        sb.append(this.getPriority()).append(Tokens.fieldSeparator);
        //url *mandatory*
        sb.append(Tokens.removeTokens(this.getUrl().trim())).append(Tokens.fieldSeparator);
        //Actionables *mandatory*
        if(actionables!=null && actionables.isDefinedProperties()) sb.append(actionables.writeTo());
        
        sb.append(Tokens.Class_ID_Recommender).append(Tokens.endLevel);
        
        return  sb.toString();               

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Recommender readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Recommender);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Recommender+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Recommender item=new Recommender();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Recommender.length()+1, idx_en);        
        
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
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);

        //"priority" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The priority field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The priority field is not present and it is mandatory");
        item.setPriority(Integer.valueOf(value));

        //"url" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The url field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The url field is not present and it is mandatory");
        item.setUrl(value);
        
        //"actionables" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(!StringUtils.isEmpty(value)) item.setActionables((Actionables)Actionables.readFromSt(value));        
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || StringUtils.isEmpty(url) || priority==null);
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
     * @return the priority
     */
    @XmlElement(name="priority", required=true)    
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the actionables
     */
    @XmlElement(name="Actionables", required=true)    
    public Actionables getActionables() {
        return actionables;
    }

    /**
     * @param actionables the actionables to set
     */
    public void setActionables(Actionables actionables) {
        this.actionables = actionables;
    }

    /**
     * @return the url
     */
    @XmlElement(name="url", required=true)    
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    public static Recommender test(int k)
    {
        Recommender r=new Recommender();
        r.setActionables(Actionables.test(3));
        r.setID("ID"+k);
        r.setName("name reco"+k);
        r.setPriority(1);
        r.setUrl("www.jjj"+k+".com");
        
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
        if(!(target instanceof Recommender)) return false;
        
        Recommender cp=(Recommender)target;
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
        if(!(ptr instanceof Recommender)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Recommender comp=(Recommender)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(Recommender.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  Actionables *mandatory*
        ArrayList<TokenDifference> result=this.actionables.findDifferences(comp.getActionables());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
                
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Recommender r=test(1);
        
        String xml=TranslateXML.toXml(r);
        String json=TranslateJSON.toJSON(r);
        String brief=r.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Recommender r2=(Recommender)Recommender.readFromSt(brief);
        
        System.out.println("ID: "+r2.getID());
        System.out.println("Name: "+r2.getName());
        System.out.println("URL: "+r2.getUrl());
        System.out.println("Priority: "+r2.getPriority());
        
        Actionables list2=r2.getActionables();
        for(Actionable a2:list2.getActionables())
        {
            System.out.println("Action: "+a2.getAction());
            System.out.println("On: "+a2.getDataSourceID());
            System.out.println("Mode: "+a2.getMode());
            System.out.println("Message: "+a2.getMessage());
        }     
        
        String brief3=r2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief3));//Reversible                        
        System.out.println("Equal2:"+r.equals(r2));
        
    }        
    
}
