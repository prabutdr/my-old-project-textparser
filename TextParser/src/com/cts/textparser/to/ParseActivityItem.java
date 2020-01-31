/**
 * 
 */
package com.cts.textparser.to;

import java.util.List;

import com.cts.textparser.constant.ActiveInd;


/**
 * Transfer object to represent a activity
 * 
 * @author 153093
 *
 */
public class ParseActivityItem extends DictionaryItem implements Comparable<ParseActivityItem> {

	/**
	 * Indicator to specify whether this activity in active
	 */
	private ActiveInd activeInd;
	
	/**
	 * Name of operation
	 */
	private String operation;
	
	/**
	 * List of parameters for the operation
	 */
	private List<String> parameters;

	
	/**
	 * Compare to method for natural order sorting
	 */
	@Override
	public int compareTo(ParseActivityItem o) {
		return this.getName().compareTo(o.getName());
	}
	
	/**
	 * Return name as string representation
	 * as this will be used in token comparator
	 */
	@Override
	public String toString() {
		if(this.getName() == null) {
			return super.toString();
		}
		
		return this.getName();
	}


	/**
	 * @return the activeInd
	 */
	public ActiveInd getActiveInd() {
		return activeInd;
	}

	/**
	 * @param activeInd the activeInd to set
	 */
	public void setActiveInd(ActiveInd activeInd) {
		this.activeInd = activeInd;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

}
