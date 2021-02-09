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
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It is responsible for representing a given unit along the measurement projects
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Unit")
@XmlType(propOrder={"IDUnit","name","description","symbol","SI_symbol","SI_name"})
public class Unit implements Serializable, Level{
    /**
     * The ID related to the Unit
     */
    private String IDUnit;
    /**
     * The Unit´s name
     */
    private String name;
    /**
     * The Unit´s description
     */
    private String description;
    /**
     * The Unit´s symbol
     */
    private String symbol;
    /**
     * The associated symbol in the International System of Units for the this unit when be possible
     */
    private String SI_symbol;
    /**
     * The associated name in the International System of Units for the this unit when be possible
     */    
    private String SI_name;

    /**
     * Default Constructor
     */
    public Unit()
    {
        
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param ID The ID for the unit
     * @param name The Unit´s name
     * @param symbol  The Unit´s symbol
     * @throws org.ciedayap.ipd.exception.ProcessingException It is raised when som,e mandatory attributes is not defined     
     */
    public Unit(String ID, String name, String symbol) throws ProcessingException
    {
        if(StringUtils.isEmpty(ID)) throw new ProcessingException("The Unit´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new ProcessingException("The Unit´s name is not defined");
        if(StringUtils.isEmpty(symbol)) throw new ProcessingException("The Unit´s symbol is not defined");
        
        this.IDUnit=ID;
        this.name=name;
        this.symbol=symbol;
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        IDUnit=null;
        name=null;
        description=null;
        symbol=null;
        SI_symbol=null;
        SI_name=null;

        return true;
    }
    
    /**
     * A basic factory method
     * @param ID The Unity´s ID
     * @param name The Unit´s name
     * @param symbol The Unit´s symbol
     * @return A new Unit´s instance, null otherwise
     * @throws ProcessingException It is raised when some mandatory attribute is not defined
     */
    public static synchronized Unit create(String ID, String name, String symbol) throws ProcessingException
    {
        Unit u=new Unit();
        u.setIDUnit(ID);
        u.setName(name);
        u.setSymbol(symbol);
        
        return u;        
    }
    
    @Override
    public int getLevel() {
        return 10;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        //IDUnit *mandatory*
        sb.append(Tokens.removeTokens(this.getIDUnit().trim())).append(Tokens.fieldSeparator);
        //name *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //description *optional*
        sb.append((StringUtils.isEmpty(this.getDescription()))?"":Tokens.removeTokens(this.getDescription().trim())).append(Tokens.fieldSeparator);
        //symbol *mandatory*
        sb.append(Tokens.removeTokens(this.getSymbol().trim())).append(Tokens.fieldSeparator);
        //SI_symbol *optional*
        sb.append((StringUtils.isEmpty(this.getSI_symbol()))?"":Tokens.removeTokens(this.getSI_symbol().trim())).append(Tokens.fieldSeparator);
        //SI_name *optional*
        sb.append((StringUtils.isEmpty(this.getSI_name()))?"":Tokens.removeTokens(this.getSI_name().trim()));
        sb.append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }
    
    public static Unit readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Unit item=new Unit();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        
        
        //IDUnit *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The IDUnit field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw  new ProcessingException("The IDUnit field is not present and it is mandatory.");
        item.setIDUnit(value.trim());
        
        //name *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);
        
        //description *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field separator is not present.");
        value=cleanedText.substring(0,idx_en);
        
        item.setDescription((StringUtils.isEmpty(value))?null:value.trim());
        
        //symbol *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The symbol field separator is not present.");
        value=cleanedText.substring(0,idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The symbol field is not present and it is mandatory");
        item.setSymbol(value);
                        
        //SI_symbol *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The SI_symbol field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        
        item.setSI_symbol((StringUtils.isEmpty(value))?null:value.trim());
        
        //SI_name *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en>=0) throw new ProcessingException("Malformed Unit. It was detected more field separators that the expected");
        value=cleanedText.substring(0);
        item.setSI_name((StringUtils.isEmpty(value))?null:value.trim());
        
        return item;
    }
    
    @Override
    public String toString()
    {
        try {
            return this.writeTo();
        } catch (ProcessingException ex) {
            return "Exception: "+ex.getMessage();
        }
    }

    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(IDUnit) || StringUtils.isEmpty(symbol));
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
        return this.getIDUnit();
    }

    /**
     * @return the IDUnit
     */
    @XmlElement(name="IDUnit", required=true)
    public String getIDUnit() {
        return IDUnit;
    }

    /**
     * @param IDUnit the IDUnit to set
     */
    public void setIDUnit(String IDUnit) {
        this.IDUnit = IDUnit;
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
     * @return the description
     */
    @XmlElement(name="description")
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
     * @return the symbol
     */
    @XmlElement(name="symbol", required=true)
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the SI_symbol
     */
    @XmlElement(name="SI_symbol")
    public String getSI_symbol() {
        return SI_symbol;
    }

    /**
     * @param SI_symbol the SI_symbol to set
     */
    public void setSI_symbol(String SI_symbol) {
        this.SI_symbol = SI_symbol;
    }

    /**
     * @return the SI_name
     */
    @XmlElement(name="SI_name")
    public String getSI_name() {
        return SI_name;
    }

    /**
     * @param SI_name the SI_name to set
     */
    public void setSI_name(String SI_name) {
        this.SI_name = SI_name;
    }    
    
    public static Unit test(int i)
    {
        Unit u=new Unit();
        u.setIDUnit("ID"+i);
        u.setName("Meters"+i);
        u.setDescription("Some description"+i);
        u.setSI_name("meters");
        u.setSI_symbol("m");
        u.setSymbol("m");
        
        return u;
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
        if(!(target instanceof Unit)) return false;
        
        Unit cp=(Unit)target;
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
        if(!(ptr instanceof Unit)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Unit comp=(Unit)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(Unit.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Unit u=test(1);
        
        String xml=TranslateXML.toXml(u);
        String json=TranslateJSON.toJSON(u);
        String brief=u.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Unit u2=(Unit) u.readFrom(brief);
        System.out.println("ID: "+u2.getIDUnit());
        System.out.println("Name: "+u2.getName());
        System.out.println("Description: "+u2.getDescription());
        System.out.println("Symbol: "+u2.getSymbol());
        System.out.println("SI_name: "+u2.getSI_name());
        System.out.println("SI_symbol: "+u2.getSI_symbol());        
        System.out.println("Equal: "+u.equals(u2));
    }
}
