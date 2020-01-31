/**
 * 
 */
package com.cts.textparser.util;

import com.cts.textparser.to.TextParserExceptionTO;

/**
 * Application exception class for this application
 * 
 * @author 153093
 *
 */
public class TextParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4033122750839065814L;
	
	/**
	 * To hold fault details of the application exception
	 */
	private TextParserExceptionTO textParserExceptionTO;

	
	/**
	 * Construct exception with given message & fault details
	 * 
	 * @param 	message
	 * 			Exception message
	 * 
	 * @param 	exceptionTO
	 * 			Complete fault details
	 */
	public TextParserException(String message, TextParserExceptionTO exceptionTO) {
		super(message);
		this.textParserExceptionTO = exceptionTO;
	}

	/**
	 * Construct exception with given message, fault details & cause
	 * 
	 * @param 	message
	 * 			Exception message
	 * 
	 * @param 	exceptionTO
	 * 			Complete fault details
	 * 
	 * @param 	cause
	 * 			Exception cause
	 */
	public TextParserException(String message, TextParserExceptionTO exceptionTO, Throwable cause) {
		super(message, cause);
		this.textParserExceptionTO = exceptionTO;
	}

	/**
	 * @return the textParserExceptionTO
	 */
	public TextParserExceptionTO getTextParserExceptionTO() {
		return textParserExceptionTO;
	}
}
