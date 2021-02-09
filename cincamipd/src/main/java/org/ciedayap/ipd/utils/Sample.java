/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.ciedayap.ipd.IPD;
import org.ciedayap.ipd.MeasurementProject;
import org.ciedayap.ipd.MeasurementProjects;
import org.ciedayap.ipd.context.Context;
import org.ciedayap.ipd.context.ContextProperties;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.measurement.DataSource;
import org.ciedayap.ipd.measurement.DataSourceAdapter;
import org.ciedayap.ipd.measurement.DataSourceAdapters;
import org.ciedayap.ipd.measurement.DataSources;
import org.ciedayap.ipd.measurement.Metric;
import org.ciedayap.ipd.measurement.Metrics;
import org.ciedayap.ipd.measurement.Scale;
import org.ciedayap.ipd.measurement.ScaleType;
import org.ciedayap.ipd.measurement.Unit;
import org.ciedayap.ipd.requirements.Attribute;
import org.ciedayap.ipd.requirements.Attributes;
import org.ciedayap.ipd.context.ContextProperty;
import org.ciedayap.ipd.evaluation.DecisionCriteria;
import org.ciedayap.ipd.evaluation.DecisionCriterion;
import org.ciedayap.ipd.evaluation.Recommender;
import org.ciedayap.ipd.evaluation.WeightedIndicator;
import org.ciedayap.ipd.evaluation.WeightedIndicators;
import org.ciedayap.ipd.requirements.Entities;
import org.ciedayap.ipd.requirements.EntityCategory;
import org.ciedayap.ipd.requirements.InformationNeed;
import org.ciedayap.ipd.states.ECStates;
import org.ciedayap.ipd.states.ECState;
import org.ciedayap.ipd.states.ECMetadata;
import org.ciedayap.ipd.states.ECStateProperties;
import org.ciedayap.ipd.states.Range;
import org.ciedayap.ipd.states.Scenario;
import org.ciedayap.ipd.states.ScenarioProperties;
import org.ciedayap.ipd.states.ScenarioProperty;
import org.ciedayap.ipd.states.Scenarios;
import org.ciedayap.ipd.states.StateTransitionModel;
import org.ciedayap.ipd.states.Transitions;
import org.ciedayap.ipd.states.StateTransition;
import org.ciedayap.ipd.states.State;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;
import org.ciedayap.utils.ZipUtil;

/**
 * It contains functions to provide elements for a given simulation
 * @author Mario Divan
 * @version 1.0
 */
public class Sample implements Runnable{
    private ArrayList<MeasurementProject> list;
    private final int from;
    private final int to;
    
    public Sample(int pfrom, int pto,ArrayList<MeasurementProject> plist)
    {
        from=pfrom;
        to=pto;
        list=plist;
    }
    
    public static synchronized Sample create(int pfrom, int pto, ArrayList<MeasurementProject> plist)
    {
        return new Sample(pfrom, pto, plist);
    }
    
