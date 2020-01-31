/**
 * 
 */
package com.cts.textparser.to;

import java.util.List;

import com.cts.textparser.constant.SortInd;
import com.cts.textparser.util.DictionaryUtil;

/**
 * Transfer object to represent list in data dictionary 
 * 
 * @author 153093
 *
 */
public class ListTO<T extends Comparable<T>> extends DictionaryItem {

	/**
	 * Actual list values
	 */
	private List<T> value;
	
	/**
	 * To specify sorting flag
	 */
	private SortInd sortInd;

	
	/**
	 * @return the value
	 */
	public List<T> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<T> value) {
		this.value = value;
		
		// Perform sort if required
		this.value = DictionaryUtil.sort(this.value, this.sortInd);
	}

	/**
	 * @return the sortInd
	 */
	public SortInd getSortInd() {
		return sortInd;
	}

	/**
	 * @param sortInd the sortInd to set
	 */
	public void setSortInd(SortInd sortInd) {
		this.sortInd = sortInd;
		
		// Perform sort if required
		this.value = DictionaryUtil.sort(this.value, this.sortInd);
	}


}
