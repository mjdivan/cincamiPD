/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.measurement;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
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
 * It represents the scale of a metric
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Scale")
@XmlType(propOrder={"ID","name","scaleType","unit"})
public class Scale implements Serializable, Level{
    private String  ID;
    private String name;
    private ScaleType scaleType;
    private Unit unit;
    
    public Scale(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        if(scaleType!=null)            
        {
            scaleType=null;
        }
        
        if(unit!=null)
        {
            unit.realeaseResources();
            unit=null;
        }
        
        return true;
    }
    
    public static synchronized Scale create(String id, String na, ScaleType st, Unit u)
    {
        Scale s=new Scale();
        s.setID(id);
        s.setName(na);
        s.setScaleType(st);
        s.setUnit(u);
        
        return s;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Scale);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //name *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //scaleType *mandatory*
        sb.append((StringUtils.isEmpty(this.getScaleType().toString()))?"":Tokens.removeTokens(this.getScaleType().toString())).append(Tokens.fieldSeparator);
        //Unit *mandatory*
        sb.append(unit.writeTo());

        sb.append(Tokens.Class_ID_Scale).append(Tokens.endLevel);
        
        return sb.toString();

    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Scale readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Scale);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Scale+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Scale item=new Scale();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Scale.length()+1, idx_en+Tokens.Class_ID_Scale.length());        
        
        //ID *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw  new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //name *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);
        
        //scaleType *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The scaleType field separator is not present.");
        value=cleanedText.substring(0,idx_en);
        
        item.setScaleType(ScaleType.valueOf(value));
                
        //Unit *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);                
        value=cleanedText.substring(0);
        item.setUnit((Unit) Unit.readFromSt(value));
        
        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || scaleType==null || unit==null);
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
    @XmlElement(name="ID", required=true)
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
    @XmlElement(name="name", required=true)
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
     * @return the scaleType
     */
    @XmlElement(name="scaleType", required=true)
    public ScaleType getScaleType() {
        return scaleType;
    }

    /**
     * @param scaleType the scaleType to set
     */
    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    /**
     * @return the unit
     */
    @XmlElement(name="unit", required=true)
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        if(this.unit!=null){
            try{
                this.unit.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.unit = unit;
    }
    
    public static Scale test(int i)
    {
        Scale sc=new Scale();
        sc.setID("IDS"+i);
        sc.setName("My Scale"+i);
        sc.setScaleType(ScaleType.RATIO);
        sc.setUnit(Unit.test(i));
        
        return sc;        
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
        if(!(target instanceof Scale)) return false;
        
        Scale cp=(Scale)target;
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
        if(!(ptr instanceof Scale)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Scale comp=(Scale)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        ArrayList<TokenDifference> global=new ArrayList();
        global.add(TokenDifference.create(Scale.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //Unit
        ArrayList<TokenDifference> result=this.unit.findDifferences(comp.getUnit());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;              
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Scale sc=test(1);
        
        String xml=TranslateXML.toXml(sc);
        String json=TranslateJSON.toJSON(sc);
        String brief=sc.writeTo();
        
        System.out.println("BriefPD: "+brief.length()+" JSON: "+json.length()+" XML: "+xml.length());
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Scale sc2=(Scale) Scale.readFromSt(brief);
        
        System.out.println("ID:"+sc2.getID());
        System.out.println("Name:"+sc2.getName());
        System.out.println("ScaleType:"+sc2.getScaleType());
        System.out.println("IDU: "+sc2.getUnit().getIDUnit());
        System.out.println("NameU: "+sc2.getUnit().getName());
        System.out.println("Description: "+sc2.getUnit().getDescription());
        System.out.println("Symbol: "+sc2.getUnit().getSymbol());
        System.out.println("SI_name: "+sc2.getUnit().getSI_name());
        System.out.println("SI_symbol: "+sc2.getUnit().getSI_symbol()); 
        
        System.out.println("Equal: "+sc.equals(sc2));
    }
    
}
