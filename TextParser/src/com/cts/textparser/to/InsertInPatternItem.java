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
public class InsertInPatternItem implements Comparable<InsertInPatternItem> {

	/**
	 * String value to search
	 */
	private String lookupValue;
	
	/**
	 * Target position to insert
	 */
	private Integer targetPosition;
	
	/**
	 * Insert value
	 */
	private String insertValue;
	
	
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
	 * @return the targetPosition
	 */
	public Integer getTargetPosition() {
		return targetPosition;
	}

	/**
	 * @param targetPosition the targetPosition to set
	 */
	public void setTargetPosition(Integer targetPosition) {
		this.targetPosition = targetPosition;
	}

	/**
	 * @return the insertValue
	 */
	public String getInsertValue() {
		return insertValue;
	}

	/**
	 * @param insertValue the insertValue to set
	 */
	public void setInsertValue(String insertValue) {
		this.insertValue = insertValue;
	}


	/**
	 * Compare to method for natural order sorting
	 */
	@Override
	public int compareTo(InsertInPatternItem o) {
		return this.lookupValue.compareTo(o.lookupValue);
	}
	
}
