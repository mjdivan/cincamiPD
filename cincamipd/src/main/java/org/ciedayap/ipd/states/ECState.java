/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
@XmlRootElement(name="ECState")
@XmlType(propOrder={"ecStateProperties"})
public class ECState extends State implements Serializable, Level{
    private ECStateProperties ecStateProperties;

    public ECState(){}
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        super.realeaseResources();
        
        if(ecStateProperties==null)  return true;
        
        ecStateProperties.realeaseResources();
        ecStateProperties=null;

        return true;
    }
    
    public static synchronized ECState create(String id, String name, String version,BigDecimal tLikelihood, BigDecimal eLikelihood,
            ECStateProperties ec)
    {
        ECState ecState=new ECState();
        ecState.setEcStateProperties(ec);
        ecState.setEmpiricalLikelihood(eLikelihood);
        ecState.setID(id);
        ecState.setName(name);
        ecState.setTheoreticalLikelihood(tLikelihood);
        ecState.setVersion(version);
        
        return ecState;
    }

    public static synchronized ECState create(String id, String name, String version,ECStateProperties ec)
    {
        ECState ecState=new ECState();
        ecState.setEcStateProperties(ec);
        ecState.setID(id);
        ecState.setName(name);
        ecState.setVersion(version);
        
        return ecState;
    }
    
    public synchronized State getBase()
    {
        if(!this.isDefinedProperties()) return null;
        
        return State.create(ID, name, version, empiricalLikelihood, empiricalLikelihood);
    }
    
    @Override
    public int getLevel() {
        return 6;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_ECState);
        //ID *mandatory*
        sb.append(Tokens.removeTokens(this.getID().trim())).append(Tokens.fieldSeparator);
        //name  *mandatory*
        sb.append(Tokens.removeTokens(this.getName().trim())).append(Tokens.fieldSeparator);
        //version *mandatory*
        sb.append(Tokens.removeTokens(this.getVersion().trim())).append(Tokens.fieldSeparator);
        //empiricalLikelihood  *optional*
        sb.append((empiricalLikelihood==null)?"":empiricalLikelihood).append(Tokens.fieldSeparator);
        //theoreticalLikelihood  *optional*
        sb.append((theoreticalLikelihood==null)?"":theoreticalLikelihood).append(Tokens.fieldSeparator);

        //ecStateProperties *mandatory*
        sb.append(this.ecStateProperties.writeTo());
        
        sb.append(Tokens.Class_ID_ECState).append(Tokens.endLevel);
        
        return  sb.toString();               
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }
    
    public static ECState readFromSt(String text) throws ProcessingException {        
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_ECState);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_ECState+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        ECState item=new ECState();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_ECState.length()+1, idx_en);        
        
        //"ID" *mandatory*        
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
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The name field is not present and it is mandatory");
        item.setName(value);

        //"version" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The version field is not present and it is mandatory.");
        value=cleanedText.substring(0, idx_en);        
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The version field is not present and it is mandatory");
        item.setVersion(value);
        
        //"empiricalLikelihood" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The empiricalLikelihood field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(!StringUtils.isEmpty(value))item.setEmpiricalLikelihood(new BigDecimal(value));
        
        //"theoreticalLikelihood" *optional*
        cleanedText=cleanedText.substring(idx_en+1);
        idx_en=cleanedText.indexOf(Tokens.fieldSeparator);
        if(idx_en<0) throw new ProcessingException("The theoreticalLikelihood field is not present.");
        value=cleanedText.substring(0, idx_en);        
        if(!StringUtils.isEmpty(value))item.setTheoreticalLikelihood(new BigDecimal(value));
                
        //"scenarioProperties" *mandatory*
        cleanedText=cleanedText.substring(idx_en+1);
        value=cleanedText.substring(0);
        if(StringUtils.isEmpty(value)) throw new ProcessingException("The ecStateProperties field is not present.");
        item.setEcStateProperties((ECStateProperties) ECStateProperties.readFromSt(value));
        
        return item;        
    }

    @Override
    public boolean isDefinedProperties() {
        return !(StringUtils.isEmpty(ID) || StringUtils.isEmpty(name) || StringUtils.isEmpty(version) ||
                this.ecStateProperties==null || this.ecStateProperties.isEmpty());
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
        return  this.ID;
    }

    /**
     * @return the ecStateProperties
     */
    @XmlElement(name="ECStateProperties", required=true)    
    public ECStateProperties getEcStateProperties() {
        return this.ecStateProperties;
    }

    /**
     * @param ecStateProperties the ecStateProperties to set
     */
    public void setEcStateProperties(ECStateProperties ecStateProperties) {
        if(this.ecStateProperties!=null){
            try{
                this.ecStateProperties.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.ecStateProperties = ecStateProperties;
    }
 
    public static ECState test(int i)
    {
        ECState mis=new ECState();
        mis.setEmpiricalLikelihood(BigDecimal.TEN);
        mis.setID("SCEID"+i);
        mis.setName("My Scenario"+i);
        mis.setEcStateProperties(ECStateProperties.test(3));
        mis.setTheoreticalLikelihood(BigDecimal.ONE);
        mis.setVersion("1.2.3");
        
        return mis;
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
        if(!(target instanceof ECState)) return false;
        
        ECState cp=(ECState)target;
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
        if(!(ptr instanceof ECState)) throw new ProcessingException("The instance to be compared is not of the expected type");
        ECState comp=(ECState)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        ArrayList<TokenDifference> global=new ArrayList();        
        
        global.add(TokenDifference.create(ECState.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint()));
        
        //ECStateProperties *mandatory*
        ArrayList<TokenDifference> result=this.ecStateProperties.findDifferences(comp.getEcStateProperties());
        if(result!=null && result.size()>0)
        {
            global.addAll(result);            
            result.clear();
        }
        
        return (global.size()>0)?global:null;
    }
    
    public static void main(String arg[]) throws ProcessingException
    {
        ECState mis=test(1);
        
        String xml=TranslateXML.toXml(mis);
        String json=TranslateJSON.toJSON(mis);
        String brief=mis.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        ECState mis2=(ECState)ECState.readFromSt(brief);
        System.out.println("ID: "+mis2.getID());
        System.out.println("Name: "+mis2.getName());
        System.out.println("tL: "+mis2.getTheoreticalLikelihood());
        System.out.println("eL: "+mis2.getEmpiricalLikelihood());

        
        ECStateProperties sps2=(ECStateProperties)mis2.getEcStateProperties();
        for(ECMetadata sp2: sps2.getEcStateProperties())
        {
            System.out.println("Sc.ID: "+sp2.getID());
            System.out.println("Sc.Name: "+sp2.getName());
            Range r2=sp2.getRange();
            System.out.println("ID: "+r2.getIDRange());
            System.out.println("Min: "+r2.getMinValue());
            System.out.println("minIncluded: "+r2.getMinValueIncluded());
            System.out.println("Max: "+r2.getMaxValue());
            System.out.println("maxIncluded: "+r2.getMaxValueIncluded());        
            
        }
        
        System.out.println("Equal: "+mis.equals(mis2));
    }
    
}
