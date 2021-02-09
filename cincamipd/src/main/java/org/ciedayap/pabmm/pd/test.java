/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.ciedayap.pabmm.pd.context.Context;
import org.ciedayap.pabmm.pd.context.ContextProperties;
import org.ciedayap.pabmm.pd.context.ContextProperty;
import org.ciedayap.pabmm.pd.evaluation.DecisionCriteria;
import org.ciedayap.pabmm.pd.evaluation.DecisionCriterion;
import org.ciedayap.pabmm.pd.evaluation.ElementaryIndicator;
import org.ciedayap.pabmm.pd.evaluation.ElementaryModel;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.pabmm.pd.measurement.DataSource;
import org.ciedayap.pabmm.pd.measurement.DataSourceAdapter;
import org.ciedayap.pabmm.pd.measurement.DataSourceAdapters;
import org.ciedayap.pabmm.pd.measurement.DataSources;
import org.ciedayap.pabmm.pd.measurement.DirectMetric;
import org.ciedayap.pabmm.pd.measurement.MeasurementMethod;
import org.ciedayap.pabmm.pd.measurement.Metrics;
import org.ciedayap.pabmm.pd.measurement.Scale;
import org.ciedayap.pabmm.pd.measurement.ScaleType;
import org.ciedayap.pabmm.pd.measurement.TraceGroup;
import org.ciedayap.pabmm.pd.measurement.TraceGroups;
import org.ciedayap.pabmm.pd.measurement.Unit;
import org.ciedayap.pabmm.pd.requirements.Attribute;
import org.ciedayap.pabmm.pd.requirements.Attributes;
import org.ciedayap.pabmm.pd.requirements.CalculableConcept;
import org.ciedayap.pabmm.pd.requirements.CalculableConcepts;
import org.ciedayap.pabmm.pd.requirements.ConceptModel;
import org.ciedayap.pabmm.pd.requirements.ConceptModels;
import org.ciedayap.pabmm.pd.requirements.Entities;
import org.ciedayap.pabmm.pd.requirements.Entity;
import org.ciedayap.pabmm.pd.requirements.EntityCategory;
import org.ciedayap.pabmm.pd.requirements.InformationNeed;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;
import org.ciedayap.utils.ZipUtil;

/**
 * It defines a basic measurement and evaluation project
 * @author Mario Diván
 * @version 1.0
 */
