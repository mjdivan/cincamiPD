/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.states;

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
 * It contains the states related to an entity under monitoring
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="ECStates")
@XmlType(propOrder={"ecstates"})
public class ECStates implements Serializable, Containers, Level {
    private ArrayList<ECState> ecstates;
    
    public ECStates()
    {
        ecstates=new ArrayList();
    }
    
    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(ecstates==null) return true;
        
        for(int i=0;i<ecstates.size();i++)
        {
            ecstates.get(0).realeaseResources();
        }
        ecstates.clear();
        ecstates=null;
        
        return true;
    }
    
    public static synchronized ECStates create(ArrayList<ECState> list)
    {
        ECStates item=new ECStates();
        item.setEcstates(list);
        
        return item;
    }
    
    @Override
    public int length() {
        return (ecstates==null)?0:ecstates.size();
    }

    @Override
    public boolean isEmpty() {
        if(ecstates==null) return true;
        return ecstates.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return ECState.class;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.ecstates.isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_ECStates);
        for(ECState item:ecstates)
        {
            String segment=item.writeTo();

            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_ECStates).append(Tokens.endLevel);
        
        return sb.toString();        
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static ECStates readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_ECStates);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_ECStates+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        ECStates item=new ECStates();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_ECStates.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ECState);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_ECState+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_ECState.length()+1);            
            ECState ds=null;
            try{
                ds=(ECState) ECState.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getEcstates().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_ECState.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ECState);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_ECState+Tokens.endLevel);
        }

        return item;
    }

    @Override
    public boolean isDefinedProperties() {
        return !(this.ecstates==null || ecstates.isEmpty());
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
        for (ECState item : ecstates) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    /**
     * @return the ecstates
     */
    @XmlElement(name="ECState", required=true)
    public ArrayList<ECState> getEcstates() {
        return ecstates;
    }

    /**
     * @param ecstates the ecstates to set
     */
    public void setEcstates(ArrayList<ECState> ecstates) {
        if(this.ecstates!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.ecstates = ecstates;
    }

    public static ECStates test( int k)
    {
        ECStates estados=new ECStates();
        for(int z=0;z<k;z++)
        {
            ECState mis=ECState.test(z);
            
            estados.getEcstates().add(mis);
        }

        return estados;
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
        if(!(target instanceof ECStates)) return false;
        
        ECStates cp=(ECStates)target;
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
        if(!(ptr instanceof ECStates)) throw new ProcessingException("The instance to be compared is not of the expected type");
        ECStates comp=(ECStates)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.ecstates.size()!=comp.getEcstates().size())
            return TokenDifference.createAsAList(ECStates.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<ecstates.size();i++)
        {
            ECState pthis=ecstates.get(i);
            ECState pcomp=comp.getEcstates().get(i);
            
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
        ECStates estados=test(3);
                
        String xml=TranslateXML.toXml(estados);
        String json=TranslateJSON.toJSON(estados);
        String brief=estados.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
                
        ECStates estados2=(ECStates)ECStates.readFromSt(brief);
        
        for(ECState mis2:estados2.getEcstates())
        {            
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
        }
        
        String brief3=estados2.writeTo();
        System.out.println("Equal: "+brief.equalsIgnoreCase(brief3));//Reversible                
        System.out.println("Equal2: "+estados.equals(estados2));
        
    }    
}
