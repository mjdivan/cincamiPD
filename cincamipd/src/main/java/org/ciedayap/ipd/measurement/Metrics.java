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
 * It is a container of metrics
 * 
 * @author Mario Divan
 * @version 1.0
 */
@XmlRootElement(name="Metrics")
@XmlType(propOrder={"metrics"})
public class Metrics implements Serializable, Level, Containers{
    private ArrayList<Metric> metrics;
    
    public Metrics()
    {
       metrics=new ArrayList(); 
    }

    @Override
    public boolean realeaseResources() throws ProcessingException    
    {
        if(metrics==null)  return true;
        for(int i=0;i<metrics.size();i++)
        {
            metrics.get(i).realeaseResources();
        }
        
        metrics.clear();
        metrics=null;

        return true;
    }
    
    public static synchronized Metrics create(ArrayList<Metric> l)
    {
        Metrics m=new Metrics();
        m.setMetrics(l);
        
        return m;
    }
    
    @Override
    public int getLevel() {
        return 7;
    }

    @Override
    public String writeTo() throws ProcessingException {
        if(!this.isDefinedProperties()) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append(Tokens.startLevel).append(Tokens.Class_ID_Metrics);
        for(Metric item:metrics)
        {
            String segment=item.writeTo();
            
            if(!StringUtils.isEmpty(segment))
            {
                sb.append(segment);
            }
        }
        sb.append(Tokens.Class_ID_Metrics).append(Tokens.endLevel);
        
        return sb.toString();
    }

    @Override
    public Object readFrom(String text) throws ProcessingException {
        return readFromSt(text);
    }

    public static Metrics readFromSt(String text) throws ProcessingException {    
        if(StringUtils.isEmpty(text)) return null;
        
        int idx_st=text.indexOf(Tokens.startLevel+Tokens.Class_ID_Metrics);
        int idx_en=text.lastIndexOf(Tokens.Class_ID_Metrics+Tokens.endLevel);
        if(idx_st<0 || idx_en<0) return null;
        
        Metrics item=new Metrics();
        
        //It removes the startLevel and endLevel characters
        String cleanedText=text.substring(idx_st+Tokens.Class_ID_Metrics.length()+1, idx_en);        

        idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Metric);
        idx_en=cleanedText.indexOf(Tokens.Class_ID_Metric+Tokens.endLevel);
        while(idx_st>=0 && idx_en>=0)
        {
            String segment=cleanedText.substring(0, idx_en+Tokens.Class_ID_Metric.length()+1);            
            Metric met;
            try{
                met=(Metric) Metric.readFromSt(segment);
            }catch(ProcessingException pe)
            {
                met=null;
            }
            
            if(met!=null) item.getMetrics().add(met);
            
            //It retrieves the rest of the string
            cleanedText=cleanedText.substring(idx_en+Tokens.Class_ID_Metric.length()+1);
            
            idx_st=cleanedText.indexOf(Tokens.startLevel+Tokens.Class_ID_Metric);
            idx_en=cleanedText.indexOf(Tokens.Class_ID_Metric+Tokens.endLevel);
        }

