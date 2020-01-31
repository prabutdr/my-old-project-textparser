/**
 * 
 */
package com.cts.textparser.to;

import org.apache.commons.beanutils.BeanUtils;


/**
 * Parent object for dictionary items
 * 
 * @author 153093
 *
 */
public class DictionaryItem {

	/**
	 * Attribute's name
	 */
	private String name;
	
	/**
	 * And its description
	 */
	private String description;


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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
