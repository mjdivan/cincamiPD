/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.ipd.adapters.ZonedDateTimeAdapter;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 *
 * @author mjdivan
 */
@XmlRootElement(name="IPD")
@XmlType(propOrder={"IDMessage","version","creation","projects"})
public class IPD implements Serializable,Level{
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
    public IPD()
    {
        version="1.0";
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        IDMessage=null;
        version=null;
        creation=null;
        if(projects!=null)
        {
            projects.realeaseResources();
            projects=null;
        }
        
        return true;
    }
    
    /**
     * A basic factory method.
     * 
     * @return a new CINCAMIPD´s instance 
     */
    public static synchronized IPD create()
    {
        return new IPD();
    }

    public static synchronized IPD create(String id,String ve,java.time.ZonedDateTime creation,MeasurementProjects mp)
    {
        IPD ipd= new IPD();
        ipd.setCreation(creation);
        ipd.setIDMessage(id);
        ipd.setProjects(mp);
        ipd.setVersion(ve);
        
        return ipd;
        
    }
    
    /**
     * It creates a message contemplating the list of projects 
     * @param list List of projects
     * @return  An instance of IPD, null otherwise
     */
    public static IPD create(ArrayList<MeasurementProject> list)
    {
        if(list==null || list.isEmpty()) return null;
        
        IPD var=create();
        
        if(var==null || var.getProjects()==null || var.getProjects().getProjects()==null) return null;
        
        var.getProjects().setProjects(list);
        
        return var;
    }

