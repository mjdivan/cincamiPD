/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.requirements;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Containers;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.measurement.Constraints;
import org.ciedayap.ipd.measurement.DataSource;
import org.ciedayap.ipd.measurement.DataSourceAdapter;
import org.ciedayap.ipd.measurement.DataSourceProperty;
import org.ciedayap.ipd.measurement.DataSources;
import org.ciedayap.ipd.measurement.Metric;
import org.ciedayap.ipd.measurement.Metrics;
import org.ciedayap.ipd.measurement.RangeValueC;
import org.ciedayap.ipd.measurement.Scale;
import org.ciedayap.ipd.measurement.TraceGroup;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 *
 * @author mjdivan
 */
@XmlRootElement(name="Attributes")
@XmlType(propOrder={"attributes"})
public class Attributes implements Serializable, Containers,Level{
    private ArrayList<Attribute> attributes;
    
    public Attributes()
    {
        attributes=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(attributes==null)  return true;
        for(int i=0;i<attributes.size();i++)
        {
            attributes.get(i).realeaseResources();
        }
        
        attributes.clear();
        attributes=null;

        return true;
    }
    
    public static synchronized Attributes create(ArrayList<Attribute> l)
    {
        Attributes at=new Attributes();
        at.setAttributes(l);
        
        return at;
    }
    
    @Override
    public int length() {
        return (attributes==null)?0:attributes.size();
    }

    @Override
    public boolean isEmpty() {
        if(attributes==null) return true;
        return attributes.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return Attribute.class;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Attributes);
        for(Attribute item:attributes)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Attributes).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Attributes readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Attributes);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Attributes+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Attributes item=new Attributes();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Attributes.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Attribute);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Attribute+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Attribute.length()+1);            
            Attribute met=null;
            try{
                met=(Attribute) Attribute.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                met=null;
            }
            
            if(met!=null) item.getAttributes().add(met);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Attribute.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Attribute);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Attribute+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(attributes==null || attributes.isEmpty());
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
        for (Attribute item : this.getAttributes()) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    /**
     * @return the attributes
     */
    @XmlElement(name="Attribute", required=true)
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList<Attribute> attributes) {
        if(this.attributes!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.attributes = attributes;
    }

    public static Attributes test(int k)
    {
        Attributes at=new Attributes();
        for(int i=0;i<k;i++)
        {
            at.getAttributes().add(Attribute.test(i));
        }
        
        return at;
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
        if(!(target instanceof Attributes)) return false;
        
        Attributes cp=(Attributes)target;
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
        if(!(ptr instanceof Attributes)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Attributes comp=(Attributes)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.attributes.size()!=comp.getAttributes().size())
            return TokenDifference.createAsAList(Attributes.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<attributes.size();i++)
        {
            Attribute pthis=attributes.get(i);
            Attribute pcomp=comp.getAttributes().get(i);
            
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
        Attributes ats=test(3);
        
        String xml=TranslateXML.toXml(ats);
        String json=TranslateJSON.toJSON(ats);
        String brief=ats.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Attributes ats2=(Attributes)Attributes.readFromSt(brief);
        for(Attribute at2:ats2.getAttributes())
        {
            System.out.println("    AttID: "+at2.getID());
            System.out.println("    Name: "+at2.getName());
            System.out.println("    Weight: "+at2.getWeight());
            System.out.println("    Min: "+at2.getMinEstimatedValue());
            System.out.println("    Max: "+at2.getMaxEstimatedValue());
            System.out.println("    Med: "+at2.getMedianEstimatedValue());
            System.out.println("    AVG: "+at2.getAvgEstimatedValue());
            System.out.println("    kur: "+at2.getKurtosisEstimatedValue());
            System.out.println("    ske: "+at2.getSkewnessEstimatedValue());
            System.out.println("    Mode: "+((at2.getModeEstimated()!=null)?at2.getModeEstimated().getUniqueID():"-"));

            Metrics lret=at2.getMetrics();

            for(Metric ret:lret.getMetrics())
            {
                System.out.println("++++++++++++++++++++++++++++++++++++");
                System.out.println("ID: "+ret.getID());
                System.out.println("++++++++++++++++++++++++++++++++++++");

                System.out.println("Author: "+ret.getAuthor());

                System.out.println("Name: "+ret.getName());
                System.out.println("Attr: "+ret.getIDattribute());
                System.out.println("Obj: "+ret.getObjective());
                System.out.println("Version: "+ret.getVersion());

                System.out.println("***Scale***");
                Scale sc2=(Scale) ret.getScale();

                System.out.println("ID:"+sc2.getID());
                System.out.println("Name:"+sc2.getName());
                System.out.println("ScaleType:"+sc2.getScaleType());
                System.out.println("IDU: "+sc2.getUnit().getIDUnit());
                System.out.println("NameU: "+sc2.getUnit().getName());
                System.out.println("Description: "+sc2.getUnit().getDescription());
                System.out.println("Symbol: "+sc2.getUnit().getSymbol());
                System.out.println("SI_name: "+sc2.getUnit().getSI_name());
                System.out.println("SI_symbol: "+sc2.getUnit().getSI_symbol());            

                System.out.println("***DS***");
                DataSources rds=(DataSources) ret.getDataSources();
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


                System.out.println("***Constraints***");
                Constraints r2list=(Constraints) ret.getConstraints();        
                if(r2list!=null)
                {
                    for(RangeValueC r2:r2list.getConstraints())
                    {
                        System.out.println("ID: "+r2.getID());
                        System.out.println("Name: "+r2.getName());
                        System.out.println("Algorithm: "+r2.getFilterAlgorithm());
                        System.out.println("Type: "+r2.getFilterType());
                        System.out.println("ID: "+r2.getRange().getIDRange());
                        System.out.println("Min: "+r2.getRange().getMinValue());
                        System.out.println("minIncluded: "+r2.getRange().getMinValueIncluded());
                        System.out.println("Max: "+r2.getRange().getMaxValue());
                        System.out.println("maxIncluded: "+r2.getRange().getMaxValueIncluded());        
                        System.out.println("***************");
                    }
                }

            }
        }    
        
        
        String brief2=ats2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief2));//Reversible                
        System.out.println("Equal2:"+ats.equals(ats2));
    }
    
}
