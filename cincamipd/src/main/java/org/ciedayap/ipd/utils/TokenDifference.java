/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.utils;

import java.util.ArrayList;

/**
 * This is the record that stores data about the found differences contrasting a 
 * fingerprint chain with a given instance.
 * 
 * @author Mario Divan
 */
public class TokenDifference {
    private Class theclass;
    private String ID;
    private int level;
    private String instanceFingerprint;
    private String contrastedFingerprint;
    
    public TokenDifference(){}
    
    public boolean isDefinedProperties()
    {
        return !(theclass==null || StringUtils.isEmpty(ID) || StringUtils.isEmpty(instanceFingerprint) || 
                StringUtils.isEmpty(contrastedFingerprint)
                || level<0);        
    }
    
    public static synchronized TokenDifference create(Class pclass,String id, int plevel, String or,String al)
    {
        if(pclass==null || StringUtils.isEmpty(id) || StringUtils.isEmpty(or) || StringUtils.isEmpty(al)
                || plevel<0) return null;
        
        TokenDifference td=new TokenDifference();
        td.setID(id);
        td.setLevel(plevel);
        td.setTheclass(pclass);
        td.setInstanceFingerprint(or);
        td.setContrastedFingerprint(al);
        
        return td;
    }

    public static synchronized ArrayList<TokenDifference> createAsAList(Class pclass,String id, int plevel, String or,String al)
    {
        if(pclass==null || StringUtils.isEmpty(id) || StringUtils.isEmpty(or) || StringUtils.isEmpty(al)
                || plevel<0) return null;
        
        TokenDifference td=new TokenDifference();
        td.setID(id);
        td.setLevel(plevel);
        td.setTheclass(pclass);
        td.setInstanceFingerprint(or);
        td.setContrastedFingerprint(al);
        
        ArrayList<TokenDifference> list=new ArrayList();
        list.add(td);
        
        return list;
    }
    
    /**
     * @return the theclass
     */
    public Class getTheclass() {
        return theclass;
    }

    /**
     * @param theclass the theclass to set
     */
    public void setTheclass(Class theclass) {
        this.theclass = theclass;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the instanceFingerprint
     */
    public String getInstanceFingerprint() {
        return instanceFingerprint;
    }

    /**
     * @param instanceFingerprint the instanceFingerprint to set
     */
    public void setInstanceFingerprint(String instanceFingerprint) {
        this.instanceFingerprint = instanceFingerprint;
    }

    /**
     * @return the contrastedFingerprint
     */
    public String getContrastedFingerprint() {
        return contrastedFingerprint;
    }

    /**
     * @param contrastedFingerprint the contrastedFingerprint to set
     */
    public void setContrastedFingerprint(String contrastedFingerprint) {
        this.contrastedFingerprint = contrastedFingerprint;
    }
    
    
            
}