public class test {
    public static void main(String args[]) throws EntityPDException, Exception
    {
        //1. Defining the Data Source Adapter and associated data sources
        DataSourceAdapter dsa=DataSourceAdapter.create("DSA_1", "Samsung Galaxy S6");
        DataSourceAdapters adapters=new DataSourceAdapters();
        adapters.getAdapters().add(dsa);

        //2. Defining the TraceGroup
        TraceGroup tg=TraceGroup.create("TG1", "Peter's Galaxy S6");
        TraceGroups tgroups=new TraceGroups();
        tgroups.getGroups().add(tg);
        
        //3. Defining the data sources
        DataSource ds_env_humi=DataSource.create("ds_env_humi", "Environmental Humidity's Sensor", adapters);//Interval
        ds_env_humi.setGroups(tgroups);     
        DataSources list_ds_env_humi=new DataSources();
        list_ds_env_humi.getSources().add(ds_env_humi);
                            
        DataSource ds_env_temp=DataSource.create("ds_env_temp", "Environmental Temperature's Sensor", adapters);//Interval
        ds_env_temp.setGroups(tgroups);      
        DataSources list_ds_env_temp=new DataSources();
        list_ds_env_temp.getSources().add(ds_env_temp);
        
        DataSource ds_env_press=DataSource.create("ds_env_press", "Environmental Pressure's Sensor", adapters);//Ratio
        ds_env_press.setGroups(tgroups);   
        DataSources list_ds_env_press=new DataSources();
        list_ds_env_press.getSources().add(ds_env_press);
        
        DataSource ds_heart=DataSource.create("ds_heart", "Heart Rate's Sensor", adapters);//Ratio
        ds_heart.setGroups(tgroups);     
        DataSources list_ds_heart=new DataSources();
        list_ds_heart.getSources().add(ds_heart);
        
        DataSource ds_temp=DataSource.create("ds_temp", "Corporal Temperature's Sensor", adapters);//Interval
        ds_temp.setGroups(tgroups);     
        DataSources list_ds_temp=new DataSources();
        list_ds_temp.getSources().add(ds_temp);
        
        
        //4 Defining Units and scales
        Unit u_humi=Unit.create("u_humi", "Percentage", "%");
        Unit u_temp=Unit.create("u_temp", "Celsius degreee", "C");
        Unit u_press=Unit.create("u_press", "Hectopascals", "hPa");
        Unit u_heart=Unit.create("u_heart", "Beats per minute", "bpm");
       
        Scale sca_humi=Scale.create("sca_humi","Environmental Humidity's Scale", ScaleType.INTERVAL, u_humi);
        Scale sca_temp=Scale.create("sca_temp","Environmental Temperature's Scale", ScaleType.INTERVAL, u_temp);
        Scale sca_press=Scale.create("sca_press","Environmental Pressure's Scale", ScaleType.RATIO, u_press);
        Scale sca_heart=Scale.create("sca_heart","Heart Rate's Scale", ScaleType.RATIO, u_heart);             
        
        //5. Defining the associated Measurement Method
        MeasurementMethod mm_humi=MeasurementMethod.create("mm_humi", "Direct Observation (Hygrometer)");
        MeasurementMethod mm_temp=MeasurementMethod.create("mm_temp", "Direct Observation (Thermometer)");
        MeasurementMethod mm_press=MeasurementMethod.create("mm_press", "Direct Observation (Barometer)");
        MeasurementMethod mm_heart=MeasurementMethod.create("mm_heart", "Direct Observation (Heart Rate Monitor)");
        
        //6 Defining The Direct Metrics related to the attributes and context properties 
        DirectMetric dm_pc_humi=DirectMetric.create("dm_pc_humi", "Value of Environmental Humidity", "pc_humi", sca_humi, list_ds_env_humi, mm_humi);
        DirectMetric dm_pc_temp=DirectMetric.create("dm_pc_temp", "Value of Environmental Temperature", "pc_temp", sca_temp, list_ds_env_temp, mm_temp);
        DirectMetric dm_pc_press=DirectMetric.create("dm_pc_press", "Value of Environmental Pressure", "pc_press", sca_press, list_ds_env_press, mm_press);
        DirectMetric dm_ctemp=DirectMetric.create("dm_ctemp", "Value of Corporal Temperature", "ctemp", sca_temp, list_ds_temp, mm_temp);
        DirectMetric dm_heart=DirectMetric.create("dm_heart", "Value of Heart Rate", "heartrate", sca_heart, list_ds_heart, mm_heart);
        
        //7 Defining the decision criteria associated to each metric value
            //Environmental Humidity
            DecisionCriteria envHumidityCriteria=new DecisionCriteria();
            DecisionCriterion dc=DecisionCriterion.create("humidity_low", "Low Humidity", BigDecimal.ZERO, BigDecimal.valueOf(40.0));
            dc.setNotifiableUnderLowerThreshold(false);
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(false);
            envHumidityCriteria.getCriteria().add(dc);//Low Humidity
            
            dc=DecisionCriterion.create("humidity_normal", "Normal Humidity", BigDecimal.valueOf(40.01),BigDecimal.valueOf(60));
            dc.setNotifiableUnderLowerThreshold(false);
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(true);
            dc.setNaut_message("The Environmental Humidity is upper than 60%");
            envHumidityCriteria.getCriteria().add(dc);//Normal Humidity

            dc=DecisionCriterion.create("humidity_high", "High Humidity", BigDecimal.valueOf(60.01),BigDecimal.valueOf(100));
            dc.setNotifiableUnderLowerThreshold(false);
            dc.setNotifiableBetweenThreshold(true);
            dc.setNbt_message("The Environmental Humidity is High");
            dc.setNotifiableAboveUpperThreshold(true);
            dc.setNaut_message("The Environmental Humidity is High");
            envHumidityCriteria.getCriteria().add(dc);//High Humidity

            //Environmental Temperature
            DecisionCriteria envTemp=new DecisionCriteria();
            dc=DecisionCriterion.create("temp_low", "Low Temperature", BigDecimal.valueOf(10.0),BigDecimal.valueOf(18));
            dc.setNotifiableUnderLowerThreshold(true);
            dc.setNult_message("The Environmental Temperature is under 10 celsius degree");
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(false);
            envTemp.getCriteria().add(dc);//Low Temperature

            dc=DecisionCriterion.create("temp_normal", "Normal Temperature", BigDecimal.valueOf(18.01),BigDecimal.valueOf(29));
            dc.setNotifiableUnderLowerThreshold(false);
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(false);            
            envTemp.getCriteria().add(dc);//Normal Temperature

            dc=DecisionCriterion.create("temp_high", "High Temperature", BigDecimal.valueOf(29.01),BigDecimal.valueOf(36));
            dc.setNotifiableUnderLowerThreshold(false);
            dc.setNotifiableBetweenThreshold(true);
            dc.setNbt_message("Warning. High Temperature");
            dc.setNotifiableAboveUpperThreshold(true);  
            dc.setNaut_message("Alert. Very High Temperature");
            envTemp.getCriteria().add(dc);//High Temperature

            //Environmental Pressure
            DecisionCriteria envPress=new DecisionCriteria();
            dc=DecisionCriterion.create("press_normal", "Normal Enviromental Pressure", BigDecimal.valueOf(900.0),BigDecimal.valueOf(1100));
            dc.setNotifiableUnderLowerThreshold(false);            
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(true);
            envPress.getCriteria().add(dc);//Normal Pressure

            //Corporal Temperature
            DecisionCriteria corpTemp=new DecisionCriteria();
            dc=DecisionCriterion.create("corptemp_normal", "Corporal Temperature", BigDecimal.valueOf(36.0),BigDecimal.valueOf(37.1));
            dc.setNotifiableUnderLowerThreshold(true);   
            dc.setNult_message("Warning. The Corporal Temperature is Under 36 celsiud degree");
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(true);
            dc.setNaut_message("Warning. The Corporal Temperature is Above 37.1 celsius degree");
            corpTemp.getCriteria().add(dc);//Normal Pressure      
            
            //Heart Rate
            DecisionCriteria heartRate=new DecisionCriteria();
            dc=DecisionCriterion.create("heartRate_normal", "Heart Rate", BigDecimal.valueOf(62.0),BigDecimal.valueOf(75));
            dc.setNotifiableUnderLowerThreshold(true);   
            dc.setNult_message("Warning. The Heart Rate is under than 62 bpm");
            dc.setNotifiableBetweenThreshold(false);
            dc.setNotifiableAboveUpperThreshold(true);
            dc.setNaut_message("Warning. The Heart Rate is upper than 75 bpm");
            heartRate.getCriteria().add(dc);//Normal Pressure      
   
        //8. Defining the Elementary Indicator 
            //Humidity
            ElementaryModel emodel_humi=ElementaryModel.create("elmo_humidity", "Environmental Humidity's Elementary Model ", envHumidityCriteria);
            ElementaryIndicator ind_env_humidity=ElementaryIndicator.create("ind_env_humidity", "Level of the Environmental Humidity", BigDecimal.valueOf(0.34), sca_humi, emodel_humi);
            //Environmental Temperature
            ElementaryModel emodel_env_temp=ElementaryModel.create("elmo_env_temp", "Environmental Temperature's Elementary Model ", envTemp);
            ElementaryIndicator ind_env_temp=ElementaryIndicator.create("ind_env_temp", "Level of the Environmental Temperature", BigDecimal.valueOf(0.33), sca_temp, emodel_env_temp);
            //Environmental Pressure
            ElementaryModel emodel_env_press=ElementaryModel.create("elmo_env_press", "Environmental Pressure's Elementary Model ", envPress);
            ElementaryIndicator ind_env_press=ElementaryIndicator.create("ind_env_press", "Level of the Environmental Pressure", BigDecimal.valueOf(0.33), sca_press, emodel_env_press);
            //Corporal Temperature
            ElementaryModel emodel_corptemp=ElementaryModel.create("elmo_corptemp", "Corporal Temperature's Elementary Model ", corpTemp);
            ElementaryIndicator ind_corpTemp=ElementaryIndicator.create("ind_corpTemp", "Level of the Corporal Temperature", BigDecimal.valueOf(1), sca_temp, emodel_corptemp);
            //Heart Rate
            ElementaryModel emodel_heart=ElementaryModel.create("elmo_hearttemp", "Heart Ratee's Elementary Model ", heartRate);
            ElementaryIndicator ind_heartRate=ElementaryIndicator.create("ind_heartRate", "Level of the Heart Rate", BigDecimal.valueOf(1), sca_heart, emodel_heart);
            
        //9. Defining the entity's attributes
        Metrics heartMetrics=new Metrics();
        heartMetrics.getRelated().add(dm_heart);
        Attribute at_heart=Attribute.create("heartrate","The Heart Rate", heartMetrics);
        at_heart.setDefinition("Number of beats per minute (bpm)");
        at_heart.setWeight(BigDecimal.valueOf(0.27));
        at_heart.setIndicator(ind_heartRate);
        
        Metrics corpTempMetrics=new Metrics();
        corpTempMetrics.getRelated().add(dm_ctemp);
        Attribute at_ctemp=Attribute.create("ctemp", "The Corporal Temperature", corpTempMetrics);
        at_ctemp.setDefinition("Value of the axilar temperature in Celsius degree");
        at_ctemp.setWeight(BigDecimal.valueOf(0.23));
        at_ctemp.setIndicator(ind_corpTemp);
        
        Attributes describedBy=new Attributes();
        describedBy.getCharacteristics().add(at_ctemp);
        describedBy.getCharacteristics().add(at_heart);        

        //10. Defining the Context Properties
        Metrics envHumMetrics=new Metrics();
        envHumMetrics.getRelated().add(dm_pc_humi);
        ContextProperty cp_humi=ContextProperty.create("pc_humi", "The Environmental Humidity", envHumMetrics);
        cp_humi.setDefinition("Amount of the water vapor in the air");
        cp_humi.setWeight(BigDecimal.valueOf(0.20));
        cp_humi.setIndicator(ind_env_humidity);
                          
        Metrics envTempMetrics=new Metrics();
        envTempMetrics.getRelated().add(dm_pc_temp);
        ContextProperty cp_temp=ContextProperty.create("pc_temp", "The Environmental Temperature", envTempMetrics);
        cp_temp.setDefinition("Value of the environmental temperature in Celsius degree");
        cp_temp.setWeight(BigDecimal.valueOf(0.15));
        cp_temp.setIndicator(ind_env_temp);
        
        Metrics envPressMetrics=new Metrics();
        envPressMetrics.getRelated().add(dm_pc_press);
        ContextProperty cp_press=ContextProperty.create("pc_press", "The Environmental Pressure", envPressMetrics);
        cp_press.setDefinition("Pressures resulting from human activities which bring about changes in the state of the environment");
        cp_press.setWeight(BigDecimal.valueOf(0.15));
        cp_press.setIndicator(ind_env_press);
        
        //11. Definint the Context
        Context ctxOutpatient=Context.create("ctx_outpatient", "The Outpatient Context");
        ContextProperties props=new ContextProperties();
        props.getContextProperties().add(cp_humi);
        props.getContextProperties().add(cp_temp);
        props.getContextProperties().add(cp_press);
        ctxOutpatient.setDescribedBy(props);

        
        //12. Defining the Entity under Monitoring
        Entity ent=Entity.create("Ent1", "Outpatient A (Peter)");     
        Entities monitored=new Entities();
        monitored.getEntitiesList().add(ent);

        //13. Defining the EntityCategory and associating the monitored entities
        EntityCategory ecat=EntityCategory.create("EC1", "Outpatient");
        ecat.setMonitored(monitored);
        ecat.setDescribedBy(describedBy);
 
        //14. Defining the Concept Model
        ConceptModel myCM=ConceptModel.create("cmod", "Outpatient Monitoring version 1.0");
        ConceptModels myCMs=new ConceptModels();
        myCMs.getRepresentedList().add(myCM);

        //15. Defining the calculable concept
        CalculableConcept calcon=CalculableConcept.create("calcon1", "Health");
        calcon.setRepresentedBy(myCMs);
        CalculableConcepts concepts=new CalculableConcepts();
        concepts.getCalculableConcepts().add(calcon);

        //16. Defining the Information
        InformationNeed IN_1=InformationNeed.create("IN_1", "Monitor the Outpatient", 
                "Avoid severe damages through the prevention of risks with direct impact in the outpatient health", 
                ecat, ctxOutpatient);
        IN_1.setDescribedBy(concepts);
                                        
        //17. Defining the Measurement Project
        MeasurementProject PRJ_1=MeasurementProject.create("PRJ_1","Outpatient Monitoring", ZonedDateTime.now(), IN_1, ZonedDateTime.now());
        MeasurementProjects projs=new MeasurementProjects();
        projs.getProjects().add(PRJ_1);

        //18. Associating the Measurement Project definition with the CINCAMI/PD message                
        CINCAMIPD message=CINCAMIPD.create(projs);
        
        //19. Generating the XML
        long before=System.nanoTime();
        String xml=TranslateXML.toXml(message);    
        long after=System.nanoTime();
        long xml_translating=after-before;
        before=System.nanoTime();
        byte[] compGZIP=ZipUtil.compressGZIP(xml);
        after=System.nanoTime();
        long xml_compression=after-before;
        //System.out.println(xml);
                
        //20. Generating to JSON
        before=System.nanoTime();
        String JSON=TranslateJSON.toJSON(message);
        after=System.nanoTime();
        long json_translating=after-before;
        //System.out.println(JSON);
        
        //21. Testing the GZIP Compression
        before=System.nanoTime();
        String xmld=ZipUtil.decompressGZIP(compGZIP);
        after=System.nanoTime();
        long xml_decompression=after-before;
              
        //21. Testing the conversion from the XML to Object Model        
        CINCAMIPD converted;
        before=System.nanoTime();
        converted = (CINCAMIPD) TranslateXML.toObject(CINCAMIPD.class, xmld);
        after=System.nanoTime();
        long xml_mapping=after-before;
        String xml2="";
        if(converted!=null)
        {
            //21.a Converse the recovered objet model to XML again
            xml2=TranslateXML.toXml(converted);
            
            if(xml2!=null && xml2.equalsIgnoreCase(xmld))
            {
                System.out.println("Object Model <-> CINCAMIPD XML [Compatible]");
            }
            else
            {
                System.out.println("Object Model <-> CINCAMIPD XML [Rejected]");
                //System.out.println(xml2);
                //buscar la posición con diferencia!
            }
        }
        else
        {
            System.out.println("XML Incompatible with CINCAMIPD");
        }
        
        //22. Testing the conversion from JSON to Object Model
        before=System.nanoTime();
        byte compJSON[]=ZipUtil.compressGZIP(JSON);
        after=System.nanoTime();
        long json_compression=after-before;
        before=System.nanoTime();
        String JSON2=ZipUtil.decompressGZIP(compJSON);
        after=System.nanoTime();
        long json_decompression=after-before;
        
        before=System.nanoTime();
        CINCAMIPD convertedJSON=(CINCAMIPD) TranslateJSON.toObject(CINCAMIPD.class, JSON2); 
        after=System.nanoTime();
        long json_mapping=after-before;
        String JSON3="";
        if(convertedJSON!=null)
        {
            JSON3=TranslateJSON.toJSON(convertedJSON);
            String JSON4=TranslateJSON.toJSON(TranslateJSON.toObject(CINCAMIPD.class, JSON3));
            
            //Checking multiple conversions
            if(JSON3!=null && JSON3.equalsIgnoreCase(JSON4))
            {
                System.out.println("Object Model <-> CINCAMIPD JSON [Compatible]");
            }
            else
            {
                System.out.println("Object Model <-> CINCAMIPD JSON [Rejected]");
               
            }
        }
        
        //23. Info XML
        System.out.println();
        System.out.println("XML: "+xml.getBytes().length+" bytes. Length: "+xml.length()+" Time (ns): "+xml_translating);        
        System.out.println("XML GZIP: "+compGZIP.length+" bytes. Time (ns): "+xml_compression);       
        System.out.println("Decompressed XML: "+xmld.getBytes().length+" bytes. Length: "+xmld.length()+
                " Time(ns): "+xml_decompression);
        System.out.println("GZIP: "+xml.equalsIgnoreCase(xmld));
        System.out.println("New XML2: "+xml2.getBytes().length+" bytes. Length: "+xml2.length()+" Time Mapping (ns): "+xml_mapping);
        System.out.println(xml);
        
        //24. Info JSON
        System.out.println();
        System.out.println("JSON: "+JSON.getBytes().length+" bytes. Length: "+JSON.length()+" Time(ns): "+json_translating);
        System.out.println("JSON GZIP: "+compJSON.length+" bytes. Time(ns): "+json_compression);       
        System.out.println("Decompressed JSON: "+JSON2.getBytes().length+" bytes. Length: "+JSON2.length()+
                " Time(ns): "+json_decompression);
        System.out.println("GZIP JSON: "+JSON.equalsIgnoreCase(JSON2));
        System.out.println("Time JSON Mapping (ns): "+json_mapping);
        System.out.println(JSON3);

    }
    
    
}
