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
 * This class is associated with the category of a given entity
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="EntityCategory")
@XmlType(propOrder={"ID","name","description","superCategory","describedBy","monitored"})
public class EntityCategory implements Serializable, SingleConcept{
    private String ID;
    private String name;
    private String description;
    private EntityCategory superCategory;
    private Attributes describedBy;
    private Entities monitored;
    
    /**
     * Default constructor
     */
    public EntityCategory()
    {
        superCategory=null;
        describedBy=new Attributes();
        monitored=new Entities();
    }
    
    /**
     * Constructor related to the mandatory properties
     * @param id The ID related to the entity category along the project
     * @param name The descriptive name related to the entity category
     * @throws EntityPDException It is raised when the ID or name are not defined.
     */
    public EntityCategory(String id, String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The ID for the EntityCategory is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The name for the EntityCategory is not defined");
        
        this.ID=id;
        this.name=name;
        
        superCategory=new EntityCategory();
        describedBy=new Attributes();
        monitored=new Entities();
    }
    
    /**
     * A basic factory method
     * @param ID
     * @param name
     * @return A new EntityCategory´s instance 
     * @throws EntityPDException It is raised when a mandatory attribute is not defined
     */
    public static EntityCategory create(String ID, String name) throws EntityPDException
    {
        return new EntityCategory(ID,name);
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
     * @return the superCategory
     */
    @XmlElement(name="EntityCategory")
    public EntityCategory getSuperCategory() {
        return superCategory;
    }

    /**
     * @param superCategory the superCategory to set
     */
    public void setSuperCategory(EntityCategory superCategory) {
        this.superCategory = superCategory;
    }

    /**
     * @return the describedBy
     */
    @XmlElement(name="Attributes")
    public Attributes getDescribedBy() {
        return describedBy;
    }

    /**
     * @param describedBy the describedBy to set
     */
    public void setDescribedBy(Attributes describedBy) {
        this.describedBy = describedBy;
    }

    /**
     * @return the monitored
     */
    @XmlElement(name="Entities")
    public Entities getMonitored() {
        return monitored;
    }

    /**
     * @param monitored the monitored to set
     */
    public void setMonitored(Entities monitored) {
        this.monitored = monitored;
    }
    
}
