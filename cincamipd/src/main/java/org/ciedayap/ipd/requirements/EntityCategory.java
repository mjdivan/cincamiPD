/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.requirements;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.states.ECStates;
import org.ciedayap.ipd.states.StateTransitionModel;
import org.ciedayap.ipd.evaluation.WeightedIndicators;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 *  it represents the entity category under monitoring
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="EntityCategory")
@XmlType(propOrder={"ID","name","description","superCategoryID","attributes","entities","ecStates",
"stateTransitionModel","weightedIndicators"})
public class EntityCategory implements Serializable, Level{
    private String ID;//mandatory
    private String name; //mandatory
    private String description;//optional
    private String superCategoryID;//optional
    private Attributes attributes;//mandatory
    private Entities entities;//mandatory
    private ECStates ecStates;//optional
    private StateTransitionModel stateTransitionModel;//optional. It is mandatory just when ecStates is present
    private WeightedIndicators weightedIndicators;//mandatory
    
    public EntityCategory()
    {        
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        description=null;
        superCategoryID=null;
        if(attributes!=null)
        {
            attributes.realeaseResources();
            attributes=null;
        }
        
        if(entities!=null)
        {
            entities.realeaseResources();
            entities=null;
        }
        
        if(ecStates!=null)
        {
            ecStates.realeaseResources();
            ecStates=null;
        }
        
        if(stateTransitionModel!=null)
        {
            stateTransitionModel.realeaseResources();
            stateTransitionModel=null;
        }
        
        if(weightedIndicators!=null)
        {
            weightedIndicators.realeaseResources();
            weightedIndicators=null;
        }

        return true;
    }
    
