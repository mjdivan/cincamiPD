/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.ciedayap.pabmm.pd.context.Context;
import org.ciedayap.pabmm.pd.context.ContextProperty;
import org.ciedayap.pabmm.pd.measurement.Metric;
import org.ciedayap.pabmm.pd.requirements.Attribute;
import org.ciedayap.pabmm.pd.requirements.Attributes;
import org.ciedayap.pabmm.pd.requirements.InformationNeed;
import org.ciedayap.utils.StringUtils;
import org.ciedayap.utils.TranslateJSON;

/**
 * This class implements the project definition reading to synthesize the volume 
 * of transmitted data. Data are synthesized based on a few characters delimiting 
 * them with a strict order derived from the available metrics in the project definition. 
 * Complementary data are not supported.
 * 
 * The assumption of the methods is that the project definition contains at least
 * one attribute related to the entity category jointly with at least one 
 * contextual property.
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class Synthesizer {    
    private final CINCAMIPD pd;    
    private final MeasurementProject project;
    private final String projectJSON;
    private final String projectID;
    private final java.util.HashMap<String,Metric> attMetrics;
    private final java.util.HashMap<String,Metric> ctxMetrics;
    
    private transient java.security.MessageDigest md5;

    private final boolean valid;
    
    public Synthesizer(String pd, String projectID) throws SynthesizerException, NoSuchAlgorithmException
    {
        this.projectID=projectID;
        this.pd=stringToProjectDefinition(pd);
        
        MeasurementProject selected=null;
        for(MeasurementProject mp:this.pd.getProjects().getProjects())
        {
            if(mp!=null && mp.getID()!=null && mp.getID().equalsIgnoreCase(projectID))
            {
                selected=mp;
                break;                
            }
        }                
                        
        valid=(selected!=null);
        if(!valid) throw new SynthesizerException("The project has not been found");
        this.project=selected;
        
        attMetrics=new HashMap();
        ctxMetrics=new HashMap();
        processProject();
        projectJSON=TranslateJSON.toJSON(project);
                
        md5=MessageDigest.getInstance("MD5");
    }
    
    /**
     * This receives a project definition under the JSON data format, and it will
     * return it translated to the underlying Object Model
     * 
     * @param pd_json The project definition organized under JSON data format
     * @return a CINCAMIPD instance when the translation is possible, null otherwise
     */
    public final static CINCAMIPD stringToProjectDefinition(String pd_json)
    {
        if(pd_json==null || StringUtils.isEmpty(pd_json)) return null;
        
        CINCAMIPD convertedJSON=(CINCAMIPD) TranslateJSON.toObject(CINCAMIPD.class, pd_json);
        
        return convertedJSON;
    }        
    
    /**
     * It cleans the hash map in memory.
     */
    public void cleaner()
    {
       if(this.ctxMetrics!=null) ctxMetrics.clear();
       if(this.attMetrics!=null) attMetrics.clear();
    }
    
    public void show()
    {
        System.out.println("Project_ID: "+this.getProjectID()+" Valid: "+this.valid);
        
        attMetrics.forEach((k,v)->{System.out.println("K: "+k+"  V: "+v);});
        ctxMetrics.forEach((k,v)->{System.out.println("K: "+k+"  V: "+v);});
    }
    
    /**
     * It processes the project definition discriminating contextual and attribute metric definitions
     * into two hashmap to make easy the verification-
     * @throws SynthesizerException It is raised when some abnormal situation is detected in the project definition.
     */
    private void processProject() throws SynthesizerException
    {
        if(this.project==null) return;
        
        InformationNeed in=project.getInfneed();
        if(in==null)
        {
            throw new SynthesizerException("There is not an information need defined");
        }
        
        if(in.getSpecifiedEC()==null)
        {
            throw new SynthesizerException("There is not an Entity Category defined");
        }
                
        Attributes atts=in.getSpecifiedEC().getDescribedBy();
        if(atts==null || atts.getCharacteristics()==null || atts.getCharacteristics().isEmpty())
            throw new SynthesizerException("There are not attributes");
        
                
        Context ctx=project.getInfneed().getCharacterizedBy();
        if(ctx==null || ctx.getDescribedBy()==null || ctx.getDescribedBy().getContextProperties()==null ||
                ctx.getDescribedBy().getContextProperties().isEmpty())
        {
            throw new SynthesizerException("There is not a context defined");
        }
                
        //Entity Category
        for(Attribute att:atts.getCharacteristics())
        {
            System.out.println("Att: "+att.getName()+" W: "+att.getWeight());
            for(Metric met:att.getQuantifiedBy().getRelated())
            {
                if(StringUtils.isEmpty(att.getID()) || StringUtils.isEmpty(met.getIDmetric()))
                {
                    this.cleaner();
                    throw new SynthesizerException("Invalid Project Definition. Attribute ID: "+att.getID()+
                            " Metric ID: "+met.getIDmetric());
                }
                
                attMetrics.put(met.getIDmetric(), met);
            }
        }
        
        //Context Properties
        for(ContextProperty cp:ctx.getDescribedBy().getContextProperties())
        {
          for(Metric met:cp.getQuantifiedBy().getRelated())
          {
                if(StringUtils.isEmpty(cp.getID()) || StringUtils.isEmpty(met.getIDmetric()))
                {
                    this.cleaner();
                    throw new SynthesizerException("Invalid Project Definition. Context Property ID: "+cp.getID()+
                            " Metric ID: "+met.getIDmetric());
                }

                ctxMetrics.put(met.getIDmetric(), met);
            }              
        }
    }
    
    /**
     * It identifies whether the metric belongs to the attribute or not
     * @param metricID Metric ID to be verified
     * @return TRUE when the metric belongs to the attribute, FALSE otherwise
     */
    public boolean isAttributeMetric(String metricID)            
    {
        if(metricID==null || metricID.trim().length()==0) return false;
        if(this.attMetrics==null || this.attMetrics.isEmpty()) return false;
        
        return attMetrics.containsKey(metricID);        
    }
    
    /**
     * It identifies whether the metric belongs to the context or not
     * @param metricID Metric ID to be verified
     * @return TRUE when the metric belongs to the context, FALSE otherwise
     */
    public boolean isContextMetric(String metricID)            
    {
        if(metricID==null || metricID.trim().length()==0) return false;
        if(this.ctxMetrics==null || this.ctxMetrics.isEmpty()) return false;
        
        return ctxMetrics.containsKey(metricID);        
    }    
    /**
     * It returns the metric definition independently it is associated with the context
     * or the attribute
     * @param metricID The metric ID to be verified
     * @return The metric definition when this is defined into the project, NULL otherwise
     */
    public Metric getDefinition(String metricID)
    {
        if(isAttributeMetric(metricID)) attMetrics.get(metricID);
        if(isContextMetric(metricID)) ctxMetrics.get(metricID);
        
        return null;
    }
    
    /**
     * It converts the hexadecimal to a String representaiton
     * @param bytes The hexadecimal to be converted
     * @return A string representing the hash as a hexadecimal 
     */
    public static String toHexString(byte[] bytes) 
    {
      if(bytes==null || bytes.length==0) return null;

      StringBuilder hexString = new StringBuilder();

      for (byte myByte: bytes) 
      {
          String hex = Integer.toHexString(0xFF & myByte);
          if (hex.length() == 1) hexString.append('0');

          hexString.append(hex);
      }

      return hexString.toString();
    }    
    
    /**
     * It computes a MD5 hash from the brief message
     * @param brief The brief message to be computed
     * @return The hash expressed as a hexadecimal String
     * @throws NoSuchAlgorithmException When MD5 is not defined
     */
    private String computeHash(String brief) throws NoSuchAlgorithmException
    {       
        if(brief==null || brief.trim().length()==0) return null;
        
        if(md5==null) md5=MessageDigest.getInstance("MD5");
        
        
        md5.update(brief.getBytes());
        return toHexString(md5.digest());
    }

    
    public String writeBrief(String briefMessage) throws NoSuchAlgorithmException
    {
        if(briefMessage==null || briefMessage.trim().length()==0) return null;
        
        String md5brief=computeHash(briefMessage);
        String md5project=computeHash(projectJSON);
        StringBuilder sb=new StringBuilder();
        sb.append(md5project).append(".").append(md5brief);
        
        String finalhash=computeHash(sb.toString());
        sb.delete(0, sb.length());
        
        sb.append("{").append(finalhash).append("}").append(briefMessage);
        return sb.toString();
    }
    
    public String readBrief(String completeBrief) throws NoSuchAlgorithmException
    {
        if(completeBrief==null || completeBrief.trim().length()==0) return null;
        
        int s=completeBrief.indexOf("{");
        int e=completeBrief.indexOf("}");
        if(s<0 || e<0) return null;
        String finalHashReceived=(completeBrief.substring(s+1, e));
        if(finalHashReceived==null || finalHashReceived.trim().length()==0) return null;
                
        String rest=completeBrief.substring(e+1, completeBrief.length());
        if(rest==null || rest.trim().length()==0) return null;
        
        String md5brief=computeHash(rest);
        String md5project=computeHash(projectJSON);

        StringBuilder sb=new StringBuilder();
        sb.append(md5project).append(".").append(md5brief);
        
        String finalhashComputed=computeHash(sb.toString());
        sb.delete(0, sb.length());
        
        if(finalhashComputed!=null && finalhashComputed.equalsIgnoreCase(finalHashReceived))
        {
            return rest;
        }        

        return null;        
    }
    
    public ArrayList<String> getAttMetricsID()
    {
        ArrayList<String> list=new ArrayList();
        
        list.addAll(attMetrics.keySet());
        return list;
    }
    
    public ArrayList<String> getCtxMetricsID()
    {
        ArrayList<String> list=new ArrayList();
        
        list.addAll(ctxMetrics.keySet());
        return list;
    }
    
    public static void main(String args[]) throws SynthesizerException, NoSuchAlgorithmException
    {
        String json="{\"IDMessage\":\"1\",\"version\":\"1.0\",\"creation\":\"2020-03-26T07:50:57.05-03:00[America/Argentina/Salta]\",\"projects\":{\"projects\":[{\"ID\":\"PRJ_1\",\"name\":\"Outpatient Monitoring\",\"startDate\":\"2020-03-26T07:50:57.046-03:00[America/Argentina/Salta]\",\"infneed\":{\"ID\":\"IN_1\",\"purpose\":\"Avoid severe damages through the prevention of risks with direct impact in the outpatient health\",\"shortTitle\":\"Monitor the Outpatient\",\"specifiedEC\":{\"ID\":\"EC1\",\"name\":\"Outpatient\",\"superCategory\":{\"describedBy\":{\"characteristics\":[]},\"monitored\":{\"entitiesList\":[]}},\"describedBy\":{\"characteristics\":[{\"ID\":\"ctemp\",\"name\":\"The Corporal Temperature\",\"definition\":\"Value of the axilar temperature in Celsius degree\",\"quantifiedBy\":{\"related\":[{\"IDmetric\":\"dm_ctemp\",\"name\":\"Value of Corporal Temperature\",\"version\":\"1.0\",\"IDAttribute\":\"ctemp\",\"scale\":{\"IDScale\":\"sca_temp\",\"name\":\"Environmental Temperature\\u0027s Scale\",\"scaleType\":\"INTERVAL\",\"expressedIn\":{\"units\":[{\"IDUnit\":\"u_temp\",\"name\":\"Celsius degreee\",\"symbol\":\"C\"}]}},\"sources\":{\"sources\":[{\"dataSourceID\":\"ds_temp\",\"name\":\"Corporal Temperature\\u0027s Sensor\",\"groups\":{\"groups\":[{\"traceGroupID\":\"TG1\",\"name\":\"Peter\\u0027s Galaxy S6\"}]},\"adapters\":{\"adapters\":[{\"dsAdapterID\":\"DSA_1\",\"name\":\"Samsung Galaxy S6\"}]}}]}}]},\"indicator\":{\"modeledBy\":{\"idEM\":\"elmo_corptemp\",\"name\":\"Corporal Temperature\\u0027s Elementary Model \",\"criteria\":{\"criteria\":[{\"idDecisionCriterion\":\"corptemp_normal\",\"name\":\"Corporal Temperature\",\"lowerThreshold\":36.0,\"upperThreshold\":37.1,\"notifiableUnderLowerThreshold\":true,\"nult_message\":\"Warning. The Corporal Temperature is Under 36 celsiud degree\",\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":true,\"naut_message\":\"Warning. The Corporal Temperature is Above 37.1 celsius degree\"}]}},\"indicatorID\":\"ind_corpTemp\",\"name\":\"Level of the Corporal Temperature\",\"weight\":1}},{\"ID\":\"heartrate\",\"name\":\"The Heart Rate\",\"definition\":\"Number of beats per minute (bpm)\",\"quantifiedBy\":{\"related\":[{\"IDmetric\":\"dm_heart\",\"name\":\"Value of Heart Rate\",\"version\":\"1.0\",\"IDAttribute\":\"heartrate\",\"scale\":{\"IDScale\":\"sca_heart\",\"name\":\"Heart Rate\\u0027s Scale\",\"scaleType\":\"RATIO\",\"expressedIn\":{\"units\":[{\"IDUnit\":\"u_heart\",\"name\":\"Beats per minute\",\"symbol\":\"bpm\"}]}},\"sources\":{\"sources\":[{\"dataSourceID\":\"ds_heart\",\"name\":\"Heart Rate\\u0027s Sensor\",\"groups\":{\"groups\":[{\"traceGroupID\":\"TG1\",\"name\":\"Peter\\u0027s Galaxy S6\"}]},\"adapters\":{\"adapters\":[{\"dsAdapterID\":\"DSA_1\",\"name\":\"Samsung Galaxy S6\"}]}}]}}]},\"indicator\":{\"modeledBy\":{\"idEM\":\"elmo_hearttemp\",\"name\":\"Heart Ratee\\u0027s Elementary Model \",\"criteria\":{\"criteria\":[{\"idDecisionCriterion\":\"heartRate_normal\",\"name\":\"Heart Rate\",\"lowerThreshold\":62.0,\"upperThreshold\":75,\"notifiableUnderLowerThreshold\":true,\"nult_message\":\"Warning. The Heart Rate is under than 62 bpm\",\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":true,\"naut_message\":\"Warning. The Heart Rate is upper than 75 bpm\"}]}},\"indicatorID\":\"ind_heartRate\",\"name\":\"Level of the Heart Rate\",\"weight\":1}}]},\"monitored\":{\"entitiesList\":[{\"ID\":\"Ent1\",\"name\":\"Outpatient A (Peter)\",\"relatedTo\":{\"entitiesList\":[]}}]}},\"describedBy\":{\"calculableConcepts\":[{\"ID\":\"calcon1\",\"name\":\"Health\",\"combines\":{\"characteristics\":[]},\"representedBy\":{\"representedList\":[{\"ID\":\"cmod\",\"name\":\"Outpatient Monitoring version 1.0\"}]},\"subconcepts\":{\"calculableConcepts\":[]}}]},\"characterizedBy\":{\"describedBy\":{\"contextProperties\":[{\"related\":{\"contextProperties\":[]},\"ID\":\"pc_humi\",\"name\":\"The Environmental Humidity\",\"definition\":\"Amount of the water vapor in the air\",\"quantifiedBy\":{\"related\":[{\"IDmetric\":\"dm_pc_humi\",\"name\":\"Value of Environmental Humidity\",\"version\":\"1.0\",\"IDAttribute\":\"pc_humi\",\"scale\":{\"IDScale\":\"sca_humi\",\"name\":\"Environmental Humidity\\u0027s Scale\",\"scaleType\":\"INTERVAL\",\"expressedIn\":{\"units\":[{\"IDUnit\":\"u_humi\",\"name\":\"Percentage\",\"symbol\":\"%\"}]}},\"sources\":{\"sources\":[{\"dataSourceID\":\"ds_env_humi\",\"name\":\"Environmental Humidity\\u0027s Sensor\",\"groups\":{\"groups\":[{\"traceGroupID\":\"TG1\",\"name\":\"Peter\\u0027s Galaxy S6\"}]},\"adapters\":{\"adapters\":[{\"dsAdapterID\":\"DSA_1\",\"name\":\"Samsung Galaxy S6\"}]}}]}}]},\"indicator\":{\"modeledBy\":{\"idEM\":\"elmo_humidity\",\"name\":\"Environmental Humidity\\u0027s Elementary Model \",\"criteria\":{\"criteria\":[{\"idDecisionCriterion\":\"humidity_low\",\"name\":\"Low Humidity\",\"lowerThreshold\":0,\"upperThreshold\":40.0,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":false},{\"idDecisionCriterion\":\"humidity_normal\",\"name\":\"Normal Humidity\",\"lowerThreshold\":40.01,\"upperThreshold\":60,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":true,\"naut_message\":\"The Environmental Humidity is upper than 60%\"},{\"idDecisionCriterion\":\"humidity_high\",\"name\":\"High Humidity\",\"lowerThreshold\":60.01,\"upperThreshold\":100,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":true,\"nbt_message\":\"The Environmental Humidity is High\",\"notifiableAboveUpperThreshold\":true,\"naut_message\":\"The Environmental Humidity is High\"}]}},\"indicatorID\":\"ind_env_humidity\",\"name\":\"Level of the Environmental Humidity\",\"weight\":0.34}},{\"related\":{\"contextProperties\":[]},\"ID\":\"pc_temp\",\"name\":\"The Environmental Temperature\",\"definition\":\"Value of the environmental temperature in Celsius degree\",\"quantifiedBy\":{\"related\":[{\"IDmetric\":\"dm_pc_temp\",\"name\":\"Value of Environmental Temperature\",\"version\":\"1.0\",\"IDAttribute\":\"pc_temp\",\"scale\":{\"IDScale\":\"sca_temp\",\"name\":\"Environmental Temperature\\u0027s Scale\",\"scaleType\":\"INTERVAL\",\"expressedIn\":{\"units\":[{\"IDUnit\":\"u_temp\",\"name\":\"Celsius degreee\",\"symbol\":\"C\"}]}},\"sources\":{\"sources\":[{\"dataSourceID\":\"ds_env_temp\",\"name\":\"Environmental Temperature\\u0027s Sensor\",\"groups\":{\"groups\":[{\"traceGroupID\":\"TG1\",\"name\":\"Peter\\u0027s Galaxy S6\"}]},\"adapters\":{\"adapters\":[{\"dsAdapterID\":\"DSA_1\",\"name\":\"Samsung Galaxy S6\"}]}}]}}]},\"indicator\":{\"modeledBy\":{\"idEM\":\"elmo_env_temp\",\"name\":\"Environmental Temperature\\u0027s Elementary Model \",\"criteria\":{\"criteria\":[{\"idDecisionCriterion\":\"temp_low\",\"name\":\"Low Temperature\",\"lowerThreshold\":10.0,\"upperThreshold\":18,\"notifiableUnderLowerThreshold\":true,\"nult_message\":\"The Environmental Temperature is under 10 celsius degree\",\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":false},{\"idDecisionCriterion\":\"temp_normal\",\"name\":\"Normal Temperature\",\"lowerThreshold\":18.01,\"upperThreshold\":29,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":false},{\"idDecisionCriterion\":\"temp_high\",\"name\":\"High Temperature\",\"lowerThreshold\":29.01,\"upperThreshold\":36,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":true,\"nbt_message\":\"Warning. High Temperature\",\"notifiableAboveUpperThreshold\":true,\"naut_message\":\"Alert. Very High Temperature\"}]}},\"indicatorID\":\"ind_env_temp\",\"name\":\"Level of the Environmental Temperature\",\"weight\":0.33}},{\"related\":{\"contextProperties\":[]},\"ID\":\"pc_press\",\"name\":\"The Environmental Pressure\",\"definition\":\"Pressures resulting from human activities which bring about changes in the state of the environment\",\"quantifiedBy\":{\"related\":[{\"IDmetric\":\"dm_pc_press\",\"name\":\"Value of Environmental Pressure\",\"version\":\"1.0\",\"IDAttribute\":\"pc_press\",\"scale\":{\"IDScale\":\"sca_press\",\"name\":\"Environmental Pressure\\u0027s Scale\",\"scaleType\":\"RATIO\",\"expressedIn\":{\"units\":[{\"IDUnit\":\"u_press\",\"name\":\"Hectopascals\",\"symbol\":\"hPa\"}]}},\"sources\":{\"sources\":[{\"dataSourceID\":\"ds_env_press\",\"name\":\"Environmental Pressure\\u0027s Sensor\",\"groups\":{\"groups\":[{\"traceGroupID\":\"TG1\",\"name\":\"Peter\\u0027s Galaxy S6\"}]},\"adapters\":{\"adapters\":[{\"dsAdapterID\":\"DSA_1\",\"name\":\"Samsung Galaxy S6\"}]}}]}}]},\"indicator\":{\"modeledBy\":{\"idEM\":\"elmo_env_press\",\"name\":\"Environmental Pressure\\u0027s Elementary Model \",\"criteria\":{\"criteria\":[{\"idDecisionCriterion\":\"press_normal\",\"name\":\"Normal Enviromental Pressure\",\"lowerThreshold\":900.0,\"upperThreshold\":1100,\"notifiableUnderLowerThreshold\":false,\"notifiableBetweenThreshold\":false,\"notifiableAboveUpperThreshold\":true}]}},\"indicatorID\":\"ind_env_press\",\"name\":\"Level of the Environmental Pressure\",\"weight\":0.33}}]},\"ID\":\"ctx_outpatient\",\"name\":\"The Outpatient Context\",\"relatedTo\":{\"entitiesList\":[]}}},\"lastChange\":\"2020-03-26T07:50:57.046-03:00[America/Argentina/Salta]\"}]}}";
        Synthesizer s=new Synthesizer(json,"PRJ_1");
        s.show();
        
        
        String brief="{dsadap1}{PRJ1;EC1;IDEntity1;dsid_1}{2020-03-30T12:29:06.913-03:00[America/Argentina/Salta];idMetric1}(0.23506366996956274;0.3333333333333333333333333333333333)(-0.41985748118642735;0.3333333333333333333333333333333333)(-0.8885070748712927;0.3333333333333333333333333333333333)*{PRJ1;EC1;IDEntity1;dsid_1}{2020-03-30T12:29:07.021-03:00[America/Argentina/Salta];idMetric1}(0.23506366996956274;0.3333333333333333333333333333333333)(-0.41985748118642735;0.3333333333333333333333333333333333)(-0.8885070748712927;0.3333333333333333333333333333333333)*{PRJ1;EC1;IDEntity1;dsid_1}{2020-03-30T12:29:07.033-03:00[America/Argentina/Salta];idMetric1}(0.23506366996956274;0.3333333333333333333333333333333333)(-0.41985748118642735;0.3333333333333333333333333333333333)(-0.8885070748712927;0.3333333333333333333333333333333333)*{PRJ1;EC1;IDEntity1;dsid_1}{2020-03-30T12:29:07.042-03:00[America/Argentina/Salta];idMetric1}(0.23506366996956274;0.3333333333333333333333333333333333)(-0.41985748118642735;0.3333333333333333333333333333333333)(-0.8885070748712927;0.3333333333333333333333333333333333)*{PRJ1;EC1;IDEntity1;dsid_1}{2020-03-30T12:29:07.051-03:00[America/Argentina/Salta];idMetric1}(0.23506366996956274;0.3333333333333333333333333333333333)(-0.41985748118642735;0.3333333333333333333333333333333333)(-0.8885070748712927;0.3333333333333333333333333333333333)*";
        String out=s.writeBrief(brief);
        System.out.println(out);
        
        String received=s.readBrief(out);
        System.out.println(received);
    }

    /**
     * @return the projectID
     */
    public String getProjectID() {
        return projectID;
    }
    
    public String getEntityCategory()
    {
        if(pd==null) return null;
        
        for(MeasurementProject prj:pd.getProjects().getProjects())
        {
            if(prj.getID().equalsIgnoreCase(projectID))
            {
                return prj.getInfneed().getSpecifiedEC().getID();
            }
        }
        
        return null;
    }
}
