/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.measurement;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It is responsible for representing a given unit along the measurement projects
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Unit")
@XmlType(propOrder={"IDUnit","name","description","symbol","SI_symbol","SI_name"})
public class Unit implements Serializable, SingleConcept{
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
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException It is raised when som,e mandatory attributes is not defined
     */
    public Unit(String ID, String name, String symbol) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The Unit´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The Unit´s name is not defined");
        if(StringUtils.isEmpty(symbol)) throw new EntityPDException("The Unit´s symbol is not defined");
        
        this.IDUnit=ID;
        this.name=name;
        this.symbol=symbol;
    }
    
    /**
     * A basic factory method
     * @param ID The Unity´s ID
     * @param name The Unit´s name
     * @param symbol The Unit´s symbol
     * @return A new Unit´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static Unit create(String ID, String name, String symbol) throws EntityPDException
    {
        return new Unit(ID,name,symbol);        
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
    
}