        return item;
    }
    
    @Override
    public boolean isDefinedProperties() {
        return !(metrics==null || metrics.isEmpty());
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
        this.getMetrics().forEach(item -> {
            sb.append(item.getID()).append("-");
        });
        
        return sb.toString();

    }

    @Override
    public int length() {
        return (metrics!=null)?metrics.size():0;
    }

    @Override
    public boolean isEmpty() {
        if(metrics==null) return true;
        return metrics.isEmpty();        
    }

    @Override
    public Class getKindOfElement() {
        return Metric.class;
    }

    /**
     * @return the metrics
     */
    @XmlElement(name="Metric", required=true)
    public ArrayList<Metric> getMetrics() {
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(ArrayList<Metric> metrics) {
        if(this.metrics!=null){
            try{
                this.realeaseResources();
            }catch(ProcessingException pe){}
        }
        
        this.metrics = metrics;
    }
    
    public static Metrics test(int k)
    {
        Metrics m=new Metrics();
        for(int i=0;i<k;i++)
        {
            m.getMetrics().add(Metric.test(i));
        }
        
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
        if(!(target instanceof Metrics)) return false;
        
        Metrics cp=(Metrics)target;
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
        if(!(ptr instanceof Metrics)) throw new ProcessingException("The instance to be compared is not of the expected type");
        Metrics comp=(Metrics)ptr;
        if(!comp.isDefinedProperties()) throw new ProcessingException("The instance to be compared does not have the properties defined");
        if(!this.isDefinedProperties()) throw new ProcessingException("The instance to compare does not have the properties defined");
        
        if(this.equals(comp)) return null;                
        if(this.metrics.size()!=comp.getMetrics().size())
            return TokenDifference.createAsAList(Metrics.class, this.getUniqueID(), this.getLevel(), this.computeFingerprint(), comp.computeFingerprint());
        
        ArrayList<TokenDifference> global=new ArrayList();
        for(int i=0;i<metrics.size();i++)
        {
            Metric pthis=metrics.get(i);
            Metric pcomp=comp.getMetrics().get(i);
            
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
        Metrics listmets=test(3);        
        
        String xml=TranslateXML.toXml(listmets);
        String json=TranslateJSON.toJSON(listmets);
        String brief=listmets.writeTo();
        
        System.out.println(xml);
        System.out.println(json);
        System.out.println(brief);
        
        Metrics lret=(Metrics) Metrics.readFromSt(brief);
        
        lret.getMetrics().stream().map(ret -> {
            System.out.println("++++++++++++++++++++++++++++++++++++");
            return ret;
        }).map(ret -> {
            System.out.println("ID: "+ret.getID());
            return ret;
        }).map(ret -> {
            System.out.println("++++++++++++++++++++++++++++++++++++");
            return ret;            
        }).map(ret -> {
            System.out.println("Author: "+ret.getAuthor());
            return ret;            
        }).map(ret -> {
            System.out.println("Name: "+ret.getName());
            return ret;
        }).map(ret -> {
            System.out.println("Attr: "+ret.getIDattribute());
            return ret;
        }).map(ret -> {
            System.out.println("Obj: "+ret.getObjective());
            return ret;
        }).map(ret -> {
            System.out.println("Version: "+ret.getVersion());
            return ret;
        }).map(ret -> {
            System.out.println("***Scale***");
            return ret;
        }).map(ret -> {
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
            return ret;
        }).map((Metric ret) -> {
            DataSources rds=(DataSources) ret.getDataSources();
            rds.getDataSources().stream().map(nds -> {
                System.out.println("ID: "+nds.getDataSourceID());
                return nds;
            }).map(nds -> {
                System.out.println("Name: "+nds.getName());
                return nds;
            }).map(nds -> {
                System.out.println("Type: "+nds.getType());
                return nds;
            }).map(nds -> {
                System.out.println("Description: "+nds.getDescription());
                return nds;
            }).map(nds -> {
                System.out.println("Data fomrats: "+nds.getDataFormats());
                return nds;
            }).map(nds -> {
                System.out.println("Adapters: ");
                return nds;
            }).map(nds -> {
                nds.getDataSourceAdapters().getDataSourceAdapters().stream().map(tg -> {
                    System.out.println("ID: "+tg.getDsAdapterID());
                    return tg;
                }).map(tg -> {
                    System.out.println("Name: "+tg.getName());
                    return tg;
                }).map(tg -> {
                    System.out.println("Virtual: "+tg.getVirtual());
                    return tg;
                }).map(tg -> {
                    System.out.println("Description: "+tg.getDescription());
                    return tg;
                }).map(tg -> {
                    System.out.println("Altitude: "+tg.getLocation_altitude());
                    return tg;
                }).map(tg -> {
                    System.out.println("Latitude: "+tg.getLocation_latitude());
                    return tg;
                }).map(tg -> {
                    System.out.println("Longitude: "+tg.getLocation_longitude());
                    return tg;
                }).map(tg -> {
                    System.out.println("Formats: "+tg.getSupportedFormats());
                    return tg;
                }).forEachOrdered(tg -> {
                    System.out.println("Types: "+tg.getSupportedTypes());
                });
                return nds;
            }).map(nds -> {
                System.out.println("DS Properties: ");
                return nds;
            }).map(nds -> {
                for(DataSourceProperty tg:nds.getDataSourceProperties().getDataSourceProperties())
                {
                    System.out.println("ID: "+tg.getPropertyID());
                    System.out.println("Name: "+tg.getName());
                    System.out.println("Description: "+tg.getDescription());
                    System.out.println("Value: "+tg.getValue());
                    System.out.println("Relevance: "+tg.getRelevance());
                }
                return nds;
            }).map(nds -> {
                System.out.println("TGs: ");
                return nds;
            }).map(nds -> {
                for(TraceGroup tg:nds.getTraceGroups().getTraceGroups())
                {
                    System.out.println("ID: "+tg.getTraceGroupID());
                    System.out.println("Name: "+tg.getName());
                    System.out.println("Definition: "+tg.getDefinition());
                }
                return nds;
            }).forEachOrdered(_item -> {
                System.out.println("*****************");
            });
            System.out.println("***Constraints***");
            return ret;
        }).map(ret -> (Constraints) ret.getConstraints()).filter(r2list -> (r2list!=null)).forEachOrdered(r2list -> {
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
        });
        
        System.out.println("Equal: "+listmets.equals(lret));
    }    
    
}
