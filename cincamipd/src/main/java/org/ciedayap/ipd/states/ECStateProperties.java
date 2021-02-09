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
 * It contains the list of properties related to the environment
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="ECStateProperties")
@XmlType(propOrder={"ecStateProperties"})
public class ECStateProperties implements Serializable, Level, Containers{
    private java.util.ArrayList<ECMetadata>  ecStateProperties;
    
    public ECStateProperties()
    {
        ecStateProperties=new ArrayList();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(ecStateProperties==null)  return true;
        for(int i=0;i<ecStateProperties.size();i++)
        {
            ecStateProperties.get(i).realeaseResources();
        }
        
        ecStateProperties.clear();
        ecStateProperties=null;

        return true;
    }
    
    public static synchronized ECStateProperties create(ArrayList<ECMetadata> list)
    {
        ECStateProperties item=new ECStateProperties();
        
        item.setEcStateProperties(list);
        return item;
    }
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(ecStateProperties.isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_ECStateProperties);
        for(ECMetadata item:ecStateProperties)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_ECStateProperties).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static ECStateProperties readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_ECStateProperties);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_ECStateProperties+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        ECStateProperties item=new ECStateProperties();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_ECStateProperties.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ECMetadata);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_ECMetadata+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_ECMetadata.length()+1);            
            ECMetadata ds=null;
            try{
                ds=(ECMetadata) ECMetadata.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                ds=null;
            }
            
            if(ds!=null) item.getEcStateProperties().add(ds);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_ECMetadata.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_ECMetadata);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_ECMetadata+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(this.ecStateProperties==null || ecStateProperties.isEmpty());
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
        for (ECMetadata item : this.ecStateProperties) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();
    }

    @Override
    public int length() {
        return (ecStateProperties!=null)?ecStateProperties.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(ecStateProperties==null) return true;
        
        return ecStateProperties.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return ECMetadata.class;
    }

    /**
     * @return the ecMetadataProperties
     */
    @XmlElement(name="ECMetadata", required=true)        
    public java.util.ArrayList<ECMetadata> getEcStateProperties() {
        return this.ecStateProperties;
    }

    /**
     * @param ecStateProperties the ecStateProperties to set
     */
    public void setEcStateProperties(java.util.ArrayList<ECMetadata> ecStateProperties) {
        if(this.ecStateProperties!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.ecStateProperties = ecStateProperties;
    }
    
    public static ECStateProperties test(int i)
    {
        ECStateProperties list=new ECStateProperties();
        for(int j=0;j<i;j++)
        {
            list.getEcStateProperties().add(ECMetadata.test(j));                    
        }
        
        return list;
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
        if(!(target instanceof ECStateProperties)) return false;
        
        ECStateProperties cp=(ECStateProperties)target;
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
        if(!(ptr instanceof ECStateProperties)) throw new ProcessingException("The instance to be compared is not of the expected type");
        ECStateProperties comp=(ECStateProperties)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.ecStateProperties.size()!=comp.getEcStateProperties().size())
            return TokenDifference.createAsAList(ECStateProperties.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<ecStateProperties.size();i++)
        {
            ECMetadata pthis=ecStateProperties.get(i);
            ECMetadata pcomp=comp.getEcStateProperties().get(i);
            
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
        ECStateProperties sps=test(3);

        String xml=TranslateXML.toXml(sps);
        String json=TranslateJSON.toJSON(sps);
        String brief=sps.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        ECStateProperties sps2=(ECStateProperties)ECStateProperties.readFromSt(brief);
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
        
        System.out.println("Equal: "+sps.equals(sps2));
    }
}
