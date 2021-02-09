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
 * It is a container of transitions
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Transitions")
@XmlType(propOrder={"transitions"})
public class Transitions implements Serializable, Level, Containers{
    private ArrayList<StateTransition> transitions;
    
    public Transitions()
    {
        transitions=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(transitions==null)  return true;
        for(int i=0;i<transitions.size();i++)
        {
            transitions.get(i).realeaseResources();
        }
        
        transitions.clear();
        transitions=null;
        
        return true;
    }
    
    public static synchronized Transitions create(ArrayList<StateTransition> list)
    {
        Transitions t=new Transitions();
        
        t.setTransitions(list);
        return t;
    }
    
    @Override
    public int getLevel() {
        return 7;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(transitions.isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Transitions);
        for(StateTransition item:transitions)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Transitions).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Transitions readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Transitions);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Transitions+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Transitions item=new Transitions();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Transitions.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_StateTransition);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_StateTransition+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_StateTransition.length()+1);            
            StateTransition st=null;
            try{
                st=(StateTransition) StateTransition.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                st=null;
            }
            
            if(st!=null) item.getTransitions().add(st);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_StateTransition.length()+1);
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_StateTransition);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_StateTransition+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return (transitions!=null && !transitions.isEmpty());
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
        for (StateTransition item : transitions) 
        {
            sb.append(item.getUniqueID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {        
        return (transitions!=null)?transitions.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(transitions==null) return true;
        return transitions.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return StateTransition.class;
    }

    /**
     * @return the transitions
     */
    @XmlElement(name="StateTransition", required=true)  
    public ArrayList<StateTransition> getTransitions() {
        return transitions;
    }

    /**
     * @param transitions the transitions to set
     */
    public void setTransitions(ArrayList<StateTransition> transitions) {
        if(this.transitions!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.transitions = transitions;
    }
    
    public static Transitions test(int k)
    {
        Transitions list=new Transitions();
        for(int i=0;i<k;i++)
        {
            StateTransition st=StateTransition.test(i);
            
            list.getTransitions().add(st);
        }
        
        return list;        
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
        if(!(target instanceof Transitions)) return false;
        
        Transitions cp=(Transitions)target;
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
        if(!(ptr instanceof Transitions)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Transitions comp=(Transitions)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.transitions.size()!=comp.getTransitions().size())
            return TokenDifference.createAsAList(Transitions.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<transitions.size();i++)
        {
            StateTransition pthis=transitions.get(i);
            StateTransition pcomp=comp.getTransitions().get(i);
            
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
        Transitions list=test(4);
        
        
        String xml=TranslateXML.toXml(list);
        String json=TranslateJSON.toJSON(list);
        String brief=list.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Transitions list2=(Transitions)Transitions.readFromSt(brief);
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
        
        System.out.println("Equals: "+list.equals(list2));
    }    
}

