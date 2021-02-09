/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.measurement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * The data source adapter (or measurement adapter). It is responsible for translating the raw data
 * to the measurement interchange schema based on the metadata.
 * 
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="TraceGroup")
@XmlType(propOrder={"dsAdapterID","name","virtual","description","supportedTypes","supportedFormats","location_latitude",
    "location_longitude","location_altitude"})
public class DataSourceAdapter implements Serializable, Level{
    /**
     * It is the data source adapter ID. It is  mandatory
     */
    private String dsAdapterID;
    /**
     * A synthetic name for the data source adapter. It is mandatory
     */
    private String name;
    /**
     * It indicates whether the measurement adapter is virtual or not. It is mandatory
     */
    private Boolean virtual;
    /**
     * A brief device description. It is optional.
     */
    private String description; 
    /**
     * A comma-separated list with each mime type supported as translating´s origin. It is optional.
     * An updated list is available on http://www.iana.org/assignments/media-types/media-types.xhtml
     * For example: "application/geo+json, application/json, application/mp4, application/xml"
     */
    private String supportedTypes;
    /**
     * Each kind of Supported Data format as a comma-separated list. It is optional.
     */    
    private String supportedFormats;
    /**
     * Latitude related to the  measurement adapter. It is optional
     */
    private BigDecimal location_latitude;
    /**
     * * Longituude related to the  measurement adapter. It is  optional
     */
    private BigDecimal location_longitude;
    /**
     * Altitude related to the  measurement adapter. It is optional
     */
    private BigDecimal location_altitude;
    
    public DataSourceAdapter()
    {}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        dsAdapterID=null;
        name=null;
        virtual=null;
        description=null;
        supportedTypes=null;
        supportedFormats=null;
        location_latitude=null;
        location_longitude=null;
        location_altitude=null;
        
