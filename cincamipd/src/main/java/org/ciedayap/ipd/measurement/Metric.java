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
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It represents the metric related to a given data source
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Metric")
@XmlType(propOrder={"ID","name","objective","author","version","IDattribute","scale","dataSources","constraints"})
public class Metric implements Serializable, Level{
    private String ID;
    private String name;
    private String objective;
    private String author;
    private String version;
    private String IDattribute;
    private Scale scale;
    private DataSources dataSources;
    private Constraints constraints;
    
    public Metric(){}

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        ID=null;
        name=null;
        objective=null;
        author=null;
        version=null;
        IDattribute=null;
        if(scale!=null)
        {
            scale.realeaseResources();
            scale=null;
        }

        if(dataSources!=null)
        {
            dataSources.realeaseResources();
            dataSources=null;
        }
        
        if(constraints!=null)
        {
            constraints.realeaseResources();
            constraints=null;
        }

        return true;
    }
    
    public static synchronized Metric create(String id, String na, String ve,String idat, Scale sca,DataSources dsa)
    {
        Metric m=new Metric();
        m.setID(id);
        m.setName(na);
        m.setVersion(ve);
        m.setIDattribute(idat);
        m.setScale(sca);
        m.setDataSources(dsa);
        
        return m;
    }
    
    @Override
    public int getLevel() {
        return 8;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Metric);
        
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //objective  *optional*
        sb.append((StringUtils.isEmpty(this.getObjective()))?"":Tokens.removeTokens(this.getObjective().trim())).append(Tokens.fieldSeparator);
        //author  *optional*
        sb.append((StringUtils.isEmpty(this.getAuthor()))?"":Tokens.removeTokens(this.getAuthor().trim())).append(Tokens.fieldSeparator);
        //version  *mandatory*
        sb.append(Tokens.removeTokens(this.getVersion().trim())).append(Tokens.fieldSeparator);
        //idattribute  *mandatory*
        sb.append(Tokens.removeTokens(this.getIDattribute().trim())).append(Tokens.fieldSeparator);
        //scale  *mandatory*
        sb.append(scale.writeTo()).append(Tokens.fieldSeparator);        
        //dataSources  *mandatory*
        sb.append(dataSources.writeTo()).append(Tokens.fieldSeparator);
        //Constraints  *optional*
        if(constraints!=null && !constraints.isEmpty())
            sb.append(constraints.writeTo());
        
        sb.append(Tokens.Class_ID_Metric).append(Tokens.endLevel);

        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Metric readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Metric);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Metric+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Metric item=new Metric();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Metric.length()+1, idx_en);        
        
        //ID *mandatory*
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The ID field is not present and it is mandatory.");
        String value=cleanedText.substring(0, idx_en);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ID field is not present and it is mandatory.");
        item.setID(value);
        
        //"name" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The name field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory.");
        item.setName(value);

        //"objective" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The objective field is not present.");
        value=cleanedText.substring(0, idx_en);
        
        if(!StringUtils.isEmpty(value)) item.setObjective(value);
        
        //"author" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The author field is not present.");
        value=cleanedText.substring(0, idx_en);
        
        if(!StringUtils.isEmpty(value)) item.setAuthor(value);        
        
        //"version" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The version field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The version field is not present and it is mandatory.");
        item.setVersion(value);

        //"idattribute" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The idattribute field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The idattribute field is not present and it is mandatory.");
        item.setIDattribute(value);
        
        //scale *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Scale+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The Scale field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_Scale.length()+Tokens.endLevel.length());

        if(StringUtils.isEmpty(value)) throw new ProcessingException("The Scale field is not present and it is mandatory.");
        item.setScale((Scale)Scale.readFromSt(text));
        
        //"dataSources" *mandatory*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Scale.length()+Tokens.endLevel.length()+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_DataSources+Tokens.endLevel+Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The dataSources field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en+Tokens.Class_ID_DataSources.length()+Tokens.endLevel.length());
        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The dataSources field is not present and it is mandatory.");
        item.setDataSources((DataSources)DataSources.readFromSt(text));
        
        //Constraints *optional*
        cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_DataSources.length()+Tokens.endLevel.length()+1);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Constraints+Tokens.endLevel);
        if(idx_en<0) item.setConstraints(null);
        else
        {
            value=cleanedText.substring(0);
            if(StringUtils.isEmpty(value)) throw new ProcessingException("The constraints field is not present *malformed*.");
            item.setConstraints((Constraints)Constraints.readFromSt(text));
        }
        
        return item;        
    }  
    
    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name)  || StringUtils.isEmpty(version) || StringUtils.isEmpty(this.IDattribute) ||
                scale==null || !scale.isDefinedProperties() || dataSources==null || !dataSources.isDefinedProperties());        
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
        return this.ID;
    }

    /**
     * @return the ID
     */
    @XmlElement(name="ID", required=true)
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
     * @return the objective
     */
    @XmlElement(name="objective", required=false)
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
     * @return the author
     */
    @XmlElement(name="author", required=false)
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the version
     */
    @XmlElement(name="version", required=false)
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
     * @return the IDattribute
     */
    @XmlElement(name="IDattribute", required=true)
    public String getIDattribute() {
        return IDattribute;
    }

    /**
     * @param IDattribute the IDattribute to set
     */
    public void setIDattribute(String IDattribute) {
        this.IDattribute = IDattribute;
    }

    /**
     * @return the dataSources
     */
    @XmlElement(name="dataSources", required=true)
    public DataSources getDataSources() {
        return dataSources;
    }

    /**
     * @param dataSources the dataSources to set
     */
    public void setDataSources(DataSources dataSources) {
        if(this.dataSources!=null){
            try{
                this.dataSources.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.dataSources = dataSources;
    }

    /**
     * @return the constraints
     */
    @XmlElement(name="constraints", required=false)
    public Constraints getConstraints() {
        if(this.constraints!=null){
            try{
                this.constraints.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }
    
    public static Metric test(int i)
    {
        Metric m=new Metric();
        m.setAuthor("author"+i);
        m.setConstraints(Constraints.test(3));
        m.setDataSources(DataSources.test(3));
        m.setID("ID"+i);
        m.setIDattribute("IDAttr"+i);
        m.setName("My metric name"+i);
        m.setObjective("My objective"+i);
        m.setVersion("1.0.0");
        m.setScale(Scale.test(1));
        
        return m;
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
        if(!(target instanceof Metric)) return false;
        
        Metric cp=(Metric)target;
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
        if(!(ptr instanceof Metric)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Metric comp=(Metric)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();
        
        global.add(TokenDifference.create(Metric.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //  scale
        ArrayList<TokenDifference> result=this.scale.findDifferences(comp.getScale());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }

        //  dataSources
        result=this.dataSources.findDifferences(comp.getDataSources());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);
            result.clear();
        }
        
        //  constraints *optional*
        if(constraints!=null && constraints.length()>0 &&
                comp.getConstraints()!=null && comp.getConstraints().length()>0)
        {
            result=this.constraints.findDifferences(comp.getConstraints());
            if(result!=null && result.size()>0)
            {
                global.addAll(result);
                result.clear();
            }
        }
        else
        {
            if((constraints!=null && constraints.length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(Constraints.class, constraints.getUniqueID(), 
                        constraints.getLevel(), constraints.computeFingerprint(), null));
            }

            if((comp.getConstraints()!=null && comp.getConstraints().length()>0))                    
            {//One of them has something
                global.add(TokenDifference.create(Constraints.class, comp.getConstraints().getUniqueID(), 
                        comp.getConstraints().getLevel(), null, comp.getConstraints().computeFingerprint()));
            }
        }
                 
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        Metric m=test(1);
        
        String xml=TranslateXML.toXml(m);
        String json=TranslateJSON.toJSON(m);
        String brief=m.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Metric ret=(Metric) Metric.readFromSt(brief);
        
        System.out.println("Author: "+ret.getAuthor());
        System.out.println("ID: "+ret.getID());
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
        
        System.out.println("Equal: "+m.equals(ret));
    }    

    /**
     * @return the scale
     */
    @XmlElement(name="scale", required=true)
    public Scale getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Scale scale) {
        if(this.scale!=null){
            try{
                this.scale.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.scale = scale;
    }
}
