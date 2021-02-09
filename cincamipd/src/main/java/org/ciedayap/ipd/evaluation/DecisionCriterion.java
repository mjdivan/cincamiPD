/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import org.ciedayap.ipd.utils.StringUtils;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.states.Range;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 *  It represents an individual decision criterion to be interpeted.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DecisionCriterion")
@XmlType(propOrder={"idDecisionCriterion","name","description","range","notifiableBelowRange","notifiableBetweenRange",
    "notifiableAboveRange","nbelr_message","nbetr_message","nabor_message","recommender"})
public class DecisionCriterion implements Serializable, Level{
    private String idDecisionCriterion;//mandatory
    private String name; //mandatory
    private String description;//optional
    private Range range;//mandatory
    private Boolean notifiableBelowRange;//mandatory
    private Boolean notifiableBetweenRange;//mandatory
    private Boolean notifiableAboveRange;//mandatory
    private String nbelr_message;//mandatory when notifiableBelowRange is true
    private String nbetr_message;//mandatory when notifiableBetweenRange is true
    private String nabor_message;//mandatory when notifiableAboveRange
    private Recommender recommender;//mandatory
    
    public DecisionCriterion(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        idDecisionCriterion=null;
        name=null;
        description=null;
        range=null;
        notifiableBelowRange=null;
        notifiableBetweenRange=null;
        notifiableAboveRange=null;
        nbelr_message=null;
        nbetr_message=null;
        nabor_message=null;
        
        if(recommender!=null)
        {
            recommender.realeaseResources();
            recommender=null;
        }
        return true;
    }
    
    public static synchronized DecisionCriterion create(String id, String na, Range r, 
            Boolean belr,Boolean betr, Boolean abor,
            String sbelr,String sbetr, String sabor,
            Recommender rec)
    {
        DecisionCriterion dc=new DecisionCriterion();
        dc.setIdDecisionCriterion(id);
        dc.setName(na);
        dc.setRange(r);
        dc.setNotifiableBelowRange(belr);
        dc.setNotifiableBetweenRange(betr);
        dc.setNotifiableAboveRange(abor);
        dc.setNbelr_message(sbelr);
        dc.setNbetr_message(sbetr);
        dc.setNabor_message(sabor);
        dc.setRecommender(rec);
        
        return dc;
    }

    public static synchronized DecisionCriterion create(String id, String na, Range r,             
            Recommender rec)
    {
        DecisionCriterion dc=new DecisionCriterion();
        dc.setIdDecisionCriterion(id);
        dc.setName(na);
        dc.setRange(r);
        dc.setNotifiableBelowRange(true);
        dc.setNotifiableBetweenRange(true);
        dc.setNotifiableAboveRange(true);
        dc.setNbelr_message("Below range");
        dc.setNbetr_message("Between range");
        dc.setNabor_message("Above range");
        dc.setRecommender(rec);
        
        return dc;
    }
    
    @Override
    public int getLevel() {
        return 8;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_DecisionCriterion);
        //IdDecisionCriterion *mandatory*
        sb.append(Tokens.removeTokens(this.getIdDecisionCriterion().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //description *optional*
        sb.append((StringUtils.isEmpty(description)?"":Tokens.removeTokens(description.trim()))).append(Tokens.fieldSeparator);
        //range *mandatory*
        sb.append(range.writeTo()).append(Tokens.fieldSeparator);
        //notifiableBelowRange;//mandatory
        sb.append(this.notifiableBelowRange).append(Tokens.fieldSeparator);
        //notifiableBetweenRange;//mandatory
        sb.append(this.notifiableBetweenRange).append(Tokens.fieldSeparator);
        //notifiableAboveRange;//mandatory
        sb.append(this.notifiableAboveRange).append(Tokens.fieldSeparator);
        //nbelr_message;//mandatory when notifiableBelowRange is true
        if(this.notifiableBelowRange) 
            sb.append(Tokens.removeTokens(this.nbelr_message.trim())).append(Tokens.fieldSeparator);
         else
            sb.append(Tokens.fieldSeparator);

        if(this.notifiableBetweenRange) 
            sb.append(Tokens.removeTokens(this.nbetr_message.trim())).append(Tokens.fieldSeparator);
         else
            sb.append(Tokens.fieldSeparator);

        if(this.notifiableAboveRange) 
            sb.append(Tokens.removeTokens(this.nabor_message.trim())).append(Tokens.fieldSeparator);
         else
            sb.append(Tokens.fieldSeparator);
        
        sb.append(recommender.writeTo());//mandatory        
        sb.append(Tokens.Class_ID_DecisionCriterion).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DecisionCriterion readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_DecisionCriterion);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_DecisionCriterion+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DecisionCriterion item=new DecisionCriterion();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_DecisionCriterion.length()+1, idx_en);        
        
