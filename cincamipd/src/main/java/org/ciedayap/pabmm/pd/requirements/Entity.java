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
 * This class is responsible for representing the entity under monitoring
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Entity")
@XmlType(propOrder={"ID","name","description","relatedTo"})
public class Entity implements Serializable, SingleConcept{
    /**
     * ID related to the entity under monitoring along the projects
     */
    private String ID;
    /**
     * Descriptive name for the entity
     */
    private String name;
    /**
     * Brief description related to the entity under analysis
     */
    private String description;
    /**
     * The related entities
     */
    private Entities relatedTo;
    
    /**
     * Default constructor
     */
    public Entity()
    {
        ID=name=description=null;
        relatedTo=new Entities();
    } 
    
    /**
     * Constructor related to the mandatory properties
     * @param ID Unique Entity Identification along the project
     * @param name Descriptive name related to the entity
     * @throws EntityPDException when some mandatory attribute is not defined
     */
    public Entity(String ID, String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID))
        {
            throw new EntityPDException("The Entity ID is not defined");
        }
        
        if(StringUtils.isEmpty(name))
        {
            throw new EntityPDException("The entity´s name is not defined");
        }
                
        this.ID=ID;
        this.name=name;
        description=null;
        relatedTo=new Entities();
    }

    /**
     * Static method for creating an instance
     * @param ID
     * @param name
     * @return This method returns a new Entity´s instance 
     * @throws EntityPDException It is raised when the ID or name are not defined
     */
    public static Entity create(String ID,String name) throws EntityPDException
    {
        return new Entity(ID,name);
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
        return this.ID;
    }

    /**
     * @return the relatedTo
     */
    @XmlElement(name="Entities")
    public Entities getRelatedTo() {
        return relatedTo;
    }

    /**
     * @param relatedTo the relatedTo to set
     */
    public void setRelatedTo(Entities relatedTo) {
        this.relatedTo = relatedTo;
    }
}
