/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.exceptions;

/**
 * This class encapsulates the exceptions related to the diferent kinds
 * of mistakes for the Entity concept.
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class EntityPDException extends Exception{
    public EntityPDException()
    {
        super();
    }
    
    public EntityPDException(String message)
    {
        super(message);
    }
    
}
