/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.evaluation;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.ipd.measurement.Scale;

/**
 * It represents the base class for the indicators.
 * The common functionality is implemented here.
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Indicator")
@XmlType(propOrder={"attributeID","scale"})
public abstract class Indicator {
    protected String attributeID;//mandatory
    protected Scale scale;

    /**
     * @return the attributeID
     */
    public String getAttributeID() {
        return attributeID;
    }

    /**
     * @param attributeID the attributeID to set
     */
    public void setAttributeID(String attributeID) {
        this.attributeID = attributeID;
    }

    /**
     * @return the scale
     */
    public Scale getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Scale scale) {
        this.scale = scale;
    }
}