    /**
     * It creates a message contemplating the list of projects 
     * @param list List of projects
     * @return  An instance of IPD, null otherwise
     */
    public static IPD create(MeasurementProjects list)
    {
        if(list==null || list.isEmpty()) return null;
        
        IPD var=create();
        
        if(var==null || var.getProjects()==null || var.getProjects().getProjects()==null) return null;
        
        var.setProjects(list);
        
        return var;
    }    
    
    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public boolean isDefinedProperties() {

        return !(StringUtils.isEmpty(version) || creation==null || projects==null ||
                projects.isEmpty() ||StringUtils.isEmpty(IDMessage));
        
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
        return this.getIDMessage();
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_IPD);
        
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getIDMessage().trim())).append(Tokens.fieldSeparator);
        //version  *mandatory*
        sb.append(Tokens.removeTokens(this.getVersion().trim())).append(Tokens.fieldSeparator);
        //creation *mandatory*
        sb.append(creation.toString()).append(Tokens.fieldSeparator);
        //projects  *mandatory*
        sb.append(projects.writeTo());
        
        sb.append(Tokens.Class_ID_IPD).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static IPD readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_IPD);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_IPD+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        IPD item=new IPD();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_IPD.length()+1, idx_en);        
        
        //"ID" *mandatory*        
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The IDMessage field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The IDMessage field is not present and it is mandatory.");
        item.setIDMessage(value);
        
        //"version" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The version field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The version field is not present and it is mandatory");
        item.setVersion(value);
        
        //"creation" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The creation field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The creation field is not present and it is mandatory");
        item.setCreation(ZonedDateTime.parse(value));
                
        //"scenarioProperties" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The projects field is not present.");
        item.setProjects(MeasurementProjects.readFromSt(value));
        
        return item;        
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
    @XmlElement(name="MeasurementProjects", required=true)
    public MeasurementProjects getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(MeasurementProjects projects) {
        if(this.projects!=null){
            try{
                this.projects.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.projects = projects;
    }
    
    public static IPD test(int i)
    {
        IPD item=new IPD();
        item.setCreation(ZonedDateTime.now());
        item.setIDMessage("1");
        item.setVersion("1.0");
        item.setProjects(MeasurementProjects.test(i));
        
        return item;
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
        if(!(target instanceof IPD)) return false;
        
        IPD cp=(IPD)target;
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
        if(!(ptr instanceof IPD)) throw new ProcessingException("The instance to be compared is not of the expected type");
        IPD comp=(IPD)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(IPD.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  projects *mandatory
        ArrayList<TokenDifference> result;
        result=this.projects.findDifferences(comp.getProjects());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        IPD data=IPD.test(3);
                
        String xml=TranslateXML.toXml(data);
        String json=TranslateJSON.toJSON(data);
        String brief=data.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);

        IPD data2=IPD.readFromSt(brief);
        System.out.println("IDm: "+data2.getIDMessage());
        System.out.println("Version: "+data2.getVersion());
        System.out.println("Creation: "+data2.getCreation());
        System.out.println("Projects: \n"+data2.getProjects());
        
        System.out.println("XML Size: "+xml.getBytes().length);
        System.out.println("JSON Size: "+json.getBytes().length);
        System.out.println("BRIEF Size: "+brief.getBytes().length);
        
        String brief2=data2.writeTo();
        System.out.println("Equal: "+brief2.equalsIgnoreCase(brief));        
        System.out.println("Equal2:"+data.equals(data2));
        
        /*data2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().setName("modified");
        ArrayList<TokenDifference> myList=data.findDifferences(data2);
        if(myList!=null)
        {
            for(TokenDifference td:myList)
            {
                System.out.println("Level: "+td.getLevel()+" Class: "+td.getTheclass()+" ID: "+td.getID()+
                        " [Or;Al]=["+td.getInstanceFingerprint()+" ; "+td.getContrastedFingerprint()+"]");
            }
        }
        else
        {
            System.out.println("No differences found");
        }*/
        
        testDifferences();
    }
    
    public static void testDifferences() throws ProcessingException
    {
        System.out.println("***Testing differences***");
        IPD original=IPD.test(1);
        String brief=original.writeTo();
        //System.out.println(brief);
                
        IPD alternative=IPD.readFromSt(brief);
        //Tracegroups
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0).getMetrics()
        //        .getMetrics().get(0).getDataSources().getDataSources().get(0).getTraceGroups().getTraceGroups().get(0).setName("modified");

        //DataSourceAdapter
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0).getMetrics()
        //        .getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters().get(0)
        //        .setName("modified");

        //DataSourceProperty
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0).getMetrics()
        //        .getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceProperties().getDataSourceProperties().get(0)
        //        .setName("modified");

        //Range->Metric
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0).getMetrics()
        //        .getMetrics().get(0).getConstraints().getConstraints().get(0).getRange().setMaxValue(new BigDecimal("150"));

        //Unit->Metric->Attribute
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0).getMetrics()
        //        .getMetrics().get(0).getScale().getUnit().setName("modified");
        
        //Unit->Metric->ContextProperties
        //alternative.getProjects().getProjects().get(0).getInfneed().getContext().getContextProperties().getContextProperties().get(0).getMetrics()
        //        .getMetrics().get(0).getScale().getUnit().setName("modified");        

        //Range->Scenario->Ctx
        //alternative.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0)
        //        .getScenarioProperties().getScenarioProperties().get(0).getRange().setMaxValue(new BigDecimal("150"));

        //Range->ECMetadata->ECState...
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0)
        //        .getEcStateProperties().getEcStateProperties().get(0).getRange().setMaxValue(new BigDecimal("150"));

        //StateTransition->EntityCategory...
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getStateTransitionModel().getTransitions().getTransitions()
        //        .get(0).getOrigin().setName("modified");

        //StateTransitionModel->Context
        //alternative.getProjects().getProjects().get(0).getInfneed().getContext().getStateTransitionModel().getTransitions().getTransitions()
        //        .get(0).getOrigin().setName("modified");
        
        //Actionable / WeightedIndicators / EntityCategory
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators().getWeightedIndicators().get(0)
        //        .getDecisionCriteria().getDecisionCriteria().get(0).getRecommender().getActionables().getActionables().get(0).setMode("modified");
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators().getWeightedIndicators().get(0)
        //        .getDecisionCriteria().getDecisionCriteria().get(0).getRecommender().getActionables().getActionables().get(1).setMode("modified");
        
        //Actionable / WeightedIndicators / Scale
        //alternative.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators().getWeightedIndicators().get(0)
        //        .getScale().setName("modified");
        
        ArrayList<TokenDifference> myList=original.findDifferences(alternative);
        if(myList!=null)
        {
            for(TokenDifference td:myList)
            {
                System.out.println("Level: "+td.getLevel()+" Class: "+td.getTheclass()+" ID: "+td.getID()+
                        " [Or;Al]=["+td.getInstanceFingerprint()+" ; "+td.getContrastedFingerprint()+"]");
            }
        }
        else
        {
            System.out.println("No differences found");
        }
        
    }
}
