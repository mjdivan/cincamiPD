/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author mjdivan
 */
public class Tokens {
    public static String startLevel="{";
    public static String endLevel="}";
    public static String fieldSeparator=";";
    public final static String CLASS_ID_DELIMITER="|";
    public final static String FINGERPRINT_ALGORITHM="MD5";
    
    public static String Class_ID_IPD=CLASS_ID_DELIMITER+"IPD"+CLASS_ID_DELIMITER;
    public static String Class_ID_MeasurementProject=CLASS_ID_DELIMITER+"MPR"+CLASS_ID_DELIMITER;
    public static String Class_ID_MeasurementProjects=CLASS_ID_DELIMITER+"MPS"+CLASS_ID_DELIMITER;
    public static String Class_ID_InformationNeed=CLASS_ID_DELIMITER+"INF"+CLASS_ID_DELIMITER;
    public static String Class_ID_DataSource=CLASS_ID_DELIMITER+"DS"+CLASS_ID_DELIMITER;
    public static String Class_ID_DataSources=CLASS_ID_DELIMITER+"DSS"+CLASS_ID_DELIMITER;
    public static String Class_ID_Constraint=CLASS_ID_DELIMITER+"CO"+CLASS_ID_DELIMITER;
    public static String Class_ID_Constraints=CLASS_ID_DELIMITER+"COS"+CLASS_ID_DELIMITER;
    public static String Class_ID_Scale=CLASS_ID_DELIMITER+"SCA"+CLASS_ID_DELIMITER;
    public static String Class_ID_Metric=CLASS_ID_DELIMITER+"ME"+CLASS_ID_DELIMITER;
    public static String Class_ID_Metrics=CLASS_ID_DELIMITER+"MES"+CLASS_ID_DELIMITER;
    public static String Class_ID_StateTransition=CLASS_ID_DELIMITER+"ST"+CLASS_ID_DELIMITER;
    public static String Class_ID_Transitions=CLASS_ID_DELIMITER+"TRA"+CLASS_ID_DELIMITER;
    public static String Class_ID_StateTransitionModel=CLASS_ID_DELIMITER+"STM"+CLASS_ID_DELIMITER;
    public static String Class_ID_ScenarioProperty=CLASS_ID_DELIMITER+"SP"+CLASS_ID_DELIMITER;
    public static String Class_ID_ScenarioProperties=CLASS_ID_DELIMITER+"SPS"+CLASS_ID_DELIMITER;    
    public static String Class_ID_Scenario=CLASS_ID_DELIMITER+"SCE"+CLASS_ID_DELIMITER;
    public static String Class_ID_Scenarios=CLASS_ID_DELIMITER+"SNS"+CLASS_ID_DELIMITER;    
    public static String Class_ID_ECMetadata=CLASS_ID_DELIMITER+"EC"+CLASS_ID_DELIMITER;
    public static String Class_ID_ECStateProperties=CLASS_ID_DELIMITER+"ECS"+CLASS_ID_DELIMITER;
    public static String Class_ID_ECState=CLASS_ID_DELIMITER+"EST"+CLASS_ID_DELIMITER;    
    public static String Class_ID_ECStates=CLASS_ID_DELIMITER+"STS"+CLASS_ID_DELIMITER;   
    public static String Class_ID_Actionable=CLASS_ID_DELIMITER+"ALE"+CLASS_ID_DELIMITER;   
    public static String Class_ID_Actionables=CLASS_ID_DELIMITER+"ALS"+CLASS_ID_DELIMITER;   
    public static String Class_ID_Recommender=CLASS_ID_DELIMITER+"REC"+CLASS_ID_DELIMITER;
    public static String Class_ID_DecisionCriterion=CLASS_ID_DELIMITER+"DC"+CLASS_ID_DELIMITER;
    public static String Class_ID_Range=CLASS_ID_DELIMITER+"RGE"+CLASS_ID_DELIMITER;
    public static String Class_ID_DecisionCriteria=CLASS_ID_DELIMITER+"DCA"+CLASS_ID_DELIMITER;
    public static String Class_ID_WeightedIndicator=CLASS_ID_DELIMITER+"WIN"+CLASS_ID_DELIMITER;
    public static String Class_ID_WeightedIndicators=CLASS_ID_DELIMITER+"WIS"+CLASS_ID_DELIMITER;
    public static String Class_ID_Values=CLASS_ID_DELIMITER+"VUS"+CLASS_ID_DELIMITER;
    public static String Class_ID_Attribute=CLASS_ID_DELIMITER+"ATT"+CLASS_ID_DELIMITER;
    public static String Class_ID_Attributes=CLASS_ID_DELIMITER+"ATS"+CLASS_ID_DELIMITER;
    public static String Class_ID_CtxProperty=CLASS_ID_DELIMITER+"CP"+CLASS_ID_DELIMITER;
    public static String Class_ID_CtxProperties=CLASS_ID_DELIMITER+"CPS"+CLASS_ID_DELIMITER;
    public static String Class_ID_Context=CLASS_ID_DELIMITER+"CTX"+CLASS_ID_DELIMITER;
    public static String Class_ID_Entity=CLASS_ID_DELIMITER+"ENT"+CLASS_ID_DELIMITER;    
    public static String Class_ID_Entities=CLASS_ID_DELIMITER+"ETS"+CLASS_ID_DELIMITER;    
    public static String Class_ID_EntityCategory=CLASS_ID_DELIMITER+"ECT"+CLASS_ID_DELIMITER;    
    
    public static String removeTokens(String text)
    {
        if(StringUtils.isEmpty(text)) return null;
        
        text=text.trim();
        text=text.replace(startLevel, "");
        text=text.replace(endLevel, "");
        text=text.replace(fieldSeparator, "");        
        text=text.replace(CLASS_ID_DELIMITER,"");
        
        return text;
    }
    
    public static String getFingerprint(String o)
    {
        if(StringUtils.isEmpty(o)) return null;
        MessageDigest md;
        
        try {
            md=MessageDigest.getInstance(Tokens.FINGERPRINT_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        
        return StringUtils.toHexString(md.digest(o.getBytes()));                
    }

}
