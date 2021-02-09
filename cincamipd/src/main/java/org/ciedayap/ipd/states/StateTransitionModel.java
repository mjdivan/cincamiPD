/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

import java.io.Serializable;
import java.util.ArrayList;
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
 * It gathers the state transition model with all the involved  transitions
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="StateTransitionModel")
@XmlType(propOrder={"ID","name","version","transitions"})
public class StateTransitionModel implements Serializable, Level{
    private String ID;
    private String name;            
    private String version;
    private Transitions transitions;
    
    public StateTransitionModel()
    {        
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        version=null;
        name=null;
        if(transitions!=null) 
        {
            transitions.realeaseResources();
            transitions=null;
        }
        
        return true;
    }
    
    public static synchronized StateTransitionModel create(String id, String na,String ve, Transitions t)
    {
        StateTransitionModel stm=new StateTransitionModel();
        stm.setID(id);
        stm.setName(na);
        stm.setTransitions(t);
        stm.setVersion(ve);
        
        return stm;
    }
    
    @Override
    public int getLevel() {
        return 6; 
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_StateTransitionModel);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(getID())).append(Tokens.fieldSeparator);
        //name *mandatory*
        sb.append(Tokens.removeTokens(getName())).append(Tokens.fieldSeparator);
        //version *mandatory*
        sb.append(Tokens.removeTokens(getVersion())).append(Tokens.fieldSeparator);
        //transitions *mandatory*
        sb.append(getTransitions().writeTo());

        sb.append(Tokens.Class_ID_StateTransitionModel).append(Tokens.endLevel);
        
        return  sb.toString();               

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static StateTransitionModel readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_StateTransitionModel);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_StateTransitionModel+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        StateTransitionModel item=new StateTransitionModel();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_StateTransitionModel.length()+1, idx_en);        
        
        //ID *mandatory*
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ID field is not present.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory");
        item.setID(value);

        //name *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);

        //version *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The version field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The version field is not present and it is mandatory");
        item.setVersion(value);
        
        //transitions *mandatory*     
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The transitions field is not present and it is mandatory.");
        item.setTransitions((Transitions)Transitions.readFromSt(value));
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || StringUtils.isEmpty(version)  || transitions==null ||
                !transitions.isDefinedProperties());
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
        return getID();
    }    

    /**
     * @return the ID
     */
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the transitions
     */
    public Transitions getTransitions() {
        return transitions;
    }

    /**
     * @param transitions the transitions to set
     */
    public void setTransitions(Transitions transitions) {
        if(this.transitions!=null){
            try{
                this.transitions.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.transitions = transitions;
    }
    
    public static StateTransitionModel test(int i)
    {
        StateTransitionModel stm=new StateTransitionModel();
        stm.setID("IDSTM");
        stm.setName("NameSTM");
        stm.setVersion("1.0.1");
        stm.setTransitions(Transitions.test(3));
        
        return stm;
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
        if(!(target instanceof StateTransitionModel)) return false;
        
        StateTransitionModel cp=(StateTransitionModel)target;
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
        if(!(ptr instanceof StateTransitionModel)) throw new ProcessingException("The instance to be compared is not of the expected type");
        StateTransitionModel comp=(StateTransitionModel)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();        
        
        global.add(TokenDifference.create(StateTransitionModel.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        ArrayList<TokenDifference> result=this.getTransitions().findDifferences(comp.getTransitions());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);            
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        StateTransitionModel  stm=test(4);
                
        String xml=TranslateXML.toXml(stm);
        String json=TranslateJSON.toJSON(stm);
        String brief=stm.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        StateTransitionModel stm2=(StateTransitionModel)StateTransitionModel.readFromSt(brief);
        
        System.out.println("ID: "+stm2.getID());
        System.out.println("Name: "+stm2.getName());
        System.out.println("Version: "+stm2.getVersion());
        Transitions list2=(Transitions)stm2.getTransitions();
        for(StateTransition st2:list2.getTransitions())
        {
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
        }
        
        System.out.println("Equals: "+stm.equals(stm2));
    }    
    
}
