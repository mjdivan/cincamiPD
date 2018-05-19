/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

/**
 * This interface represents the basic method to be implemented for any concept class.
 * It allows knowing if the concepts have all the obligatory properties successfully defined.
 * 
 * @author Mario Diván
 * @version 1.0
 */
public interface SingleConcept {
    /**
     * This method will return TRUE when all the mandatory properties have a given value
     * @return TRUE when all the mandatory properties have at least a valid value, FALSE otherwise.
     */
    public boolean isDefinedProperties();
    /**
     * This method just returns the name related to the class
     * @return The class name
     */
    public String getCurrentClassName();
    /**
     * This method returns the fully qualified class name
     * @return Fully qialified class name.
     */
    public String getQualifiedCurrentClassName();
    /**
     * It returns the unique ID representative for a given object
     * @return The unique ID
     */
    public String getUniqueID();
}
