/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd;

/**
 * This encapsulates the exceptions related to the synthesizer
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class SynthesizerException extends Exception{
    /**
     * Default constructor
     */
    public SynthesizerException()
    {
        super();
    }
    
    /**
     * Constructor indicating a short exception message
     * @param mes The short message to be shown
     */
    public SynthesizerException(String mes)
    {
        super(mes);
    }
    
}
