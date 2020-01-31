/**
 * 
 */
package com.cts.textparser.bo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.cache.DictionaryCache;
import com.cts.textparser.constant.ActiveInd;
import com.cts.textparser.constant.DictionaryConstants;
import com.cts.textparser.constant.ErrorConstants;
import com.cts.textparser.constant.GeneralConstants;
import com.cts.textparser.to.ListTO;
import com.cts.textparser.to.OperationTO;
import com.cts.textparser.to.ParseActivityItem;
import com.cts.textparser.to.TextParserTO;
import com.cts.textparser.util.DictionaryFactory;
import com.cts.textparser.util.TextParserException;
import com.cts.textparser.util.ApplicationUtil;

/**
 * Business object class to perform text parse activity actions
 * 
 * @author 153093
 *
 */
@Component("textParserBO")
public class TextParserBO {

	private static final Logger LOGGER = Logger.getLogger(TextParserBO.class);

	/**
	 * Common utility reference
	 */
	@Autowired(required = true)
	@Qualifier("applicationUtil")
	private ApplicationUtil applicationUtil;
	
	/**
	 * Dictionary factory
	 */
	@Autowired(required = true)
	@Qualifier("dictionaryFactory")
	private DictionaryFactory dictionaryFactory;
	
	/**
	 * Dictionary cache which holds text parse activities
	 */
	@Autowired(required = true)
	@Qualifier("parseActivityDictionaryCache")
	private DictionaryCache<String, ListTO<ParseActivityItem>> parseActivityDictionaryCache;
	
	/**
	 * Dictionary cache which holds operation details
	 */
	@Autowired(required = true)
	@Qualifier("operationDictionaryCache")
	private DictionaryCache<String, OperationTO> operationDictionaryCache;
	
	/**
	 * Method to perform parse business operation
	 * 
	 * @param 	textParserTO
	 * 			Internal transfer object
	 * 
	 * @throws 	TextParserException
	 */
	public void parseText(TextParserTO textParserTO) throws TextParserException {
		LOGGER.debug("Parsing of input text starts...");
		
		for (ParseActivityItem parseActivityItem: parseActivityDictionaryCache.get(DictionaryConstants.PARSE_ACTIVITY).getValue()) {
			if (parseActivityItem.getActiveInd() == ActiveInd.NO) {
				LOGGER.debug("Skipping parse activity as not valid or not required - " + parseActivityItem.getName());
				continue;
			}
			
			OperationTO operationTO = operationDictionaryCache.get(parseActivityItem.getOperation());
			if (operationTO == null) {
				LOGGER.debug("Operation not found for parse activity - " + parseActivityItem.getName());
				throw applicationUtil.buildTextParserException(ErrorConstants.OPERATION_NOT_FOUND, textParserTO);
			}
			
			LOGGER.debug("Performing activity - " + parseActivityItem.getName());
			performParseActivity(parseActivityItem, operationTO, textParserTO);
		}
		
		LOGGER.debug("Parsing of input text ends.");
	}
	
	/**
	 * Helper method to perform parse activity as configured using reflection
	 * 
	 * @param 	parseActivityItem
	 * 			Holds activity detail
	 * 
	 * @param 	operationTO
	 * 			Holds operation detail
	 * 
	 * @param 	textParserTO
	 * 			Internal transfer object
	 * 
	 * @throws 	TextParserException
	 */
	private void performParseActivity(ParseActivityItem parseActivityItem, OperationTO operationTO, TextParserTO textParserTO) throws TextParserException {
		String resultCode;
		try {
			// Load class
			Class<?> operationClass = Class.forName(operationTO.getClassName());
			
			// Build parameters & their types as array
			Object[] parameters = new Object[parseActivityItem.getParameters().size() + 1];
			Class<?>[] parameterTypes = new Class[parameters.length];
			
			// TODO: Should be done one time for type inference per operation
			int paramCounter = 0;
			for(String parameterName: parseActivityItem.getParameters()) {
				parameters[paramCounter] = dictionaryFactory.getValue(parameterName);
				parameterTypes[paramCounter] = parameters[paramCounter].getClass();
				paramCounter++;
			}
			
			// Adding internal transfer object as default parameter 
			parameters[paramCounter] = textParserTO;
			parameterTypes[paramCounter] = parameters[paramCounter].getClass();
			
			// Retrieve target method from the class
			Method operationMethod = operationClass.getMethod(operationTO.getMethodName(), parameterTypes);
			
			// Call target method with given parameter to accomplish the activity
			resultCode = (String) operationMethod.invoke(null, parameters);
			if(!GeneralConstants.SUCCESS_RESULT_CODE.equals(resultCode)) {
				// Any failure result code
				LOGGER.error("Parse activity failed with result code - " + resultCode);
				throw applicationUtil.buildTextParserException(resultCode, textParserTO);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error("Exception occurred while ", e);
			throw applicationUtil.buildTextParserException(ErrorConstants.UNABLE_TO_PERF_PARSE_ACTIVITY, textParserTO, e);
		}
	}
	
}
