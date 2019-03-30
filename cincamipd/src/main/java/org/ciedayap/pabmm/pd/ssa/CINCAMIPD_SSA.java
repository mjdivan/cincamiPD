/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;
import org.ciedayap.pabmm.pd.SingleConcept;
import org.ciedayap.utils.StringUtils;

/**
 * It is responsible for interchanging the definition of a set of States and Scenarios Analysis
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="CINCAMIPD_SSA")
@XmlType(propOrder={"IDMessage","version","creation","projects"})
public class CINCAMIPD_SSA implements Serializable,SingleConcept{
    /**
     * An uniqued identificator for the message
     */
    private String IDMessage;
    /**
     * The version related to the CINCAMIPD/SSA
     */
    private String version;
    /**
     * It is the timestamp related to the message creation
     */
    private java.time.ZonedDateTime creation;
    /**
     * It contains the projects incorporated in this message
     */        
    private SSAProjects projects;
    
    public CINCAMIPD_SSA()
    {
        IDMessage="1";
        version="1.0";
        creation=ZonedDateTime.now();
        projects=new SSAProjects();
    }

    /**
     * A simple factory method
     * @return A new instance of CINCAMIPD/SSA class
     */
    public synchronized static CINCAMIPD_SSA create()
    {
        return new CINCAMIPD_SSA();
    }
    
    /**
     * A factory method indicating a list of projects
     * @param list the list of measurement projects
     * @return A new CINCAMIPD´s instance with the indicated list of measurement projects, null otherwise
     */
    public synchronized static CINCAMIPD_SSA create(ArrayList<SSAProject> list)
    {
        CINCAMIPD_SSA var=create();
        
        if(var==null || var.getProjects()==null || var.getProjects().getProjects()==null) return null;
        
        var.getProjects().setProjects(list);
        
        return var;
    }    
    
    /**
     * A factory method indicating a list of SSA projects
     * @param projects the list of SSA projects
     * @return A new CINCAMIPD/SSA instance with the indicated list of SSA projects, null otherwise
     */
    public static CINCAMIPD_SSA create(SSAProjects projects)
    {
        CINCAMIPD_SSA var=create();
        
        if(var==null || var.getProjects()==null) return null;
        
        var.setProjects(projects);
        
        return var;
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
    @XmlElement(name="SSAProjects", required=true)
    public SSAProjects getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(SSAProjects projects) {
        this.projects = projects;
    }

    @Override
    public boolean isDefinedProperties() {
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
}
