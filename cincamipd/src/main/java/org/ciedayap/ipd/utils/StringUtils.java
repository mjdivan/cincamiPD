/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.ipd.utils;

/**
 *
 * @author mjdivan
 */
public class StringUtils {
    public static boolean isEmpty(String a)
    {
        if(isNull(a)) return true;
        
        return (a.trim().length()==0);
    }
    
    public static boolean isNull(String a)
    {
        return (a==null);
    }
    
    /**
    * This algorithm has been adapted from https://stackoverflow.com/a/332101
    * @param bytes The byte array to be converted to String hex
    * @return A hexadecimal String representation from the byte array
    */
    public static String toHexString(byte[] bytes) 
    {
      if(bytes==null || bytes.length==0) return null;

      StringBuilder hexString = new StringBuilder();

      for (byte myByte: bytes) 
      {
          String hex = Integer.toHexString(0xFF & myByte);
          if (hex.length() == 1) hexString.append('0');

          hexString.append(hex);
      }

      return hexString.toString();
    }        
}
