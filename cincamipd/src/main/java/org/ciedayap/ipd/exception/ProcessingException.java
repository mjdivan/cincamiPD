/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.exception;

/**
 * This class specializes the exceptions for brief data  processing.
 * @author Mario Diván
 * @version 1.0
 */
public class ProcessingException extends Exception{
    public ProcessingException()
    {
        super();
    }
    
    public ProcessingException(String s)
    {
        super(s);
    }
}
