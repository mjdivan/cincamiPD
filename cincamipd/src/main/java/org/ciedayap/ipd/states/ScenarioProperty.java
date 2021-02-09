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
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It describes a property related to the environment
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="ScenarioProperty")
@XmlType(propOrder={"ID","name","range"})
public class ScenarioProperty implements Serializable, Level{
    /**
     * The context property ID
     */
    private String ID;
    /**
     * The context property name
     */
    private String name;
    /**
     * The expected variation range
     */
    private Range range;
    
    public ScenarioProperty(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        if(range!=null)
        {
            range.realeaseResources();
            range=null;
        }
        
        return true;
    }
    
    public static synchronized ScenarioProperty create(String id,String name, Range r)
    {
        ScenarioProperty item=new ScenarioProperty();
        item.setID(id);
        item.setName(name);
        item.setRange(r);
        
        return item;
    }
    
    @Override
    public int getLevel() {
        return 8;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_ScenarioProperty);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //Name *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //ScenarioProperty *mandatory*
        sb.append(range.writeTo());
        
        sb.append(Tokens.Class_ID_ScenarioProperty).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static ScenarioProperty readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_ScenarioProperty);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_ScenarioProperty+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        ScenarioProperty item=new ScenarioProperty();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_ScenarioProperty.length()+1, idx_en);        
        
        //"ID" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);

        //"name" *mandatory*        
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);

        //"range" *mandatory*        
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The range field is not present and it is mandatory.");
        Range r2=(Range) Range.readFromSt(value);
        if(r2==null) throw new ProcessingException("The range field is malformed and it is mandatory.");
        item.setRange(r2);
        
        return item;        
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || range==null || !range.isDefinedProperties());
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
     * @return the range
     */
    public Range getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(Range range) {
        if(this.range!=null){
            try{
                this.range.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.range = range;
    }
    
    public static ScenarioProperty test(int i)
    {
        Range r=Range.test();
     
        ScenarioProperty sp=new ScenarioProperty();
        sp.setRange(r);
        sp.setID("IDSc"+i);
        sp.setName("scenarioPropName"+i);

        return sp;
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
        if(!(target instanceof ScenarioProperty)) return false;
        
        ScenarioProperty cp=(ScenarioProperty)target;
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
        if(!(ptr instanceof ScenarioProperty)) throw new ProcessingException("The instance to be compared is not of the expected type");
        ScenarioProperty comp=(ScenarioProperty)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                        
        ArrayList<TokenDifference> global=new ArrayList();
        global.add(TokenDifference.create(ScenarioProperty.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //Range *mandatory*
        ArrayList<TokenDifference> result=this.range.findDifferences(comp.getRange());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;        
    }
    
    public static void main(String arg[]) throws ProcessingException
    {     
        ScenarioProperty sp=test(1);

        String xml=TranslateXML.toXml(sp);
        String json=TranslateJSON.toJSON(sp);
        String brief=sp.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        ScenarioProperty sp2=(ScenarioProperty) ScenarioProperty.readFromSt(brief);
        System.out.println("Sc.ID: "+sp2.getID());
        System.out.println("Sc.Name: "+sp2.getName());
        Range r2=sp2.getRange();
        System.out.println("ID: "+r2.getIDRange());
        System.out.println("Min: "+r2.getMinValue());
        System.out.println("minIncluded: "+r2.getMinValueIncluded());
        System.out.println("Max: "+r2.getMaxValue());
        System.out.println("maxIncluded: "+r2.getMaxValueIncluded());  
        
        System.out.println("Equal: "+sp.equals(sp2));
        
        ArrayList<TokenDifference> list=sp.findDifferences(sp2);
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