        return true;
    }

    public static synchronized DataSourceAdapter create(String id, String na, Boolean vi)            
    {
        DataSourceAdapter dsa=new DataSourceAdapter();
        dsa.setDsAdapterID(id);
        dsa.setName(na);
        dsa.setVirtual(vi);
        
        return dsa;
    }    
    
    @Override
    public int getLevel() {
        return 12;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        
        //"dsAdapterID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getDsAdapterID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);
        //"virtual" *Mandatory
        sb.append(this.getVirtual()).append(Tokens.fieldSeparator);
        
        //"description" *Optional*
        sb.append((StringUtils.isEmpty(this.description))?"":Tokens.removeTokens(this.description)).append(Tokens.fieldSeparator);
        
        //"supportedTypes" *Optional*
        sb.append((StringUtils.isEmpty(this.supportedTypes))?"":Tokens.removeTokens(this.supportedTypes)).append(Tokens.fieldSeparator);

        //"supportedFormats" *Optional*
        sb.append((StringUtils.isEmpty(this.supportedFormats))?"":Tokens.removeTokens(this.supportedFormats)).append(Tokens.fieldSeparator);
        
        //"location_latitude",
        sb.append((location_latitude==null)?"":location_latitude).append(Tokens.fieldSeparator);
        //"location_longitude",
        sb.append((location_longitude==null)?"":location_longitude).append(Tokens.fieldSeparator);
        //"location_altitude",
        sb.append((location_altitude==null)?"":location_altitude);        
        
        sb.append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DataSourceAdapter readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSourceAdapter item=new DataSourceAdapter();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        
        
        //"dsAdapterID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The dsAdapterID field is not present and it is mandatory.");
        item.setDsAdapterID(value);
        
        //"name" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);
        
        //"virtual" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The virtual field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The virtual field is not present and it is mandatory.");
        item.setVirtual(Boolean.valueOf(value));
        
        //"description" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The description field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setDescription(value);
        
        //"supportedTypes" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The supportedTypes field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSupportedTypes(value);
        
        //"supportedFormats" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The supportedFormats field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSupportedFormats(value);
        
        //"location_latitude" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The location_latitude field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setLocation_latitude(new BigDecimal(value));
        
        //"location_longitude" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The location_longitude field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setLocation_longitude(new BigDecimal(value));
        
        //"location_altitude" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(!StringUtils.isEmpty(value)) item.setLocation_altitude(new BigDecimal(value));
                
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(this.dsAdapterID) || StringUtils.isEmpty(this.name) || this.virtual==null);
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
        return this.getDsAdapterID();
    }

    /**
     * @return the dsAdapterID
     */
    @XmlElement(name="dsAdapterID", required=true)    
    public String getDsAdapterID() {
        return dsAdapterID;
    }

    /**
     * @param dsAdapterID the dsAdapterID to set
     */
    public void setDsAdapterID(String dsAdapterID) {
        this.dsAdapterID = dsAdapterID;
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
     * @return the virtual
     */
    @XmlElement(name="virtual", required=true)    
    public Boolean getVirtual() {
        return virtual;
    }

    /**
     * @param virtual the virtual to set
     */
    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
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
     * @return the supportedTypes
     */
    @XmlElement(name="supportedTypes", required=false)    
    public String getSupportedTypes() {
        return supportedTypes;
    }

    /**
     * @param supportedTypes the supportedTypes to set
     */
    public void setSupportedTypes(String supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    /**
     * @return the supportedFormats
     */
    @XmlElement(name="supportedFormats", required=false)             
    public String getSupportedFormats() {
        return supportedFormats;
    }

    /**
     * @param supportedFormats the supportedFormats to set
     */
    public void setSupportedFormats(String supportedFormats) {
        this.supportedFormats = supportedFormats;
    }

    /**
     * @return the location_latitude
     */
    @XmlElement(name="location_latitude", required=false)             
    public BigDecimal getLocation_latitude() {
        return location_latitude;
    }

    /**
     * @param location_latitude the location_latitude to set
     */
    public void setLocation_latitude(BigDecimal location_latitude) {
        this.location_latitude = location_latitude;
    }

    /**
     * @return the location_longitude
     */
    @XmlElement(name="location_longitude", required=false)             
    public BigDecimal getLocation_longitude() {
        return location_longitude;
    }

    /**
     * @param location_longitude the location_longitude to set
     */
    public void setLocation_longitude(BigDecimal location_longitude) {
        this.location_longitude = location_longitude;
    }

    /**
     * @return the location_altitude
     */
    @XmlElement(name="location_altitude", required=false)
    public BigDecimal getLocation_altitude() {
        return location_altitude;
    }

    /**
     * @param location_altitude the location_altitude to set
     */
    public void setLocation_altitude(BigDecimal location_altitude) {
        this.location_altitude = location_altitude;
    }
    
    @Override
    public String toString()
    {
        try {
            return this.writeTo();
        } catch (ProcessingException ex) {
            return "Exception: "+ex.getMessage();
        }
    }

    public static DataSourceAdapter test(int i)
    {
        DataSourceAdapter u=new DataSourceAdapter();
        u.setDsAdapterID("ID"+i);
        u.setName("DSA"+i);
        u.setVirtual(Boolean.FALSE);
        u.setDescription("some description"+i);
        u.setLocation_altitude(BigDecimal.ONE);
        u.setLocation_latitude(BigDecimal.ZERO);
        u.setLocation_longitude(BigDecimal.TEN);
        u.setSupportedFormats("formats"+i);
        u.setSupportedTypes("application/xml, application/json");

        return u;        
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
        if(!(target instanceof DataSourceAdapter)) return false;
        
        DataSourceAdapter cp=(DataSourceAdapter)target;
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
        if(!(ptr instanceof DataSourceAdapter)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSourceAdapter comp=(DataSourceAdapter)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;
                
        return TokenDifference.createAsAList(DataSourceAdapter.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        DataSourceAdapter u=test(1);
        String xml=TranslateXML.toXml(u);
        String json=TranslateJSON.toJSON(u);
        String brief=u.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSourceAdapter tg=(DataSourceAdapter) u.readFrom(brief);
        System.out.println("ID: "+tg.getDsAdapterID());
        System.out.println("Name: "+tg.getName());
        System.out.println("Virtual: "+tg.getVirtual());
        System.out.println("Description: "+tg.getDescription());
        System.out.println("Altitude: "+tg.getLocation_altitude());
        System.out.println("Latitude: "+tg.getLocation_latitude());
        System.out.println("Longitude: "+tg.getLocation_longitude());
        System.out.println("Formats: "+tg.getSupportedFormats());
        System.out.println("Types: "+tg.getSupportedTypes());
        
        System.out.println("Equal: "+u.equals(tg));
    }    
}
