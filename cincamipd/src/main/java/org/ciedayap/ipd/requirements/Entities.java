/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.requirements;

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
 * It contains a set of Entities
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Entities")
@XmlType(propOrder={"entities"})
public class Entities implements Serializable, Level, Containers{
    private ArrayList<Entity> entities;
    
    public Entities()
    {
        entities=new ArrayList();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(entities==null)  return true;
        for(int i=0;i<entities.size();i++)
        {
            entities.get(i).realeaseResources();
        }
        
        entities.clear();
        entities=null;

        return true;
    }
    
    public static synchronized Entities create(ArrayList<Entity> ent)
    {
        Entities e=new Entities();
        e.setEntities(ent);
        return e;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Entities);
        for(Entity item:entities)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Entities).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Entities readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Entities);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Entities+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Entities item=new Entities();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Entities.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Entity);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Entity+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Entity.length()+1);            
            Entity met=null;
            try{
                met= Entity.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                met=null;
            }
            
            if(met!=null) item.getEntities().add(met);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Entity.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Entity);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Entity+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(entities==null || entities.isEmpty());
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
        for (Entity item : this.getEntities()) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {
        return (entities==null)?0:entities.size();
    }

    @Override
    public boolean isEmpty() {
        if(entities==null) return true;
        return entities.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return Entity.class;
    }

    /**
     * @return the entities
     */
    @XmlElement(name="Entity", required=true)
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(ArrayList<Entity> entities) {
        if(this.entities!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.entities = entities;
    }  
    
    public static Entities test(int k)
    {
        Entities ents=new Entities();
        
        for (int i=0;i<k;i++)
        {           
            ents.getEntities().add(Entity.test(i));
        }

        return ents;        
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
        if(!(target instanceof Entities)) return false;
        
        Entities cp=(Entities)target;
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
        if(!(ptr instanceof Entities)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Entities comp=(Entities)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.entities.size()!=comp.getEntities().size())
            return TokenDifference.createAsAList(Entities.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<entities.size();i++)
        {
            Entity pthis=entities.get(i);
            Entity pcomp=comp.getEntities().get(i);
            
            ArrayList<TokenDifference> result=pthis.findDifferences(pcomp);
            if(result!=null && result.size()>0)
            {
                global.addAll(result);            
                result.clear();
            }
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {   
        Entities ents=test(3);
        
            String xml=TranslateXML.toXml(ents);
            String json=TranslateJSON.toJSON(ents);
            String brief=ents.writeTo();

            System.out.println(xml);
            System.out.println(json);
            System.out.println(brief);

            Entities ents2=Entities.readFromSt(brief);
            
            for(Entity b:ents2.getEntities())
            {            
                System.out.println("ID: "+b.getID());
                System.out.println("Name: "+b.getName());
                System.out.println("Desc: "+b.getDescription());
            }
            
        String brief2=ents2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));//Reversible                
        System.out.println("Equal2: "+ents.equals(ents2));
    }
    
}