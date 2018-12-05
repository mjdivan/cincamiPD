/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;
import org.ciedayap.utils.StringUtils;

/**
 * It is responsible for interchanging a set of measurement project definitions
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="CINCAMIPD")
@XmlType(propOrder={"IDMessage","version","creation","projects"})
public class CINCAMIPD implements Serializable,SingleConcept{
    /**
     * An unique identification related to this message
     */
    private String IDMessage;
    /**
     * The version related to the CINCAMI/Project Definition (PD)
     */
    private String version;
    /**
     * The creation datetime related to this message
     */
    private java.time.ZonedDateTime creation;
    /**
     * The set of measurement projects informed in this message
     */
    private MeasurementProjects projects;

    /**
     * Default constructor
     */
    public CINCAMIPD()
    {
        version="1.0";
        creation=java.time.ZonedDateTime.now();
        projects=new MeasurementProjects();
        IDMessage="1";
    }
    
    /**
     * A basic factory method.
     * 
     * @return a new CINCAMIPD´s instance 
     */
    public static CINCAMIPD create()
    {
        return new CINCAMIPD();
    }
    
    /**
     * A factory method indicating a list of measurement projects
     * @param list the list of measurement projects
     * @return A new CINCAMIPD´s instance with the indicated list of measurement projects, null otherwise
     */
    public static CINCAMIPD create(ArrayList<MeasurementProject> list)
    {
        CINCAMIPD var=create();
        
        if(var==null || var.getProjects()==null || var.getProjects().getProjects()==null) return null;
        
        var.getProjects().setProjects(list);
        
        return var;
    }

    /**
     * A factory method indicating a list of measurement projects
     * @param projects the list of measurement projects
     * @return A new CINCAMIPD´s instance with the indicated list of measurement projects, null otherwise
     */
    public static CINCAMIPD create(MeasurementProjects projects)
    {
        CINCAMIPD var=create();
        
        if(var==null || var.getProjects()==null) return null;
        
        var.setProjects(projects);
        
        return var;
    }
    
    @Override
    public boolean isDefinedProperties() 
    {
        return !(StringUtils.isEmpty(version) || creation==null || projects==null ||
                projects.getProjects()==null || projects.getProjects().isEmpty() ||StringUtils.isEmpty(IDMessage));
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
        return IDMessage;
    }

    /**
     * @return the version
     */
    @XmlAttribute(name="version", required=true)
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the creation
     */
    @XmlAttribute(name="creation", required=true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class) 
    public java.time.ZonedDateTime getCreation() {
        return creation;
    }

    /**
     * @param creation the creation to set
     */
    public void setCreation(java.time.ZonedDateTime creation) {
        this.creation = creation;
    }

    /**
     * @return the projects
     */
    @XmlElement(name="MeasurementProjects", required=true)
    public MeasurementProjects getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(MeasurementProjects projects) {
        this.projects = projects;
    }

    /**
     * @return the IDMessage
     */
    @XmlAttribute(name="IDMessage", required=true)
    public String getIDMessage() {
        return IDMessage;
    }

    /**
     * @param IDMessage the IDMessage to set
     */
    public void setIDMessage(String IDMessage) {
        this.IDMessage = IDMessage;
    }
}
