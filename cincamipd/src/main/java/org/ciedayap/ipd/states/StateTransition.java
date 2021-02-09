/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 *
 * @author mjdivan
 */
@XmlRootElement(name="StateTransition")
@XmlType(propOrder={"cost","income","likelihood","similarity","origin","target"})
public class StateTransition implements Serializable, Level{
    /**
     * Cost related to take the  transition.  Optional
     */
    private BigDecimal cost;
    /**
     * Income related to take the  transition.  Optional
     */    
    private BigDecimal income;
    /**
     * Likelihood related to take the  transition.  Optional
     */    
    private BigDecimal likelihood;
    /**
     * Similarity between origin and target about the  transition.  Optional
     */    
    private BigDecimal similarity;
    /**
     * The origin of the transition. Mandatory
     */
    private State origin;
    /**
     * The target of the transition. Mandatory
     */
    private State target;
    
    public StateTransition()
    {        
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        cost=null;
        income=null;
        likelihood=null;
        similarity=null;
        if(origin!=null) origin.realeaseResources();
        if(target!=null) target.realeaseResources();

        return true;
    }
    
    public static synchronized StateTransition create(State or,State ta,BigDecimal sim, BigDecimal like,BigDecimal in,BigDecimal co)            
    {
        StateTransition st=new StateTransition();
        st.setCost(co);
        st.setIncome(in);
        st.setLikelihood(like);
        
        if(or instanceof ECState)
            st.setOrigin(((ECState)or).getBase());
        else
        {
            if(or instanceof Scenario)
                st.setOrigin(((Scenario)or).getBase());
            else
                st.setOrigin(or);
        }
        
        st.setSimilarity(sim);

        if(ta instanceof ECState)
            st.setTarget(((ECState)ta).getBase());
        else
        {
            if(ta instanceof Scenario)
                st.setTarget(((Scenario)ta).getBase());
            else
                st.setTarget(ta);
        }


        
        return st;        
    }

    public static synchronized StateTransition create(State or,State ta)            
    {
        StateTransition st=new StateTransition();
        if(or instanceof ECState)
            st.setOrigin(((ECState)or).getBase());
        else
        {
            if(or instanceof Scenario)
                st.setOrigin(((Scenario)or).getBase());
            else
                st.setOrigin(or);
        }       

        if(ta instanceof ECState)
            st.setTarget(((ECState)ta).getBase());
        else
        {
            if(ta instanceof Scenario)
                st.setTarget(((Scenario)ta).getBase());
            else
                st.setTarget(ta);
        }
        
        return st;        
    }
    
    @Override
    public int getLevel() {
        return 8;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_StateTransition);
        //cost *optional*
        sb.append((this.getCost()==null)?"":this.getCost()).append(Tokens.fieldSeparator);
        //income *optional*
        sb.append((this.getIncome()==null)?"":this.getIncome()).append(Tokens.fieldSeparator);
        //likelihood *optional*
        sb.append((this.getLikelihood()==null)?"":this.getLikelihood()).append(Tokens.fieldSeparator);
        //similarity *optional*
        sb.append((this.getSimilarity()==null)?"":this.getSimilarity()).append(Tokens.fieldSeparator);
        //origin *mandatory*
        sb.append(origin.writeTo()).append(Tokens.fieldSeparator);
        //target *mandatory*
        sb.append(target.writeTo());
        
        sb.append(Tokens.Class_ID_StateTransition).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static StateTransition readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_StateTransition);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_StateTransition+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        StateTransition item=new StateTransition();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_StateTransition.length()+1, idx_en);        
        
        //"cost" *optional*
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The cost field is not present.");
        String value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setCost(new BigDecimal(value));

        //"income" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The income field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setIncome(new BigDecimal(value));

        //"likelihood"  *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The likelihood field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setLikelihood(new BigDecimal(value));

        //"similarity" *optional* 
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The similarity field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSimilarity(new BigDecimal(value));
        
        //"origin" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The Origin field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.endLevel.length());
        item.setOrigin((State) State.readFromSt(value));
        
        //"target" *mandatory*        
        cleanedText=cleanedText.substring(idx_en+Tokens.endLevel.length()+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The target field is not present and it is mandatory.");
        item.setTarget((State)State.readFromSt(value));
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(origin==null || target==null || !origin.isDefinedProperties() || !target.isDefinedProperties());
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
        
        return getOrigin().getID()+"-"+getTarget().getID();
    }

    /**
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * @return the income
     */
    public BigDecimal getIncome() {
        return income;
    }

    /**
     * @param income the income to set
     */
    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    /**
     * @return the likelihood
     */
    public BigDecimal getLikelihood() {
        return likelihood;
    }

    /**
     * @param likelihood the likelihood to set
     */
    public void setLikelihood(BigDecimal likelihood) {
        this.likelihood = likelihood;
    }

    /**
     * @return the similarity
     */
    public BigDecimal getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(BigDecimal similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the origin
     */
    public State getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(State origin) {
        if(this.origin!=null){
            try{
                this.origin.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.origin = origin;
    }

    /**
     * @return the target
     */
    public State getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(State target) {
        if(this.target!=null){
            try{
                this.target.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.target = target;
    }
    
    public static StateTransition test(int i)
    {
        StateTransition st=new StateTransition();
        st.setCost(BigDecimal.ONE);
        st.setIncome(BigDecimal.TEN);
        st.setLikelihood(new BigDecimal("0.56"));
        st.setSimilarity(new BigDecimal("0.77"));

        st.setOrigin(State.test(1));
        st.setTarget(State.test(2));
        
        return st;
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
        if(!(target instanceof StateTransition)) return false;
        
        StateTransition cp=(StateTransition)target;
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
        if(!(ptr instanceof StateTransition)) throw new ProcessingException("The instance to be compared is not of the expected type");
        StateTransition comp=(StateTransition)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(StateTransition.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        StateTransition st=test(1);
        
        String xml=TranslateXML.toXml(st);
        String json=TranslateJSON.toJSON(st);
        String brief=st.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        StateTransition st2=(StateTransition) StateTransition.readFromSt(brief);
        
        System.out.println("ID: "+st2.getUniqueID());
        System.out.println("Cost: "+st2.getCost());
        System.out.println("Income: "+st2.getIncome());
        System.out.println("Likelihood: "+st2.getLikelihood());
        System.out.println("Similarity: "+st2.getSimilarity());
      
        State r=st2.getOrigin();
        System.out.println("***ORIGIN***");
        System.out.println("ID: "+r.getID());
        System.out.println("Name: "+r.getName());
        System.out.println("Version: "+r.getVersion());
        System.out.println("eL: "+r.getEmpiricalLikelihood());
        System.out.println("tL: "+r.getTheoreticalLikelihood());
        r=st2.getTarget();
        System.out.println("***TARGET***");
        System.out.println("ID: "+r.getID());
        System.out.println("Name: "+r.getName());
        System.out.println("Version: "+r.getVersion());
        System.out.println("eL: "+r.getEmpiricalLikelihood());
        System.out.println("tL: "+r.getTheoreticalLikelihood());

        System.out.println("Equal: "+st.equals(st2));
        ArrayList<TokenDifference> list=st.findDifferences(st2);
        if(list==null) System.out.println("**No difference**");
        else
        {
            for(TokenDifference td:list)
            {
                System.out.println("Level: "+td.getLevel());
                System.out.println("ID: "+td.getID());
                System.out.println("Class: "+td.getTheclass());
            }
        }        
    }
    
}
