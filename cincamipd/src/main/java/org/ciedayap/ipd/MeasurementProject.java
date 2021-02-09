/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import org.ciedayap.ipd.requirements.InformationNeed;
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
@XmlRootElement(name="MeasurementProject")
@XmlType(propOrder={"ID","name","responsibleSurname","responsibleName","responsibleEmail","responsibleCell",
"startDate","endDate","infneed","lastChange"})
public class MeasurementProject implements Serializable, Level{
    /**
     * The unique ID for the measurement project
     */
    private String ID;//mandatory
    /**
     * A descriptive name for the project
     */
    private String name;//mandatory
    /**
     * The person´s surname acting as responsible
     */
    private String responsibleSurname;//mandatory
    /**
     * The person´s name acting as responsible
     */
    private String responsibleName;//mandatory
    /**
     * The person´s email acting as primary responsible
     */
    private String responsibleEmail;//mandatory
    /**
     * The person´s cell acting as primary responsible
     */
    private String responsibleCell;//mandatory
    /**
     * The start date for the measurement project
     */
    private java.time.ZonedDateTime startDate;//mandatory
    /**
     * The end date for the project. It is optional
     */
    private java.time.ZonedDateTime endDate;//mandatory
    /**
     * The informaiton need related to the project.
     */
    private InformationNeed infneed;//mandatory
    /**
     * The last change date related to the project
     */
    private java.time.ZonedDateTime lastChange;//mandatory

    public MeasurementProject(){}
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        responsibleSurname=null;
        responsibleName=null;
        responsibleEmail=null;
        responsibleCell=null;
        startDate=null;
        endDate=null;
        lastChange=null;
        
        if(infneed!=null)
        {
            infneed.realeaseResources();
            infneed=null;
        }
        
