/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.pabmm.pd.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * It represents a possible answer from the evaluation associated with a elementary model.
 * @author Mario Diván
 * @version 1.0
 */
public class ElementaryDecision implements Serializable{
    /**
     * It indicates whether the instance is associated with an error message or not
     */
    private boolean Error;
    /**
     * The error message if the isError attribute is true.
     */
    private String errorMsg;
    /**
     * The evaluated value
     */
    private BigDecimal evaluatedValue;
    /**
     * The Elementary Model´s ID used for the evaluation
     */
    private String idElementaryModel;
    /**
     * It indicates whether a decision criterion contains the evaluated value or not
     */
    private boolean associatedCriterion;
    /**
     * It eventually incorporates the corresponding descriptive message related to the evaluation
     */
    private String message;
    /**
     * It indicates whether the notification of the message should be made or not.
     */
    private boolean performNotification;

    /**
     * Default constructor
     */
    public ElementaryDecision()
    {
        Error=false;
    }
    
    /**
     * Constructor related to an error message
     * @param evaluated The evaluated value
     * @param errorM The error message
     */
    public ElementaryDecision(BigDecimal evaluated,String errorM)
    {
        this.Error=true;
        this.errorMsg=errorM;
        this.evaluatedValue=evaluated;
    }
    
    /**
     * Constructor related to an elementary deficion
     * @param evaluated The evaluated decision
     * @param idEM The Elementary model´s ID
     * @param asocrit It indicates whether there is a decision criterion or not
     * @param mes The descriptive message
     * @param noti It indicates whether the message should be notified or not
     */
    public ElementaryDecision(BigDecimal evaluated,String idEM,boolean asocrit,String mes,boolean noti)
    {
        this.associatedCriterion=asocrit;
        this.evaluatedValue=evaluated;
        this.idElementaryModel=idEM;
        this.message=mes;
        this.performNotification=noti;
    }

    /**
     * A basic factory method related to a given decision coming from the elementary model
     * @param evaluated The evaluated decision
     * @param idEM The Elementary model´s ID
     * @param asocrit It indicates whether there is a decision criterion or not
     * @param mes The descriptive message
     * @param noti It indicates whether the message should be notified or not
     * @return A new ElementaryDecision´s instance, null otherwise
     */
    public static ElementaryDecision createAnswer(BigDecimal evaluated,String idEM,boolean asocrit,String mes,boolean noti)
    {
        return new ElementaryDecision(evaluated,idEM,asocrit,mes,noti);
    }
    
    /**
     * A basic factory method for the error messages
     * @param evaluated The evaluated value
     * @param message The error message
     * @return A new ElementaryDecision instance, null otherwise.
     */
    public static ElementaryDecision createError(BigDecimal evaluated,String message)
    {
      return new ElementaryDecision(evaluated,message);   
    }
    
    
    /**
     * @return the Error
     */
    public boolean isError() {
        return Error;
    }

    /**
     * @param Error the Error to set
     */
    public void setError(boolean Error) {
        this.Error = Error;
        
        if(!this.isError()) this.setErrorMsg(null);
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the evaluatedValue
     */
    public BigDecimal getEvaluatedValue() {
        return evaluatedValue;
    }

    /**
     * @param evaluatedValue the evaluatedValue to set
     */
    public void setEvaluatedValue(BigDecimal evaluatedValue) {
        this.evaluatedValue = evaluatedValue;
    }

    /**
     * @return the idElementaryModel
     */
    public String getIdElementaryModel() {
        return idElementaryModel;
    }

    /**
     * @param idElementaryModel the idElementaryModel to set
     */
    public void setIdElementaryModel(String idElementaryModel) {
        this.idElementaryModel = idElementaryModel;
    }

    /**
     * @return the associatedCriterion
     */
    public boolean isAssociatedCriterion() {
        return associatedCriterion;
    }

    /**
     * @param associatedCriterion the associatedCriterion to set
     */
    public void setAssociatedCriterion(boolean associatedCriterion) {
        this.associatedCriterion = associatedCriterion;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the performNotification
     */
    public boolean isPerformNotification() {
        return performNotification;
    }

    /**
     * @param performNotification the performNotification to set
     */
    public void setPerformNotification(boolean performNotification) {
        this.performNotification = performNotification;
    }

}
