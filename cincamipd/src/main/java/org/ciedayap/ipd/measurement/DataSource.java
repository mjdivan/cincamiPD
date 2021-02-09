/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.measurement;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import  org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * The class modeling the basic metadata of a data source
 * @author Mario José Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSource")
@XmlType(propOrder={"dataSourceID","name","description","type","dataFormats","traceGroups","dataSourceAdapters","dataSourceProperties"})
public class DataSource implements Serializable, Level{
    /**
     * The data source´s ID *Mandatory*
     */
    private String dataSourceID;
    /**
     * The data source´s name  *Mandatory*
     */
    private String name;
    /**
     * A brief description related to the data source *Optional*
     */
    private String description;
    /**
     * The data source´s type *Mandatory*
     */
    private String type;
    /**
     * A comma-separated list with the supported data formats by the data source *Optional*
     */
    private String dataFormats;
    /**
     * A set of TraceGroup instances which this data source is a member. *Optional*
     */
    private TraceGroups traceGroups;
    /**
     * A set of DataSourceAdapte instances which is used for this data source for converting
     * the original data format in cincamimis streams (be it in xml, json, or brief). At least one measurement adapter is 
     * required for a given data source.  *Mandatory*
     */
    private DataSourceAdapters dataSourceAdapters;    
    /**
     * It represents the properties of the data source *Optional*
     */
    private DataSourceProperties dataSourceProperties;
    
    public DataSource()
    {
        traceGroups=new TraceGroups();
        dataSourceAdapters=new DataSourceAdapters();
        dataSourceProperties=new DataSourceProperties();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        dataSourceID=null;
        name=null;
        description=null;
        type=null;
        dataFormats=null;
        if(traceGroups!=null)
        {
            traceGroups.realeaseResources();
            traceGroups=null;
        }
        
        if(dataSourceAdapters!=null)
        {
            dataSourceAdapters.realeaseResources();
            dataSourceAdapters=null;
        }
        
        if(dataSourceProperties!=null)
        {
            dataSourceProperties.realeaseResources();
            dataSourceProperties=null;
        }
        
        return true;
    }
    
    public static synchronized DataSource create(String id, String na, String type, DataSourceAdapters dsa)
    {
        DataSource ds=new DataSource();
        ds.setDataSourceID(id);
        ds.setName(na);
        ds.setType(type);
        ds.setDataSourceAdapters(dsa);
        
        return ds;
    }
    
    @Override
    public int getLevel() {
        return 10;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_DataSource);
        
        //"dataSourceID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getDataSourceID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);        
        //"description" *Optional*
        sb.append((StringUtils.isEmpty(this.getDescription()))?"":Tokens.removeTokens(this.getDescription())).append(Tokens.fieldSeparator);        
        //"type", *Mandatory*
        sb.append(this.getType()).append(Tokens.fieldSeparator);
        //"dataFormats", *Optional*
        sb.append((StringUtils.isEmpty(this.getDataFormats()))?"":Tokens.removeTokens(this.getDataFormats())).append(Tokens.fieldSeparator);        
        //"traceGroups", *Optional*
        if(this.traceGroups!=null && !this.traceGroups.isEmpty())
            sb.append(traceGroups.writeTo()).append(Tokens.fieldSeparator);        
        else
            sb.append(Tokens.fieldSeparator);

        //"dataSourceAdapters", *Mandatory*
        sb.append(dataSourceAdapters.writeTo()).append(Tokens.fieldSeparator);
                      
        //"dataSourceProperties" *Optional*
        if(dataSourceProperties!=null &&  !dataSourceProperties.isEmpty())
            sb.append(dataSourceProperties.writeTo());
        else
            sb.append("");                
                
