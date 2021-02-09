/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It describes the measurement projects to be  informed
 * @author mjdivan
 * @version 1.0
 */
@XmlRootElement(name="MeasurementProjects")
@XmlType(propOrder={"projects"})
public class MeasurementProjects implements Containers,Level, Serializable{
    private  ArrayList<MeasurementProject> projects;
    
    /**
     * Default constructor
     */
    public MeasurementProjects()
    {
        projects=new ArrayList<>();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(projects==null)  return true;
        for(int i=0;i<projects.size();i++)
        {
            projects.get(i).realeaseResources();
        }
        
        projects.clear();
        projects=null;

        return true;
    }
    
    public static synchronized MeasurementProjects create(ArrayList<MeasurementProject> list)
    {
        MeasurementProjects mp=new MeasurementProjects();
        mp.setProjects(list);
        return mp;
    }
    
    @Override
    public int length() {
        if(projects==null)  return 0;
        
        return projects.size();
    }

    @Override
    public boolean isEmpty() {
        return (length()==0); 
    }

    @Override
    public Class getKindOfElement() {
        return MeasurementProject.class;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_MeasurementProjects);
        for(MeasurementProject item:projects)
        {
            String segment=item.writeTo();

            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_MeasurementProjects).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String  text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static MeasurementProjects readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_MeasurementProjects);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_MeasurementProjects+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        MeasurementProjects item=new MeasurementProjects();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_MeasurementProjects.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_MeasurementProject);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_MeasurementProject+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_MeasurementProject.length()+1);            
            MeasurementProject ds=null;
            try{
                ds=(MeasurementProject) MeasurementProject.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getProjects().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_MeasurementProject.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_MeasurementProject);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_MeasurementProject+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(this.projects==null || this.projects.isEmpty());
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
        if(!this.isDefinedProperties()) return null;
        StringBuilder sb=new StringBuilder();
        projects.stream().forEach(p-> sb.append(p.getID()).append("-"));
        
        return sb.toString();
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
        if(this.projects!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.projects = projects;
    }
    
    public static MeasurementProjects test(int j)
    {
        MeasurementProjects mp=new MeasurementProjects();
        for(int i=0;i<j;i++)
        {
            mp.getProjects().add(MeasurementProject.test(i));
        }
        
        return mp;
    }
    
    @Override
    public String computeFingerprint(){
        if(!this.isDefinedProperties()) return null;
        
        String original;
        try {
            original = this.writeTo();
        } catch (ProcessingException ex) {
            return null;
        }
        
        return (original==null)?null:Tokens.getFingerprint(original);
    }    
    
    @Override
    public int hashCode()
    {
        String ret=computeFingerprint();
        return (ret==null)?0:ret.hashCode();
    }
    
    @Override
    public boolean equals(Object target)
    {
        if(target==null) return false;
        if(!(target instanceof MeasurementProjects)) return false;
        
        MeasurementProjects cp=(MeasurementProjects)target;
        String ft=cp.computeFingerprint();
        
        String or;
        try{
            or=Tokens.getFingerprint(this.writeTo());
        }catch(ProcessingException pe)
        {
            return false;
        }
        
        return (ft==null)?false:(ft.equalsIgnoreCase(or));
    }
    
    @Override
    public ArrayList<TokenDifference> findDifferences(Object ptr) throws ProcessingException {
        if(ptr==null) throw new ProcessingException("The instance to compared is null");
        if(!(ptr instanceof MeasurementProjects)) throw new ProcessingException("The instance to be compared is not of the expected type");
        MeasurementProjects comp=(MeasurementProjects)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.projects.size()!=comp.getProjects().size())
            return TokenDifference.createAsAList(MeasurementProjects.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i< this.projects.size();i++)
        {
            MeasurementProject pthis=projects.get(i);
            MeasurementProject pcomp=comp.getProjects().get(i);
            
            ArrayList<TokenDifference> result=pthis.findDifferences(pcomp);
            if(result!=null && result.size()>0)
            {
                global.addAll(result);            
                result.clear();
            }
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {
        MeasurementProjects mp=test(4);
        
        String xml=TranslateXML.toXml(mp);
        String json=TranslateJSON.toJSON(mp);
        String brief=mp.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        MeasurementProjects mp2=MeasurementProjects.readFromSt(brief);
        String brief2=mp2.writeTo();
        
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));
        System.out.println("XML: "+xml.getBytes().length);
        System.out.println("JSON: "+json.getBytes().length);
        System.out.println("Brief: "+brief.getBytes().length);  
        
        System.out.println("Equal: "+mp.equals(mp2));
    }
}
