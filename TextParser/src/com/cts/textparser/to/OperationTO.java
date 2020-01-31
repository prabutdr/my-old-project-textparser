/**
 * 
 */
package com.cts.textparser.to;

import java.util.List;


/**
 * Transfer object to represent a operation
 * 
 * @author 153093
 *
 */
public class OperationTO extends DictionaryItem {

	/**
	 * Class name where operation method is defined
	 */
	private String className;
	
	/**
	 * Name of operation method
	 */
	private String methodName;
	
	/**
	 * List of parameter names which will be resolved by parser
	 */
	private List<String> parameters;
	

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
}