        //"ID" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The IdDecisionCriterion( field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The IdDecisionCriterion( field is not present and it is mandatory.");
        item.setIdDecisionCriterion(value);
        
        //"name" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);

        //"description" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(!StringUtils.isEmpty(value)) item.setDescription(value);
        
        //"range" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Range+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The range field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_Range.length()+Tokens.endLevel.length());        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The range field is not present and it is mandatory");
        item.setRange((Range)Range.readFromSt(value));

        //"notifiableBelowRange" *mandatory*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Range.length()+Tokens.endLevel.length()+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The notifiableBelowRange field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The notifiableBelowRange field is not present and it is mandatory");
        item.setNotifiableBelowRange(Boolean.valueOf(value));

        //"notifiableBetweenRange" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The notifiableBetweenRange field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The notifiableBetweenRange field is not present and it is mandatory");
        item.setNotifiableBetweenRange(Boolean.valueOf(value));

        //"notifiableAboveRange" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The notifiableAboveRange field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The notifiableAboveRange field is not present and it is mandatory");
        item.setNotifiableAboveRange(Boolean.valueOf(value));

        //"nbelr_message" *optionally mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The nbelr_message field is not present.");
        value=cleanedText.substring(0, idx_en);                
        if(item.getNotifiableBelowRange() && StringUtils.isEmpty(value)) throw new ProcessingException("The nbelr_message field is not present and it is mandatory");
        item.setNbelr_message(value);

        //"nbetr_message" *optionally mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The nbetr_message field is not present.");
        value=cleanedText.substring(0, idx_en);                
        if(item.getNotifiableBetweenRange() && StringUtils.isEmpty(value)) throw new ProcessingException("The nbetr_message field is not present and it is mandatory");
        item.setNbetr_message(value);

        //"nabor_message" *optionally mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The nabor_message field is not present.");
        value=cleanedText.substring(0, idx_en);                
        if(item.getNotifiableAboveRange() && StringUtils.isEmpty(value)) throw new ProcessingException("The nabor_message field is not present and it is mandatory");
        item.setNabor_message(value);
        
