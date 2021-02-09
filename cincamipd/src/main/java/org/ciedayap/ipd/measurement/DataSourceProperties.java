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
 * It  is a container of DataSource properties
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="DataSourceProperties")
@XmlType(propOrder={"dataSourceProperties"})
public class DataSourceProperties implements Serializable, Level, Containers{
    private java.util.ArrayList<DataSourceProperty> dataSourceProperties;
    
    public static synchronized DataSourceProperties create(ArrayList<DataSourceProperty> l)
    {
        DataSourceProperties dsp=new DataSourceProperties();
        dsp.setDataSourceProperties(l);
        
        return dsp;
    }
    
    public DataSourceProperties()
    {
        dataSourceProperties=new ArrayList();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(dataSourceProperties==null)  return true;
        for(int i=0;i<dataSourceProperties.size();i++)
        {
            dataSourceProperties.get(i).realeaseResources();
        }
        
        dataSourceProperties.clear();
        dataSourceProperties=null;

        return true;
    }

    @Override
    public int getLevel() {
        return 11;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getDataSourceProperties().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel);
        for(DataSourceProperty item:dataSourceProperties)
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

    public static DataSourceProperties readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel);
        int idx_en=text.lastIndexOf(Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        DataSourceProperties item=new DataSourceProperties();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel);
        idx_en=cleanedText.indexOf(Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+1);            
            DataSourceProperty tg=null;
            try{
                tg=(DataSourceProperty) DataSourceProperty.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                tg=null;
            }
            
            if(tg!=null) item.getDataSourceProperties().add(tg);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+1);
            idx_st=cleanedText.indexOf(Tokens.startLevel);
            idx_en=cleanedText.indexOf(Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(dataSourceProperties==null || dataSourceProperties.isEmpty());
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
        for (DataSourceProperty item : dataSourceProperties) 
        {
            sb.append(item.getPropertyID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {
        return (dataSourceProperties!=null)?dataSourceProperties.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(dataSourceProperties==null) return true;
        
        return dataSourceProperties.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return DataSourceProperty.class;
    }

    /**
     * @return the dataSourceProperties
     */
    @XmlElement(name="DataSourceProperty", required=true)    
    public java.util.ArrayList<DataSourceProperty> getDataSourceProperties() {
        return dataSourceProperties;
    }

    /**
     * @param dataSourceProperties the dataSourceProperties to set
     */
    public void setDataSourceProperties(java.util.ArrayList<DataSourceProperty> dataSourceProperties) {
        if(this.dataSourceProperties!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSourceProperties = dataSourceProperties;
    }
    
    public static DataSourceProperties test(int k)
    {
        DataSourceProperties list=new  DataSourceProperties();
        
        for(int i=0;i<3;i++)
        {
            DataSourceProperty u=DataSourceProperty.test(i);
            list.getDataSourceProperties().add(u);
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
        if(!(target instanceof DataSourceProperties)) return false;
        
        DataSourceProperties cp=(DataSourceProperties)target;
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
        if(!(ptr instanceof DataSourceProperties)) throw new ProcessingException("The instance to be compared is not of the expected type");
        DataSourceProperties comp=(DataSourceProperties)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.dataSourceProperties.size()!=comp.getDataSourceProperties().size())
            return TokenDifference.createAsAList(DataSourceProperties.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<this.dataSourceProperties.size();i++)
        {
            DataSourceProperty pthis=this.dataSourceProperties.get(i);
            DataSourceProperty pcomp=comp.getDataSourceProperties().get(i);
            
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
        DataSourceProperties list=DataSourceProperties.test(3);
        String xml=TranslateXML.toXml(list);
        String json=TranslateJSON.toJSON(list);
        String brief=list.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        DataSourceProperties tgs=(DataSourceProperties) DataSourceProperties.readFromSt(brief);
        for(DataSourceProperty tg:tgs.getDataSourceProperties())
        {
            System.out.println("ID: "+tg.getPropertyID());
            System.out.println("Name: "+tg.getName());
            System.out.println("Description: "+tg.getDescription());
            System.out.println("Value: "+tg.getValue());
            System.out.println("Relevance: "+tg.getRelevance());
        }
        
        System.out.println("Equal: "+list.equals(tgs));
    }    
    
}
