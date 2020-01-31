/**
 * 
 */
package com.cts.textparser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cts.textparser.constant.SortInd;

/**
 * The utility class to provide generic methods for Dictionary data
 * 
 * @author 153093
 *
 */
public class DictionaryUtil {

	private static final Logger LOGGER = Logger.getLogger(DictionaryUtil.class);
	
	/**
	 * Method to sort given list per given sort indicator.
	 * 
	 * @param 	items
	 * 			List which needs to be sorted
	 * 			
	 * @param 	sortInd
	 * 			Sorting flag to specify sort action
	 * 
	 */
	public static <T extends Comparable<T>> List<T> sort(List<T> items, SortInd sortInd) {
		if(items == null || sortInd == null) {
			LOGGER.debug("No sorting performed for list as Illegal params");
			return items;
		}
		
		switch(sortInd) {
			case NATURAL_ORDER_ASCENDING:
				Collections.sort(items);
				LOGGER.debug("Sorted list by natural ordering (ascending)");
				break;
				
			case TOKEN_ORDER_DESCENDING:
				Collections.sort(items, new StringTokenDescComparator());
				LOGGER.debug("Sorted list by string token descending comparator");
				break;
				
			case NO_SORT:
				LOGGER.debug("No sorting performed for list as not required");
				break;
				
			default:
				LOGGER.debug("No sorting performed for list as Invalid sort indicator");
				break;
		}
		
		return items;
	}

	/**
	 * Method to sort given map per given sort indicator.
	 * 
	 * @param 	items
	 * 			Map which needs to be sorted
	 * 			
	 * @param 	sortInd
	 * 			Sorting flag to specify sort action
	 * 
	 */
	public static Map<String, String> sort(Map<String, String> items, SortInd sortInd) {
		if(items == null || sortInd == null) {
			LOGGER.debug("No sorting performed for map as Illegal params");
			return items;
		}
		
		Map<String, String> sortedMap = items;
		switch(sortInd) {
			case NATURAL_ORDER_ASCENDING:
				sortedMap = new TreeMap<>(items);
				LOGGER.debug("Sorted map by natural ordering (ascending)");
				break;
				
			case TOKEN_ORDER_DESCENDING:
				// Sort keys
				List<String> keys = new ArrayList<>(items.keySet());
				Collections.sort(keys, new StringTokenDescComparator());
				
				// Construct map items by key order
				sortedMap = new LinkedHashMap<>();
				for(String key: keys) {
					sortedMap.put(key, items.get(key));
				}
				
				LOGGER.debug("Sorted map by string token descending comparator");
				break;
				
			case NO_SORT:
				LOGGER.debug("No sorting performed for map as not required");
				break;
				
			default:
				LOGGER.debug("No sorting performed for map as Invalid sort indicator");
				break;
		}
		
		return sortedMap;
	}
}