    public static MeasurementProject create(int i) throws ProcessingException
    {
        //Scale
        Unit bpm=Unit.create("bpm", "Beat per minute", "bpm");
        Unit temp=Unit.create("temp","Celsius Temperature", "ºC");
        Unit mmhg=Unit.create("mmHg", "Milimeters of mercury", "mmHg");
        Unit percentage=Unit.create("%", "Percentage", "%");
        Unit hpa=Unit.create("hPa", "Hectopascal", "hPa");
        Unit level=Unit.create("LV", "Level", "-");
        Scale sbpm=Scale.create("sbpm", "Scale for the heartbeat", ScaleType.RATIO, bpm);
        Scale stemp=Scale.create("stemp","Scale for the temperature", ScaleType.INTERVAL, temp);        
        Scale smmhg=Scale.create("smmhg", "Scale for the blood pressure", ScaleType.RATIO, mmhg);
        Scale sperc=Scale.create("sperc", "Scale for the percentage", ScaleType.RATIO, percentage);
        Scale shpa=Scale.create("shpa", "Scale for the environmental pressure", ScaleType.RATIO, hpa);
        Scale slevel=Scale.create("slevel", "Scale for the level", ScaleType.ORDINAL, level);
        
        //Attributes            
            //Heartbeat
            DataSourceAdapter DSA=DataSourceAdapter.create("MA-01", "Local Measurement Adapter", Boolean.FALSE);
            DataSourceAdapters DSAS=new DataSourceAdapters();
            DSAS.getDataSourceAdapters().add(DSA);
            DataSource DShb=DataSource.create("DS-HB", "Heartbeat Sensor", "Wearable", DSAS);
            DataSources DSShb=new DataSources();
            DSShb.getDataSources().add(DShb);            
            Metric mHB=Metric.create("MHB-01", "Value of the heartbeat", "1.0", "HB-01", sbpm, DSShb);
            Metrics HBlist=new Metrics();
            HBlist.getMetrics().add(mHB);                                
            Attributes attsList=new Attributes();
            attsList.getAttributes().add(Attribute.create("HB-01", "Heartbeat", new BigDecimal(0.25),HBlist));

            //Corporal Temperature
            DataSource DSct=DataSource.create("DS-CT", "Corporal Temperature Sensor", "Wearable", DSAS);
            DataSources DSSct=new DataSources();
            DSSct.getDataSources().add(DSct);
            Metric mCT=Metric.create("MCT-01", "Value of the corporal temperature", "1.0", "CT-01", stemp, DSSct);
            Metrics CTlist=new Metrics();
            CTlist.getMetrics().add(mCT);
            attsList.getAttributes().add(Attribute.create("CT-01", "Corporal Temperature",new BigDecimal(0.25),CTlist));
            
            //Systolic Blood Pressure
            DataSource DSbp=DataSource.create("DS-BP", "Blood Pressure Sensor", "Wearable", DSAS);
            DataSources DSSbp=new DataSources();
            DSSbp.getDataSources().add(DSbp);
            Metric mSBP=Metric.create("MSBP-01", "Value of the systolic blood pressure", "1.0", "SBP-01", smmhg, DSSbp);
            Metrics SBPlist=new Metrics();
            SBPlist.getMetrics().add(mSBP);            
            attsList.getAttributes().add(Attribute.create("SBP-01", "Systolic Blood Pressure", new BigDecimal(0.25), SBPlist));
            
            //Diastolic Blood Pressure
            Metric mDBP=Metric.create("MDBP-01", "Value of the diastolic blood pressure", "1.0", "DBP-01", smmhg, DSSbp);
            Metrics DBPlist=new Metrics();
            DBPlist.getMetrics().add(mDBP);                        
            attsList.getAttributes().add(Attribute.create("DBP-01", "Diastolic Blood Pressure", new BigDecimal(0.25),DBPlist));
            
        //ECStates
        ECStates ecStates=new ECStates();

            //Running            
            ECStateProperties run_prop=new ECStateProperties();            
            ECMetadata run_hb=ECMetadata.create("HB-01", "Heartbeat", 
                    Range.create("rHB-01", new BigDecimal(81), Boolean.TRUE, new BigDecimal(140), Boolean.TRUE));
            run_prop.getEcStateProperties().add(run_hb);
            ECMetadata run_ct=ECMetadata.create("CT-01", "Corporal Temperature", 
                    Range.create("rCT-01", new BigDecimal(36), Boolean.TRUE, new BigDecimal(37.4), Boolean.TRUE));
            run_prop.getEcStateProperties().add(run_ct);
            ECMetadata run_sbp=ECMetadata.create("SBP-01", "Systolic Blood Pressure", 
                    Range.create("rSBP-01", new BigDecimal(110), Boolean.TRUE, new BigDecimal(150), Boolean.TRUE));
            run_prop.getEcStateProperties().add(run_sbp);
            ECMetadata run_dbp=ECMetadata.create("DBP-01", "Diastolic Blood Pressure", 
                    Range.create("rSBP-01", new BigDecimal(75), Boolean.TRUE, new BigDecimal(90), Boolean.TRUE));
            run_prop.getEcStateProperties().add(run_dbp);                        
            ECState running=ECState.create("RU", "Runing", "1.0", run_prop);
            ecStates.getEcstates().add(running);
            
            //Walking            
            ECStateProperties walk_prop=new ECStateProperties();            
            ECMetadata walk_hb=ECMetadata.create("HB-01", "Heartbeat", 
                    Range.create("wHB-01", new BigDecimal(51), Boolean.TRUE, new BigDecimal(80), Boolean.FALSE));
            walk_prop.getEcStateProperties().add(walk_hb);
            ECMetadata walk_ct=ECMetadata.create("CT-01", "Corporal Temperature", 
                    Range.create("wCT-01", new BigDecimal(36), Boolean.TRUE, new BigDecimal(37.4), Boolean.TRUE));
            walk_prop.getEcStateProperties().add(walk_ct);
            ECMetadata walk_sbp=ECMetadata.create("SBP-01", "Systolic Blood Pressure", 
                    Range.create("wSBP-01", new BigDecimal(110), Boolean.TRUE, new BigDecimal(130), Boolean.TRUE));
            walk_prop.getEcStateProperties().add(walk_sbp);
            ECMetadata walk_dbp=ECMetadata.create("DBP-01", "Diastolic Blood Pressure", 
                    Range.create("wSBP-01", new BigDecimal(75), Boolean.TRUE, new BigDecimal(80), Boolean.TRUE));
            walk_prop.getEcStateProperties().add(walk_dbp);                        
            ECState walking=ECState.create("WK", "Walking", "1.0", walk_prop);
            ecStates.getEcstates().add(walking);

            //Resting            
            ECStateProperties rest_prop=new ECStateProperties();            
            ECMetadata rest_hb=ECMetadata.create("HB-01", "Heartbeat", 
                    Range.create("rHB-01", new BigDecimal(40), Boolean.TRUE, new BigDecimal(50), Boolean.FALSE));
            rest_prop.getEcStateProperties().add(rest_hb);
            ECMetadata rest_ct=ECMetadata.create("CT-01", "Corporal Temperature", 
                    Range.create("rCT-01", new BigDecimal(36), Boolean.TRUE, new BigDecimal(37.4), Boolean.TRUE));
            rest_prop.getEcStateProperties().add(rest_ct);
            ECMetadata rest_sbp=ECMetadata.create("SBP-01", "Systolic Blood Pressure", 
                    Range.create("rSBP-01", new BigDecimal(110), Boolean.TRUE, new BigDecimal(120), Boolean.TRUE));
            rest_prop.getEcStateProperties().add(rest_sbp);
            ECMetadata rest_dbp=ECMetadata.create("DBP-01", "Diastolic Blood Pressure", 
                    Range.create("rSBP-01", new BigDecimal(70), Boolean.TRUE, new BigDecimal(80), Boolean.TRUE));
            rest_prop.getEcStateProperties().add(rest_dbp);
            ECState resting=ECState.create("RT", "Resting", "1.0", rest_prop);
            ecStates.getEcstates().add(resting);
            
        //StateTransitionModel
        Transitions transitions=new Transitions();    
            State presting=resting.getBase();
            State pwalking=walking.getBase();
            State prunning=running.getBase();
            transitions.getTransitions().add(StateTransition.create(presting, presting));
            transitions.getTransitions().add(StateTransition.create(presting, pwalking));
            transitions.getTransitions().add(StateTransition.create(pwalking, presting));
            transitions.getTransitions().add(StateTransition.create(pwalking, prunning));
            transitions.getTransitions().add(StateTransition.create(prunning, pwalking));
            
            StateTransitionModel stm=StateTransitionModel.create("TM-ESTATE-01", "Transition Model", "1.0", transitions);
            
        //Entities
        Entities entities=Entities.test(3);
        
        //WeightedIndicators
        WeightedIndicators wis=new WeightedIndicators();
            //DecisionCriteria
            DecisionCriteria criteria=new DecisionCriteria();
            Recommender recommender=Recommender.test(1);
            DecisionCriterion criterion=DecisionCriterion.create("LW", "Lower", Range.create("rLower", new BigDecimal(50), Boolean.TRUE, new BigDecimal(60), Boolean.TRUE), recommender);
            criteria.getDecisionCriteria().add(criterion);
            criterion=DecisionCriterion.create("ME", "Medium", Range.create("rMedium", new BigDecimal(60), Boolean.FALSE, new BigDecimal(80), Boolean.TRUE), recommender);            
            criteria.getDecisionCriteria().add(criterion);
            criterion=DecisionCriterion.create("HG", "High", Range.create("rHigh", new BigDecimal(80), Boolean.FALSE, new BigDecimal(280), Boolean.FALSE), recommender);            
            criteria.getDecisionCriteria().add(criterion);
            
            WeightedIndicator wi=WeightedIndicator.create("I-HB-HO-RT", "Level of heartbeat", "HO", "RT", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-HO-WK", "Level of heartbeat", "HO", "WK", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-HO-RU", "Level of heartbeat", "HO", "RU", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);

            wi=WeightedIndicator.create("I-HB-CA-RT", "Level of heartbeat", "CA", "RT", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-CA-WK", "Level of heartbeat", "CA", "WK", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-CA-RU", "Level of heartbeat", "CA", "RU", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);

            wi=WeightedIndicator.create("I-HB-NO-RT", "Level of heartbeat", "NO", "RT", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-NO-WK", "Level of heartbeat", "NO", "WK", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            wi=WeightedIndicator.create("I-HB-NO-RU", "Level of heartbeat", "NO", "RU", new BigDecimal(0.11), 
                    new BigDecimal(0.11), criteria, "HB-01", slevel);
            wis.getWeightedIndicators().add(wi);
            
        //EntityCategory
        EntityCategory entCategory=EntityCategory.create("OutP", "Outpatient", attsList, entities, wis);
        entCategory.setEcStates(ecStates);
        entCategory.setStateTransitionModel(stm);
        
        //ContextProperties
        ContextProperties cpList=new ContextProperties();
        
            //Relative humidity
            DataSource DS_cphum=DataSource.create("DS-EH", "Environmental Humidity Sensor", "Wearable", DSAS);
            DataSources DSS_cphum=new DataSources();
            DSS_cphum.getDataSources().add(DS_cphum);
            Metric m_cp_hum=Metric.create("M-CP-HUM-01", "Value of the Environmental Humidity", "1.0", "CP-HUM-01", sperc, DSS_cphum);
            Metrics list_cp_hum=new Metrics();
            list_cp_hum.getMetrics().add(m_cp_hum);            
            ContextProperty cp_hum=ContextProperty.create("CP-HUM-01", "Environmental Humidity", new BigDecimal(0.33),list_cp_hum, new BigDecimal(0.33));
            cpList.getContextProperties().add(cp_hum);
            
            //Environmental Temperature
            DataSource DS_cpetemp=DataSource.create("DS-ET", "Environmental Temperature Sensor", "Wearable", DSAS);
            DataSources DSS_cpetemp=new DataSources();
            DSS_cpetemp.getDataSources().add(DS_cpetemp);
            Metric m_cp_temp=Metric.create("M-CP-TEMP-01", "Value of the Environmental Temperature", "1.0", "CP-ETEMP-01", stemp, DSS_cpetemp);
            Metrics list_cp_temp=new Metrics();
            list_cp_temp.getMetrics().add(m_cp_temp);                        
            ContextProperty cp_etemp=ContextProperty.create("CP-ETEMP-01", "Environmental Temperature", new BigDecimal(0.34),list_cp_temp,new BigDecimal(0.34));            
            cpList.getContextProperties().add(cp_etemp);
            
            //Environmental Pressure
            DataSource DS_epress=DataSource.create("DS-EPRESS", "Environmental Pressure Sensor", "Wearable", DSAS);
            DataSources DSS_epress=new DataSources();
            DSS_epress.getDataSources().add(DS_epress);
            Metric m_cp_epress=Metric.create("M-CP-EPRESS-01", "Value of the Environmental Pressure (Barometric)", "1.0", "CP-EP-01", shpa, DSS_epress);
            Metrics list_cp_epress=new Metrics();
            list_cp_epress.getMetrics().add(m_cp_epress);                                    
            ContextProperty cp_ep=ContextProperty.create("CP-EP-01", "Environmental Pressure", new BigDecimal(0.33),list_cp_epress,new BigDecimal(0.33));              
            cpList.getContextProperties().add(cp_ep);
            
        //Scenarios
        Scenarios scenarios=new Scenarios();

            //Hostil (HO)
            ScenarioProperties sce_prop=new ScenarioProperties();            
            ScenarioProperty sce_temp=ScenarioProperty.create("ETEMP-01", "Environmental Temperature", 
                    Range.create("rETEMP-01", new BigDecimal(35), Boolean.TRUE, new BigDecimal(50), Boolean.TRUE));
            sce_prop.getScenarioProperties().add(sce_temp);
            ScenarioProperty sce_hum=ScenarioProperty.create("EHUM-01", "Environmental Humidity", 
                    Range.create("rEHUM-01", new BigDecimal(60), Boolean.TRUE, new BigDecimal(100), Boolean.TRUE));
            sce_prop.getScenarioProperties().add(sce_hum);            
            Scenario hostil=Scenario.create("HO", "Hostil", "1.0", sce_prop);            
            scenarios.getScenarios().add(hostil);
        
            //Caution (CA)
            sce_prop=new ScenarioProperties();            
            ScenarioProperty sp=ScenarioProperty.create("ETEMP-01", "Environmental Temperature", 
                    Range.create("rETEMP-01", new BigDecimal(29), Boolean.TRUE, new BigDecimal(35), Boolean.FALSE));
            sce_prop.getScenarioProperties().add(sp);
            sp=ScenarioProperty.create("EHUM-01", "Environmental Humidity", 
                    Range.create("rEHUM-01", new BigDecimal(40), Boolean.TRUE, new BigDecimal(60), Boolean.FALSE));
            sce_prop.getScenarioProperties().add(sp);            
            Scenario caution=Scenario.create("CA", "Caution", "1.0", sce_prop);                        
            scenarios.getScenarios().add(caution);
            
            //Normal (NO)
            sce_prop=new ScenarioProperties();            
            sp=ScenarioProperty.create("ETEMP-01", "Environmental Temperature", 
                    Range.create("rETEMP-01", new BigDecimal(19), Boolean.TRUE, new BigDecimal(29), Boolean.FALSE));
            sce_prop.getScenarioProperties().add(sp);
            sp=ScenarioProperty.create("EHUM-01", "Environmental Humidity", 
                    Range.create("rEHUM-01", new BigDecimal(0), Boolean.TRUE, new BigDecimal(40), Boolean.FALSE));
            sce_prop.getScenarioProperties().add(sp);            
            Scenario normal=Scenario.create("NO", "Normal", "1.0", sce_prop);                        
            scenarios.getScenarios().add(normal);
        
        //StateTransitionModel for Scenarios
        transitions=new Transitions();    
            State pnormal=normal.getBase();
            State pcaution=caution.getBase();
            State phostil=hostil.getBase();
            transitions.getTransitions().add(StateTransition.create(pnormal, pnormal));
            transitions.getTransitions().add(StateTransition.create(pnormal, pcaution));          
            transitions.getTransitions().add(StateTransition.create(pnormal, phostil));
            transitions.getTransitions().add(StateTransition.create(pcaution, pcaution));
            transitions.getTransitions().add(StateTransition.create(pcaution, pnormal));
            transitions.getTransitions().add(StateTransition.create(phostil, phostil));
            transitions.getTransitions().add(StateTransition.create(phostil, pnormal));
            transitions.getTransitions().add(StateTransition.create(pcaution, phostil));
            transitions.getTransitions().add(StateTransition.create(phostil, pcaution));
            
            StateTransitionModel scetm=StateTransitionModel.create("TM-SCENARIO-01", "Transition Model", "1.0", transitions);
            
        //Context
        Context ctx=Context.create("CTX-01","The Environment", cpList);
        ctx.setScenarios(scenarios);
        ctx.setStateTransitionModel(scetm);        
                
        //InformationNeed
        InformationNeed inf=InformationNeed.create("INFNEED-01", "To monitor outpatients", "The Outpatient Monitoring", entCategory);
        inf.setContext(ctx);        
        
        //MeasurementProject
        return MeasurementProject.create("PRJ"+i, "Project "+i, "Divan", "Mario","mxxxx@eco.unlpam.edu.ar",  "My cell", ZonedDateTime.now(), 
                ZonedDateTime.now().plusYears(2), inf, ZonedDateTime.now());
    }
    
