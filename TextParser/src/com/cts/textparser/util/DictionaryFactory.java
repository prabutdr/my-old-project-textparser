package com.cts.textparser.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cts.textparser.cache.DictionaryCache;
import com.cts.textparser.constant.DictionaryConstants;
import com.cts.textparser.constant.ErrorConstants;
import com.cts.textparser.constant.GeneralConstants;
import com.cts.textparser.to.DictionaryItem;
import com.cts.textparser.to.MapTO;

/**
 * The factory class to manage dictionary data in cache
 * 
 * @author 153093
 *
 */
@Component("dictionaryFactory")
public class DictionaryFactory {

	private static final Logger LOGGER = Logger.getLogger(DictionaryFactory.class);

	/**
	 * Reference to hold all dictionary caches
	 */
	@Resource(name="dictionaryCaches")
	private Map<String, DictionaryCache<String, DictionaryItem>> dictionaryCaches;
	
	/**
	 * Common utility reference
	 */
	@Autowired(required = true)
	@Qualifier("applicationUtil")
	private ApplicationUtil applicationUtil;
	

	/**
	 * To retrieve all attribute names as list
	 * 
	 * @return
	 */
	public List<String> getAttributeNames() {
		return dictionaryCaches.get(DictionaryConstants.ATTRIBUTE).getKeys();
	}
	
	/**
	 * To retrieve value object from dictionary using its reference
	 * 
	 * @param 	name
	 * 
	 * @return
	 * 
	 * @throws 	TextParserException
	 */
	public Object getValue(String reference) throws TextParserException {
		if (StringUtils.isBlank(reference)
				|| !reference.matches(DictionaryConstants.REFERENCE_NAME_FORMAT)) {
			// Name is blank or not a valid dictionary reference, return as it is
			return reference;
		}
		
		List<String> nameHierarchy = getNameHierarchy(reference); // TODO: Can we store splitted name hierarchy directly in dictionary, instead of splitting it every time
		if (nameHierarchy.size() <= DictionaryConstants.CATEGORY_NAME_INDEX) {
			// As unable to identify dictionary category
			LOGGER.info("Dictionary category not specified - " + reference);
			throw applicationUtil.buildTextParserException(ErrorConstants.INVALID_DICTIONARY_ITEM_REFERENCE);
		}
		
		// Retrieve dictionary category
		DictionaryCache<String, DictionaryItem> dictionaryCategory = dictionaryCaches.get(nameHierarchy.get(DictionaryConstants.CATEGORY_NAME_INDEX));
		if (dictionaryCategory == null) {
			// Dictionary category not exist
			LOGGER.info("Dictionary category not exists - " + reference);
			throw applicationUtil.buildTextParserException(ErrorConstants.INVALID_DICTIONARY_ITEM_REFERENCE);
		}
		
		if (nameHierarchy.size() <= DictionaryConstants.ITEM_NAME_INDEX) {
			// Dictionary item name in category not provided then return category reference
			return dictionaryCategory;
		}
		
		// Retrieve dictionary item from the category
		DictionaryItem dictionaryItem = dictionaryCategory.get(nameHierarchy.get(DictionaryConstants.ITEM_NAME_INDEX));
		if (dictionaryItem == null) {
			// Dictionary item not exist
			LOGGER.info("Dictionary item not found in given dictionary category - " + reference);
			throw applicationUtil.buildTextParserException(ErrorConstants.INVALID_DICTIONARY_ITEM_REFERENCE);
		}
		
		if (nameHierarchy.size() <= DictionaryConstants.ITEM_PROPERTY_NAME_INDEX) {
			// Dictionary sub item name in data item not provided then return dictionary item reference
			return dictionaryItem;
		}
		
		return getProperty(dictionaryItem, nameHierarchy.get(DictionaryConstants.ITEM_PROPERTY_NAME_INDEX));
	}
	
	/**
	 * Helper method to retrieve property value from a dictionary item
	 * 
	 * @param 	dictionaryItem
	 * @param 	propertyName
	 * 
	 * @return
	 * 
	 * @throws TextParserException
	 */
	private Object getProperty(DictionaryItem dictionaryItem, String propertyName) throws TextParserException {
		String methodName = GeneralConstants.PROPERTY_METHOD_PREFIX + WordUtils.capitalize(propertyName);
		Object result = null;
		try {
			Method methodRef = dictionaryItem.getClass().getMethod(methodName);
			result = methodRef.invoke(dictionaryItem);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error("Exception occurred while retrieving property from dictionary item - " + propertyName, e);
			throw applicationUtil.buildTextParserException(ErrorConstants.INVALID_DICTIONARY_ITEM_REFERENCE, e);
		}
		return result;
	}
	
	/**
	 * Helper method to retrieve name split hierarchy by eliminating delimiters
	 * 
	 * @param name
	 * @return
	 */
	private static List<String> getNameHierarchy(String name) {
		List<String> nameHierarchy = new ArrayList<>();
		
		nameHierarchy =  new ArrayList<>(Arrays.asList(name.split(DictionaryConstants.REFERENCE_NAME_DELIMITER)));
		Iterator<String> iterator = nameHierarchy.iterator();
		while (iterator.hasNext()) {
			if(StringUtils.isBlank(iterator.next())) {
				iterator.remove(); // Remove empty's
			}
		}
		
		return nameHierarchy;
	}
	
	public static void main(String[] args) {
		String s = "${list.listName.values}";
		
		System.out.println(getNameHierarchy(s));
		System.out.println(s.matches(DictionaryConstants.REFERENCE_NAME_FORMAT));
		System.out.println("${".matches(DictionaryConstants.REFERENCE_NAME_FORMAT));
		
		DictionaryItem dictionaryItem = new MapTO();
		System.out.println(dictionaryItem.getClass());
		dictionaryItem = new DictionaryItem();
		System.out.println(dictionaryItem.getClass());
	}
}
