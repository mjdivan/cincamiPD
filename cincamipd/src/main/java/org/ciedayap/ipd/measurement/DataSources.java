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
import org.ciedayap.ipd.Containers;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It contains the list of data sources
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="DataSources")
@XmlType(propOrder={"dataSources"})
public class DataSources implements Serializable, Level, Containers{
    private java.util.ArrayList<DataSource>  dataSources;
    
    public DataSources()
    {
        dataSources=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(dataSources==null)  return true;
        for(int i=0;i<dataSources.size();i++)
        {
            dataSources.get(i).realeaseResources();
        }
        
        dataSources.clear();
        dataSources=null;

        return true;
    }
    
    public static synchronized DataSources create(ArrayList<DataSource> l)
    {
        DataSources ds=new DataSources();
        ds.setDataSources(l);
        
        return ds;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getDataSources().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_DataSources);
        for(DataSource item:dataSources)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_DataSources).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DataSources readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_DataSources);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_DataSources+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSources item=new DataSources();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_DataSources.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_DataSource);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_DataSource+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_DataSource.length()+1);            
            DataSource ds=null;
            try{
                ds=(DataSource) DataSource.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getDataSources().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_DataSource.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_DataSource);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_DataSource+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(dataSources==null || dataSources.isEmpty());
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
        for (DataSource item : this.getDataSources()) 
        {
            sb.append(item.getDataSourceID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {
        return (dataSources!=null)?dataSources.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(dataSources==null) return true;
        
        return dataSources.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return DataSource.class;
    }

    /**
     * @return the dataSources
     */
    @XmlElement(name="DataSource", required=true)
    public java.util.ArrayList<DataSource> getDataSources() {
        return dataSources;
    }

    /**
     * @param dataSources the dataSources to set
     */
    public void setDataSources(java.util.ArrayList<DataSource> dataSources) {
        if(this.dataSources!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSources = dataSources;
    }
    
    public static DataSources test(int k)
    {
        DataSources dsa=new DataSources();
        for(int i=0;i<k;i++)
        {
            dsa.getDataSources().add(DataSource.test(i));
        }
        
        return dsa;
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
        if(!(target instanceof DataSources)) return false;
        
        DataSources cp=(DataSources)target;
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
        if(!(ptr instanceof DataSources)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSources comp=(DataSources)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.dataSources.size()!=comp.getDataSources().size())
            return TokenDifference.createAsAList(DataSources.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<this.dataSources.size();i++)
        {
            DataSource pthis=this.dataSources.get(i);
            DataSource pcomp=comp.getDataSources().get(i);
            
            ArrayList<TokenDifference> result=pthis.findDifferences(pcomp);
            if(result!=null && result.size()>0)
            {
                global.addAll(result);            
                result.clear();
            }
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {        
        DataSources dss=test(3);        
        
        String xml=TranslateXML.toXml(dss);
        String json=TranslateJSON.toJSON(dss);
        String brief=dss.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSources rds=(DataSources) DataSources.readFromSt(brief);
        for(DataSource nds:rds.getDataSources())
        {
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

            System.out.println("*****************");
        }
               
        System.out.println("Equal: "+rds.equals(dss));
    }
}
