/**
 * 
 */
package com.cts.textparser.to;

/**
 * Transfer object to hold exception details
 * 
 * @author 153093
 *
 */
public class TextParserExceptionTO {

	/**
	 * Result code for the exception
	 */
	private String resultCode;
	
	/**
	 * Description for the result code
	 */
	private String resultDescription;
	
	/**
	 * Internal Transfer object data
	 */
	private TextParserTO textParserTO;

	
	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultDescription
	 */
	public String getResultDescription() {
		return resultDescription;
	}

	/**
	 * @param resultDescription the resultDescription to set
	 */
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 * @return the textParserTO
	 */
	public TextParserTO getTextParserTO() {
		return textParserTO;
	}

	/**
	 * @param textParserTO the textParserTO to set
	 */
	public void setTextParserTO(TextParserTO textParserTO) {
		this.textParserTO = textParserTO;
	}
	
	
}
