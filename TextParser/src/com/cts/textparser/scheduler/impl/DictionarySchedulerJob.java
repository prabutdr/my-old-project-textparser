/**
 * 
 */
package com.cts.textparser.scheduler.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.bo.LoadDictionaryCacheBO;
import com.cts.textparser.scheduler.SchedulerJob;
import com.cts.textparser.util.TextParserException;

/**
 * @author 153093
 *
 */
@Component("dictionarySchedulerJob")
public class DictionarySchedulerJob implements SchedulerJob {

	private static final Logger LOGGER = Logger.getLogger(DictionarySchedulerJob.class);
	
	/**
	 * Business object to load dictionary caches
	 */
	@Autowired(required = true)
	@Qualifier("loadDictionaryCacheBO")
	private LoadDictionaryCacheBO loadDictionaryCacheBO;

	
	/**
	 * Main execution method for this job which will load
	 * all dictionary caches
	 */
	@Override
	public void execute() {
		try {
			// Load lists
			loadDictionaryCacheBO.loadListDictionaryCache();
			
			// Load maps
			loadDictionaryCacheBO.loadMapDictionaryCache();
			
			// Load replaceInPatters
			loadDictionaryCacheBO.loadReplaceInPatternDictionaryCache();
			
			// Load insertInPatters
			loadDictionaryCacheBO.loadInsertInPatternDictionaryCache();
			
			// Load attributes
			loadDictionaryCacheBO.loadAttributeDictionaryCache();

			// Load operations
			loadDictionaryCacheBO.loadOperationDictionaryCache();

			// Load parse activities
			loadDictionaryCacheBO.loadParseActivityDictionaryCache();

		} catch (TextParserException e) {
			LOGGER.error("execute - failed with TextParserException", e);
		}
	}

}