    public void generateProjects() throws ProcessingException
    {
        if(from<0 || to<0 || to<from) throw new ProcessingException("The generation limits have been defined improperly");
        if(list==null) throw new ProcessingException("The list is null");
        
        for(int i=from;i<=to;i++)
        {
            list.add(create(i));
        }                
    }
    
    public static void main(String args[]) throws ProcessingException, Exception
    {
        Scanner reader=new Scanner(System.in);
        int opt=0;
        ArrayList<ArrayList> list;
        do{
            System.out.println("Simulation options:");
            System.out.println("\t1. Individual operation rate with a constant number of projects [for 10 minutes with 10 projects]");
            System.out.println("\t2. Evolution of the individual operation rate when the number of projects varies [from 10 to 100 projects]");
            System.out.println("\t3. Evolution of the individual operation rate when the number of projects varies [from 10 to 100 projects]");
            System.out.println("Enter your choice [1-3](Exit with 0): ");
            opt=reader.nextInt();
        
            System.out.println("Your choice: "+opt);
            switch(opt)
            {
                case 0:
                    System.out.println("Bye bye...");
                    break;
                case 1:
                    list=simulation1(10,10);//10 minute, ten projects
                    Sample.store("/Users/mjdivan/Downloads/simulation1_10minv2.txt", list, ";");
                    break;
                case 2:
                    list=simulation2(100);
                    Sample.store("/Users/mjdivan/Downloads/simulation2_100prjv2.txt", list, ";");                    
                    break;
                case 3:
                    list=simulation3(2);
                    Sample.store("/Users/mjdivan/Downloads/simulation3_2minPerStage.txt", list, ";");
                    break;
                default:
                    System.out.println("Wrong option!");
                    Thread.sleep(1000);
            }
        }while(opt!=0);

    }