    public static synchronized EntityCategory create(String id, String na, Attributes at, Entities ent, WeightedIndicators wi)
    {
        EntityCategory entcat=new EntityCategory();
        entcat.setAttributes(at);
        entcat.setID(id);
        entcat.setName(na);
        entcat.setEntities(ent);
        entcat.setWeightedIndicators(wi);
        
        return entcat;        
    }
    
    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_EntityCategory);
        
        //"ID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);        
        //"description" *optional*
        sb.append((StringUtils.isEmpty(description))?"":Tokens.removeTokens(this.getDescription())).append(Tokens.fieldSeparator);
        //"superCategory" *optional*
        sb.append((StringUtils.isEmpty(this.superCategoryID))?"":Tokens.removeTokens(this.superCategoryID)).append(Tokens.fieldSeparator);
        //attributes *mandatory*
        sb.append(attributes.writeTo()).append(Tokens.fieldSeparator);
        //entities *mandatory*
        sb.append(entities.writeTo()).append(Tokens.fieldSeparator);
        //ecStates *optional*
        if(ecStates!=null && ecStates.isDefinedProperties())
        {
            if(this.stateTransitionModel==null || !this.stateTransitionModel.isDefinedProperties())   
                throw new ProcessingException("It is necessary the state transition model when states are defined");
            else
            {
                //ecstates
                sb.append(ecStates.writeTo()).append(Tokens.fieldSeparator);
                sb.append(stateTransitionModel.writeTo()).append(Tokens.fieldSeparator);
            }
        }
        else
        {
            sb.append("").append(Tokens.fieldSeparator);//No ecstates
            sb.append("").append(Tokens.fieldSeparator);//No transition model
        }
        
        sb.append(this.weightedIndicators.writeTo());
        sb.append(Tokens.Class_ID_EntityCategory).append(Tokens.endLevel);
        
        return sb.toString();        

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static EntityCategory readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_EntityCategory);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_EntityCategory+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        EntityCategory item=new EntityCategory();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_EntityCategory.length()+1, idx_en);                
        
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
                
        //"description" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setDescription(value);

        //"superCategory" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The superCategory field is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSuperCategoryID(value);
 
        //attributes *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Attributes+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The attributes field is not present.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_Attributes.length()+Tokens.endLevel.length());
        if(StringUtils.isEmpty(value)) throw new ProcessingException("Attributes are not present and it is mandatory");
        item.setAttributes(Attributes.readFromSt(value));
        
        //entities *mandatory*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Attributes.length()+Tokens.endLevel.length()+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Entities+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The entities field is not present.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_Entities.length()+Tokens.endLevel.length());
        if(StringUtils.isEmpty(value)) throw new ProcessingException("Entities are not present and it is mandatory");
        item.setEntities(Entities.readFromSt(value));
        
        //ecStates *optional*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Entities.length()+Tokens.endLevel.length()+1);
        
        idx_en=cleanedText.indexOf(Tokens.Class_ID_ECStates+Tokens.endLevel+Tokens.fieldSeparator);
        boolean states=false;
        if(idx_en<0) 
        {//ecstates no present
            states=false;
            idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
            if(idx_en<0) throw new ProcessingException("The ECStates field is not present");
            
            cleanedText=cleanedText.substring(idx_en+1);
        }
        else
        {//ecstates present
            value=cleanedText.substring(0, idx_en+Tokens.Class_ID_ECStates.length()+Tokens.endLevel.length());
            if(StringUtils.isEmpty(value)) throw new ProcessingException("ECStates are not present");
            
            item.setEcStates(ECStates.readFromSt(value));            
            states=true;
            
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_ECStates.length()+Tokens.endLevel.length()+1);
        }
   
        idx_en=cleanedText.indexOf(Tokens.Class_ID_StateTransitionModel+Tokens.endLevel+Tokens.fieldSeparator);        
        
        if(idx_en<0)
        {//No Transition Model
            if(states)
            {
                throw new ProcessingException("A Transition Model was not found but there exist a definition of states");
            }
            else
            {
                idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
                if(idx_en<0) throw new ProcessingException("The StateTransitionModel field is not present");

                cleanedText=cleanedText.substring(idx_en+1);
            }            
        }
        else
        {//Transition Model
            if(!states)
            {
                throw new ProcessingException("A Transition Model was found but there no exist a definition of states");
            }
            else
            {
                value=cleanedText.substring(0, idx_en+Tokens.Class_ID_StateTransitionModel.length()+Tokens.endLevel.length());
                if(StringUtils.isEmpty(value)) throw new ProcessingException("StateTransitionModel field is not present");
                item.setStateTransitionModel(StateTransitionModel.readFromSt(value));

                cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_StateTransitionModel.length()+Tokens.endLevel.length()+1);                
            }
        }
        
        //weightedIndicators *mandatory*
        value=cleanedText.substring(0);
        WeightedIndicators wi=WeightedIndicators.readFromSt(value);
        if(wi==null) throw new ProcessingException("Malformed WeightedIndicators field");
        item.setWeightedIndicators(wi);
            
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        if(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || attributes==null || !attributes.isDefinedProperties() ||
                 this.entities==null || !entities.isDefinedProperties() || !weightedIndicators.isDefinedProperties()) return false;
        
        if(ecStates!=null && ecStates.isDefinedProperties())
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
        return this.ID;
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
     * @return the superCategoryID
     */
    public String getSuperCategoryID() {
        return superCategoryID;
    }

    /**
     * @param superCategoryID the superCategoryID to set
     */
    public void setSuperCategoryID(String superCategoryID) {
        this.superCategoryID = superCategoryID;
    }

    /**
     * @return the attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Attributes attributes) {
        if(this.attributes!=null){
            try{
                this.attributes.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.attributes = attributes;
    }

    /**
     * @return the entities
     */
    public Entities getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(Entities entities) {
        if(this.entities!=null){
            try{
                this.entities.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.entities = entities;
    }

    /**
     * @return the ecStates
     */
    public ECStates getEcStates() {
        return ecStates;
    }

    /**
     * @param ecStates the ecStates to set
     */
    public void setEcStates(ECStates ecStates) {
        if(this.ecStates!=null){
            try{
                this.ecStates.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.ecStates = ecStates;
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

    /**
     * @return the weightedIndicators
     */
    public WeightedIndicators getWeightedIndicators() {
        return weightedIndicators;
    }

    /**
     * @param weightedIndicators the weightedIndicators to set
     */
    public void setWeightedIndicators(WeightedIndicators weightedIndicators) {
        if(this.weightedIndicators!=null){
            try{
                this.weightedIndicators.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.weightedIndicators = weightedIndicators;
    }
    
    public static EntityCategory test(int k)
    {                
        EntityCategory entcat=new EntityCategory();
        entcat.setID("myEntCat"+k);
        entcat.setName("outpatient"+k);
        entcat.setDescription("entcat description"+k);//optional
        entcat.setSuperCategoryID("superCat");//optional
        entcat.setAttributes(Attributes.test(3));//mandatory
        entcat.setEntities(Entities.test(3));//mandtory
        entcat.setWeightedIndicators(WeightedIndicators.test(3));//mandatory
        entcat.setEcStates(ECStates.test(3));//optio
        entcat.setStateTransitionModel(StateTransitionModel.test(3));//optio
        
        return entcat;
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
        if(!(target instanceof EntityCategory)) return false;
        
        EntityCategory cp=(EntityCategory)target;
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
        if(!(ptr instanceof EntityCategory)) throw new ProcessingException("The instance to be compared is not of the expected type");
        EntityCategory comp=(EntityCategory)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(EntityCategory.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  Attributes *mandatory
        ArrayList<TokenDifference> result;
        result=this.attributes.findDifferences(comp.getAttributes());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }

        // entities *mandatory*  
        result=this.entities.findDifferences(comp.getEntities());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  ecStates *optional*
        if(ecStates!=null && ecStates.length()>0 &&
                comp.getEcStates()!=null && comp.getEcStates().length()>0)
        {
            result=this.ecStates.findDifferences(comp.getEcStates());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
            
            result=this.stateTransitionModel.findDifferences(comp.getStateTransitionModel());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }            
        }
        else
        {
            if((ecStates!=null && ecStates.length()>0))
            {
                global.add(TokenDifference.create(ECStates.class, ecStates.getUniqueID(), 
                        ecStates.getLevel(), ecStates.computeFingerprint(), null));
                global.add(TokenDifference.create(StateTransitionModel.class, stateTransitionModel.getUniqueID(), 
                        stateTransitionModel.getLevel(), stateTransitionModel.computeFingerprint(), null));
                
            }
            
            if(comp.getEcStates()!=null && comp.getEcStates().length()>0)
            {
                global.add(TokenDifference.create(ECStates.class, comp.getEcStates().getUniqueID(), 
                        comp.getEcStates().getLevel(), null, comp.getEcStates().computeFingerprint()));                
                global.add(TokenDifference.create(StateTransitionModel.class, comp.getStateTransitionModel().getUniqueID(), 
                        comp.getStateTransitionModel().getLevel(), null,comp.getStateTransitionModel().computeFingerprint()));                
            }            
        }
        
        // weightedIndicators *mandatory*  
        result=this.weightedIndicators.findDifferences(comp.getWeightedIndicators());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {
        EntityCategory entcat=test(3);
        
            String xml=TranslateXML.toXml(entcat);
            String json=TranslateJSON.toJSON(entcat);
            String brief=entcat.writeTo();

            //System.out.println(xml);
            //System.out.println(json);
            System.out.println(brief);

        EntityCategory ec2=EntityCategory.readFromSt(brief);
        
        System.out.println("ID: "+ec2.getID());
        System.out.println("Name: "+ec2.getName());
        System.out.println("Description: "+ec2.getDescription());
        System.out.println("Super: "+ec2.getSuperCategoryID());
        System.out.println("\n*Atts*:\n "+ec2.getAttributes().writeTo());
        System.out.println("\n*Entities*:\n "+ec2.getEntities().writeTo());
        System.out.println("\n*WeightedIndicators*:\n "+ec2.getWeightedIndicators().writeTo());
        System.out.println("\n*ECStates*:\n "+((ec2.getEcStates()==null)?"":ec2.getEcStates().writeTo()));
        if(ec2.getEcStates()!=null) System.out.println("ECStateSize: "+ec2.getEcStates().length());
        System.out.println("\n*STM*:\n "+((ec2.getStateTransitionModel()==null)?"":ec2.getStateTransitionModel().writeTo()));
        
        String brief2=ec2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));//Reversible
        System.out.println("Equal2: "+entcat.equals(ec2));
    }
}
