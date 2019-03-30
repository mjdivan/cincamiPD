/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.ssa;

/**
 * It represents the basic information related to a given Entity Category State
 * @author Mario Diván
 * @version 1.0
 */
public interface ECStateProperty {
    /**
     * It is the unique identification for the attribute or entity abstract property 
     * @return The unique ID
     */
    public String getID();
    /**
     * It describes the concept which allows describing a entity property
     * @return A string with the description
     */
    public String getDescription();
    /**
     * The extended variation range for the entity property
     * 
     * @return It returns the expected variation range for the property
     */
    public Range getRange();    
}
