/**
 * 
 */
package com.cts.textparser.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.bo.TextParserBO;
import com.cts.textparser.service.TextParserService;
import com.cts.textparser.to.TextParserTO;
import com.cts.textparser.util.TextParserException;
import com.cts.textparser.util.ApplicationUtil;

/**
 * Entry point service class for this application
 * 
 * @author 153093
 *
 */
@Component("textParserService")
public class TextParserServiceImpl implements TextParserService {

	private static final Logger LOGGER = Logger.getLogger(TextParserServiceImpl.class);
	
	/**
	 * Common utility reference
	 */
	@Autowired(required = true)
	@Qualifier("applicationUtil")
	private ApplicationUtil applicationUtil;
	
	/**
	 * Parser business operation reference
	 */
	@Autowired(required = true)
	@Qualifier("textParserBO")
	private TextParserBO textParserBO;

	
	/**
	 * Method to perform parse operation on inputText per rules defined.
	 * 
	 * @param 	inputText
	 * 			Transmitted input text
	 * 
	 * @return	{@link TextParserTO} which contains parsed attributes
	 * 
	 * @throws 	TextParserException 
	 * 
	 */
	@Override
	public TextParserTO parseText(String inputText) throws TextParserException {
		LOGGER.debug("Text parser service starts...");
		TextParserTO textParserTO = applicationUtil.buildTextParserTO(inputText);
		
		textParserBO.parseText(textParserTO);
		
		LOGGER.debug("Text parser service ends.");
		LOGGER.debug(textParserTO);
		return textParserTO;
	}

}
