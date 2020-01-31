/**
 * 
 */
package com.cts.textparser.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.cache.DictionaryCache;
import com.cts.textparser.constant.DictionaryConstants;
import com.cts.textparser.to.AttributeTO;
import com.cts.textparser.to.DictionaryItem;
import com.cts.textparser.to.InsertInPatternItem;
import com.cts.textparser.to.ListTO;
import com.cts.textparser.to.MapTO;
import com.cts.textparser.to.OperationTO;
import com.cts.textparser.to.ReplaceInPatternItem;
import com.cts.textparser.to.ParseActivityItem;
import com.cts.textparser.util.TextParserException;
import com.cts.textparser.util.WorkbookUtil;

/**
 * Business object class to perform load operation for dictionary data
 * 
 * @author 153093
 *
 */
@Component("loadDictionaryCacheBO")
public class LoadDictionaryCacheBO {

	private static final Logger LOGGER = Logger.getLogger(LoadDictionaryCacheBO.class);
	
	/**
	 * Dictionary workbook
	 */
	@Autowired(required = true)
	@Qualifier("workbookUtil")
	private WorkbookUtil workbookUtil;
	
	/**
	 * Dictionary cache to hold dictionary lists
	 */
	@Autowired(required = true)
	@Qualifier("listDictionaryCache")
	private DictionaryCache<String, ListTO<String>> listDictionaryCache;
	
	/**
	 * Dictionary cache to hold dictionary maps
	 */
	@Autowired(required = true)
	@Qualifier("mapDictionaryCache")
	private DictionaryCache<String, MapTO> mapDictionaryCache;
	
	/**
	 * Dictionary cache to hold dictionary replaceInPattern lists
	 */
	@Autowired(required = true)
	@Qualifier("replaceInPatternDictionaryCache")
	private DictionaryCache<String, ListTO<ReplaceInPatternItem>> replaceInPatternDictionaryCache;
	
	/**
	 * Dictionary cache to hold dictionary insertInPattern lists
	 */
	@Autowired(required = true)
	@Qualifier("insertInPatternDictionaryCache")
	private DictionaryCache<String, ListTO<InsertInPatternItem>> insertInPatternDictionaryCache;
	
	/**
	 * Dictionary cache to hold attribute details
	 */
	@Autowired(required = true)
	@Qualifier("attributeDictionaryCache")
	private DictionaryCache<String, AttributeTO> attributeDictionaryCache;
	
	/**
	 * Dictionary cache to hold operation details
	 */
	@Autowired(required = true)
	@Qualifier("operationDictionaryCache")
	private DictionaryCache<String, OperationTO> operationDictionaryCache;
	
	/**
	 * Dictionary cache to text parse activities
	 */
	@Autowired(required = true)
	@Qualifier("parseActivityDictionaryCache")
	private DictionaryCache<String, ListTO<ParseActivityItem>> parseActivityDictionaryCache;
	

