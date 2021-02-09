/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.measurement.Scale;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It talks about the indicator associated with a given context and entity state.
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="WeightedIndicator")
@XmlType(propOrder={"indicatorID","indicatorName","scenarioID","ecstateID","weight","parameter","decisionCriteria"})
public class WeightedIndicator extends Indicator implements Serializable,Level{
    private String indicatorID;
    private String indicatorName;
    private String scenarioID;
    private String ecstateID;
    private BigDecimal weight;
    private BigDecimal parameter;
    private DecisionCriteria decisionCriteria;
    
    public WeightedIndicator(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        indicatorID=null;
        indicatorName=null;
        scenarioID=null;
        ecstateID=null;
        weight=null;
        parameter=null;
        if(decisionCriteria!=null)
        {
            decisionCriteria.realeaseResources();
            decisionCriteria=null;
        }
        
        return true;
    }
    
    /**
     * 
     * @param id The indicator ID
     * @param na The indicator name
     * @param sce The scenario ID
     * @param ecs The entity state ID
     * @param we The weight associated
     * @param par the corresponding parameter
     * @param dc The decision criteria
     * @param attributeID The attribute ID that the indicator is interpreting
     * @param sc the scale
     * @return A new instance
     */
    public static synchronized WeightedIndicator create(String id,String na,String sce,String ecs, BigDecimal we,BigDecimal par,
            DecisionCriteria dc, String attributeID, Scale sc)
    {
        WeightedIndicator wi=new WeightedIndicator();
        wi.setDecisionCriteria(dc);
        wi.setEcstateID(ecs);
        wi.setIndicatorID(id);
        wi.setIndicatorName(na);
        wi.setParameter(par);
        wi.setScenarioID(sce);
        wi.setWeight(we);
        wi.setAttributeID(attributeID);
        wi.setScale(sc);
        
        return wi;
    }
    
