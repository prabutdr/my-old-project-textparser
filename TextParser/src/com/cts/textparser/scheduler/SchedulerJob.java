/**
 * 
 */
package com.cts.textparser.scheduler;

/**
 * Interface that defines list of method the scheduler jobs must implement
 * 
 * @author 153093
 *
 */
public interface SchedulerJob {

	/**
	 * Method to perform this job
	 */
	public void execute();
}
