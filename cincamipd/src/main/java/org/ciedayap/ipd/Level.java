/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd;

import java.util.ArrayList;
import org.ciedayap.ipd.exception.ProcessingException;
import org.ciedayap.ipd.utils.TokenDifference;
import org.ciedayap.pabmm.pd.SingleConcept;

/**
 * This interface describes a set of services needed to implement the parsing.
 * 
 * @author mjdivan
 * @version 1.0
 */
public interface Level extends SingleConcept{
    /**
     * It describes the level of the hierarchy being processed
     * @return A value upper  or equal to  0, indicating the level. 0 indicates the root.
     */
    public int getLevel();
    
    /**
     * I describes how the object is translated to a String representation following the 
     * @return A reversible string representation of the object.
     * @throws org.ciedayap.ipd.exception.ProcessingException It is raised when some anomaly occurs
     */
    public String writeTo() throws ProcessingException;
    /**
     * It reads the string representation and it can recreate the object
     * @param text The text to be analyzed
     * @return The object created from the text analysis
     * @throws org.ciedayap.ipd.exception.ProcessingException It is raised when some anomaly occurs
     */
    public Object readFrom(String text) throws ProcessingException;    
    
    /**
     * It computes a fingerprint using the writeTo output. It allows to 
     * @return A MD5 fingerprint when it was computed properly, null otherwise.
     * @throws ProcessingException It could be raised due to incomplete data or problems related to the writeTo method.
     */
    public String computeFingerprint() throws ProcessingException;  
    
    /**
     * It contrasts an instance of the tree with the homologous in another tree to find differences. 
     * When some difference is reached, a TokenDifference record is returned. Null otherwise.
     * @param ptr the homologous instance on a second tree to be compared
     * @return NULL when there is no difference. Otherwise, a new instance of TokenDifference informing details will be returned.
     * @throws ProcessingException It is raised when the parameter or the instance to be compared does not have the properties defined properly.
     */
    public ArrayList<TokenDifference> findDifferences(Object ptr) throws ProcessingException;    
    /**
     * It uses the hierarchy to release resources from the lowest level to the root.
     * @return TRUE when resources have been released.
     * @throws org.ciedayap.ipd.exception.ProcessingException It is raised when some anomaly is detected (e.g., a null pointer)
     */
    public boolean realeaseResources() throws ProcessingException;
}
