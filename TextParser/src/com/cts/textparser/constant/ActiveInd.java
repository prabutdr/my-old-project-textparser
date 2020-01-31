/**
 * 
 */
package com.cts.textparser.constant;

/**
 * @author 153093
 *
 */
public enum ActiveInd {
	YES("Y"), 
	NO("N");

	private String value;

	private ActiveInd(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Helper method to get active indicator from string value
	 * 
	 * @param value
	 * @return
	 */
	public static ActiveInd fromString(String value) {
		if (value != null) {
			for (ActiveInd activeInd: ActiveInd.values()) {
				if (value.equalsIgnoreCase(activeInd.value)) {
					return activeInd;
				}
			}
		}
		
		return null;
	}
}
