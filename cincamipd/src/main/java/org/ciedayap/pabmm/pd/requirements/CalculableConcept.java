/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.requirements;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents a calculable concept
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="CalculableConcept")
@XmlType(propOrder={"ID","name","definition","references","combines","representedBy","subconcepts"})
public class CalculableConcept implements Serializable, SingleConcept{
    /**
     * The ID for the Calculable Concept
     */
    private String ID;
    /**
     * The name of the calculable concept
     */
    private String name;
    /**
     * The definition associated with the calculable concept
     */
    private String definition;
    /**
     * The references related to the calculable concept
     */
    private String references;
    /**
     * The attributes which combine the calculable concept
     */
    private Attributes combines;
    /**
     * The concept models which represent the calculable concept
     */
    private ConceptModels representedBy;
    /**
     * The eventual subconcept when they are available
     */
    private CalculableConcepts subconcepts;
    
    /**
     * Default constructor
     */
    public CalculableConcept()
    {
        combines=new Attributes();
        representedBy=new ConceptModels();
        subconcepts=new CalculableConcepts();
    }

    /**
     * The constructor associated with the mandatory attributes
     * @param ID The identification for the calculable concept
     * @param name The name for the calculable concept
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public CalculableConcept(String ID, String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The ID is not defined for the calculable concept");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The name is not defined for the calculable concept");
        
        this.ID=ID;
        this.name=name;
        combines=new Attributes();
        representedBy=new ConceptModels();
        subconcepts=new CalculableConcepts();
    }
    
    /**
     * The basic factory method
     * @param ID
     * @param name
     * @return a new CalculableConcept´s instance
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static CalculableConcept create(String ID,String name) throws EntityPDException
    {
        return new CalculableConcept(ID,name);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return  !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name));
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
     * @return the definition
     */
    @XmlElement(name="definition")
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * @return the references
     */
    @XmlElement(name="references")
    public String getReferences() {
        return references;
    }

    /**
     * @param references the references to set
     */
    public void setReferences(String references) {
        this.references = references;
    }

    /**
     * @return the combines
     */
    @XmlElement(name="Attributes")
    public Attributes getCombines() {
        return combines;
    }

    /**
     * @param combines the combines to set
     */
    public void setCombines(Attributes combines) {
        this.combines = combines;
    }

    /**
     * @return the representedBy
     */
    @XmlElement(name="ConceptModels")
    public ConceptModels getRepresentedBy() {
        return representedBy;
    }

    /**
     * @param representedBy the representedBy to set
     */
    public void setRepresentedBy(ConceptModels representedBy) {
        this.representedBy = representedBy;
    }

    /**
     * @return the subconcept
     */
    @XmlElement(name="CalculableConcepts")
    public CalculableConcepts getSubconcepts() {
        return subconcepts;
    }

    /**
     * @param subconcept the subconcept to set
     */
    public void setSubconcepts(CalculableConcepts subconcept) {
        this.subconcepts = subconcept;
    }
    
}