        sb.append(Tokens.Class_ID_DataSource).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DataSource readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_DataSource);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_DataSource+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSource item=new DataSource();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_DataSource.length()+1, idx_en);        
        
        //"dataSourceID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The dataSourceID field is not present and it is mandatory.");
        item.setDataSourceID(value);
        
        //"name" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);
                
        //"description" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setDescription(value);
        
        //"Type" *Mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The type field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setType(value);
        
        //"dataFormats" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The dataFormats field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setDataFormats(value);
        
        //"traceGroups", *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        
        int next=cleanedText.indexOf(Tokens.fieldSeparator);
        boolean empty=false;
        if(next>=0 && next<2)
        {//It is an empty field
            idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
            empty=true;
        }
        else
        {//The field has a value
            idx_en=cleanedText.indexOf(Tokens.endLevel+Tokens.fieldSeparator);
            if(idx_en<0)
            {
                throw new ProcessingException("The traceGroups field separator is not present.");
            }
            else
            {
                value=cleanedText.substring(0, idx_en+1);//Be careful, here is "};"
                
                TraceGroups tg=(TraceGroups) TraceGroups.readFromSt(value);
                if(tg!=null) item.setTraceGroups(tg);                
            }
        }        
        
        //"dataSourceAdapters", *Mandatory*
        if(empty)
        {
            cleanedText=cleanedText.substring(idx_en+1);//OJO Here is ";"
            
        }
        else
            cleanedText=cleanedText.substring(idx_en+2);//OJO Here is "};"
        
        idx_en=cleanedText.indexOf(Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The dataSourceAdapters field separator is not present.");
        value=cleanedText.substring(0, idx_en+1);// because "}" must be included. The end is "};", so for that reasonis  +1
        
        DataSourceAdapters dsa=(DataSourceAdapters) DataSourceAdapters.readFromSt(value);
        if(dsa!=null) item.setDataSourceAdapters(dsa);
                
        //"dataSourceProperties" *Optional*
        cleanedText=cleanedText.substring(idx_en+2);
        value=cleanedText.substring(0);
        
        DataSourceProperties dsp=(DataSourceProperties) DataSourceProperties.readFromSt(value);        
        if(dsp!=null) item.setDataSourceProperties(dsp);
                        
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(this.dataSourceID) || StringUtils.isEmpty(this.name) ||
                StringUtils.isEmpty(type) || dataSourceAdapters==null || dataSourceAdapters.isEmpty());
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
        return this.getDataSourceID();
    }   

    /**
     * @return the dataSourceID
     */
    @XmlElement(name="dataSourceID", required=true)  
    public String getDataSourceID() {
        return dataSourceID;
    }

    /**
     * @param dataSourceID the dataSourceID to set
     */
    public void setDataSourceID(String dataSourceID) {
        this.dataSourceID = dataSourceID;
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
     * @return the description
     */
    @XmlElement(name="description", required=false)  
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the type
     */
    @XmlElement(name="type", required=true)
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the dataFormats
     */
    @XmlElement(name="dataFormats", required=false)
    public String getDataFormats() {
        return dataFormats;
    }

    /**
     * @param dataFormats the dataFormats to set
     */
    public void setDataFormats(String dataFormats) {
        this.dataFormats = dataFormats;
    }

    /**
     * @return the traceGroups
     */
    @XmlElement(name="traceGroups", required=false)
    public TraceGroups getTraceGroups() {
        return traceGroups;
    }

    /**
     * @param traceGroups the traceGroups to set
     */
    public void setTraceGroups(TraceGroups traceGroups) {
        if(this.traceGroups!=null){
            try{
                this.traceGroups.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.traceGroups = traceGroups;
    }

    /**
     * @return the dataSourceAdapters
     */
    @XmlElement(name="dataSourceAdapters", required=true)
    public DataSourceAdapters getDataSourceAdapters() {
        return dataSourceAdapters;
    }

    /**
     * @param dataSourceAdapters the dataSourceAdapters to set
     */
    public void setDataSourceAdapters(DataSourceAdapters dataSourceAdapters) {
        if(this.dataSourceAdapters!=null){
            try{
                this.dataSourceAdapters.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSourceAdapters = dataSourceAdapters;
    }

    /**
     * @return the dataSourceProperties
     */
    public DataSourceProperties getDataSourceProperties() {
        return dataSourceProperties;
    }

    /**
     * @param dataSourceProperties the dataSourceProperties to set
     */
    public void setDataSourceProperties(DataSourceProperties dataSourceProperties) {
        if(this.dataSourceProperties!=null){
            try{
                this.dataSourceProperties.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSourceProperties = dataSourceProperties;
    }
    
    public static DataSource test(int k)
    {
        DataSourceAdapters dsa=DataSourceAdapters.test(3);
        DataSourceProperties dsp=DataSourceProperties.test(3);
        TraceGroups tgs=TraceGroups.test(3);
        DataSource ds=new DataSource();
        
        ds.setDataFormats("data formats...");
        ds.setDataSourceAdapters(dsa);
        ds.setDataSourceID("dsID"+k);
        ds.setDataSourceProperties(dsp);
        ds.setDescription("DS Description"+k);
        ds.setName("NameDS"+k);
        ds.setTraceGroups(tgs);
        ds.setType("physical");
        
        return ds;
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
        if(!(target instanceof DataSource)) return false;
        
        DataSource cp=(DataSource)target;
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
        if(!(ptr instanceof DataSource)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSource comp=(DataSource)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(DataSource.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  private TraceGroups traceGroups;**optional**
        ArrayList<TokenDifference> result;
        if(traceGroups!=null && traceGroups.length()>0 &&
                comp.getTraceGroups()!=null && comp.getTraceGroups().length()>0)
        {
            result=this.traceGroups.findDifferences(comp.getTraceGroups());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((traceGroups!=null && traceGroups.length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(TraceGroups.class, traceGroups.getUniqueID(), 
                        traceGroups.getLevel(), traceGroups.computeFingerprint(), null));
            }

            if((comp.getTraceGroups()!=null && comp.getTraceGroups().length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(TraceGroups.class, comp.getTraceGroups().getUniqueID(), 
                        comp.getTraceGroups().getLevel(), null, comp.getTraceGroups().computeFingerprint()));
            }
        }

        //  private DataSourceAdapters dataSourceAdapters;      
        result=this.dataSourceAdapters.findDifferences(comp.getDataSourceAdapters());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  private DataSourceProperties dataSourceProperties; **optional**
        if(dataSourceProperties!=null && dataSourceProperties.length()>0 &&
                comp.getDataSourceProperties()!=null && comp.getDataSourceProperties().length()>0)
        {
            result=this.dataSourceProperties.findDifferences(comp.getDataSourceProperties());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((dataSourceProperties!=null && dataSourceProperties.length()>0))
            {
                global.add(TokenDifference.create(DataSourceProperties.class, dataSourceProperties.getUniqueID(), 
                        dataSourceProperties.getLevel(), dataSourceProperties.computeFingerprint(), null));
            }
            
            if(comp.getDataSourceProperties()!=null && comp.getDataSourceProperties().length()>0)
            {
                global.add(TokenDifference.create(DataSourceProperties.class, comp.getDataSourceProperties().getUniqueID(), 
                        comp.getDataSourceProperties().getLevel(), null, comp.getDataSourceProperties().computeFingerprint()));                
            }            
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        DataSource ds=test(1);
                
        String xml=TranslateXML.toXml(ds);
        String json=TranslateJSON.toJSON(ds);
        String brief=ds.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSource nds=(DataSource) DataSource.readFromSt(brief);
        
        
            System.out.println("ID: "+nds.getDataSourceID());
            System.out.println("Name: "+nds.getName());
            System.out.println("Type: "+nds.getType());
            System.out.println("Description: "+nds.getDescription());
            System.out.println("Data fomrats: "+nds.getDataFormats());
            
            System.out.println("Adapters: ");
            for(DataSourceAdapter tg:nds.getDataSourceAdapters().getDataSourceAdapters())
            {
                System.out.println("ID: "+tg.getDsAdapterID());
                System.out.println("Name: "+tg.getName());
                System.out.println("Virtual: "+tg.getVirtual());
                System.out.println("Description: "+tg.getDescription());
                System.out.println("Altitude: "+tg.getLocation_altitude());
                System.out.println("Latitude: "+tg.getLocation_latitude());
                System.out.println("Longitude: "+tg.getLocation_longitude());
                System.out.println("Formats: "+tg.getSupportedFormats());
                System.out.println("Types: "+tg.getSupportedTypes());
            }

            System.out.println("DS Properties: ");
            for(DataSourceProperty tg:nds.getDataSourceProperties().getDataSourceProperties())
            {
                System.out.println("ID: "+tg.getPropertyID());
                System.out.println("Name: "+tg.getName());
                System.out.println("Description: "+tg.getDescription());
                System.out.println("Value: "+tg.getValue());
                System.out.println("Relevance: "+tg.getRelevance());
            }
            
            System.out.println("TGs: ");
            for(TraceGroup tg:nds.getTraceGroups().getTraceGroups())
            {
                System.out.println("ID: "+tg.getTraceGroupID());
                System.out.println("Name: "+tg.getName());
                System.out.println("Definition: "+tg.getDefinition());
            }
    
        System.out.println("Equal: "+ds.equals(nds));
    }    
    
}