    @Override
    public void run() {
     try{
            generateProjects();
     }catch(ProcessingException pe)
     {
         pe.printStackTrace();
     }
    }
    
    public static synchronized IPD generateIPDMessage(int suggestedID,int pnofProjects) throws ProcessingException
    {
        ArrayList<MeasurementProject> list=new ArrayList();
        
        int npools=10;
        int nofProjects=pnofProjects;
        
        if(nofProjects%10!=0)
        {
            throw new ProcessingException("The number of projects is not a multiple of "+npools);
        }

        ArrayList<Sample> items=new ArrayList();
        int jump=nofProjects/npools;
        
        ExecutorService pool=Executors.newFixedThreadPool(npools);        
        for(int i=0;i<npools;i++)
        {
            pool.execute(Sample.create(((i==0)?i*jump:i*jump+1), i*jump+jump, list));
        }

        pool.shutdown();
        
        while(!pool.isTerminated()){}
        
        return IPD.create(String.valueOf(suggestedID), "1.0", ZonedDateTime.now(), MeasurementProjects.create(list));        
    }
    
    public static ArrayList measureIndividualTime(long start,IPD message) throws ProcessingException, Exception
    {
        long before,after;        
        
        ArrayList record=new ArrayList();

        //#Projects
        record.add(message.getProjects().length());

        //Time
        record.add(ZonedDateTime.now());
        record.add(System.nanoTime()-start);

        //BriefPD
        before=System.nanoTime();
        String briefPD=message.writeTo();
        after=System.nanoTime();
        record.add(after-before);//BriefPD Generation(ns);
        record.add(briefPD.getBytes().length);//Plain BirefPD Size (bytes)

            before=System.nanoTime();
            byte[] compressedBrief=ZipUtil.compressGZIP(briefPD);
            after=System.nanoTime();
            record.add(after-before);//BriefPD compressionTime
            record.add(compressedBrief.length);//compressed Bried size (bytes)

            //decompresion
            before=System.nanoTime();
            String briefPD2=ZipUtil.decompressGZIP(compressedBrief);
            after=System.nanoTime();
            record.add(after-before);//BriefPD decompressionTime (ns)

            //regeneration
            before=System.nanoTime();
            IPD message2=IPD.readFromSt(briefPD2);
            after=System.nanoTime();
            record.add(after-before);//BriefPD Regeneration Time (ns)

            //Easy verification
            before=System.nanoTime();
            Boolean isequal=message.equals(message2);
            after=System.nanoTime();
            record.add(after-before);//Equal operation Time
            record.add(isequal);//Equal operation result (ns)

            //findDifferences
            //A modificiation in the last level of the hierarchy
            message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters()
                    .get(0).setName("modified");
            before=System.nanoTime();
            message.findDifferences(message2);
            after=System.nanoTime();
            record.add(after-before);//findDifferences operation Time (ns)

        //XML
        before=System.nanoTime();
        String xml=TranslateXML.toXml(message);
        after=System.nanoTime();
        record.add(after-before);//XML Generation(ns);
        record.add(xml.getBytes().length);//Plain XML Size (bytes)

            before=System.nanoTime();
            compressedBrief=ZipUtil.compressGZIP(xml);
            after=System.nanoTime();
            record.add(after-before);//XML compressionTime (ns)
            record.add(compressedBrief.length);//compressed XML size (bytes)

            //decompresion
            before=System.nanoTime();
            String xml2=ZipUtil.decompressGZIP(compressedBrief);
            after=System.nanoTime();
            record.add(after-before);//XML decompressionTime (ns)   
            
            //regeneration time
            before=System.nanoTime();
            IPD messageFromXML=(IPD)TranslateXML.toObject(IPD.class, xml2);
            after=System.nanoTime();
            record.add(after-before);//XML regeneration time (ns)   

        //JSON
        before=System.nanoTime();
        String json=TranslateJSON.toJSON(message);
        after=System.nanoTime();
        record.add(after-before);//JSON Generation(ns);
        record.add(json.getBytes().length);//Plain JSON Size (bytes)

            before=System.nanoTime();
            compressedBrief=ZipUtil.compressGZIP(json);
            after=System.nanoTime();
            record.add(after-before);//JSON compressionTime (ns)
            record.add(compressedBrief.length);//compressed JSON size (bytes)

            //decompresion
            before=System.nanoTime();
            String json2=ZipUtil.decompressGZIP(compressedBrief);
            after=System.nanoTime();
            record.add(after-before);//JSON decompressionTime (ns)  
            
            //regeneration time
            before=System.nanoTime();
            IPD messageFromJSON=(IPD)TranslateJSON.toObject(IPD.class, json2);
            after=System.nanoTime();
            record.add(after-before);//XML regeneration time (ns)               

        return record;             
    }
    
