/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It represents a set of SSAPRoject instance
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="SSAProjects")
@XmlType(propOrder={"projects"})
public class SSAProjects implements Serializable{
    private ArrayList<SSAProject> projects;
    
    /**
     * Default Constructor
     */
    public SSAProjects()
    {
        projects=new ArrayList();
    }

    public synchronized static SSAProjects create()
    {
        return new SSAProjects();
    }
    
    /**
     * @return the projects
     */
    @XmlElement(name="SSAProject", required=true)
    public ArrayList<SSAProject> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(ArrayList<SSAProject> projects) {
        this.projects = projects;
    }
}