	/**
	 * Method to load lists from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadListDictionaryCache() throws TextParserException {
		LOGGER.info("loadListDictionaryCache starts...");
		List<ListTO<String>> listTOs = workbookUtil.getListsFromSheet(DictionaryConstants.LIST);
		loadCache(listDictionaryCache, buildMapFromList(listTOs));
		LOGGER.info("loadListDictionaryCache ends.");
	}
	
	/**
	 * Method to load maps from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadMapDictionaryCache() throws TextParserException {
		LOGGER.info("loadMapDictionaryCache starts...");
		List<MapTO> listTOs = workbookUtil.getMapsFromSheet(DictionaryConstants.MAP);
		loadCache(mapDictionaryCache, buildMapFromList(listTOs));
		LOGGER.info("loadMapDictionaryCache ends.");
	}
	
	/**
	 * Method to load replaceInPatterns from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadReplaceInPatternDictionaryCache() throws TextParserException {
		LOGGER.info("loadReplaceInPatternDictionaryCache starts...");
		List<ListTO<ReplaceInPatternItem>> listTOs = workbookUtil.getReplaceInPatternsFromSheet(DictionaryConstants.REPLACE_IN_PATTERN);
		loadCache(replaceInPatternDictionaryCache, buildMapFromList(listTOs));
		LOGGER.info("loadReplaceInPatternDictionaryCache ends.");
	}
	
	/**
	 * Method to load insertInPatterns from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadInsertInPatternDictionaryCache() throws TextParserException {
		LOGGER.info("loadInsertInPatternDictionaryCache starts...");
		List<ListTO<InsertInPatternItem>> listTOs = workbookUtil.getInsertInPatternsFromSheet(DictionaryConstants.INSERT_IN_PATTERN);
		loadCache(insertInPatternDictionaryCache, buildMapFromList(listTOs));
		LOGGER.info("loadInsertInPatternDictionaryCache ends.");
	}
	
	/**
	 * Method to load attribute details from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadAttributeDictionaryCache() throws TextParserException {
		LOGGER.info("loadAttributeDictionaryCache starts...");
		List<AttributeTO> attributes = workbookUtil.getAttributesFromSheet(DictionaryConstants.ATTRIBUTE);
		loadCache(attributeDictionaryCache, buildMapFromList(attributes));
		LOGGER.info("loadAttributeDictionaryCache ends.");
	}
	
	/**
	 * Method to load operation details from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadOperationDictionaryCache() throws TextParserException {
		LOGGER.info("loadOperationDictionaryCache starts...");
		List<OperationTO> operations = workbookUtil.getOperationsFromSheet(DictionaryConstants.OPERATION);
		loadCache(operationDictionaryCache, buildMapFromList(operations));
		LOGGER.info("loadOperationDictionaryCache ends.");
	}
	
	/**
	 * Method to load parse activity details from workbook to respective cache
	 * 
	 * @throws TextParserException
	 */
	public void loadParseActivityDictionaryCache() throws TextParserException {
		LOGGER.info("loadParseActivityDictionaryCache starts...");
		List<ParseActivityItem> parseActivities = workbookUtil.getParseActivitiesFromSheet(DictionaryConstants.PARSE_ACTIVITY);
		
		ListTO<ParseActivityItem> parseActivityListTO = new ListTO<>();
		parseActivityListTO.setName(DictionaryConstants.PARSE_ACTIVITY);
		parseActivityListTO.setValue(parseActivities);
		
		List<ListTO<ParseActivityItem>> parseActivitylist = new ArrayList<>();
		parseActivitylist.add(parseActivityListTO);
		
		loadCache(parseActivityDictionaryCache, buildMapFromList(parseActivitylist));
		LOGGER.info("loadParseActivityDictionaryCache ends.");
	}
	
	/**
	 * Helper method to load map values to given cache
	 * 
	 * @param 	cache
	 * @param 	map
	 */
	private <K, V> void loadCache(DictionaryCache<K, V> cache, Map<K, V> map) {
		LOGGER.info("loadCache starts...");
		if(map == null || map.isEmpty()) {
			LOGGER.info("loadCache - skipping - map is null/empty");
		}
		
		List<K> cachedKeys = cache.getKeys();
		if(cachedKeys != null && !cachedKeys.isEmpty()) {
			List<K> obsoleteKeys = new ArrayList<>();
			for(K cachedKey: cachedKeys) {
				if(!map.containsKey(cachedKey)) {
					obsoleteKeys.add(cachedKey);
				}
			}
			
			if(!obsoleteKeys.isEmpty()) {
				cache.removeAll(obsoleteKeys);
				LOGGER.info("loadCache - Removed obsolete records - count - " + obsoleteKeys.size());
			}
		}
		
		cache.putAll(map);
		LOGGER.info("loadCache ends.");
	}
	
	/**
	 * Helper method to build map from list of Dictionary Items
	 * 
	 * @param 	dictionaryItems
	 * 
	 * @return	dictionaryItems as map	
	 */
	private <T extends DictionaryItem> Map<String, T> buildMapFromList(List<T> dictionaryItems) {
		if(dictionaryItems == null || dictionaryItems.isEmpty()) {
			return null;
		}
		
		Map<String, T> map = new HashMap<String, T>();
		for(T dictionaryItem: dictionaryItems) {
			if(StringUtils.isNotBlank(dictionaryItem.getName())) {
				map.put(dictionaryItem.getName(), dictionaryItem);
			}
			else {
				LOGGER.info("buildMapFromList - Ignoring invalid dictionary item - " + dictionaryItem);
			}
		}
		return map;
	}

}
