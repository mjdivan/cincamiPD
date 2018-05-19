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
 * It represents the concept models associated with the calculable concepts.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="ConceptModel")
@XmlType(propOrder={"ID","name","specification","references","constraints"})
public class ConceptModel implements Serializable, SingleConcept{
    /**
     * The ID related to the concept model
     */
    private String ID;
    /**
     * The conceptual model´s name
     */
    private String name;
    /**
     * The Conceptual model´s specification
     */
    private String specification;
    /**
     * The references which substantiate the conceptual model
     */
    private String references;
    /**
     * The constraints on the conceptual model
     */
    private String constraints;

    /**
     * Default constructor
     */
    public ConceptModel()
    {}
    
    /**
     * The constructor associated with the mandatory attributes
     * @param ID The identification related to the conceptual model
     * @param name The synthetic name for the conceptual model
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public ConceptModel(String ID, String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The Conceptual model´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The Conceptual model´s name is not defined");
        
        this.ID=ID;
        this.name=name;
    }
    
    /**
     * A basic factory method
     * @param ID
     * @param name
     * @return A new ConceptModel´s instance
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static ConceptModel create(String ID, String name) throws EntityPDException
    {
        return new ConceptModel(ID,name);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name));
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
     * @return the specification
     */
    @XmlElement(name="specification")
    public String getSpecification() {
        return specification;
    }

    /**
     * @param specification the specification to set
     */
    public void setSpecification(String specification) {
        this.specification = specification;
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
     * @return the constraints
     */
    @XmlElement(name="constraints")
    public String getConstraints() {
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
    
}
