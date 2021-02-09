/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd;

/**
 * It describes the  minimum behavior related to a container
 * @author mjdivan
 * @version 1.0
 */
public interface Containers {
    /**
     * It returns the number of elements in the container
     * @return number of elements
     */
    public int length();
    /**
     * It indicates whether the container is empty or not
     * @return TRUE represents an empty container, otherwise FALSE
     */
    public boolean isEmpty();
    /**
     * It indicates the kind of elements contained in the container
     * @return Kind of elements in the container
     */
    public Class getKindOfElement();
}
