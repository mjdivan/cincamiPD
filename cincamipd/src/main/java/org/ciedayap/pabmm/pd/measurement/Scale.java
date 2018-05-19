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
 * It represents the scale associated with a metric or indicator
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Scale")
@XmlType(propOrder={"IDScale","name","scaleType","expressedIn"})
public class Scale implements Serializable, SingleConcept{
    /**
     * The Scale ID
     */
    private String IDScale;
    /**
     * The scale name
     */
    private String name;
    /**
     * The scale type
     */
    private ScaleType scaleType;
    /**
     * An array with at least one unit in which the scale is expressed
     */
    private Units expressedIn;

    /**
     * Default constructor
     */
    public Scale()
    {}
    
    /**
     * Constructor for the mandatory attributes of the Scale instance
     * @param id The unique ID
     * @param name The scale name
     * @param type The scale type
     * @param u The unit in which the scale will be expressed
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException it is raised when a mandatory attribute is not defined
     */
    public Scale(String id, String name, ScaleType type, Unit u) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The scale ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The scale name is not defined");
        if(type==null) throw new EntityPDException("The scale type is not defined");
        if(u==null || !u.isDefinedProperties()) throw new EntityPDException("The unit for the scale is not defined");
        
        this.IDScale=id;
        this.name=name;
        this.scaleType=type;
        this.expressedIn=new Units();
        this.expressedIn.getUnits().add(u);
    }
 
     /**
     * A basic factory method
     * @param id The unique ID
     * @param name The scale name
     * @param type The scale type
     * @param u The unit in which the scale will be expressed
     * @return A new Scale´s Unit, null otherwise
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException it is raised when a mandatory attribute is not defined
     */
    public static Scale create(String id, String name, ScaleType type, Unit u) throws EntityPDException
    {
        return new Scale(id,name,type,u);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(IDScale) || StringUtils.isEmpty(name)
                || scaleType==null || expressedIn==null ||
                expressedIn.getUnits()==null || expressedIn.getUnits().isEmpty());
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
       return this.getIDScale();
    }

    /**
     * @return the IDScale
     */
    @XmlElement(name="IDScale", required=true)
    public String getIDScale() {
        return IDScale;
    }

    /**
     * @param IDScale the IDScale to set
     */
    public void setIDScale(String IDScale) {
        this.IDScale = IDScale;
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
    @XmlElement(name="ScaleType", required=true)
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
     * @return the expressedIn
     */
    @XmlElement(name="Units", required=true)
    public Units getExpressedIn() {
        return expressedIn;
    }

    /**
     * @param expressedIn the expressedIn to set
     */
    public void setExpressedIn(Units expressedIn) {
        this.expressedIn = expressedIn;
    }            
}
