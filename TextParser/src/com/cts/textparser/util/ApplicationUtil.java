/**
 * 
 */
package com.cts.textparser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.constant.ErrorConstants;
import com.cts.textparser.constant.GeneralConstants;
import com.cts.textparser.to.TextParserExceptionTO;
import com.cts.textparser.to.TextParserTO;

/**
 * Utility class to define common methods for this application
 * 
 * @author 153093
 *
 */
@Component("applicationUtil")
public class ApplicationUtil {

	private static final Logger LOGGER = Logger.getLogger(ApplicationUtil.class);
	
	/**
	 * To hold list of result codes & descriptions
	 */
	@Autowired(required = true)
	@Qualifier("resultCodeProperties")
	private Properties resultCodeProperties;
	
	/**
	 * Dictionary cache to hold dictionary lists
	 */
	@Autowired(required = true)
	@Qualifier("dictionaryFactory")
	private DictionaryFactory dictionaryFactory;
	
	/**
	 * To retrieve result code description from property file
	 * 
	 * @param 	resultCode
	 * 
	 * @return	result description for result code
	 */
	public String getResultDescription(String resultCode) {
		return resultCodeProperties.getProperty(resultCode);
	}
	
	/**
	 * Helper method to build application exception with given error code & cause 
	 * 
	 * @param 	resultCode
	 * @param 	cause
	 * 
	 * @return	Application exception which is built
	 */
	public TextParserException buildTextParserException(String resultCode, TextParserTO textParserTO, Throwable cause) {
		
		// Build fault info
		TextParserExceptionTO textParserExceptionTO = new TextParserExceptionTO();
		textParserExceptionTO.setResultCode(StringUtils.isBlank(resultCode)? ErrorConstants.RUNTIME_EXCEPTION: resultCode);
		textParserExceptionTO.setResultDescription(this.getResultDescription(textParserExceptionTO.getResultCode()));
		textParserExceptionTO.setTextParserTO(textParserTO);
		
		// Build application exception
		TextParserException exception;
		if (cause == null) {
			exception = new TextParserException(textParserExceptionTO.getResultDescription(), textParserExceptionTO);
		} else {
			exception = new TextParserException(textParserExceptionTO.getResultDescription(), textParserExceptionTO, cause);
		}
		
		return exception;
	}

	/**
	 * Helper method to build application exception with given error code 
	 * 
	 * @param 	resultCode
	 * 
	 * @return	Application exception which is built
	 */
	public TextParserException buildTextParserException(String resultCode) {
		return this.buildTextParserException(resultCode, null, null);
	}
	
	/**
	 * Helper method to build application exception with given error code & textParserTO
	 * 
	 * @param 	resultCode
	 * @param	textParserTO
	 * 
	 * @return	Application exception which is built
	 */
	public TextParserException buildTextParserException(String resultCode, TextParserTO textParserTO) {
		return this.buildTextParserException(resultCode, textParserTO, null);
	}
	
	/**
	 * Helper method to build application exception with given error code & cause
	 * 
	 * @param 	resultCode
	 * @param 	cause
	 * 
	 * @return	Application exception which is built
	 */
	public TextParserException buildTextParserException(String resultCode, Throwable cause) {
		return this.buildTextParserException(resultCode, null, cause);
	}
	

	/**
	 * Build & initialize {@link TextParserTO} object using given input text
	 * 
	 * @param 	inputText
	 * 
	 * @return
	 * @throws TextParserException 
	 */
	public TextParserTO buildTextParserTO(String inputText) throws TextParserException {
		LOGGER.debug("Building new text parser transfer object starts...");
		if(StringUtils.isBlank(inputText)) {
			// Not a valid input
			throw buildTextParserException(ErrorConstants.INPUT_TEXT_MISSING);
		}
		
		// Build attribute empty maps per configuration
		Map<String, Map<Integer, String>> attributes = new HashMap<String, Map<Integer, String>>();
		Map<Integer, String> attribute;
		for(String attributeName: dictionaryFactory.getAttributeNames()) {
			attribute = new TreeMap<Integer, String>();
			attributes.put(attributeName, attribute);
		}
		

		// Validate & set input text into attribute map
		Map<Integer, String> inputTextAttribute = attributes.get(GeneralConstants.ATTRIBUTE_INPUT_TEXT);
		if (inputTextAttribute == null) {
			// input text attribute not found in the attribute map
			throw buildTextParserException(ErrorConstants.INPUT_TEXT_ATTR_MISSING);
		}
		// Set input text into attribute map
		inputTextAttribute.put(GeneralConstants.DEFAULT_POSITION_INDEX, inputText);
		
		// Place attribute map in transfer object
		TextParserTO textParserTO = new TextParserTO();
		textParserTO.setAttributes(attributes);
		
		LOGGER.debug("Building new text parser transfer object ends.");
		return textParserTO;
	}
}
