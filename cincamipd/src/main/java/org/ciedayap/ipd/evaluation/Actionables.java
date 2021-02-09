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
import org.ciedayap.ipd.Containers;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It contains a set of actions associated with one or more data sources
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Actionables")
@XmlType(propOrder={"actionables"})
public class Actionables implements Serializable, Containers, Level{
    private ArrayList<Actionable> actionables;
    
    public Actionables()
    {
        actionables=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(actionables==null)  return true;
        for(int i=0;i<actionables.size();i++)
        {
            actionables.get(i).realeaseResources();
        }
        
        actionables.clear();
        actionables=null;

        return true;
    }
    
    public static synchronized Actionables create(ArrayList<Actionable> l)
    {
        Actionables a=new Actionables();
        a.setActionables(l);
        return a;
    }
    
    @Override
    public int length() {
        return (getActionables()==null)?0:getActionables().size();
    }

    @Override
    public boolean isEmpty() {
        if(getActionables()==null) return true;
        return getActionables().isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return Actionable.class;
    }

    @Override
    public int getLevel() {
        return 10;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getActionables().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Actionables);
        for(Actionable item:getActionables())
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Actionables).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Actionables readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Actionables);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Actionables+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Actionables item=new Actionables();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Actionables.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Actionable);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Actionable+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Actionable.length()+1);            
            Actionable ds=null;
            try{
                ds=(Actionable) Actionable.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getActionables().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Actionable.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Actionable);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Actionable+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(actionables==null || actionables.isEmpty());
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
        for (Actionable item : getActionables()) 
        {
            sb.append(item.getUniqueID()).append("-");
        }
        
        return sb.toString();        
    }

    /**
     * @return the actionables
     */
    @XmlElement(name="Actionable", required=true)    
    public ArrayList<Actionable> getActionables() {
        return actionables;
    }

    /**
     * @param actionables the actionables to set
     */
    public void setActionables(ArrayList<Actionable> actionables) {
        if(this.actionables!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.actionables = actionables;
    }
    
    public static Actionables test(int k)
    {
        Actionables a=new Actionables();
        for(int i=0;i<k;i++)
        {
            a.getActionables().add(Actionable.test(i));
        }
        
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
        if(!(target instanceof Actionables)) return false;
        
        Actionables cp=(Actionables)target;
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
        if(!(ptr instanceof Actionables)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Actionables comp=(Actionables)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.actionables.size()!=comp.getActionables().size())
            return TokenDifference.createAsAList(Actionables.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<actionables.size();i++)
        {
            Actionable pthis=actionables.get(i);
            Actionable pcomp=comp.getActionables().get(i);
            
            ArrayList<TokenDifference> result=pthis.findDifferences(pcomp);
            if(result!=null && result.size()>0)
            {
                global.addAll(result);            
                result.clear();
            }
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Actionables list=Actionables.test(3);
        
        String xml=TranslateXML.toXml(list);
        String json=TranslateJSON.toJSON(list);
        String brief=list.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Actionables list2=(Actionables)Actionables.readFromSt(brief);
        for(Actionable a2:list2.getActionables())
        {
            System.out.println("Action: "+a2.getAction());
            System.out.println("On: "+a2.getDataSourceID());
            System.out.println("Mode: "+a2.getMode());
            System.out.println("Message: "+a2.getMessage());
        }
        
        System.out.println("Equal: "+list.equals(list2));
    }        
}
