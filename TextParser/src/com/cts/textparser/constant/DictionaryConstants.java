/**
 * 
 */
package com.cts.textparser.constant;

/**
 * @author 153093
 *
 */
public class DictionaryConstants {

	// Dictionary item categories
	public static final String LIST = "list";
	public static final String MAP = "map";
	public static final String REPLACE_IN_PATTERN = "replaceInPattern";
	public static final String INSERT_IN_PATTERN = "insertInPattern";
	public static final String ATTRIBUTE = "attribute";
	public static final String OPERATION = "operation";
	public static final String PARSE_ACTIVITY = "parseActivity";
	
	// Index of category & item in dictionary item reference name hierarchy 
	public static final Integer CATEGORY_NAME_INDEX = 0;
	public static final Integer ITEM_NAME_INDEX = 1;
	public static final Integer ITEM_PROPERTY_NAME_INDEX = 2;
	
	// Regex format of dictionary item references
	public static final String REFERENCE_NAME_FORMAT = "^\\$\\{.*\\}$";
	
	// Delimiter regex to resolve dictionary item references
	public static final String REFERENCE_NAME_DELIMITER = "[${.} ]";
	
	// Dictionary item reference for parseActivity
	public static final String DIR_PARSE_ACTIVITY = "${parseActivity}";
}