        //"recommender" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The recommender field is not present.");
        item.setRecommender((Recommender)Recommender.readFromSt(value));
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(idDecisionCriterion) || StringUtils.isEmpty(name) ||
                range==null || !range.isDefinedProperties() || notifiableBelowRange==null ||
                notifiableBetweenRange==null || notifiableAboveRange==null || recommender==null ||
                (notifiableBelowRange==true && StringUtils.isEmpty(nbelr_message)) ||
                (notifiableBetweenRange==true && StringUtils.isEmpty(nbetr_message)) ||
                (notifiableAboveRange==true && StringUtils.isEmpty(nabor_message)));
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
        return this.idDecisionCriterion;
    }

    /**
     * @return the IdDecisionCriterion
     */
    public String getIdDecisionCriterion() {
        return idDecisionCriterion;
    }

    /**
     * @param IdDecisionCriterion the IdDecisionCriterion to set
     */
    public void setIdDecisionCriterion(String IdDecisionCriterion) {
        this.idDecisionCriterion = IdDecisionCriterion;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the range
     */
    public Range getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(Range range) {
        this.range = range;
    }

    /**
     * @return the notifiableBelowRange
     */
    public Boolean getNotifiableBelowRange() {
        return notifiableBelowRange;
    }

    /**
     * @param notifiableBelowRange the notifiableBelowRange to set
     */
    public void setNotifiableBelowRange(Boolean notifiableBelowRange) {
        this.notifiableBelowRange = notifiableBelowRange;
    }

    /**
     * @return the notifiableBetweenRange
     */
    public Boolean getNotifiableBetweenRange() {
        return notifiableBetweenRange;
    }

    /**
     * @param notifiableBetweenRange the notifiableBetweenRange to set
     */
    public void setNotifiableBetweenRange(Boolean notifiableBetweenRange) {
        this.notifiableBetweenRange = notifiableBetweenRange;
    }

    /**
     * @return the notifiableAboveRange
     */
    public Boolean getNotifiableAboveRange() {
        return notifiableAboveRange;
    }

    /**
     * @param notifiableAboveRange the notifiableAboveRange to set
     */
    public void setNotifiableAboveRange(Boolean notifiableAboveRange) {
        this.notifiableAboveRange = notifiableAboveRange;
    }

    /**
     * @return the nbelr_message
     */
    public String getNbelr_message() {
        return nbelr_message;
    }

    /**
     * @param nbelr_message the nbelr_message to set
     */
    public void setNbelr_message(String nbelr_message) {
        this.nbelr_message = nbelr_message;
    }

    /**
     * @return the nbetr_message
     */
    public String getNbetr_message() {
        return nbetr_message;
    }

    /**
     * @param nbetr_message the nbetr_message to set
     */
    public void setNbetr_message(String nbetr_message) {
        this.nbetr_message = nbetr_message;
    }

    /**
     * @return the nabor_message
     */
    public String getNabor_message() {
        return nabor_message;
    }

    /**
     * @param nabor_message the nabor_message to set
     */
    public void setNabor_message(String nabor_message) {
        this.nabor_message = nabor_message;
    }

    /**
     * @return the recommender
     */
    public Recommender getRecommender() {
        return recommender;
    }

    /**
     * @param recommender the recommender to set
     */
    public void setRecommender(Recommender recommender) {
        if(this.recommender!=null){
            try{
                this.recommender.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.recommender = recommender;
    }

    public static DecisionCriterion test(int k)
    {
        DecisionCriterion dc=new DecisionCriterion();
        
        dc.setDescription("My decision criterion"+k);
        dc.setIdDecisionCriterion("IDDC"+k);
        dc.setName("Name DC"+k);
        dc.setRange(Range.test());
        dc.setRecommender(Recommender.test(1));
        dc.setNotifiableBelowRange(Boolean.FALSE);
        dc.setNotifiableBetweenRange(Boolean.FALSE);
        dc.setNotifiableAboveRange(Boolean.TRUE);
        dc.setNbelr_message("Below");
        dc.setNbetr_message("Between");
        dc.setNabor_message("Above");

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
        if(!(target instanceof DecisionCriterion)) return false;
        
        DecisionCriterion cp=(DecisionCriterion)target;
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
        if(!(ptr instanceof DecisionCriterion)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DecisionCriterion comp=(DecisionCriterion)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(DecisionCriterion.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  Range *mandatory*
        ArrayList<TokenDifference> result=this.range.findDifferences(comp.getRange());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  Recommender *mandatory*        
        result=this.recommender.findDifferences(comp.getRecommender());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        DecisionCriterion dc=DecisionCriterion.test(1);
        
        String xml=TranslateXML.toXml(dc);
        String json=TranslateJSON.toJSON(dc);
        String brief=dc.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);


        DecisionCriterion dc2=(DecisionCriterion)DecisionCriterion.readFromSt(brief);
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
        
        String brief3=dc2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief3));//Reversible                        
        System.out.println("Equal2:"+dc.equals(dc2));
    }
}
