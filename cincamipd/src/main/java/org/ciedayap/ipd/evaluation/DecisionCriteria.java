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
 * A set of decision criterion
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DecisionCriteria")
@XmlType(propOrder={"decisionCriteria"})
public class DecisionCriteria implements Serializable, Containers, Level{
    private ArrayList<DecisionCriterion> decisionCriteria;
    
    public DecisionCriteria()
    {
        decisionCriteria=new ArrayList<>();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(decisionCriteria==null)  return true;
        for(int i=0;i<decisionCriteria.size();i++)
        {
            decisionCriteria.get(i).realeaseResources();
        }
        
        decisionCriteria.clear();
        decisionCriteria=null;

        return true;
    }
    
    public static synchronized DecisionCriteria create(ArrayList<DecisionCriterion> l)
    {
        DecisionCriteria dc=new DecisionCriteria();
        dc.setDecisionCriteria(l);
        return dc;
    }
    
    @Override
    public int length() {
       return (decisionCriteria==null)?0:decisionCriteria.size();
    }

    @Override
    public boolean isEmpty() {
        if(decisionCriteria==null) return true;
        return decisionCriteria.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return DecisionCriterion.class;
    }

    @Override
    public int getLevel() {
        return 7;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_DecisionCriteria);
        for(DecisionCriterion item:decisionCriteria)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_DecisionCriteria).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static DecisionCriteria readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_DecisionCriteria);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_DecisionCriteria+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DecisionCriteria item=new DecisionCriteria();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_DecisionCriteria.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_DecisionCriterion);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_DecisionCriterion+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_DecisionCriterion.length()+1);            
            DecisionCriterion tg=null;
            try{
                tg=(DecisionCriterion) DecisionCriterion.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                tg=null;
            }
            
            if(tg!=null) item.getDecisionCriteria().add(tg);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+1);
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_DecisionCriterion);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_DecisionCriterion+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(decisionCriteria==null || decisionCriteria.isEmpty());
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
        for (DecisionCriterion item : decisionCriteria) 
        {
            sb.append(item.getIdDecisionCriterion()).append("-");
        }
        
        return sb.toString();
    }

    /**
     * @return the decisionCriteria
     */
    @XmlElement(name="DecisionCriterion", required=true)    
    public ArrayList<DecisionCriterion> getDecisionCriteria() {
        return decisionCriteria;
    }

    /**
     * @param decisionCriteria the decisionCriteria to set
     */
    public void setDecisionCriteria(ArrayList<DecisionCriterion> decisionCriteria) {
        if(this.decisionCriteria!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.decisionCriteria = decisionCriteria;
    }
    
    public static DecisionCriteria test(int k)
    {
        DecisionCriteria dc=new DecisionCriteria();
        for(int i=0;i<k;i++)
        {
            dc.getDecisionCriteria().add(DecisionCriterion.test(i));
        }
        
        return dc;
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
        if(!(target instanceof DecisionCriteria)) return false;
        
        DecisionCriteria cp=(DecisionCriteria)target;
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
        if(!(ptr instanceof DecisionCriteria)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DecisionCriteria comp=(DecisionCriteria)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.decisionCriteria.size()!=comp.getDecisionCriteria().size())
            return TokenDifference.createAsAList(DecisionCriteria.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<decisionCriteria.size();i++)
        {
            DecisionCriterion pthis=decisionCriteria.get(i);
            DecisionCriterion pcomp=comp.decisionCriteria.get(i);
            
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
        DecisionCriteria ldc=test(3);
        String xml=TranslateXML.toXml(ldc);
        String json=TranslateJSON.toJSON(ldc);
        String brief=ldc.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);


        DecisionCriteria ldc2=(DecisionCriteria)DecisionCriteria.readFromSt(brief);
        
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
        
        System.out.println("Equal: "+ldc.equals(ldc2));
    }    
}
