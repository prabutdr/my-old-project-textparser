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
import com.cts.textparser.to.ListTO;
import com.cts.textparser.to.MapTO;
import com.cts.textparser.to.TextParserTO;

/**
 * Utility class to hold methods which will do text standardize operations.
 * 
 * @author 153093
 *
 */
public class TextStandardizeOperations {

	private static final Logger LOGGER = Logger.getLogger(TextStandardizeOperations.class);

	/**
	 * Method to convert attribute values into upper case
	 * 
	 * @param 	attributeName
	 * 			Target attribute name to be modified
	 * 
	 * @param 	textParserTO
	 * 			Internal transfer object which holds all attribute maps
	 * 
	 * @return	Result code
	 */
	public static String convertToUpperCase(String attributeName, TextParserTO textParserTO) {
		LOGGER.debug("Convert to upper case starts... " + attributeName);
		// Retrieve attribute values map
		Map<Integer, String> attributeMap = textParserTO.getAttribute(attributeName);
		if (attributeMap == null) {
			LOGGER.debug("convertToUpperCase - Not a valid attribute - " + attributeName);
			return ErrorConstants.UNABLE_TO_PERF_PARSE_ACTIVITY;
		}
		
		// Convert all attribute values to upper case and store them back into attribute value map
		Iterator<Entry<Integer, String>> attributeMapIterator = attributeMap.entrySet().iterator();
		while (attributeMapIterator.hasNext()) {
			Entry<Integer, String> attributeValue = attributeMapIterator.next();
			attributeValue.setValue(attributeValue.getValue().toUpperCase());
		}
		
		LOGGER.debug("Convert to upper case ends.");
		return GeneralConstants.SUCCESS_RESULT_CODE;
	}

	public static String replaceTokens(String attribute, MapTO replaceTokenMap, TextParserTO textParserTO) {
		LOGGER.debug("Replace tokens starts... " + replaceTokenMap.getName());
		// Retrieve attribute values map
		Map<Integer, String> attributeMap = textParserTO.getAttribute(attribute);
		if (attributeMap == null) {
			LOGGER.debug("replaceTokens - Not a valid attribute - " + attribute);
			return ErrorConstants.UNABLE_TO_PERF_PARSE_ACTIVITY;
		}
		
		// Convert all attribute values to upper case and store them back into attribute value map
		Iterator<Entry<Integer, String>> attributeMapIterator = attributeMap.entrySet().iterator();
		while (attributeMapIterator.hasNext()) {
			Entry<Integer, String> attributeValue = attributeMapIterator.next();
			attributeValue.setValue(attributeValue.getValue().toUpperCase());
		}
		
		LOGGER.debug("Replace tokens ends.");
		return GeneralConstants.SUCCESS_RESULT_CODE;
		
//		logger.debug("PharmSIGPreParserBOImpl: Replacing special characters... " + replaceInd);
//		String result = input;
//
//		for(String[] replaceTokenRule: pharmSIGServiceUtil.getReplaceTokenRules()) {
//			if(GeneralConstants.REPLACE_IND_ALWAYS.equals(replaceTokenRule[0])
//					|| replaceInd.equals(replaceTokenRule[0])) {
//			//	logger.debug("PharmSIGPreParserBOImpl: replacing token (" + result + ", " + replaceTokenRule[1] + ", " + replaceTokenRule[2] + ")");
//				result = result.replaceAll(replaceTokenRule[1], replaceTokenRule[2]);
//			}
//		}
//
//		// Remove more than one white spaces with single white space character
//		result = result.replaceAll(RegexConstants.ONE_OR_MORE_WHITE_SPACES, GeneralConstants.SPACE_STRING);
//
//		logger.debug("PharmSIGPreParserBOImpl: After replacing special characters - " + replaceInd + ": " + result);
//		return GeneralConstants.SUCCESS_RESULT_CODE;
	}

}
