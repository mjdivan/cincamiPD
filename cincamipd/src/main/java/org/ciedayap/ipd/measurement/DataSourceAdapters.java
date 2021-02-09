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
 *
 * @author mjdivan
 */
@XmlRootElement(name="DataSourceAdapters")
@XmlType(propOrder={"dataSourceAdapters"})
public class DataSourceAdapters implements Serializable, Level, Containers{
    private java.util.ArrayList<DataSourceAdapter> dataSourceAdapters;
    
    public DataSourceAdapters()
    {
        dataSourceAdapters=new java.util.ArrayList<>();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(dataSourceAdapters==null)  return true;
        for(int i=0;i<dataSourceAdapters.size();i++)
        {
            dataSourceAdapters.get(i).realeaseResources();
        }
        
        dataSourceAdapters.clear();
        dataSourceAdapters=null;

        return true;
    }

    public static synchronized DataSourceAdapters create(ArrayList<DataSourceAdapter> l)
    {
        DataSourceAdapters dsa=new DataSourceAdapters();
        dsa.setDataSourceAdapters(l);
        
        return dsa;
    }
    
    @Override
    public int getLevel() {
        return 11;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getDataSourceAdapters().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        for(DataSourceAdapter item:dataSourceAdapters)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.endLevel);
        
        return sb.toString();

    }
    
    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static DataSourceAdapters readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSourceAdapters item=new DataSourceAdapters();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel);
        idx_en=cleanedText.indexOf(Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+1);            
            DataSourceAdapter dataItem=null;
            try{
                dataItem=(DataSourceAdapter) DataSourceAdapter.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                dataItem=null;
            }
            
            if(dataItem!=null) item.getDataSourceAdapters().add(dataItem);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+1);
            idx_st=cleanedText.indexOf(Tokens.startLevel);
            idx_en=cleanedText.indexOf(Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(this.dataSourceAdapters==null || this.dataSourceAdapters.isEmpty());
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
        for (DataSourceAdapter item : this.dataSourceAdapters) 
        {
            sb.append(item.getDsAdapterID()).append("-");
        }
        
        return sb.toString();

    }

    @Override
    public int length() {
        return (this.dataSourceAdapters!=null)?this.dataSourceAdapters.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(this.dataSourceAdapters==null) return true;
        
        return this.dataSourceAdapters.isEmpty();

    }

    @Override
    public Class getKindOfElement() {
        return DataSourceAdapter.class;
    }

    /**
     * @return the dataSourceAdapters
     */
    @XmlElement(name="DataSourceAdapter", required=true)
    public java.util.ArrayList<DataSourceAdapter> getDataSourceAdapters() {
        return dataSourceAdapters;
    }

    /**
     * @param dataSourceAdapters the dataSourceAdapters to set
     */
    public void setDataSourceAdapters(java.util.ArrayList<DataSourceAdapter> dataSourceAdapters) {
        if(this.dataSourceAdapters!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSourceAdapters = dataSourceAdapters;
    }
 
    public static DataSourceAdapters test(int k)
    {
        DataSourceAdapters list=new  DataSourceAdapters();
        
        for(int i=0;i<k;i++)
        {
            DataSourceAdapter u=DataSourceAdapter.test(i);
            
            list.getDataSourceAdapters().add(u);
        }

        return list;
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
        if(!(target instanceof DataSourceAdapters)) return false;
        
        DataSourceAdapters cp=(DataSourceAdapters)target;
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
        if(!(ptr instanceof DataSourceAdapters)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSourceAdapters comp=(DataSourceAdapters)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.dataSourceAdapters.size()!=comp.getDataSourceAdapters().size())
            return TokenDifference.createAsAList(DataSourceAdapters.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<this.dataSourceAdapters.size();i++)
        {
            DataSourceAdapter pthis=this.dataSourceAdapters.get(i);
            DataSourceAdapter pcomp=comp.getDataSourceAdapters().get(i);
            
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
        DataSourceAdapters list=test(3);
        
        String xml=TranslateXML.toXml(list);
        String json=TranslateJSON.toJSON(list);
        String brief=list.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSourceAdapters tgs=(DataSourceAdapters) DataSourceAdapters.readFromSt(brief);
        for(DataSourceAdapter tg:tgs.getDataSourceAdapters())
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
        
        System.out.println("Equal: "+tgs.equals(list));
    }    
}
