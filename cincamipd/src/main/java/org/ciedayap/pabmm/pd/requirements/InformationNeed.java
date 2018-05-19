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
import org.ciedayap.pabmm.pd.context.Context;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * This class defines the aim associated with all the measurement and evaluation
 * project. It is important because allows knowing the meaning related to
 * the measurement process in terms of the entity under monitoring.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="InformationNeed")
@XmlType(propOrder={"ID","purpose","shortTitle","userViewpoint","specifiedEC","describedBy","characterizedBy"})
public class InformationNeed implements Serializable, SingleConcept{
    /**
     * An ID related to the information need
     */
    private String ID;
    /**
     * The synthetic purpose related to the measurement and evaluation project.
     */
    private String purpose;
    /**
     * A short title for the information need
     */
    private String shortTitle;
    /**
     * The user view point
     */
    private String userViewpoint;
    /**
     * The entity category under monitoring
     */
    private EntityCategory specifiedEC;
    /**
     * The calculable concepts which describe the information need
     */
    private CalculableConcepts describedBy;
    /**
     * The context related to the entity under monitoring
     */
    private Context characterizedBy;
    
    /**
     * Default constructor
     */
    public InformationNeed()
    {
        specifiedEC=new EntityCategory();
        describedBy=new CalculableConcepts();
    }
    
    /**
     * Constructor associated with the object´s mandatory properties
     * @param ID The instance identification
     * @param stitle The short title for the information need
     * @param purpose The associated purpose 
     * @param ec The entity category associated with the process monitoring
     * @param con The context related to the entity under monitoring
     * @throws EntityPDException It is raised when some mandatory attribute is not defined (such as ID, stitle or purpose)
     */
    public InformationNeed(String ID, String stitle,String purpose,EntityCategory ec,Context con) throws EntityPDException
    {
      if(StringUtils.isEmpty(ID)) throw new EntityPDException("The ID related to the information need is not defined"); 
      if(StringUtils.isEmpty(stitle)) throw new EntityPDException("The short title related to the information need is not defined"); 
      if(StringUtils.isEmpty(purpose)) throw new EntityPDException("The purpose related to the information need is not defined"); 
      if(ec==null || !ec.isDefinedProperties()) throw new EntityPDException("The entity category related to the information need is not defined"); 
      if(con==null || !con.isDefinedProperties()) throw new EntityPDException("The contect related to the information need is not defined"); 
      
      this.ID=ID;
      this.shortTitle=stitle;
      this.purpose=purpose;
      this.specifiedEC=ec;
      this.characterizedBy=con;
      describedBy=new CalculableConcepts();
    }

    /**
     * A basic factory method.
     * 
     * @param ID The instance identification
     * @param stitle The short title for the information need
     * @param purpose The associated purpose 
     * @param ec The entity category associated with the process monitoring
     * @param con The context related to the entity under monitoring
     * @return a new InformationNeed´s instance
     * @throws EntityPDException It is raised when some mandatory attribute is not defined
     */
    public static InformationNeed create(String ID, String stitle, String purpose, EntityCategory ec, Context con) throws EntityPDException
    {
        return new InformationNeed(ID,stitle,purpose,ec, con);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(shortTitle) ||
                StringUtils.isEmpty(purpose) || specifiedEC==null || this.characterizedBy==null
                || !specifiedEC.isDefinedProperties() || !this.characterizedBy.isDefinedProperties());
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
     * @return the purpose
     */
    @XmlElement(name="purpose", required=true)
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the shortTitle
     */
    @XmlElement(name="shortTitle", required=true)
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * @param shortTitle the shortTitle to set
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    /**
     * @return the userViewpoint
     */
    @XmlElement(name="userViewpoint")
    public String getUserViewpoint() {
        return userViewpoint;
    }

    /**
     * @param userViewpoint the userViewpoint to set
     */
    public void setUserViewpoint(String userViewpoint) {
        this.userViewpoint = userViewpoint;
    }

    /**
     * @return the specifiedEC
     */
    @XmlElement(name="EntityCategory", required=true)
    public EntityCategory getSpecifiedEC() {
        return specifiedEC;
    }

    /**
     * @param specifiedEC the specifiedEC to set
     */
    public void setSpecifiedEC(EntityCategory specifiedEC) {
        this.specifiedEC = specifiedEC;
    }

    /**
     * @return the describedBy
     */
    @XmlElement(name="CalculableConcepts")
    public CalculableConcepts getDescribedBy() {
        return describedBy;
    }

    /**
     * @param describedBy the describedBy to set
     */
    public void setDescribedBy(CalculableConcepts describedBy) {
        this.describedBy = describedBy;
    }

    /**
     * @return the characterizedBy
     */
    @XmlElement(name="Context", required=true)
    public Context getCharacterizedBy() {
        return characterizedBy;
    }

    /**
     * @param characterizedBy the characterizedBy to set
     */
    public void setCharacterizedBy(Context characterizedBy) {
        this.characterizedBy = characterizedBy;
    }
    
}