    @Override
    public int getLevel() {
        return 6;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_WeightedIndicator);
        //attributeID *mandatory*
        sb.append(Tokens.removeTokens(this.getAttributeID().trim())).append(Tokens.fieldSeparator);
        //indicatorID *mandatory*
        sb.append(Tokens.removeTokens(this.getIndicatorID().trim())).append(Tokens.fieldSeparator);
        //indicatorName  *mandatory*
        sb.append(Tokens.removeTokens(this.getIndicatorName().trim())).append(Tokens.fieldSeparator);
        //scenarioID  *mandatory*
        sb.append(Tokens.removeTokens(this.getScenarioID().trim())).append(Tokens.fieldSeparator);
        //ecstateID  *mandatory*
        sb.append(Tokens.removeTokens(this.getEcstateID().trim())).append(Tokens.fieldSeparator);
        //weight  *mandatory*
        sb.append(this.getWeight()).append(Tokens.fieldSeparator);
        //parameter
        sb.append(this.getParameter()).append(Tokens.fieldSeparator);//mandatory  
        //scale *mandatory*
        sb.append(scale.writeTo()).append(Tokens.fieldSeparator);//mandatory  
        //decisionCriteria
        sb.append(decisionCriteria.writeTo());
        sb.append(Tokens.Class_ID_WeightedIndicator).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static WeightedIndicator readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_WeightedIndicator);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_WeightedIndicator+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        WeightedIndicator item=new WeightedIndicator();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_WeightedIndicator.length()+1, idx_en);        
        
        //"attributeID" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The attributeID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The attributeID field is not present and it is mandatory.");
        item.setAttributeID(value);

        //"indicatorID" *mandatory*        
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The indicatorID field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The indicatorID field is not present and it is mandatory");
        item.setIndicatorID(value);
        
        //"indicatorName" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The indicatorName field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The indicatorName field is not present and it is mandatory");
        item.setIndicatorName(value);

        //"scenarioID" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The scenarioID field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The scenarioID field is not present and it is mandatory");
        item.setScenarioID(value);

        //"ecstateID" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ecstateID field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ecstateID field is not present and it is mandatory");
        item.setEcstateID(value);

        //"weight" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The weight field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The weight field is not present and it is mandatory");
        item.setWeight(new BigDecimal(value));

        //"parameter" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The parameter field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The parameter field is not present and it is mandatory");
        item.setParameter(new BigDecimal(value));

        //"Scale" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Scale+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The scale field is not present.");
        value=cleanedText.substring(0, idx_en+idx_en+Tokens.Class_ID_Scale.length()+Tokens.endLevel.length());        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The scale field is not present and it is mandatory");
        item.setScale(Scale.readFromSt(value));
        
        //"decisionCriteria" *mandatory*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Scale.length()+Tokens.endLevel.length()+1);        
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The decisionCriteria field is not present.");
        item.setDecisionCriteria((DecisionCriteria)DecisionCriteria.readFromSt(value));
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(attributeID) || StringUtils.isEmpty(indicatorID) || StringUtils.isEmpty(indicatorName) || StringUtils.isEmpty(scenarioID) ||
                StringUtils.isEmpty(ecstateID) || weight==null || parameter==null || !decisionCriteria.isDefinedProperties() ||
                scale==null || !scale.isDefinedProperties());
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
        sb.append(attributeID).append("-").append(indicatorID).append("-").append(scenarioID).append("-").append(ecstateID);
        return sb.toString();
    }

    /**
     * @return the indicatorID
     */
    public String getIndicatorID() {
        return indicatorID;
    }

    /**
     * @param indicatorID the indicatorID to set
     */
    public void setIndicatorID(String indicatorID) {
        this.indicatorID = indicatorID;
    }

    /**
     * @return the indicatorName
     */
    public String getIndicatorName() {
        return indicatorName;
    }

    /**
     * @param indicatorName the indicatorName to set
     */
    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    /**
     * @return the scenarioID
     */
    public String getScenarioID() {
        return scenarioID;
    }

    /**
     * @param scenarioID the scenarioID to set
     */
    public void setScenarioID(String scenarioID) {
        this.scenarioID = scenarioID;
    }

    /**
     * @return the ecstateID
     */
    public String getEcstateID() {
        return ecstateID;
    }

    /**
     * @param ecstateID the ecstateID to set
     */
    public void setEcstateID(String ecstateID) {
        this.ecstateID = ecstateID;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the parameter
     */
    public BigDecimal getParameter() {
        return parameter;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(BigDecimal parameter) {
        this.parameter = parameter;
    }

    /**
     * @return the decisionCriteria
     */
    public DecisionCriteria getDecisionCriteria() {
        return decisionCriteria;
    }

    /**
     * @param decisionCriteria the decisionCriteria to set
     */
    public void setDecisionCriteria(DecisionCriteria decisionCriteria) {
        if(this.decisionCriteria!=null){
            try{
                this.decisionCriteria.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.decisionCriteria = decisionCriteria;
    }
    
    public static WeightedIndicator test(int k)
    {
        WeightedIndicator wi=new WeightedIndicator();
        wi.setDecisionCriteria(DecisionCriteria.test(3));
        wi.setEcstateID("ecstateID"+k);
        wi.setScenarioID("scenarioID"+k);
        wi.setIndicatorID("indicatorID"+k);
        wi.setAttributeID("attributeID"+k);
        wi.setIndicatorName("indicatorName"+k);
        wi.setParameter(BigDecimal.TEN);
        wi.setWeight(BigDecimal.ONE);
        wi.setScale(Scale.test(1));

        return wi;
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
        if(!(target instanceof WeightedIndicator)) return false;
        
        WeightedIndicator cp=(WeightedIndicator)target;
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
        if(!(ptr instanceof WeightedIndicator)) throw new ProcessingException("The instance to be compared is not of the expected type");
        WeightedIndicator comp=(WeightedIndicator)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(WeightedIndicator.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));

        //  Scale *mandatory*
        ArrayList<TokenDifference> result=this.scale.findDifferences(comp.getScale());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  DecisionCriteria *mandatory*
        result=this.decisionCriteria.findDifferences(comp.getDecisionCriteria());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
                
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        WeightedIndicator wi=test(3);
        
        String xml=TranslateXML.toXml(wi);
        String json=TranslateJSON.toJSON(wi);
        String brief=wi.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);


        WeightedIndicator wi2=(WeightedIndicator)WeightedIndicator.readFromSt(brief);
        
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

        System.out.println("Equal: "+wi.equals(wi2));
    }        
}
