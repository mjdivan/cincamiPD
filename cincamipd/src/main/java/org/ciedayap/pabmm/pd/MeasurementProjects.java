/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It is responsible for gathering all the measurement projects to be transmitted
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="MeasurementProjects")
@XmlType(propOrder={"projects"})
public class MeasurementProjects implements Serializable{
    private ArrayList<MeasurementProject> projects;
    
    /**
     * Default constructor
     */
    public MeasurementProjects()
    {
        projects=new ArrayList<>();
    }

    /**
     * @return the projects
     */
    @XmlElement(name="MeasurementProject", required=true)
    public ArrayList<MeasurementProject> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(ArrayList<MeasurementProject> projects) {
        this.projects = projects;
    }
    
}