        return true;
    }
    
    public static synchronized MeasurementProject create(String id, String na,String rna,String rsu,String email,String cell,
            java.time.ZonedDateTime sdate,java.time.ZonedDateTime edate, InformationNeed in, java.time.ZonedDateTime lchange)
    {
        MeasurementProject mp=new MeasurementProject();
        mp.setEndDate(edate);
        mp.setID(id);
        mp.setInfneed(in);
        mp.setLastChange(lchange);
        mp.setName(na);
        mp.setResponsibleCell(cell);
        mp.setResponsibleEmail(email);
        mp.setResponsibleName(rna);
        mp.setResponsibleSurname(rsu);
        mp.setStartDate(sdate);
        
        return mp;
    }
    /**
     * @return the ID
     */
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
    @XmlElement(name="endDate", required=true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class) 
    public java.time.ZonedDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(java.time.ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the infneed
     */
    public InformationNeed getInfneed() {
        return infneed;
    }

    /**
     * @param infneed the infneed to set
     */
    public void setInfneed(InformationNeed infneed) {
        if(this.infneed!=null){
            try{
                this.infneed.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
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

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_MeasurementProject);
        
        //"ID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);
        //"responsibleSurname" *Mandatory*
        sb.append(Tokens.removeTokens(this.getResponsibleSurname())).append(Tokens.fieldSeparator);
        //"responsibleName" *Mandatory*
        sb.append(Tokens.removeTokens(this.getResponsibleName())).append(Tokens.fieldSeparator);
        //"responsibleEmail" *Mandatory*
        sb.append(Tokens.removeTokens(this.getResponsibleEmail())).append(Tokens.fieldSeparator);
        //"responsibleCell" *Mandatory*
        sb.append(Tokens.removeTokens(this.getResponsibleCell())).append(Tokens.fieldSeparator);
        //"startDate" *Mandatory*
        sb.append(startDate.toString()).append(Tokens.fieldSeparator);
        //"endDate" *Mandatory*
        sb.append(endDate.toString()).append(Tokens.fieldSeparator);
        //"InformationNeed" *Mandatory*
        sb.append(this.infneed.writeTo()).append(Tokens.fieldSeparator);
        //"lastChange" *Mandatory*
        sb.append(this.lastChange);
        
        sb.append(Tokens.Class_ID_MeasurementProject).append(Tokens.endLevel);
        
        return sb.toString();                
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static MeasurementProject readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_MeasurementProject);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_MeasurementProject+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        MeasurementProject item=new MeasurementProject();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_MeasurementProject.length()+1, idx_en);                
        
        //"ID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"name" *Mandatory*    
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);

        //"responsibleSurname" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The responsibleSurname field is not present and it is mandatory.");
        item.setResponsibleSurname(value);

        //"responsibleName" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The responsibleName field is not present and it is mandatory.");
        item.setResponsibleName(value);

        //"responsibleEmail" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The responsibleEmail field is not present and it is mandatory.");
        item.setResponsibleEmail(value);

        //"responsibleCell" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The responsibleCell field is not present and it is mandatory.");
        item.setResponsibleCell(value);

        //"startDate" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The startDate field is not present and it is mandatory.");
        item.setStartDate(ZonedDateTime.parse(value));

        //"endDate" *Mandatory*       
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The endDate field is not present and it is mandatory.");
        item.setEndDate(ZonedDateTime.parse(value));                
        
        //InformationNeed
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_InformationNeed+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The informationNeed field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_InformationNeed.length()+Tokens.endLevel.length());
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The informationNeed field is not present and it is mandatory.");
        item.setInfneed(InformationNeed.readFromSt(value));        

        //lastChange
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_InformationNeed.length()+Tokens.endLevel.length()+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The lastChange field is not present and it is mandatory.");
        item.setLastChange(ZonedDateTime.parse(value));
                                        
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(this.name) || StringUtils.isEmpty(this.responsibleCell) ||
                StringUtils.isEmpty(this.responsibleEmail) || StringUtils.isEmpty(this.responsibleName) ||
                StringUtils.isEmpty(this.responsibleSurname) || this.endDate==null || this.startDate==null ||
                this.lastChange==null || endDate.isBefore(startDate) || infneed==null ||
                !infneed.isDefinedProperties());
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
        return ID;
    }
    
    public static MeasurementProject test(int i)
    {
        MeasurementProject mp=new MeasurementProject();
        mp.setID("ID"+i);
        mp.setName("NameProject"+i);
        mp.setResponsibleCell("(+54) 92954 556692");
        mp.setResponsibleEmail("mjdivan@eco.unlpam.edu.ar");
        mp.setResponsibleName("Mario"+i);
        mp.setResponsibleSurname("Diván"+i);
        mp.setStartDate(ZonedDateTime.now());
        mp.setEndDate(ZonedDateTime.now());
        mp.setLastChange(ZonedDateTime.now());
        mp.setInfneed(InformationNeed.test(1));
        
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
        if(!(target instanceof MeasurementProject)) return false;
        
        MeasurementProject cp=(MeasurementProject)target;
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
        if(!(ptr instanceof MeasurementProject)) throw new ProcessingException("The instance to be compared is not of the expected type");
        MeasurementProject comp=(MeasurementProject)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(MeasurementProject.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  InformationNeed *mandatory
        ArrayList<TokenDifference> result;
        result=this.infneed.findDifferences(comp.getInfneed());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String args[]) throws ProcessingException
    {
        MeasurementProject mp=test(1);
        
        String xml=TranslateXML.toXml(mp);
        String json=TranslateJSON.toJSON(mp);
        String brief=mp.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        MeasurementProject mp2=MeasurementProject.readFromSt(brief);
        String brief2=mp2.writeTo();
        
        System.out.println("[start; end] =[ "+mp2.getStartDate()+"; "+mp2.getEndDate()+"]");
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));
        System.out.println("XML: "+xml.getBytes().length);
        System.out.println("JSON: "+json.getBytes().length);
        System.out.println("Brief: "+brief.getBytes().length);        
        System.out.println("Equal: "+mp.equals(mp2));
    }
}
