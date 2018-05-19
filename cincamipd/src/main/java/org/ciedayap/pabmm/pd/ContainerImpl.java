/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * This class gives an alternative implementation for the Container Interfaces
 * @author Mario Diván
 * @version 1.0
 * @param <T> The kind of class to be managed
 */
@XmlRootElement(name="ContainerImpl")
@XmlType(propOrder={"myList"})
public class ContainerImpl<T> implements Serializable,Container<T> {
    private ArrayList<T> myList;
    
    public ContainerImpl()
    {
        myList=new ArrayList();
    }

    @Override
    public boolean add(T instance) {
        if(getMyList()==null || instance==null) return false;
        return getMyList().add(instance);
    }

    @Override
    public boolean addCollection(List<T> myList) {
       if(this.getMyList()==null || myList==null || myList.isEmpty()) return false;
       
       return this.getMyList().addAll(myList);
    }

    @Override
    public boolean remove(T instance) {
        if(this.getMyList()==null || this.getMyList().isEmpty() || instance==null) return false;
        
        return this.getMyList().remove(instance);
    }

    @Override
    public boolean removeAt(int index) {
        if(this.getMyList()==null || this.getMyList().isEmpty()) return false;
        if(index<0 || index>=this.getMyList().size()) return false;
        
        return (this.getMyList().remove(index)!=null);
    }

    @Override
    public boolean removeAll() {
        if(this.getMyList()==null || this.getMyList().isEmpty()) return false;
        
        this.getMyList().clear();
        
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (this.getMyList()==null || this.getMyList().isEmpty());

    }

    @Override
    public int size() {
       if(getMyList()==null) return 0;
       
       return getMyList().size();
    }

    @Override
    public boolean isPresent(T instance) {
        if(getMyList()==null || getMyList().isEmpty()) return false;
        
        return getMyList().contains(instance);
    }

    @Override
    public boolean isPresentByID(String id) throws EntityPDException {
        return (get(id)!=null);
    }

    @Override
    public T get(String id) throws EntityPDException {
        if(getMyList()==null || getMyList().isEmpty()) return null;
        
        for(T object:getMyList())
        {
            if(!(object instanceof org.ciedayap.pabmm.pd.SingleConcept)) 
                throw new EntityPDException("The class "+object.getClass().getCanonicalName()+" has not yet implemented the SingleConcept interface");
            
            SingleConcept sc=(SingleConcept)object;
            if(sc.isDefinedProperties())
            {
                String ID=sc.getUniqueID();
                if(ID.equalsIgnoreCase(id)) return object;
            }
        }

        return null;
    }

    @Override
    public T get(int index) {
        if(myList==null || myList.isEmpty()) return null;

        if(index<0 || index>=myList.size()) return null;
        
        return myList.get(index);        
    }

    /**
     * @return the myList
     */
    @XmlElement(name="list")
    public ArrayList<T> getMyList() {
        return myList;
    }

    /**
     * @param myList the myList to set
     */
    public void setMyList(ArrayList<T> myList) {
        this.myList = myList;
    }
    
}
