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
 * It represents the method associated with the calculating or measurement.
 * This class is abstract, and it just implements the common behaviour related
 * to the method itself.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Method")
@XmlType(propOrder={"idMethod","name","specification","references"})
public class Method implements Serializable, SingleConcept{
    /**
     * The unique ID of the method
     */
    private String idMethod;
    /**
     * The method name
     */
    private String name;
    /**
     * The method specification
     */
    private String specification;
    /**
     * The method references
     */
    private String references;
    
    /**
     * Default constructor
     */
    public Method()
    {       
    }
    
    /**
     * Constructor related to the mandatory attributes
     * @param id The unique ID of the method
     * @param name The method name
     * @throws EntityPDException It is raised when the mandatory attributes are not defined
     */
    public Method(String id, String name) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The Method ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The Method name is not defined");
        
        this.idMethod=id;
        this.name=name;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(name) || StringUtils.isEmpty(idMethod));
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
        return this.getIdMethod();
    }

    /**
     * @return the idMethod
     */
    @XmlElement(name="idMethod", required=true)
    public String getIdMethod() {
        return idMethod;
    }

    /**
     * @param idMethod the idMethod to set
     */
    public void setIdMethod(String idMethod) {
        this.idMethod = idMethod;
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
}
