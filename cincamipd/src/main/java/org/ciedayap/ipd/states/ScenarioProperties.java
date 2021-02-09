/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

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
 * It contains the list of properties related to the environment
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="ScenarioProperties")
@XmlType(propOrder={"scenarioProperties"})
public class ScenarioProperties implements Serializable, Level, Containers{
    private java.util.ArrayList<ScenarioProperty>  scenarioProperties;
    
    public ScenarioProperties()
    {
        scenarioProperties=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(scenarioProperties==null)  return true;
        for(int i=0;i<scenarioProperties.size();i++)
        {
            scenarioProperties.get(i).realeaseResources();
        }
        
        scenarioProperties.clear();
        scenarioProperties=null;

        return true;
    }
    
    public static synchronized ScenarioProperties create(ArrayList<ScenarioProperty> list)
    {
        ScenarioProperties item=new ScenarioProperties();
        item.setScenarioProperties(list);
        
        return item;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(scenarioProperties.isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_ScenarioProperties);
        for(ScenarioProperty item:scenarioProperties)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_ScenarioProperties).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static ScenarioProperties readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_ScenarioProperties);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_ScenarioProperties+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        ScenarioProperties item=new ScenarioProperties();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_ScenarioProperties.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ScenarioProperty);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_ScenarioProperty+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_ScenarioProperty.length()+1);            
            ScenarioProperty ds=null;
            try{
                ds=(ScenarioProperty) ScenarioProperty.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getScenarioProperties().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_ScenarioProperty.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ScenarioProperty);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_ScenarioProperty+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(scenarioProperties==null || scenarioProperties.isEmpty());
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
        for (ScenarioProperty item : this.getScenarioProperties()) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {
        return (scenarioProperties!=null)?scenarioProperties.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(scenarioProperties==null) return true;
        
        return scenarioProperties.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return ScenarioProperty.class;
    }

    /**
     * @return the dataSources
     */
    @XmlElement(name="ScenarioProperty", required=true)    
    public java.util.ArrayList<ScenarioProperty> getScenarioProperties() {
        return scenarioProperties;
    }

    /**
     * @param scenarioProperties the scenarioProperties to set
     */
    public void setScenarioProperties(java.util.ArrayList<ScenarioProperty> scenarioProperties) {
        if(this.scenarioProperties!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.scenarioProperties = scenarioProperties;
    }
    
    public static ScenarioProperties test(int i)
    {
        ScenarioProperties item=new ScenarioProperties();
        for(int k=0;k<i;k++)
        {
            item.getScenarioProperties().add(ScenarioProperty.test(k));
        }
        
        return item;
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
        if(!(target instanceof ScenarioProperties)) return false;
        
        ScenarioProperties cp=(ScenarioProperties)target;
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
        if(!(ptr instanceof ScenarioProperties)) throw new ProcessingException("The instance to be compared is not of the expected type");
        ScenarioProperties comp=(ScenarioProperties)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.scenarioProperties.size()!=comp.getScenarioProperties().size())
            return TokenDifference.createAsAList(ScenarioProperties.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<scenarioProperties.size();i++)
        {
            ScenarioProperty pthis=scenarioProperties.get(i);
            ScenarioProperty pcomp=comp.getScenarioProperties().get(i);
            
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
        ScenarioProperties sps=test(3);

        String xml=TranslateXML.toXml(sps);
        String json=TranslateJSON.toJSON(sps);
        String brief=sps.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        ScenarioProperties sps2=(ScenarioProperties)ScenarioProperties.readFromSt(brief);
        for(ScenarioProperty sp2: sps2.getScenarioProperties())
        {
            System.out.println("Sc.ID: "+sp2.getID());
            System.out.println("Sc.Name: "+sp2.getName());
            Range r2=sp2.getRange();
            System.out.println("ID: "+r2.getIDRange());
            System.out.println("Min: "+r2.getMinValue());
            System.out.println("minIncluded: "+r2.getMinValueIncluded());
            System.out.println("Max: "+r2.getMaxValue());
            System.out.println("maxIncluded: "+r2.getMaxValueIncluded());        
            
        }
        
        System.out.println("Equal: "+sps.equals(sps2));
    }
}
