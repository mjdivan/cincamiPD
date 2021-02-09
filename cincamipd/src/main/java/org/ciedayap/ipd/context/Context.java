/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.context;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.states.Scenarios;
import org.ciedayap.ipd.states.StateTransitionModel;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;
/**
 * It represents the context surrounding the entity under analysis
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Context")
@XmlType(propOrder={"ID","name","contextProperties","scenarios","stateTransitionModel"})
public class Context implements Serializable, Level{
    private String ID;//mandatory
    private String name;//mandatory
    private ContextProperties contextProperties;//mandatory
    private Scenarios scenarios; //optional
    private StateTransitionModel stateTransitionModel;//optional
    
    public Context()
    {}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        
        if(contextProperties!=null)
        {
            contextProperties.realeaseResources();
            contextProperties=null;
        }

        if(scenarios!=null)
        {
            scenarios.realeaseResources();
            scenarios=null;            
        }

        if(stateTransitionModel!=null)
        {
            stateTransitionModel.realeaseResources();
            stateTransitionModel=null;            
        }
        
        return true;
    }
    
    public static synchronized Context create(String id, String na, ContextProperties cp)
    {
        Context c=new Context();
        c.setContextProperties(cp);
        c.setID(id);
        c.setName(na);
        
        return c;
    }
    
    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Context);
        
        //"ID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);        
        //contextProperties *mandatory*
        sb.append(contextProperties.writeTo()).append(Tokens.fieldSeparator);
        //scenarios *optional*
        if(scenarios!=null && scenarios.isDefinedProperties())
        {
            if(this.stateTransitionModel==null || !this.stateTransitionModel.isDefinedProperties())   
                throw new ProcessingException("It is necessary the state transition model when states are defined");
            else
            {
                //ecstates
                sb.append(scenarios.writeTo()).append(Tokens.fieldSeparator);
                sb.append(stateTransitionModel.writeTo());
            }
        }
        else
        {
            sb.append("").append(Tokens.fieldSeparator);//No scenarios
            sb.append("");//No transition model
        }
        
        sb.append(Tokens.Class_ID_Context).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Context readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Context);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Context+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Context item=new Context();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Context.length()+1, idx_en);                
        
        //"ID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"name" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);
                 
        //contextProperties *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_CtxProperties+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The attributes field is not present.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_CtxProperties.length()+Tokens.endLevel.length());
        if(StringUtils.isEmpty(value)) throw new ProcessingException("Context properties are not present and it is mandatory");
        item.setContextProperties(ContextProperties.readFromSt(value));        
        
        //scenarios *optional*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_CtxProperties.length()+Tokens.endLevel.length()+1);
        
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Scenarios+Tokens.endLevel+Tokens.fieldSeparator);
        boolean scenarios=false;
        if(idx_en<0) 
        {//ecstates no present
            scenarios=false;
            idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
            if(idx_en<0) throw new ProcessingException("The Scenarios field is not present");
            
            cleanedText=cleanedText.substring(idx_en+1);
        }
        else
        {//ecstates present
            value=cleanedText.substring(0, idx_en+Tokens.Class_ID_Scenarios.length()+Tokens.endLevel.length());
            if(StringUtils.isEmpty(value)) throw new ProcessingException("Scenarios are not present");
            
            item.setScenarios(Scenarios.readFromSt(value));            
            scenarios=true;
            
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Scenarios.length()+Tokens.endLevel.length()+1);
        }
   
        idx_en=cleanedText.indexOf(Tokens.Class_ID_StateTransitionModel+Tokens.endLevel+Tokens.fieldSeparator);        
        value=cleanedText.substring(0);
        if(!scenarios)
        {
            if(!StringUtils.isEmpty(value)) throw new ProcessingException("Some string was incorporated in the state transition model but "
                    + "there no exist scenarios");
        }
        else
        {            
            if(StringUtils.isEmpty(value)) throw new ProcessingException("StateTransitionModel field is not present and scenarios were defined");
            item.setStateTransitionModel(StateTransitionModel.readFromSt(value));
        }

            
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        if(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || contextProperties==null ||
                !contextProperties.isDefinedProperties()) return false;
        
        if(scenarios!=null && scenarios.isDefinedProperties())
        {
            return !(stateTransitionModel==null || !stateTransitionModel.isDefinedProperties());
        }
        else
        {
            return (stateTransitionModel==null || !stateTransitionModel.isDefinedProperties());
        }
        
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
        return ID;
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
     * @return the contextProperties
     */
    public ContextProperties getContextProperties() {
        return contextProperties;
    }

    /**
     * @param contextProperties the contextProperties to set
     */
    public void setContextProperties(ContextProperties contextProperties) {
        if(this.contextProperties!=null){
            try{
                this.contextProperties.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.contextProperties = contextProperties;
    }

    /**
     * @return the scenarios
     */
    public Scenarios getScenarios() {
        return scenarios;
    }

    /**
     * @param scenarios the scenarios to set
     */
    public void setScenarios(Scenarios scenarios) {
        if(this.scenarios!=null){
            try{
                this.scenarios.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.scenarios = scenarios;
    }

    /**
     * @return the stateTransitionModel
     */
    public StateTransitionModel getStateTransitionModel() {
        return stateTransitionModel;
    }

    /**
     * @param stateTransitionModel the stateTransitionModel to set
     */
    public void setStateTransitionModel(StateTransitionModel stateTransitionModel) {
        if(this.stateTransitionModel!=null){
            try{
                this.stateTransitionModel.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.stateTransitionModel = stateTransitionModel;
    }
    
    public static Context test(int k)
    {
        Context c=new Context();
        c.setID("ID"+k);
        c.setName("Name"+k);
        c.setScenarios(Scenarios.test(3));
        c.setStateTransitionModel(StateTransitionModel.test(1));
        c.setContextProperties(ContextProperties.test(3));

        return c;
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
        if(!(target instanceof Context)) return false;
        
        Context cp=(Context)target;
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
        if(!(ptr instanceof Context)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Context comp=(Context)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(Context.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  ContextProperties *mandatory*
        ArrayList<TokenDifference> result;
        result=this.contextProperties.findDifferences(comp.getContextProperties());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  Scenarios; **optional**
        if(scenarios!=null && scenarios.length()>0 &&
                comp.getScenarios()!=null && comp.getScenarios().length()>0)
        {
            result=this.scenarios.findDifferences(comp.getScenarios());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((scenarios!=null && scenarios.length()>0))
            {
                global.add(TokenDifference.create(Scenarios.class, scenarios.getUniqueID(), 
                        scenarios.getLevel(), scenarios.computeFingerprint(), null));
            }
            
            if(comp.getScenarios()!=null && comp.getScenarios().length()>0)
            {
                global.add(TokenDifference.create(Scenarios.class, comp.getScenarios().getUniqueID(), 
                        comp.getScenarios().getLevel(), null, comp.getScenarios().computeFingerprint()));                
            }            
        }

        //  STM; **optional**
        if(this.stateTransitionModel!=null && stateTransitionModel.isDefinedProperties() &&
                comp.getStateTransitionModel()!=null && comp.getStateTransitionModel().isDefinedProperties())
        {
            result=this.stateTransitionModel.findDifferences(comp.getStateTransitionModel());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((stateTransitionModel!=null && stateTransitionModel.isDefinedProperties()))
            {
                global.add(TokenDifference.create(StateTransitionModel.class, stateTransitionModel.getUniqueID(), 
                        stateTransitionModel.getLevel(), stateTransitionModel.computeFingerprint(), null));
            }
            
            if(comp.getStateTransitionModel()!=null && comp.getStateTransitionModel().isDefinedProperties())
            {
                global.add(TokenDifference.create(StateTransitionModel.class, comp.getStateTransitionModel().getUniqueID(), 
                        comp.getStateTransitionModel().getLevel(), null, comp.getStateTransitionModel().computeFingerprint()));                
            }            
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {
        Context ctx=test(1);
        
        String xml=TranslateXML.toXml(ctx);
        String json=TranslateJSON.toJSON(ctx);
        String brief=ctx.writeTo();

        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Context ctx2=Context.readFromSt(brief);
        System.out.println("ID: "+ctx.getID());
        System.out.println("Name: "+ctx.getName());
        System.out.println("Properties:\n "+ctx.getContextProperties().writeTo());
        
        System.out.println("Scenarios: \n");
        if(ctx.getScenarios()!=null) System.out.println(ctx2.getScenarios().writeTo());
        else System.out.println("null");
        
        System.out.println("STM: \n");
        if(ctx.getStateTransitionModel()!=null) System.out.println(ctx2.getStateTransitionModel().writeTo());
        else System.out.println("null");

        String brief2=ctx2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));//Reversible
        System.out.println("Equal: "+ctx.equals(ctx2));
    }
}
