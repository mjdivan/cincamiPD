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
import org.ciedayap.ipd.Containers;
import org.ciedayap.ipd.Level;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.StringUtils;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.ipd.utils.Tokens;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It represents a set of constraints
 * 
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Constraints")
@XmlType(propOrder={"constraints"})
public class Constraints implements Serializable, Level, Containers{
    private ArrayList<RangeValueC> constraints;
    
    public Constraints()
    {
        constraints=new ArrayList<>();
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(constraints==null)  return true;
        for(int i=0;i<constraints.size();i++)
        {
            constraints.get(i).realeaseResources();
        }
        
        constraints.clear();
        constraints=null;

        return true;
    }
    
    public static synchronized Constraints create(ArrayList<RangeValueC> l)
    {
        Constraints c=new Constraints();
        c.setConstraints(l);
        
        return c;
    }
    
    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        if(this.getConstraints().isEmpty()) throw new ProcessingException("The list is empty");
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Constraints);
        for(Constraint item:constraints)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Constraints).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Constraints readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Constraints);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Constraints+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Constraints item=new Constraints();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Constraints.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Constraint);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Constraint+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Constraint.length()+1);            
            RangeValueC rvc=null;
            try{
                rvc=(RangeValueC) RangeValueC.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                rvc=null;
            }
            
            if(rvc!=null) item.getConstraints().add(rvc);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Constraint.length()+1);
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Constraint);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Constraint+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(this.constraints==null || this.constraints.isEmpty());
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
        for (Constraint item : constraints) 
        {
            sb.append(item.getID()).append("-");
        }
        
        return sb.toString();

    }

    @Override
    public int length() {
        return (constraints!=null)?constraints.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(constraints==null) return true;
        return constraints.isEmpty();
    }

    @Override
    public Class getKindOfElement() {
        return RangeValueC.class;
    }

    /**
     * @return the constraints
     */
    @XmlElement(name="RangeValueC", required=true)
    public ArrayList<RangeValueC> getConstraints() {
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(ArrayList<RangeValueC> constraints) {
        if(this.constraints!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.constraints = constraints;
    }
    
    public static Constraints test(int k)
    {
        Constraints list=new Constraints();
        
        for (int i=0;i<k;i++)
        {
            RangeValueC rvc=RangeValueC.test(i);
            list.getConstraints().add(rvc);
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
        if(!(target instanceof Constraints)) return false;
        
        Constraints cp=(Constraints)target;
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
        if(!(ptr instanceof Constraints)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Constraints comp=(Constraints)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.constraints.size()!=comp.getConstraints().size())
            return TokenDifference.createAsAList(Constraints.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<constraints.size();i++)
        {
            Constraint pthis=constraints.get(i);
            Constraint pcomp=comp.getConstraints().get(i);
            
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
        Constraints list=test(3);
        
        String xml=TranslateXML.toXml(list);
        String json=TranslateJSON.toJSON(list);
        String brief=list.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Constraints r2list=(Constraints) Constraints.readFromSt(brief);
        
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
        
        System.out.println("Equal: "+list.equals(r2list));
    }
    
    
}
