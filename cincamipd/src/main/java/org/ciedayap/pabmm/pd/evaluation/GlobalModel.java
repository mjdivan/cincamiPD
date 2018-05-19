/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * It represents the set of decision criteria useful for interpreting the global indicators
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="GlobalModel")
@XmlType(propOrder={"idGM","name","criteria"})
public class GlobalModel implements Serializable, SingleConcept{
    /**
     * The Global Model´s ID
     */
    private String idGM;
    /**
     * The Global Model´s name
     */
    private String name;
    /**
     * The set of decision criteria under a DecisionCriteria instance
     */
    private DecisionCriteria criteria;
    
    /**
     * Default constructor
     */
    public GlobalModel()
    {
        criteria=new DecisionCriteria();
    }
    
    /**
     * Constructor associated with the mandatory attributes
     * @param id The ClobalModel´s ID
     * @param name The GlobalModel´s name
     * @param dc The associated decision criteria
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public GlobalModel(String id, String name, DecisionCriteria dc) throws EntityPDException
    {
        if(StringUtils.isEmpty(id)) throw new EntityPDException("The Elementary Model´s ID is not defined");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The Elementary Model´s name is not defined");
        criteria=dc;
        this.idGM=id;
        this.name=name;
    }
 
    /**
     * A basic factory method
     * @param id The ClobalModel´s ID
     * @param name The GlobalModel´s name
     * @param dc The associated decision criteria
     * @return A new GlobalModel´s instance, null otherwise
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static GlobalModel create(String id, String name, DecisionCriteria dc) throws EntityPDException
    {
        return new GlobalModel(id,name,dc);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(idGM) || StringUtils.isEmpty(name) || 
                criteria==null);
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
        return this.getIdGM();
    }

    /**
     * @return the idGM
     */
    @XmlElement(name="idGM", required=true)
    public String getIdGM() {
        return idGM;
    }

    /**
     * @param idGM the idGM to set
     */
    public void setIdGM(String idGM) {
        this.idGM = idGM;
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
     * @return the criteria
     */
    @XmlElement(name="DecisionCriteria", required=true)
    public DecisionCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(DecisionCriteria criteria) {
        this.criteria = criteria;
    }
    
}
