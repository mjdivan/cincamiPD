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
 * A set of Weigthed Indicators
 * @author mjdivan
 */
@XmlRootElement(name="WeightedIndicators")
@XmlType(propOrder={"weightedIndicators"})
public class WeightedIndicators implements Serializable, Containers, Level{
    private ArrayList<WeightedIndicator> weightedIndicators;

    public WeightedIndicators()
    {
        weightedIndicators=new ArrayList();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(weightedIndicators==null)  return true;
        for(int i=0;i<weightedIndicators.size();i++)
        {
            weightedIndicators.get(i).realeaseResources();
        }
        
        weightedIndicators.clear();
        weightedIndicators=null;

        return true;
    }
    
    public static synchronized WeightedIndicators create(ArrayList<WeightedIndicator> list)
    {
        WeightedIndicators wi=new WeightedIndicators();
        wi.setWeightedIndicators(list);
        return wi;
    }
    
    @Override
    public int length() {
        return (weightedIndicators==null)?0:weightedIndicators.size();
    }

    @Override
    public boolean isEmpty() {
        if(weightedIndicators==null) return true;
        return weightedIndicators.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return WeightedIndicator.class;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_WeightedIndicators);
        for(WeightedIndicator item:weightedIndicators)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_WeightedIndicators).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static WeightedIndicators readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_WeightedIndicators);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_WeightedIndicators+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        WeightedIndicators item=new WeightedIndicators();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_WeightedIndicators.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_WeightedIndicator);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_WeightedIndicator+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_WeightedIndicator.length()+1);            
            WeightedIndicator ds=null;
            try{
                ds=(WeightedIndicator) WeightedIndicator.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getWeightedIndicators().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_WeightedIndicator.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_WeightedIndicator);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_WeightedIndicator+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(weightedIndicators==null || weightedIndicators.isEmpty());
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
        for (WeightedIndicator item : weightedIndicators) 
        {
            sb.append(item.getUniqueID()).append("-");
        }
        
        return sb.toString();        
    }

    /**
     * @return the weightedIndicators
     */
    @XmlElement(name="WeightedIndicator", required=true)
    public ArrayList<WeightedIndicator> getWeightedIndicators() {
        return weightedIndicators;
    }

    /**
     * @param weightedIndicators the weightedIndicators to set
     */
    public void setWeightedIndicators(ArrayList<WeightedIndicator> weightedIndicators) {
        if(this.weightedIndicators!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.weightedIndicators = weightedIndicators;
    }

    public static WeightedIndicators test(int k)
    {
        WeightedIndicators wis=new WeightedIndicators();
        for(int i=0;i<k;i++)
        {
            wis.getWeightedIndicators().add(WeightedIndicator.test(i));
        }
        
        return wis;
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
        if(!(target instanceof WeightedIndicators)) return false;
        
        WeightedIndicators cp=(WeightedIndicators)target;
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
        if(!(ptr instanceof WeightedIndicators)) throw new ProcessingException("The instance to be compared is not of the expected type");
        WeightedIndicators comp=(WeightedIndicators)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.weightedIndicators.size()!=comp.getWeightedIndicators().size())
            return TokenDifference.createAsAList(WeightedIndicators.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<this.weightedIndicators.size();i++)
        {
            WeightedIndicator pthis=this.weightedIndicators.get(i);
            WeightedIndicator pcomp=comp.getWeightedIndicators().get(i);
            
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
        WeightedIndicators wis=test(3);
        
        String xml=TranslateXML.toXml(wis);
        String json=TranslateJSON.toJSON(wis);
        String brief=wis.writeTo();
                
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);

        WeightedIndicators wis2=(WeightedIndicators)WeightedIndicators.readFromSt(brief);
        for(WeightedIndicator wi2:wis2.getWeightedIndicators())
        {
            System.out.println("State: "+wi2.getEcstateID());
            System.out.println("Indicator: "+wi2.getIndicatorID());
            System.out.println("Ind.Name: "+wi2.getIndicatorName());
            System.out.println("Scenario: "+wi2.getScenarioID());
            System.out.println("W: "+wi2.getWeight());
            System.out.println("P: "+wi2.getParameter());

            DecisionCriteria ldc2=wi2.getDecisionCriteria();

            for(DecisionCriterion dc2:ldc2.getDecisionCriteria())
            {

                System.out.println("ID: "+dc2.getIdDecisionCriterion());
                System.out.println("Name: "+dc2.getName());
                System.out.println("Description: "+dc2.getDescription());
                System.out.println(((dc2.getRange().getMinValueIncluded())?"[":"(")+"R.Min:"+dc2.getRange().getMinValue());
                System.out.println(((dc2.getRange().getMaxValueIncluded())?"]":")")+"R.Max:"+dc2.getRange().getMaxValue());
                System.out.println("Below: "+dc2.getNotifiableBelowRange());
                System.out.println("Bel.Mess:"+dc2.getNbelr_message());
                System.out.println("Between: "+dc2.getNotifiableBetweenRange());
                System.out.println("Bet.Mess:"+dc2.getNbetr_message());
                System.out.println("Above: "+dc2.getNotifiableAboveRange());
                System.out.println("Abo.Mess:"+dc2.getNabor_message());

                Recommender r2=(Recommender)dc2.getRecommender();
                System.out.println("***Recommender***");
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
            }        
        } 
        
        String brief3=wis2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief3));//Reversible                
        System.out.println("Equal2: "+wis.equals(wis2));
        
    }
    
}
