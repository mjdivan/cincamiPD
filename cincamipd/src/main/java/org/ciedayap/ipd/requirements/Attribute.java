/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.requirements;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.measurement.Constraints;
import org.ciedayap.ipd.measurement.DataSource;
import org.ciedayap.ipd.measurement.DataSourceAdapter;
import org.ciedayap.ipd.measurement.DataSourceProperty;
import org.ciedayap.ipd.measurement.DataSources;
import org.ciedayap.ipd.measurement.Metric;
import org.ciedayap.ipd.measurement.Values;
import org.ciedayap.ipd.measurement.Metrics;
import org.ciedayap.ipd.measurement.RangeValueC;
import org.ciedayap.ipd.measurement.Scale;
import org.ciedayap.ipd.measurement.TraceGroup;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It describes how an attribute is quantified.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Attribute")
@XmlType(propOrder={"ID","name","objective","weight","minEstimatedValue","maxEstimatedValue","avgEstimatedValue",
"sdEstimatedValue","kurtosisEstimatedValue","skewnessEstimatedValue","medianEstimatedValue","modeEstimated",
"metrics"})
public class Attribute implements Serializable, Level{
    protected String ID;//mandatory
    protected String name;//mandatory
    protected String objective;//optional
    protected BigDecimal weight;//mandatory
    protected BigDecimal minEstimatedValue;//optional
    protected BigDecimal maxEstimatedValue;//optional
    protected BigDecimal avgEstimatedValue;//optional
    protected BigDecimal sdEstimatedValue;//optional
    protected BigDecimal kurtosisEstimatedValue;//optional
    protected BigDecimal skewnessEstimatedValue;//optional
    protected BigDecimal medianEstimatedValue;//optional
    protected Values modeEstimated;//optional
    protected Metrics metrics;//mandatory

    public Attribute(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;//mandatory
        name=null;//mandatory
        objective=null;//optional
        weight=null;//mandatory
        minEstimatedValue=null;//optional
        maxEstimatedValue=null;//optional
        avgEstimatedValue=null;//optional
        sdEstimatedValue=null;//optional
        kurtosisEstimatedValue=null;//optional
        skewnessEstimatedValue=null;//optional
        medianEstimatedValue=null;//optional
        
        if(modeEstimated!=null)
        {
            modeEstimated.realeaseResources();
            modeEstimated=null;
        }
        
        if(metrics!=null)
        {
            metrics.realeaseResources();
            metrics=null;
        }
        
        return true;
    }
    
    public static synchronized Attribute create(String ID, String nam, BigDecimal we, Metrics m)            
    {
        Attribute a=new Attribute();
        a.setWeight(we);
        a.setID(ID);
        a.setName(nam);
        a.setMetrics(m);
        
        return a;
    }
    
    @Override
    public int getLevel() {
        return 6;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Attribute);
        
        //"ID" *Mandatory*
        sb.append(Tokens.removeTokens(this.getID())).append(Tokens.fieldSeparator);
        //"name" *Mandatory*
        sb.append(Tokens.removeTokens(this.getName())).append(Tokens.fieldSeparator);        
        //"objective" *optional*
        sb.append((StringUtils.isEmpty(objective))?"":Tokens.removeTokens(this.getObjective())).append(Tokens.fieldSeparator);        
        //weight *mandatory*
        sb.append(weight).append(Tokens.fieldSeparator);
        //minEstimatedValue *optional*
        sb.append((minEstimatedValue==null)?"":minEstimatedValue).append(Tokens.fieldSeparator);
        //maxEstimatedValue *optional*
        sb.append((maxEstimatedValue==null)?"":maxEstimatedValue).append(Tokens.fieldSeparator);
        //avgEstimatedValue *optional*
        sb.append((avgEstimatedValue==null)?"":avgEstimatedValue).append(Tokens.fieldSeparator);
        //sdEstimatedValue *optional*
        sb.append((sdEstimatedValue==null)?"":sdEstimatedValue).append(Tokens.fieldSeparator);
        //kurtosisEstimatedValue *optional*
        sb.append((kurtosisEstimatedValue==null)?"":kurtosisEstimatedValue).append(Tokens.fieldSeparator);
        //skewnessEstimatedValue *optional*
        sb.append((skewnessEstimatedValue==null)?"":skewnessEstimatedValue).append(Tokens.fieldSeparator);
        //medianEstimatedValue *optional*
        sb.append((medianEstimatedValue==null)?"":medianEstimatedValue).append(Tokens.fieldSeparator);
        //modeEstimated *optional*
        sb.append((modeEstimated==null)?"":modeEstimated.writeTo()).append(Tokens.fieldSeparator);
        //metrics *mandatory*
        sb.append(metrics.writeTo());
                
