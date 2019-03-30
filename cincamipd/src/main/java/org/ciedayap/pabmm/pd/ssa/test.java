/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

import java.math.BigDecimal;
import org.ciedayap.pabmm.pd.evaluation.DecisionCriteria;
import org.ciedayap.pabmm.pd.evaluation.DecisionCriterion;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;
import org.ciedayap.utils.ZipUtil;

/**
 *
 * @author mjdivan
 */
public class test {
    public static void main(String args[]) throws SSAException, EntityPDException, Exception
    {
        //generatingSSAMessage();
        simulatingSSAMessages(50,50,20);
    }
    
    public static void generatingSSAMessage() throws SSAException, EntityPDException, Exception
    {        
        //Defining Scenario Properties for the Environmental Temperature -> Hot [+35Cº] Normal [20-34] Cold (<20
        ScenarioProperty et_hot=ScenarioProperty.create("cp_1.1", "Environmental Temperature -Hot-", 
                Range.create(new BigDecimal(35), true, new BigDecimal(50), false));        
        ScenarioProperty et_normal=ScenarioProperty.create("cp_1.2", "Environmental Temperature -Normal-", 
                Range.create(new BigDecimal(20), true, new BigDecimal(34), true));
        ScenarioProperty et_cold=ScenarioProperty.create("cp_1.3", "Environmental Temperature -Cold-", 
                Range.create(new BigDecimal(-10), true, new BigDecimal(20), false));
        ScenarioProperty eh_high=ScenarioProperty.create("cp_2.1", "Environmental Humidity -High-", 
                Range.create(new BigDecimal(60), true, new BigDecimal(100), true));        
        ScenarioProperty eh_normal=ScenarioProperty.create("cp_2.2", "Environmental Humidity -Normal-", 
                Range.create(new BigDecimal(40), true, new BigDecimal(60), false));
        ScenarioProperty eh_low=ScenarioProperty.create("cp_2.3", "Environmental Humidity -Low-", 
                Range.create(new BigDecimal(0), true, new BigDecimal(40), false));                        
        
        //Scenario: Hot - High Humidity
        StateTransitionModel transitions1=StateTransitionModel.create();
        ScenarioProperties hotHigh=ScenarioProperties.create();
        hotHigh.getProperties().add(et_hot);
        hotHigh.getProperties().add(eh_high);
        
        //Scenario: Hot - Normal Humidity
        StateTransitionModel transitions2=StateTransitionModel.create();
        ScenarioProperties hotNorm=ScenarioProperties.create();
        hotNorm.getProperties().add(et_hot);
        hotNorm.getProperties().add(eh_normal);
        
        //Scenario: Hot - Low Humidity
        StateTransitionModel transitions3=StateTransitionModel.create();
        ScenarioProperties hotLow=ScenarioProperties.create();
        hotLow.getProperties().add(et_hot);
        hotLow.getProperties().add(eh_low);

        //Scenario: Normal - High Humidity
        StateTransitionModel transitions4=StateTransitionModel.create();
        ScenarioProperties normalHigh=ScenarioProperties.create();
        normalHigh.getProperties().add(et_normal);
        normalHigh.getProperties().add(eh_high);
        
        //Scenario: Normal - Normal Humidity
        StateTransitionModel transitions5=StateTransitionModel.create();
        ScenarioProperties normalNorm=ScenarioProperties.create();
        normalNorm.getProperties().add(et_normal);
        normalNorm.getProperties().add(eh_normal);
        
        //Scenario: Normal - Low Humidity
        StateTransitionModel transitions6=StateTransitionModel.create();
        ScenarioProperties normalLow=ScenarioProperties.create();
        normalLow.getProperties().add(et_normal);
        normalLow.getProperties().add(eh_low);
        
        //Scenario: Cold - High Humidity
        StateTransitionModel transitions7=StateTransitionModel.create();
        ScenarioProperties coldHigh=ScenarioProperties.create();
        coldHigh.getProperties().add(et_cold);
        coldHigh.getProperties().add(eh_high);
        
        //Scenario: Cold - Normal Humidity
        StateTransitionModel transitions8=StateTransitionModel.create();
        ScenarioProperties coldNorm=ScenarioProperties.create();
        coldNorm.getProperties().add(et_cold);
        coldNorm.getProperties().add(eh_normal);

        
        //Scenario: Cold - Low Humidity
        StateTransitionModel transitions9=StateTransitionModel.create();
        ScenarioProperties coldLow=ScenarioProperties.create();
        coldLow.getProperties().add(et_normal);
        coldLow.getProperties().add(eh_low);
        
        //Defining the transitions (all with all)        
        for(int i=1;i<=9;i++)
        {
            for(int j=1;j<=9;j++)
            {
                if(i!=j)
                {
                    StateTransition tr=StateTransition.create(Integer.toString(i), Integer.toString(j), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
                    switch(i)
                    {
                        case 1:
                            transitions1.getTransitions().add(tr);
                            break;
                        case 2:
                            transitions2.getTransitions().add(tr);
                            break;                            
                        case 3:
                            transitions3.getTransitions().add(tr);
                            break;                            
                        case 4:
                            transitions4.getTransitions().add(tr);
                            break;                            
                        case 5:
                            transitions5.getTransitions().add(tr);
                            break;                            
                        case 6:
                            transitions6.getTransitions().add(tr);
                            break;                            
                        case 7:
                            transitions7.getTransitions().add(tr);
                            break;                            
                        case 8:
                            transitions8.getTransitions().add(tr);
                            break;                            
                        case 9:
                            transitions9.getTransitions().add(tr);
                            break;                            
                    }
                    
                }                
            }
        }
       
        //Incorporating the Scenarios
        Scenario scenario_hotHigh=Scenario.create("1", "Hot - High Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotHigh, transitions1);
        Scenario scenario_hotNormal=Scenario.create("2", "Hot - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotNorm, transitions2);
        Scenario scenario_hotLow=Scenario.create("3", "Hot - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotLow, transitions3);
        Scenario scenario_normalHigh=Scenario.create("4", "Hot - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalHigh, transitions4);
        Scenario scenario_normalNormal=Scenario.create("5", "Normal - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalNorm, transitions5);    
        Scenario scenario_normalLow=Scenario.create("6", "Normal - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalLow, transitions6);
        Scenario scenario_coldHigh=Scenario.create("7", "Cold - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldHigh, transitions7);  
        Scenario scenario_coldNormal=Scenario.create("8", "Cold - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldNorm, transitions8); 
        Scenario scenario_coldLow=Scenario.create("9", "Cold - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldLow, transitions9);        
        
        Scenarios myScenarios=Scenarios.create();
        myScenarios.getScenarios().add(scenario_hotHigh);
        myScenarios.getScenarios().add(scenario_hotNormal);
        myScenarios.getScenarios().add(scenario_hotLow);
        myScenarios.getScenarios().add(scenario_normalHigh);
        myScenarios.getScenarios().add(scenario_normalNormal);
        myScenarios.getScenarios().add(scenario_normalLow);
        myScenarios.getScenarios().add(scenario_coldHigh);
        myScenarios.getScenarios().add(scenario_coldNormal);
        myScenarios.getScenarios().add(scenario_coldHigh);
        
        //Defining the Attributes
        StateTransitionModel transitions_ecm_1=StateTransitionModel.create();
        ECMetadata ecm_age_yp=ECMetadata.create("ecm_1", "Young People", Range.create(BigDecimal.ZERO, true, new BigDecimal(25), true));
        ECStateProperties yp=ECStateProperties.create();
        yp.getStateItems().add(ecm_age_yp); 
        
        StateTransitionModel transitions_ecm_2=StateTransitionModel.create();
        ECMetadata ecm_age_adult=ECMetadata.create("ecm_2", "Adult", Range.create(new BigDecimal(25), false, new BigDecimal(60), true));
        ECStateProperties adult=ECStateProperties.create();
        adult.getStateItems().add(ecm_age_adult);   
        
        StateTransitionModel transitions_ecm_3=StateTransitionModel.create();
        ECMetadata ecm_age_elderly=ECMetadata.create("ecm_3", "Elderly People", Range.create(new BigDecimal(60), false, new BigDecimal(100), true));
        ECStateProperties elderly=ECStateProperties.create();
        elderly.getStateItems().add(ecm_age_elderly); 
        
         //Defining the transitions (all with all)        
        for(int i=1;i<=3;i++)
        {
            for(int j=1;j<=3;j++)
            {
                if(i!=j)
                {
                    StateTransition tr=StateTransition.create("EUM_"+Integer.toString(i), "EUM_"+Integer.toString(j), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
                    switch(i)
                    {
                        case 1:
                            transitions_ecm_1.getTransitions().add(tr);
                            break;
                        case 2:
                            transitions_ecm_2.getTransitions().add(tr);
                            break;                            
                        case 3:
                            transitions_ecm_3.getTransitions().add(tr);
                            break;                            
                    }
                }
            }
        }
        
        //Incorporating the ECStates
        ECState stateYP=ECState.create("EUM_1", "Young People", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_1, yp);
        ECState stateADULT=ECState.create("EUM_2", "Adult", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_2, adult);         
        ECState stateELDERLY=ECState.create("EUM_3", "Elderly People", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_3, elderly);        
        
        ECStates myStates=ECStates.create();
        myStates.getEcstates().add(stateYP);
        myStates.getEcstates().add(stateADULT);
        myStates.getEcstates().add(stateELDERLY);
        
        WeightedIndicators wi=WeightedIndicators.create();
        for(int i=1;i<=9;i++)
            for(int j=1;j<=3;j++)
            {
                WeightedIndicator wione=WeightedIndicator.create();
                wione.setEcstateID("EUM_TEMP_"+j);
                wione.setScenarioID(Integer.toString(i));
                wione.setIndicatorID("LCT");//Level of the Corporal Temperature
                wione.setWeight(new BigDecimal(0.4));
                wione.setParameter(BigDecimal.ZERO);
                
                DecisionCriterion criterion_wione=DecisionCriterion.create("dc_"+i+"_"+j, "Scenario: "+i+" State:"+j, BigDecimal.ZERO, BigDecimal.TEN);
                DecisionCriteria criteria_wione=new DecisionCriteria();
                criteria_wione.getCriteria().add(criterion_wione);                
                wione.setCriteria(criteria_wione);
                wi.getWeightedIndicators().add(wione);
                
                WeightedIndicator witwo=WeightedIndicator.create();
                witwo.setEcstateID("EUM_HR_"+j);
                witwo.setScenarioID(Integer.toString(i));
                witwo.setIndicatorID("LHR");//Level of the Heart Rate
                witwo.setWeight(new BigDecimal(0.6));
                witwo.setParameter(BigDecimal.ZERO);
                
                DecisionCriterion criterion_witwo=DecisionCriterion.create("dc_"+i+"_"+j, "Scenario: "+i+" State:"+j, BigDecimal.ZERO, BigDecimal.TEN);
                DecisionCriteria criteria_witwo=new DecisionCriteria();
                criteria_witwo.getCriteria().add(criterion_witwo);                
                witwo.setCriteria(criteria_witwo);
                wi.getWeightedIndicators().add(witwo);                
            }
        
        SSAProject myProject=SSAProject.create("pid_one", myScenarios, myStates, wi);
        SSAProjects projects=SSAProjects.create();
        projects.getProjects().add(myProject);
        CINCAMIPD_SSA message=CINCAMIPD_SSA.create(projects);
        
        if(message==null) System.out.println("Mensaje Nulo");
        
        String xml=TranslateXML.toXml(message);    
        

        byte[] compGZIP=ZipUtil.compressGZIP(xml);
            
        String xmlD=ZipUtil.decompressGZIP(compGZIP);

        System.out.println(xmlD);
        
    }
    
    public static void simulatingSSAMessages(int start, int increment, int times) throws SSAException, EntityPDException, Exception
    {                        
        //Defining Scenario Properties for the Environmental Temperature -> Hot [+35Cº] Normal [20-34] Cold (<20
        ScenarioProperty et_hot=ScenarioProperty.create("cp_1.1", "Environmental Temperature -Hot-", 
                Range.create(new BigDecimal(35), true, new BigDecimal(50), false));        
        ScenarioProperty et_normal=ScenarioProperty.create("cp_1.2", "Environmental Temperature -Normal-", 
                Range.create(new BigDecimal(20), true, new BigDecimal(34), true));
        ScenarioProperty et_cold=ScenarioProperty.create("cp_1.3", "Environmental Temperature -Cold-", 
                Range.create(new BigDecimal(-10), true, new BigDecimal(20), false));
        ScenarioProperty eh_high=ScenarioProperty.create("cp_2.1", "Environmental Humidity -High-", 
                Range.create(new BigDecimal(60), true, new BigDecimal(100), true));        
        ScenarioProperty eh_normal=ScenarioProperty.create("cp_2.2", "Environmental Humidity -Normal-", 
                Range.create(new BigDecimal(40), true, new BigDecimal(60), false));
        ScenarioProperty eh_low=ScenarioProperty.create("cp_2.3", "Environmental Humidity -Low-", 
                Range.create(new BigDecimal(0), true, new BigDecimal(40), false));                        
        
        //Scenario: Hot - High Humidity
        StateTransitionModel transitions1=StateTransitionModel.create();
        ScenarioProperties hotHigh=ScenarioProperties.create();
        hotHigh.getProperties().add(et_hot);
        hotHigh.getProperties().add(eh_high);
        
        //Scenario: Hot - Normal Humidity
        StateTransitionModel transitions2=StateTransitionModel.create();
        ScenarioProperties hotNorm=ScenarioProperties.create();
        hotNorm.getProperties().add(et_hot);
        hotNorm.getProperties().add(eh_normal);
        
        //Scenario: Hot - Low Humidity
        StateTransitionModel transitions3=StateTransitionModel.create();
        ScenarioProperties hotLow=ScenarioProperties.create();
        hotLow.getProperties().add(et_hot);
        hotLow.getProperties().add(eh_low);

        //Scenario: Normal - High Humidity
        StateTransitionModel transitions4=StateTransitionModel.create();
        ScenarioProperties normalHigh=ScenarioProperties.create();
        normalHigh.getProperties().add(et_normal);
        normalHigh.getProperties().add(eh_high);
        
        //Scenario: Normal - Normal Humidity
        StateTransitionModel transitions5=StateTransitionModel.create();
        ScenarioProperties normalNorm=ScenarioProperties.create();
        normalNorm.getProperties().add(et_normal);
        normalNorm.getProperties().add(eh_normal);
        
        //Scenario: Normal - Low Humidity
        StateTransitionModel transitions6=StateTransitionModel.create();
        ScenarioProperties normalLow=ScenarioProperties.create();
        normalLow.getProperties().add(et_normal);
        normalLow.getProperties().add(eh_low);
        
        //Scenario: Cold - High Humidity
        StateTransitionModel transitions7=StateTransitionModel.create();
        ScenarioProperties coldHigh=ScenarioProperties.create();
        coldHigh.getProperties().add(et_cold);
        coldHigh.getProperties().add(eh_high);
        
        //Scenario: Cold - Normal Humidity
        StateTransitionModel transitions8=StateTransitionModel.create();
        ScenarioProperties coldNorm=ScenarioProperties.create();
        coldNorm.getProperties().add(et_cold);
        coldNorm.getProperties().add(eh_normal);

        
        //Scenario: Cold - Low Humidity
        StateTransitionModel transitions9=StateTransitionModel.create();
        ScenarioProperties coldLow=ScenarioProperties.create();
        coldLow.getProperties().add(et_normal);
        coldLow.getProperties().add(eh_low);
        
        //Defining the transitions (all with all)        
        for(int i=1;i<=9;i++)
        {
            for(int j=1;j<=9;j++)
            {
                if(i!=j)
                {
                    StateTransition tr=StateTransition.create(Integer.toString(i), Integer.toString(j), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
                    switch(i)
                    {
                        case 1:
                            transitions1.getTransitions().add(tr);
                            break;
                        case 2:
                            transitions2.getTransitions().add(tr);
                            break;                            
                        case 3:
                            transitions3.getTransitions().add(tr);
                            break;                            
                        case 4:
                            transitions4.getTransitions().add(tr);
                            break;                            
                        case 5:
                            transitions5.getTransitions().add(tr);
                            break;                            
                        case 6:
                            transitions6.getTransitions().add(tr);
                            break;                            
                        case 7:
                            transitions7.getTransitions().add(tr);
                            break;                            
                        case 8:
                            transitions8.getTransitions().add(tr);
                            break;                            
                        case 9:
                            transitions9.getTransitions().add(tr);
                            break;                            
                    }
                    
                }                
            }
        }
       
        //Incorporating the Scenarios
        Scenario scenario_hotHigh=Scenario.create("1", "Hot - High Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotHigh, transitions1);
        Scenario scenario_hotNormal=Scenario.create("2", "Hot - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotNorm, transitions2);
        Scenario scenario_hotLow=Scenario.create("3", "Hot - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, hotLow, transitions3);
        Scenario scenario_normalHigh=Scenario.create("4", "Hot - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalHigh, transitions4);
        Scenario scenario_normalNormal=Scenario.create("5", "Normal - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalNorm, transitions5);    
        Scenario scenario_normalLow=Scenario.create("6", "Normal - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, normalLow, transitions6);
        Scenario scenario_coldHigh=Scenario.create("7", "Cold - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldHigh, transitions7);  
        Scenario scenario_coldNormal=Scenario.create("8", "Cold - Normal Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldNorm, transitions8); 
        Scenario scenario_coldLow=Scenario.create("9", "Cold - Low Humidity", BigDecimal.ZERO, BigDecimal.ZERO, coldLow, transitions9);        
        
        Scenarios myScenarios=Scenarios.create();
        myScenarios.getScenarios().add(scenario_hotHigh);
        myScenarios.getScenarios().add(scenario_hotNormal);
        myScenarios.getScenarios().add(scenario_hotLow);
        myScenarios.getScenarios().add(scenario_normalHigh);
        myScenarios.getScenarios().add(scenario_normalNormal);
        myScenarios.getScenarios().add(scenario_normalLow);
        myScenarios.getScenarios().add(scenario_coldHigh);
        myScenarios.getScenarios().add(scenario_coldNormal);
        myScenarios.getScenarios().add(scenario_coldHigh);
        
        //Defining the Attributes
        StateTransitionModel transitions_ecm_1=StateTransitionModel.create();
        ECMetadata ecm_age_yp=ECMetadata.create("ecm_1", "Young People", Range.create(BigDecimal.ZERO, true, new BigDecimal(25), true));
        ECStateProperties yp=ECStateProperties.create();
        yp.getStateItems().add(ecm_age_yp); 
        
        StateTransitionModel transitions_ecm_2=StateTransitionModel.create();
        ECMetadata ecm_age_adult=ECMetadata.create("ecm_2", "Adult", Range.create(new BigDecimal(25), false, new BigDecimal(60), true));
        ECStateProperties adult=ECStateProperties.create();
        adult.getStateItems().add(ecm_age_adult);   
        
        StateTransitionModel transitions_ecm_3=StateTransitionModel.create();
        ECMetadata ecm_age_elderly=ECMetadata.create("ecm_3", "Elderly People", Range.create(new BigDecimal(60), false, new BigDecimal(100), true));
        ECStateProperties elderly=ECStateProperties.create();
        elderly.getStateItems().add(ecm_age_elderly); 
        
         //Defining the transitions (all with all)        
        for(int i=1;i<=3;i++)
        {
            for(int j=1;j<=3;j++)
            {
                if(i!=j)
                {
                    StateTransition tr=StateTransition.create("EUM_"+Integer.toString(i), "EUM_"+Integer.toString(j), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
                    switch(i)
                    {
                        case 1:
                            transitions_ecm_1.getTransitions().add(tr);
                            break;
                        case 2:
                            transitions_ecm_2.getTransitions().add(tr);
                            break;                            
                        case 3:
                            transitions_ecm_3.getTransitions().add(tr);
                            break;                            
                    }
                }
            }
        }
        
        //Incorporating the ECStates
        ECState stateYP=ECState.create("EUM_1", "Young People", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_1, yp);
        ECState stateADULT=ECState.create("EUM_2", "Adult", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_2, adult);         
        ECState stateELDERLY=ECState.create("EUM_3", "Elderly People", BigDecimal.ZERO, BigDecimal.ZERO, transitions_ecm_3, elderly);        
        
        ECStates myStates=ECStates.create();
        myStates.getEcstates().add(stateYP);
        myStates.getEcstates().add(stateADULT);
        myStates.getEcstates().add(stateELDERLY);
        
        WeightedIndicators wi=WeightedIndicators.create();
        for(int i=1;i<=9;i++)
            for(int j=1;j<=3;j++)
            {
                WeightedIndicator wione=WeightedIndicator.create();
                wione.setEcstateID("EUM_TEMP_"+j);
                wione.setScenarioID(Integer.toString(i));
                wione.setIndicatorID("LCT");//Level of the Corporal Temperature
                wione.setWeight(new BigDecimal(0.4));
                wione.setParameter(BigDecimal.ZERO);
                
                DecisionCriterion criterion_wione=DecisionCriterion.create("dc_"+i+"_"+j, "Scenario: "+i+" State:"+j, BigDecimal.ZERO, BigDecimal.TEN);
                DecisionCriteria criteria_wione=new DecisionCriteria();
                criteria_wione.getCriteria().add(criterion_wione);                
                wione.setCriteria(criteria_wione);
                wi.getWeightedIndicators().add(wione);
                
                WeightedIndicator witwo=WeightedIndicator.create();
                witwo.setEcstateID("EUM_HR_"+j);
                witwo.setScenarioID(Integer.toString(i));
                witwo.setIndicatorID("LHR");//Level of the Heart Rate
                witwo.setWeight(new BigDecimal(0.6));
                witwo.setParameter(BigDecimal.ZERO);
                
                DecisionCriterion criterion_witwo=DecisionCriterion.create("dc_"+i+"_"+j, "Scenario: "+i+" State:"+j, BigDecimal.ZERO, BigDecimal.TEN);
                DecisionCriteria criteria_witwo=new DecisionCriteria();
                criteria_witwo.getCriteria().add(criterion_witwo);                
                witwo.setCriteria(criteria_witwo);
                wi.getWeightedIndicators().add(witwo);                
            }
        
        
        System.out.println("projects;translateToXML;extendedXMLSize;compressedXMLTime;compressedXMLSize;decompressXMLTime;translateFromXML;"+
                "translateToJSON;extendedJSONSize;compressedJSONTime;compressedJSONSize;decompressJSONTime;translateFromJSON");

        int q=times;
        for(int i=start;i<=(start*times); i+=start )
        {            
            SSAProjects projects=SSAProjects.create();
            
            for(int j=0;j<i;j++)
            {
                SSAProject myProject=SSAProject.create("pid_one_"+j, myScenarios, myStates, wi);
                projects.getProjects().add(myProject);
            }
            
            CINCAMIPD_SSA message=CINCAMIPD_SSA.create(projects);

            //XML
            long translateToXML;
            long extendedXMLSize;
            long compressedXMLTime;
            long compressedXMLSize;            
            long decompressXMLTime;
            long translateFromXML;            
            
            long before=System.nanoTime();
            String xml=TranslateXML.toXml(message);    
            long after=System.nanoTime();
            translateToXML=after-before;//1
            extendedXMLSize=xml.getBytes().length;//2
            
            before=System.nanoTime();
            byte[] compGZIP=ZipUtil.compressGZIP(xml);
            after=System.nanoTime();
            compressedXMLTime=after-before;//3
            compressedXMLSize=compGZIP.length;//4
            
            before=System.nanoTime();
            String xmlD=ZipUtil.decompressGZIP(compGZIP);
            after=System.nanoTime();
            decompressXMLTime=after-before;//5
            
            before=System.nanoTime();
            CINCAMIPD_SSA message2=(CINCAMIPD_SSA) TranslateXML.toObject(CINCAMIPD_SSA.class, xml);
            after=System.nanoTime();
            translateFromXML=after-before;//6
            
            //JSON
            long translateToJSON;
            long extendedJSONSize;
            long compressedJSONTime;
            long compressedJSONSize;            
            long decompressJSONTime;
            long translateFromJSON;            
            
            before=System.nanoTime();
            String json=TranslateJSON.toJSON(message);
            after=System.nanoTime();
            translateToJSON=after-before;//1
            extendedJSONSize=json.getBytes().length;//2
            
            before=System.nanoTime();
            byte[] compGZIPjson=ZipUtil.compressGZIP(json);
            after=System.nanoTime();
            compressedJSONTime=after-before;//3
            compressedJSONSize=compGZIPjson.length;//4
            
            before=System.nanoTime();
            String jsonD=ZipUtil.decompressGZIP(compGZIPjson);
            after=System.nanoTime();
            decompressJSONTime=after-before;//5
            
            before=System.nanoTime();
            CINCAMIPD_SSA message3=(CINCAMIPD_SSA) TranslateJSON.toObject(CINCAMIPD_SSA.class, json);
            after=System.nanoTime();
            translateFromJSON=after-before;//6         
            
            StringBuilder sb=new StringBuilder();
            sb.append(i).append(";");
            sb.append(translateToXML).append(";");
            sb.append(extendedXMLSize).append(";");
            sb.append(compressedXMLTime).append(";");
            sb.append(compressedXMLSize).append(";");
            sb.append(decompressXMLTime).append(";");
            sb.append(translateFromXML).append(";");
            sb.append(translateToJSON).append(";");
            sb.append(extendedJSONSize).append(";");
            sb.append(compressedJSONTime).append(";");
            sb.append(compressedJSONSize).append(";");
            sb.append(decompressJSONTime).append(";");
            sb.append(translateFromJSON);
            System.out.println(sb.toString());
        }
        
        
    }
}