    /**
     * It simulates the individual operation time for the indicated minutes, keeping constant the number of projects per message
     * @param minutes The number of minutes to analuze the individual operation time
     * @param nofProjects The constant number of projects per message throughout the simulation
     * @return An ArrayList with the records of the simulation with the following fields:
     *  1. #Projects: Number of measurement projects per message
     *  2. Datetime: Timestamp when the measures are taken
     *  3. Elapsed (ns): Elapsed time in nanoseconds from the simulation beggining
     *  4. BriefPD generation time (ns): Generation time for the BriefPD data format
     *  5. BriefPD message size (bytes): Size of the BriefPD message
     *  6. BriefPD compression time (ns): Compresssion time using GZIP for the BriefPD message
     *  7. BriefPD compressed size (bytes): Compressed size for the BriefPD message
     *  8. BriefPD decompression time (ns): Decompression time using GZIP for the BriefPD messsage
     *  9. BriefPD regeneration time (ns): Consumed time in regenerating the object model from the BriefPD message
     *  10. BriefPD Equal operation time (ns): Consumed time by the equal operation in BriefPD
     *  11. BriefPD Equal Result (boolean): Boolean result of the equal operation in BriefPD
     *  12. BriefPD findDifferences operation time (ns): Individual operation time of findDifferences, when some data at the lowest level of the hierarchy is modified
     *  13. XML generation time (ns): Generation time for the XML data format
     *  14. XML message size (Bytes): Size of the XML message
     *  15. XML compression time (ns): Compression time for the XML message
     *  16. XML compressed size (Bytes): Compressed size for the XML message
     *  17. XML decompression time (ns): Decompression time for the XML message
     *  18. JSON generation time (ns): Generation time for the JSON message
     *  19. JSON Message size (Bytes): Message size for the JSON message
     *  20. JSON compression time (ns): Compression time for the JSON message
     *  21. JSON compressed size (Bytes): Compressed size for the JSON message
     *  22. JSON decompression time (ns): Decompression time for the JSON message
     * 
     * @throws ProcessingException It is raised when a) Simulation time is lesser than 1 minute
     * @throws Exception 
     */
    public static ArrayList simulation1(int minutes,int nofProjects) throws ProcessingException, Exception            
    {
        if(minutes<1) throw new ProcessingException("Simulation time should be 1 minute at least.");
        if(nofProjects<1) throw new ProcessingException("The number of projects must be 1 at least.");
        
        IPD message=generateIPDMessage(1,nofProjects);
        System.out.println("Individual Times - Constant Number of projects for "+minutes+" minutes");        
        System.out.println("Starting simulation 1 ["+ZonedDateTime.now()+"]...");
        ArrayList results=new ArrayList();
        long start=System.nanoTime();        

        ArrayList titles=new ArrayList();
        titles.add("#Projects");
        titles.add("Datetime");
        titles.add("Elapsed (ns)");
        titles.add("BriefPD generation time (ns)");
        titles.add("BriefPD message size (bytes)");
        titles.add("BriefPD compression time (ns)");
        titles.add("BriefPD compressed size (bytes)");
        titles.add("BriefPD decompression time (ns)");
        titles.add("BriefPD regeneration time (ns)");   
        titles.add("BriefPD Equal operation time (ns)");
        titles.add("BriefPD Equal Result (boolean)");
        titles.add("BriefPD findDifferences operation time (ns)");   
        titles.add("XML generation time (ns)");
        titles.add("XML message size (Bytes)");
        titles.add("XML compression time (ns)");
        titles.add("XML compressed size (Bytes)");
        titles.add("XML decompression time (ns)");        
        titles.add("XML regeneration time (ns)");        
        titles.add("JSON generation time (ns)");
        titles.add("JSON message size (Bytes)");
        titles.add("JSON compression time (ns)");
        titles.add("JSON compressed size (Bytes)");
        titles.add("JSON decompression time (ns)");        
        titles.add("JSON regeneration time (ns)");        
        results.add(titles);
        
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            results.add(Sample.measureIndividualTime(start, message));
        }
        
