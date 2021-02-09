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
 * A set of Scenarios
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Scenarios")
@XmlType(propOrder={"scenarios"})
public class Scenarios implements Serializable, Containers, Level{
    private ArrayList<Scenario> scenarios;

    public Scenarios()
    {
        scenarios=new ArrayList();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(scenarios==null)  return true;
        for(int i=0;i<scenarios.size();i++)
        {
            scenarios.get(i).realeaseResources();
        }
        
        scenarios.clear();
        scenarios=null;

        return true;
    }
    
    public static synchronized Scenarios create(ArrayList<Scenario> items)
    {
        Scenarios sc=new Scenarios();
        sc.setScenarios(items);
        
        return sc;
    }
    
    @Override
    public int length() {
        return (scenarios==null)?0:scenarios.size();
    }

    @Override
    public boolean isEmpty() {
        if(scenarios==null) return true;
        
        return scenarios.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return Scenario.class;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getScenarios().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Scenarios);
        for(Scenario item:scenarios)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Scenarios).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Scenarios readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Scenarios);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Scenarios+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Scenarios item=new Scenarios();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Scenarios.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Scenario);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Scenario+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Scenario.length()+1);            
            Scenario ds=null;
            try{
                ds=(Scenario) Scenario.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getScenarios().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Scenario.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Scenario);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Scenario+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(scenarios==null || scenarios.isEmpty());
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
        for (Scenario item : scenarios) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    /**
     * @return the scenarios
     */
    @XmlElement(name="Scenario", required=true)
    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    /**
     * @param scenarios the scenarios to set
     */
    public void setScenarios(ArrayList<Scenario> scenarios) {
        if(this.scenarios!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.scenarios = scenarios;
    }
    
    public static Scenarios test( int i)
    {
        Scenarios sc=new Scenarios();
        for(int j=0;j<i;j++)
        {
            sc.getScenarios().add(Scenario.test(j));
        }
        
        return sc;
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
        if(!(target instanceof Scenarios)) return false;
        
        Scenarios cp=(Scenarios)target;
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
        if(!(ptr instanceof Scenarios)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Scenarios comp=(Scenarios)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.scenarios.size()!=comp.getScenarios().size())
            return TokenDifference.createAsAList(Scenarios.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<scenarios.size();i++)
        {
            Scenario pthis=scenarios.get(i);
            Scenario pcomp=comp.getScenarios().get(i);
            
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
        Scenarios psg=test(4);

        String xml=TranslateXML.toXml(psg);
        String json=TranslateJSON.toJSON(psg);
        String brief=psg.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Scenarios psg2=(Scenarios)Scenarios.readFromSt(brief);
        for(Scenario mis2:psg2.getScenarios())
        {
            
            System.out.println("ID: "+mis2.getID());
            System.out.println("Name: "+mis2.getName());
            System.out.println("tL: "+mis2.getTheoreticalLikelihood());
            System.out.println("eL: "+mis2.getEmpiricalLikelihood());
            ScenarioProperties sps2=(ScenarioProperties)mis2.getScenarioProperties();

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
            
        }
        
        System.out.println("Equal: "+psg.equals(psg2));
        
    }
    
}
