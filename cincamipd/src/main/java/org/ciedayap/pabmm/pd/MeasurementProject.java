/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import org.ciedayap.pabmm.pd.requirements.InformationNeed;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.utils.StringUtils;

/**
 * It represents the descritive information about the measurement and evaluation
 * project.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="MeasurementProject")
@XmlType(propOrder={"ID","name","responsibleSurname","responsibleName","responsibleEmail","responsibleCell",
"startDate","endDate","infneed","lastChange"})
public class MeasurementProject implements Serializable, SingleConcept{
    /**
     * The unique ID for the measurement project
     */
    private String ID;
    /**
     * A descriptive name for the project
     */
    private String name;
    /**
     * The person´s surname acting as responsible
     */
    private String responsibleSurname;
    /**
     * The person´s name acting as responsible
     */
    private String responsibleName;
    /**
     * The person´s email acting as primary responsible
     */
    private String responsibleEmail;
    /**
     * The person´s cell acting as primary responsible
     */
    private String responsibleCell;
    /**
     * The start date for the measurement project
     */
    private java.time.ZonedDateTime startDate;
    /**
     * The end date for the project. It is optional
     */
    private java.time.ZonedDateTime endDate;
    /**
     * The informaiton need related to the project.
     */
    private InformationNeed infneed;
    /**
     * The last change date related to the project
     */
    private java.time.ZonedDateTime lastChange;
    
    /**
     * Default Constructor
     */
    public MeasurementProject()
    {
        startDate=ZonedDateTime.now();
        lastChange=ZonedDateTime.now();
    }
    
    /**
     * Constructor assoociated with the mandatory attributes
     * @param ID The identification related to the measurement project
     * @param name The name associated with the measurement project
     * @param sdate The start date in which the PAbMM should to start to monitor the entity under analysis
     * @param in The information need related to the project
     * @param lc The last change date related to the measurement project
     * @throws EntityPDException It is raised when some mandatory attribute is not defined.
     */
    public MeasurementProject(String ID, String name, java.time.ZonedDateTime sdate,InformationNeed in,
            java.time.ZonedDateTime lc) throws EntityPDException
    {
        if(StringUtils.isEmpty(ID)) throw new EntityPDException("The ID is not defined for the Measurement Project");
        if(StringUtils.isEmpty(name)) throw new EntityPDException("The name is not defined for the Measurement Project");
        if(sdate==null) throw new EntityPDException("The start date is not defined for the Measurement Project");
        if(in==null || !in.isDefinedProperties()) throw new EntityPDException("The information need is not defined for the Measurement Project");
        if(lc==null) throw new EntityPDException("The last change date has not been defined");
        this.ID=ID;
        this.name=name;
        this.startDate=sdate;
        this.infneed=in;        
        this.lastChange=lc;
    }
    
    /**
     * a basic factory method.
     * 
     * @param ID The identification related to the measurement project
     * @param name The name associated with the measurement project
     * @param sdate The start date in which the PAbMM should to start to monitor the entity under analysis
     * @param in The information need related to the project
     * @param lc The last change date associated with the project
     * @throws EntityPDException It is raised when some mandatory attribute is not defined.
     * @return A new MeasurementProject´s instance
     */
    public static MeasurementProject create(String ID, String name, java.time.ZonedDateTime sdate,InformationNeed in,
            java.time.ZonedDateTime lc) throws EntityPDException
    {
        return new MeasurementProject(ID,name,sdate,in,lc);
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || startDate==null || infneed==null
                || !infneed.isDefinedProperties() || lastChange==null);
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
     * @return the responsibleSurname
     */
    @XmlElement(name="responsibleSurname")
    public String getResponsibleSurname() {
        return responsibleSurname;
    }

    /**
     * @param responsibleSurname the responsibleSurname to set
     */
    public void setResponsibleSurname(String responsibleSurname) {
        this.responsibleSurname = responsibleSurname;
    }

    /**
     * @return the responsibleName
     */
    @XmlElement(name="responsibleName")
    public String getResponsibleName() {
        return responsibleName;
    }

    /**
     * @param responsibleName the responsibleName to set
     */
    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    /**
     * @return the responsibleEmail
     */
    @XmlElement(name="responsibleEmail")
    public String getResponsibleEmail() {
        return responsibleEmail;
    }

    /**
     * @param responsibleEmail the responsibleEmail to set
     */
    public void setResponsibleEmail(String responsibleEmail) {
        this.responsibleEmail = responsibleEmail;
    }

    /**
     * @return the responsibleCell
     */
    @XmlElement(name="responsibleCell")
    public String getResponsibleCell() {
        return responsibleCell;
    }

    /**
     * @param responsibleCell the responsibleCell to set
     */
    public void setResponsibleCell(String responsibleCell) {
        this.responsibleCell = responsibleCell;
    }

    /**
     * @return the startDate
     */
    @XmlElement(name="startDate", required=true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class) 
    public java.time.ZonedDateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(java.time.ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    @XmlElement(name="endDate")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)     
    public java.time.ZonedDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param eDate the endDate to set
     */
    public void setEndDate(java.time.ZonedDateTime eDate) {
        if(startDate!=null && eDate!=null)
        {
            if(eDate.compareTo(startDate)<=0) return;
            
        }
        this.endDate = eDate;
    }

    /**
     * @return the infneed
     */
    @XmlElement(name="InformationNeed")
    public InformationNeed getInfneed() {
        return infneed;
    }

    /**
     * @param infneed the infneed to set
     */
    public void setInfneed(InformationNeed infneed) {
        this.infneed = infneed;
    }

    /**
     * @return the lastChange
     */
    @XmlElement(name="lastChange", required=true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public java.time.ZonedDateTime getLastChange() {
        return lastChange;
    }

    /**
     * @param lastChange the lastChange to set
     */
    public void setLastChange(java.time.ZonedDateTime lastChange) {
        this.lastChange = lastChange;
    }
    
}
