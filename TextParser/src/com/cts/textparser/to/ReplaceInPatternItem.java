/**
 * 
 */
package com.cts.textparser.to;


/**
 * Transfer object to represent replaceInPattern in data dictionary 
 * 
 * @author 153093
 *
 */
public class ReplaceInPatternItem implements Comparable<ReplaceInPatternItem> {

	/**
	 * String value to search
	 */
	private String lookupValue;
	
	/**
	 * Target value to replace
	 */
	private String targetValue;
	
	/**
	 * Replacement value
	 */
	private String replacement;
	
	
	/**
	 * Return lookupValue as string representation
	 * as this will be used in token comparator
	 */
	@Override
	public String toString() {
		if(lookupValue == null) {
			return super.toString();
		}
		
		return lookupValue;
	}


	/**
	 * @return the lookupValue
	 */
	public String getLookupValue() {
		return lookupValue;
	}


	/**
	 * @param lookupValue the lookupValue to set
	 */
	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}


	/**
	 * @return the targetValue
	 */
	public String getTargetValue() {
		return targetValue;
	}


	/**
	 * @param targetValue the targetValue to set
	 */
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}


	/**
	 * @return the replacement
	 */
	public String getReplacement() {
		return replacement;
	}


	/**
	 * @param replacement the replacement to set
	 */
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	/**
	 * Compare to method for natural order sorting
	 */
	@Override
	public int compareTo(ReplaceInPatternItem o) {
		return this.lookupValue.compareTo(o.lookupValue);
	}
	
}
