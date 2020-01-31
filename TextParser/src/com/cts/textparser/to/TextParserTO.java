/**
 * 
 */
package com.cts.textparser.to;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;



/**
 * Transfer object which will hold request & response fields required for parser
 * 
 * @author 153093
 *
 */
public class TextParserTO {

	/**
	 * Parsed attributes
	 */
	private Map<String, Map<Integer, String>> attributes;
	
	
	/**
	 * To retrieve specific attribute values by its name
	 * 
	 * @param 	attributeName
	 * 
	 * @return	attribute values
	 */
	public Map<Integer, String> getAttribute(String attributeName) {
		if (attributes == null) {
			return null;
		}
		
		return attributes.get(attributeName);
	}

	/**
	 * Describe properties as string
	 * 
	 */
	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return super.toString();
		}
	}

	/**
	 * @return the attributes
	 */
	public Map<String, Map<Integer, String>> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Map<Integer, String>> attributes) {
		this.attributes = attributes;
	}
}
