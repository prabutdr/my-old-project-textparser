/**
 * 
 */
package com.cts.textparser.service;

import com.cts.textparser.to.TextParserTO;
import com.cts.textparser.util.TextParserException;

/**
 * Interface that defines list of method the text parser service must implement.
 * 
 * @author 153093
 *
 */
public interface TextParserService {

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
	public TextParserTO parseText(String inputText) throws TextParserException;
}
