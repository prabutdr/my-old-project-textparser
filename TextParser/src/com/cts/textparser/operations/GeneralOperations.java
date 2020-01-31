/**
 * 
 */
package com.cts.textparser.operations;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.cts.textparser.constant.ErrorConstants;
import com.cts.textparser.constant.GeneralConstants;
import com.cts.textparser.to.TextParserTO;

/**
 * Utility class to hold methods which will do general operations for parser.
 * 
 * @author 153093
 *
 */
public class GeneralOperations {

	private static final Logger LOGGER = Logger.getLogger(GeneralOperations.class);

	/**
	 * Method to convert attribute values into upper case
	 * 
	 * @param 	sourceAttribute
	 * 			Name of source attribute
	 * 
	 * @param 	targetAttribute
	 * 			Name of target attribute
	 * 
	 * @param 	textParserTO
	 * 			Internal transfer object which holds all attribute maps
	 * 
	 * @return	Result code
	 */
	public static String copyAttribute(String sourceAttribute, String targetAttribute, TextParserTO textParserTO) {
		LOGGER.debug("Copy attribute starts...");
		// Retrieve source attribute values map
		Map<Integer, String> sourceAttributeMap = textParserTO.getAttribute(sourceAttribute);
		if (sourceAttributeMap == null) {
			LOGGER.debug("copyAttribute - Not a valid source attribute - " + sourceAttribute);
			return ErrorConstants.UNABLE_TO_PERF_PARSE_ACTIVITY;
		}
		
		// Retrieve target attribute values map
		Map<Integer, String> targetAttributeMap = textParserTO.getAttribute(targetAttribute);
		if (targetAttributeMap == null) {
			LOGGER.debug("copyAttribute - Not a valid target attribute - " + targetAttribute);
			return ErrorConstants.UNABLE_TO_PERF_PARSE_ACTIVITY;
		}
		
		// Copy all source attribute values to target attribute value map
		Iterator<Entry<Integer, String>> attributeMapIterator = sourceAttributeMap.entrySet().iterator();
		while(attributeMapIterator.hasNext()) {
			Entry<Integer, String> attributeValue = attributeMapIterator.next();
			targetAttributeMap.put(attributeValue.getKey(), attributeValue.getValue());
		}
		
		LOGGER.debug("Copy attribute ends.");
		return GeneralConstants.SUCCESS_RESULT_CODE;
	}
}