        System.out.println("Finishing simulation 1 ["+ZonedDateTime.now()+"]...");        
        
        return results;
    }
    
    /**
     * It simulates the individual operation time, varying the number of projects per message (from 1 to maxNofProjects)
     * @param maxNofProjects The max number of projects per message throughout the simulation. It must be a multiple of 10.
     * @return An ArrayList with the records of the simulation with the following fields:
     *  1. #Projects: Number of measurement projects per message
     *  2. Datetime: Timestamp when the measures are taken
     *  3. Elapsed (ns): Elapsed time in nanoseconds from the simulation beggining
     *  4. BriefPD generation time (ns): Generation time for the BriefPD data format
     *  5. BriefPD message size (bytes): Size of the BriefPD message
     *  6. BriefPD compression time (ns): Compresssion time using GZIP for the BriefPD message
     *  7. BriefPD compressed size (bytes): Compressed size for the BriefPD message
     *  8. BriefPD decompression time (ns): Decompression time using GZIP for the BriefPD messsage
     *  9. BriefPD regeneration time (ns): Consumed time in regenerating the object model from the BriefPD message
     *  10. BriefPD Equal operation time (ns): Consumed time by the equal operation in BriefPD
     *  11. BriefPD Equal Result (boolean): Boolean result of the equal operation in BriefPD
     *  12. BriefPD findDifferences operation time (ns): Individual operation time of findDifferences, when some data at the lowest level of the hierarchy is modified
     *  13. XML generation time (ns): Generation time for the XML data format
     *  14. XML message size (Bytes): Size of the XML message
     *  15. XML compression time (ns): Compression time for the XML message
     *  16. XML compressed size (Bytes): Compressed size for the XML message
     *  17. XML decompression time (ns): Decompression time for the XML message
     *  18. JSON generation time (ns): Generation time for the JSON message
     *  19. JSON Message size (Bytes): Message size for the JSON message
     *  20. JSON compression time (ns): Compression time for the JSON message
     *  21. JSON compressed size (Bytes): Compressed size for the JSON message
     *  22. JSON decompression time (ns): Decompression time for the JSON message
     * 
     * @throws ProcessingException It is raised when a) Simulation time is lesser than 1 minute
     * @throws Exception 
     */

    public static ArrayList simulation2(int maxNofProjects) throws ProcessingException, Exception            
    {
        if(maxNofProjects<10) throw new ProcessingException("The max number of projects must be 10 at least.");
        if((maxNofProjects%10)!=0) throw new ProcessingException("The max number of projects must be a multiple of 10.");
        
        System.out.println("Individual Times - To Vary the Number of projects between [1;"+maxNofProjects+"]");
        System.out.println("Starting simulation 2 ["+ZonedDateTime.now()+"]...");
        ArrayList results=new ArrayList();
        long start=System.nanoTime();        

        ArrayList titles=new ArrayList();
        titles.add("#Projects");
        titles.add("Datetime");
        titles.add("Elapsed (ns)");
        titles.add("BriefPD generation time (ns)");
        titles.add("BriefPD message size (bytes)");
        titles.add("BriefPD compression time (ns)");
        titles.add("BriefPD compressed size (bytes)");
        titles.add("BriefPD decompression time (ns)");
        titles.add("BriefPD regeneration time (ns)");   
        titles.add("BriefPD Equal operation time (ns)");
        titles.add("BriefPD Equal Result (boolean)");
        titles.add("BriefPD findDifferences operation time (ns)");   
        titles.add("XML generation time (ns)");
        titles.add("XML message size (Bytes)");
        titles.add("XML compression time (ns)");
        titles.add("XML compressed size (Bytes)");
        titles.add("XML decompression time (ns)");        
        titles.add("XML regeneration time (ns)");        
        titles.add("JSON generation time (ns)");
        titles.add("JSON message size (Bytes)");
        titles.add("JSON compression time (ns)");
        titles.add("JSON compressed size (Bytes)");
        titles.add("JSON decompression time (ns)");        
        titles.add("JSON regeneration time (ns)");        
        results.add(titles);
        
        for(int i=10;i<=maxNofProjects;i+=10)
        {
            System.out.println("["+i+"] "+ZonedDateTime.now());
            IPD message=generateIPDMessage(i,i);//ID, #Projects
            
            results.add(Sample.measureIndividualTime(start, message));
            
            message.realeaseResources();
            System.gc();
            Thread.sleep(1000);
        }
        
        System.out.println("Finishing simulation 2 ["+ZonedDateTime.now()+"]...");        
        
        return results;        
    }
    
    /**
     * It simulates the following individual times in different level of the message hierarchy;
     * 1. Verification 2. Regeneration 3. Replacement
     * @param minutes The minutes to perform the simulation for each individual operation per level
     * @return An ArrayList with the records of the simulation with the following fields:
     *  1. #Projects: Number of projects
     *  2. Level: The level in the message hierarchy for the concept analyzed
     *  3. Class: The class related to the simulated concept
     *  4. Datetime: A timestamp of the measurement
     *  5. Elapsed(ns): Elapsed time from the start of the simulation for the concept
     *  6. Verification Time(ns): Consumed time by the verification
     *  7. Regeneration Time(ns): Consumed time by the object model regeneration from the briefPD message limited to the concept
     *  8. Replacement Time (ns): Consumed time by replacing the old instance with the new one.
     * @throws ProcessingException It is raised when the simulation time is lesser than 1 minute
     * @throws Exception
     */
    public static ArrayList simulation3(int minutes) throws ProcessingException, Exception            
    {
        if(minutes<1) throw new ProcessingException("Simulation time should be 1 minute at least.");        
        System.out.println("Replacement Total Times - To Vary the hierarchy level");
        System.out.println("Starting simulation 3 ["+ZonedDateTime.now()+"]...");
        ArrayList results=new ArrayList();    

        ArrayList titles=new ArrayList();
        titles.add("#Projects");
        titles.add("#Level");
        titles.add("Class");        
        titles.add("Datetime");
        titles.add("Elapsed (ns)");
        titles.add("Verification Time (ns)");
        titles.add("Regeneration Time (ns)");
        titles.add("Replacement Time (ns)");
        results.add(titles);
        
        MeasurementProject xp=Sample.create(1);
        MeasurementProject xp2=Sample.create(1);
        MeasurementProjects xps=new MeasurementProjects();
        MeasurementProjects xps2=new MeasurementProjects();
        xps.getProjects().add(xp);
        xps2.getProjects().add(xp2);
        IPD message=IPD.create("1","1.0", ZonedDateTime.now(), xps);
        IPD message2=IPD.create("1","1.0", ZonedDateTime.now(), xps2);
        
        //MeasurementProject
        long before,after;        

        long start=System.nanoTime();        
        System.out.println("Starting [MeasurementProject] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            MeasurementProject mp=message.getProjects().getProjects().get(0);
            MeasurementProject mp2=message2.getProjects().getProjects().get(0);
            
            record.add(message.getProjects().length());
            record.add(mp.getLevel());
            record.add(mp.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            mp.equals(mp2);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=mp2.writeTo();
            before=System.nanoTime();
            MeasurementProject newmp=MeasurementProject.readFromSt(briefPD);            
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().remove(0);
            message.getProjects().getProjects().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            results.add(record);
        }
        
        //EntityCategory
        start=System.nanoTime();        
        System.out.println("Starting [EntityCategory] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            EntityCategory a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory();
            EntityCategory b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory();
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            EntityCategory newmp=EntityCategory.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();            
            message.getProjects().getProjects().get(0).getInfneed().setEntityCategory(newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //Attribute
        start=System.nanoTime();        
        System.out.println("Starting [Attribute] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            Attribute a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0);
            Attribute b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            Attribute newmp=Attribute.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }

        //Attribute-Metric
        start=System.nanoTime();        
        System.out.println("Starting [Metric] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            Metric a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0);
            Metric b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            Metric newmp=Metric.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().add(0, newmp);                                        
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //Attribute-Metric-DataSource
        start=System.nanoTime();        
        System.out.println("Starting [DataSource] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            DataSource a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0);
            DataSource b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            DataSource newmp=DataSource.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }

        //Attribute-Metric-DataSource-DataSourceAdapter
        start=System.nanoTime();        
        System.out.println("Starting [DataSourceAdapter] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            DataSourceAdapter a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters()
                    .get(0);
            DataSourceAdapter b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters()
                    .get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            DataSourceAdapter newmp=DataSourceAdapter.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters()
                    .remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getAttributes().getAttributes().get(0)
                    .getMetrics().getMetrics().get(0).getDataSources().getDataSources().get(0).getDataSourceAdapters().getDataSourceAdapters()
                    .add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }

        //ECState
        start=System.nanoTime();        
        System.out.println("Starting [ECState] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            ECState a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0);
            ECState b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            ECState newmp=ECState.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //ECMetadata
        start=System.nanoTime();        
        System.out.println("Starting [ECMetadata] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            ECMetadata a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0)
                    .getEcStateProperties().getEcStateProperties().get(0);
            ECMetadata b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0)
                    .getEcStateProperties().getEcStateProperties().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            ECMetadata newmp=ECMetadata.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0)
                    .getEcStateProperties().getEcStateProperties().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getEcStates().getEcstates().get(0)
                    .getEcStateProperties().getEcStateProperties().add(0,newmp);
            after=System.nanoTime();            
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //StateTransitionModel
        start=System.nanoTime();        
        System.out.println("Starting [StateTransitionModel] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            StateTransitionModel a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getStateTransitionModel();
            StateTransitionModel b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getStateTransitionModel();
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            StateTransitionModel newmp=StateTransitionModel.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().setStateTransitionModel(newmp);
            after=System.nanoTime();            
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //Context
        start=System.nanoTime();        
        System.out.println("Starting [Context] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            Context a=message.getProjects().getProjects().get(0).getInfneed().getContext();
            Context b=message2.getProjects().getProjects().get(0).getInfneed().getContext();
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            Context newmp=Context.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().setContext(newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //Scenario
        start=System.nanoTime();        
        System.out.println("Starting [Scenario] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            Scenario a=message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0);
            Scenario b=message2.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            Scenario newmp=Scenario.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }

        //ScenarioProperty
        start=System.nanoTime();        
        System.out.println("Starting [ScenarioProperty] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            ScenarioProperty a=message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0)
                    .getScenarioProperties().getScenarioProperties().get(0);
            ScenarioProperty b=message2.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0)
                    .getScenarioProperties().getScenarioProperties().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            ScenarioProperty newmp=ScenarioProperty.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0).getScenarioProperties()
                    .getScenarioProperties().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getContext().getScenarios().getScenarios().get(0).getScenarioProperties()
                    .getScenarioProperties().add(0, newmp);
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        //WeightedIndicator
        start=System.nanoTime();        
        System.out.println("Starting [WeightedIndicator] - "+ZonedDateTime.now()+"...");
        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {
            ArrayList record=new ArrayList();
            
            WeightedIndicator a=message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators()
                    .getWeightedIndicators().get(0);
            WeightedIndicator b=message2.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators()
                    .getWeightedIndicators().get(0);
            
            record.add(message.getProjects().length());
            record.add(a.getLevel());
            record.add(a.getCurrentClassName());
            record.add(ZonedDateTime.now());
            record.add(System.nanoTime()-start);
                        
            before=System.nanoTime();
            a.equals(b);
            after=System.nanoTime();
            record.add(after-before);//Verification

            String briefPD=b.writeTo();
            before=System.nanoTime();
            WeightedIndicator newmp=WeightedIndicator.readFromSt(briefPD);
            after=System.nanoTime();
            record.add(after-before);//Regeneration

            before=System.nanoTime();
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators()
                    .getWeightedIndicators().remove(0);
            message.getProjects().getProjects().get(0).getInfneed().getEntityCategory().getWeightedIndicators()
                    .getWeightedIndicators().add(0,newmp);            
            after=System.nanoTime();
            record.add(after-before);//Replacement
            
            results.add(record);
        }
        
        return results;
    }
    
    public static boolean store(String filename,ArrayList<ArrayList> results, String fieldSeparator) throws ProcessingException
    {
        if(StringUtils.isEmpty(filename)) throw new ProcessingException("The filename is null");
        if(StringUtils.isEmpty(fieldSeparator)) throw new ProcessingException("The fieldSeparator is null");
        if(results==null || results.isEmpty()) throw new ProcessingException("No results are present");

        Path path=Paths.get(filename);
        System.out.println("Writing at "+path+" ...");        
        StringBuilder sb=new StringBuilder();
        try( BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8))
            {
                for(ArrayList record: results)
                {
                    int recordSize=record.size();

                    for(int i=0;i<recordSize;i++)
                    {
                        if((i+1)==recordSize)
                        {//last column
                            sb.append(record.get(i));                    
                        }
                        else
                        {
                            sb.append(record.get(i)).append(fieldSeparator);
                        }
                    }

                    //To write in the file
                    writer.append(sb.toString());
                    writer.newLine();
                    
                    //clean the string
                    sb.delete(0, sb.length());
                }            
        }catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}
