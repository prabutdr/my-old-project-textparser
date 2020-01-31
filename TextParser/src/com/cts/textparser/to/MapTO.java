/**
 * 
 */
package com.cts.textparser.to;

import java.util.Map;

import com.cts.textparser.constant.SortInd;
import com.cts.textparser.util.DictionaryUtil;

/**
 * Transfer object to represent map in data dictionary 
 * 
 * @author 153093
 *
 */
public class MapTO extends DictionaryItem {

	/**
	 * Actual value
	 */
	private Map<String, String> value;
	
	/**
	 * To specify sorting flag
	 */
	private SortInd sortInd;

	
	/**
	 * @return the value
	 */
	public Map<String, String> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Map<String, String> value) {
		this.value = value;
		
		// Sort map if required
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
		
		// Sort map if required
		this.value = DictionaryUtil.sort(this.value, this.sortInd);
	}

}
