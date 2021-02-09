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
import org.ciedayap.pabmm.pd.context.ContextProperty;
import org.ciedayap.pabmm.pd.measurement.Metric;
import org.ciedayap.pabmm.pd.requirements.Attribute;
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
        
        if(var==null || var.getProjects()==null || var.getProjects()==null) return null;
        
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
    
    /**
     * It extracts from the project definition the list of strings under the expected format for detector
     * @param definition The project definition message
     * @param projectID The specific project ID to be extracted
     * @return An ArrayList instance containing the set of string under the format "attributeID;metricID;weighting"
     */
    public static ArrayList<String> toDetectorInitializationList(CINCAMIPD definition, String projectID)
    {
        if(definition==null || projectID==null || projectID.trim().length()==0) return null;
        if(definition.getProjects()==null || definition.getProjects().getProjects()==null ||
                definition.getProjects().getProjects().isEmpty()) return null;
        MeasurementProject proj=null;
        for(MeasurementProject item:definition.getProjects().getProjects())
        {
            if(item!=null && item.getID()!=null && item.getID().equalsIgnoreCase(projectID))
            {
                proj=item;
                break;
            }
        }
        
        if(proj==null) return null;
        
        if(proj.getInfneed()==null || proj.getInfneed().getSpecifiedEC()==null ||
                proj.getInfneed().getSpecifiedEC().getDescribedBy()==null ||
                proj.getInfneed().getSpecifiedEC().getDescribedBy().getCharacteristics()==null ||
                proj.getInfneed().getSpecifiedEC().getDescribedBy().getCharacteristics().isEmpty()) return null;
        
        if( proj.getInfneed().getCharacterizedBy()==null ||
                     proj.getInfneed().getCharacterizedBy().getDescribedBy()==null ||
                     proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties()==null ||
                     proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties().isEmpty()) return null;        
        int qtotal=proj.getInfneed().getSpecifiedEC().getDescribedBy().getCharacteristics().size()+
                   proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties().size();
       
        String met[]=new String[qtotal];
        String arratt[]=new String[qtotal];
        double prob[]=new double[qtotal];
                        
        boolean someNull=false;
        //Attributes Loop
        int i=0;
        for(Attribute att:proj.getInfneed().getSpecifiedEC().getDescribedBy().getCharacteristics())
        {
            arratt[i]=att.getID();
            if(att.getQuantifiedBy()==null || att.getQuantifiedBy().getRelated()==null ||
                    att.getQuantifiedBy().getRelated().isEmpty()) return null;
            Metric m=att.getQuantifiedBy().getRelated().get(0);
            if(m.getIDmetric()==null || m.getIDmetric().trim().length()==0) return null;
            met[i]=m.getIDmetric();
            
            if(att.getWeight()==null) 
            {
                someNull=true;
            }
            else
                prob[i]=att.getWeight().doubleValue();

            i++;
        }
        
        if( proj.getInfneed().getCharacterizedBy()==null ||
                proj.getInfneed().getCharacterizedBy().getDescribedBy()==null ||
                proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties()==null ||
                proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties().isEmpty()) return null;
        //Context Properties Loop
        for(ContextProperty cp:proj.getInfneed().getCharacterizedBy().getDescribedBy().getContextProperties())
        {
            arratt[i]=cp.getID();
            if(cp.getQuantifiedBy()==null || cp.getQuantifiedBy().getRelated()==null ||
                    cp.getQuantifiedBy().getRelated().isEmpty()) return null;
            met[i]=cp.getQuantifiedBy().getRelated().get(0).getIDmetric();
            if(cp.getWeight()==null) someNull=true;
            else prob[i]=cp.getWeight().doubleValue();
            
            i++;
        }
        
        ArrayList mylist=new ArrayList(qtotal);
        double sameProb=1.0/((double)qtotal);
        StringBuilder sb=new StringBuilder();
        for(i=0;i<arratt.length;i++)
        {
            sb.delete(0, sb.length());
            sb.append(arratt[i]).append(";")
              .append(met[i]).append(";");
            
            if(someNull)
              sb.append(sameProb);
            else
              sb.append(prob[i]);
            
            mylist.add(sb.toString());
        }
        
        return mylist;
    }
            
}
