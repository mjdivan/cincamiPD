/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

import java.util.List;
import org.ciedayap.pabmm.pd.exceptions.EntityPDException;

/**
 * This interfaces is associated with the basic methods required for the 
 * container implementation for given kind of object.
 * 
 * @author Mario
 * @param <T> It represents the kind of object to be managed by the container
 */
public interface Container<T> {
    /**
     * It incorporeates a T instance at the end of the list 
     * @param instance The object to be incorporated
     * @return TRUE if the object was syccessfully incorporated, FALSE otherwise
     */
    public boolean add(T instance);
    /**
     * It incorporates a list of T instances at the end of the list
     * @param myList The list to be incorporated
     * @return TRUE if the list could be fully incorporated, FALSE otherwise
     */
    public boolean addCollection(List<T> myList);
    /**
     * It removes an object from the list when it is present
     * @param instance The objectd to be removed
     * @return TRUE if the object was correctly removed, FALSE otherwise
     */
    public boolean remove(T instance);
    /**
     * It removes the object in the position given by the indez
     * @param index The object´s position to be removed
     * @return TRUE when the object was removed, FALSE otherwise.
     */
    public boolean removeAt(int index);
    /**
     * It removes all the objects in the containter
     * @return TRUE when all the objects was removed, FALSE otherwise
     */
    public boolean removeAll();
    /**
     * It evaluates whether the container has at least one object or not.
     * @return TRUE if the container is empty, FALSE otherwise.
     */
    public boolean isEmpty();
    /**
     * It returns the quantity of objects in the list
     * @return the quantity of objects in the list (0 or upper)
     */
    public int size();
    /**
     * It returns TRUE if a given instance is present in the list
     * @param instance The instance to be located in the list
     * @return TRUE when the instance is present along the list, FALSE otherwise.
     */
    public boolean isPresent(T instance);
    /**
     * It evaluates whether an object with the given ID is present along the list or not.
     * @param id The ID to be evaluated
     * @return TRUE when at least one object contains the indicated ID, FALSE otherwise.
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException When the class T not yet implemented the SingleConcept interface
     */
    public boolean isPresentByID(String id) throws EntityPDException;
    /**
     * It returns the Object related to the indicated ID
     * @param id The ID related to a given object
     * @return T the object associated with the indicated ID
     * @throws org.ciedayap.pabmm.pd.exceptions.EntityPDException  When the class T not yet implemented the SingleConcept interface
     */
    public T get(String id) throws EntityPDException;
    /**
     * It returns the object located in the indicated index
     * @param index The index to be analyzed
     * @return The object in the indicated index, null otherwise
     */
    public T get(int index);
}
