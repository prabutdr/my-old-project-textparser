/**
 * 
 */
package com.cts.textparser.util;

import java.util.Comparator;

import com.cts.textparser.constant.RegexConstants;

/**
 * Comparator class to order strings in descending order based on number of 
 * words & length.  String which has more number of words will come first then less, 
 * if number of words matches then string which has more character will come 
 * first then less.
 *  
 * @author 153093
 *
 */
public class StringTokenDescComparator implements Comparator<Object> {
	
	/**
	 * Compare given phrases and return numeric value based on rule.
	 * 
	 */
	@Override
    public int compare(Object text1, Object text2) {
        if (text1 == null && text2 == null) {
        	// Both are null, then equal
            return 0;
        } else if (text1 == null) {
        	// Only text1 is null, then say text1 is big for descending order
            return 1;
        } else if (text2 == null) {
        	// Only text2 is null, then say text2 is big for descending order
            return -1;
        }

        // Identify number of tokens in each text by white space
        int len1 = text1.toString().split(RegexConstants.ONE_OR_MORE_WHITE_SPACE).length;
        int len2 = text2.toString().split(RegexConstants.ONE_OR_MORE_WHITE_SPACE).length;
        
        if(len1 < len2) {
        	// say text1 is big for descending order
        	return 1;
        }
        else if(len1 > len2) {
        	// say text2 is big for descending order
        	return -1;
        }
        
        // if number of token matches, then compare length 
        return text2.toString().length() - text1.toString().length();
    }
}