        sb.append(Tokens.Class_ID_Attribute).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Attribute readFromSt(String text) throws ProcessingException {
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Attribute);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Attribute+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Attribute item=new Attribute();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Attribute.length()+1, idx_en);                
        
        //"ID" *Mandatory*       
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"name" *Mandatory*      
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);
                
        //"objective" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The objective field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setObjective(value);
        
        //"Weight" *Mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The type field separator is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        item.setWeight(new BigDecimal(value));
        
        //"minEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The minEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setMinEstimatedValue(new BigDecimal(value));

        //"maxEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The maxEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setMaxEstimatedValue(new BigDecimal(value));

        //"avgEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The avgEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setAvgEstimatedValue(new BigDecimal(value));

        //"sdEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The sdEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSdEstimatedValue(new BigDecimal(value));

        //"kurtosisEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The kurtosisEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setKurtosisEstimatedValue(new BigDecimal(value));

        //"skewnessEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The skewnessEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setSkewnessEstimatedValue(new BigDecimal(value));

        //"medianEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The medianEstimatedValue field separator is not present.");
        value=cleanedText.substring(0, idx_en);
        if(!StringUtils.isEmpty(value)) item.setMedianEstimatedValue(new BigDecimal(value));

        //"modeEstimatedValue" *Optional*
        cleanedText=cleanedText.substring(idx_en+1);
        
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Values+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en>=0) 
        {
            value=cleanedText.substring(0, Tokens.Class_ID_Values.length()+Tokens.endLevel.length()+idx_en);
            item.setModeEstimated((Values)Values.readFromSt(value));
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Values.length()+Tokens.endLevel.length()+1);            
        }
        else
        {//No values
            idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
            if(idx_en<0) throw new ProcessingException("The modeEstimatedValues field separator is not present.");
            cleanedText=cleanedText.substring(idx_en+1);            
        }

        //"metrics" *Mandatory*
        //See the conditional before
        value=cleanedText.substring(0);                       
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The metrics field separator is not present.");
        item.setMetrics((Metrics)Metrics.readFromSt(value));
                                
        return item;
    }   
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || weight==null ||
                weight.compareTo(BigDecimal.ZERO)<0 || weight.compareTo(BigDecimal.ONE)>0 ||
                !metrics.isDefinedProperties());
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
     * @return the objective
     */
    public String getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the minEstimatedValue
     */
    public BigDecimal getMinEstimatedValue() {
        return minEstimatedValue;
    }

    /**
     * @param minEstimatedValue the minEstimatedValue to set
     */
    public void setMinEstimatedValue(BigDecimal minEstimatedValue) {
        this.minEstimatedValue = minEstimatedValue;
    }

    /**
     * @return the maxEstimatedValue
     */
    public BigDecimal getMaxEstimatedValue() {
        return maxEstimatedValue;
    }

    /**
     * @param maxEstimatedValue the maxEstimatedValue to set
     */
    public void setMaxEstimatedValue(BigDecimal maxEstimatedValue) {
        this.maxEstimatedValue = maxEstimatedValue;
    }

    /**
     * @return the avgEstimatedValue
     */
    public BigDecimal getAvgEstimatedValue() {
        return avgEstimatedValue;
    }

    /**
     * @param avgEstimatedValue the avgEstimatedValue to set
     */
    public void setAvgEstimatedValue(BigDecimal avgEstimatedValue) {
        this.avgEstimatedValue = avgEstimatedValue;
    }

    /**
     * @return the sdEstimatedValue
     */
    public BigDecimal getSdEstimatedValue() {
        return sdEstimatedValue;
    }

    /**
     * @param sdEstimatedValue the sdEstimatedValue to set
     */
    public void setSdEstimatedValue(BigDecimal sdEstimatedValue) {
        this.sdEstimatedValue = sdEstimatedValue;
    }

    /**
     * @return the kurtosisEstimatedValue
     */
    public BigDecimal getKurtosisEstimatedValue() {
        return kurtosisEstimatedValue;
    }

    /**
     * @param kurtosisEstimatedValue the kurtosisEstimatedValue to set
     */
    public void setKurtosisEstimatedValue(BigDecimal kurtosisEstimatedValue) {
        this.kurtosisEstimatedValue = kurtosisEstimatedValue;
    }

    /**
     * @return the skewnessEstimatedValue
     */
    public BigDecimal getSkewnessEstimatedValue() {
        return skewnessEstimatedValue;
    }

    /**
     * @param skewnessEstimatedValue the skewnessEstimatedValue to set
     */
    public void setSkewnessEstimatedValue(BigDecimal skewnessEstimatedValue) {
        this.skewnessEstimatedValue = skewnessEstimatedValue;
    }

    /**
     * @return the medianEstimatedValue
     */
    public BigDecimal getMedianEstimatedValue() {
        return medianEstimatedValue;
    }

    /**
     * @param medianEstimatedValue the medianEstimatedValue to set
     */
    public void setMedianEstimatedValue(BigDecimal medianEstimatedValue) {
        this.medianEstimatedValue = medianEstimatedValue;
    }

    /**
     * @return the modeEstimated
     */
    @XmlElement(name="Mode", required=true)
    public Values getModeEstimated() {
        return modeEstimated;
    }

    /**
     * @param modeEstimated the modeEstimated to set
     */
    public void setModeEstimated(Values modeEstimated) {
        if(this.modeEstimated!=null){
            try{
                this.modeEstimated.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.modeEstimated = modeEstimated;
    }

    /**
     * @return the metrics
     */
    @XmlElement(name="Metrics", required=true)
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(Metrics metrics) {
        if(this.metrics!=null){
            try{
                this.metrics.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.metrics = metrics;
    }
    
    public static Attribute test(int k)
    {
        Attribute at=new Attribute();
        at.setID("IDAtt"+k);
        at.setName("name att"+k);
        at.setWeight(BigDecimal.ONE);
        at.setMetrics(Metrics.test(3));
        at.setMinEstimatedValue(new BigDecimal("5"));
        at.setMaxEstimatedValue(new BigDecimal("7"));
        at.setAvgEstimatedValue(new BigDecimal("6.5"));
        at.setMedianEstimatedValue(new BigDecimal("6.6"));
        at.setKurtosisEstimatedValue(new BigDecimal("1.6"));
        at.setSkewnessEstimatedValue(new BigDecimal("0.6"));
        at.setModeEstimated(Values.test(3));
        
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
        if(!(target instanceof Attribute)) return false;
        
        Attribute cp=(Attribute)target;
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
        if(!(ptr instanceof Attribute)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Attribute comp=(Attribute)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(Attribute.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  values **optional**
        ArrayList<TokenDifference> result;
        if(modeEstimated!=null && modeEstimated.length()>0 &&
                comp.getModeEstimated()!=null && comp.getModeEstimated().length()>0)
        {
            result=this.modeEstimated.findDifferences(comp.getModeEstimated());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((modeEstimated!=null && modeEstimated.length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(Values.class, modeEstimated.getUniqueID(), 
                        modeEstimated.getLevel(), modeEstimated.computeFingerprint(), null));
            }

            if((comp.getModeEstimated()!=null && comp.getModeEstimated().length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(Values.class, comp.getModeEstimated().getUniqueID(), 
                        comp.getModeEstimated().getLevel(), null, comp.getModeEstimated().computeFingerprint()));
            }
        }

        //  metrics *mandatory*
        result=this.metrics.findDifferences(comp.getMetrics());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
                 
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {

        Attribute at=test(1);
        
        String xml=TranslateXML.toXml(at);
        String json=TranslateJSON.toJSON(at);
        String brief=at.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Attribute at2=(Attribute)Attribute.readFromSt(brief);
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
            
            System.out.println("Equal: "+at.equals(at2));
            
        }
    }    
    
}
