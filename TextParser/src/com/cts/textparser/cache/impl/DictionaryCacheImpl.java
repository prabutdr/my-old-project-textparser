/**
 * 
 */
package com.cts.textparser.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cts.textparser.cache.DictionaryCache;

/**
 * Implementation class of DictionaryCache which will define dictionary cache operations
 * 
 * @author 153093
 *
 */
public class DictionaryCacheImpl<K, V> implements DictionaryCache<K, V> {

	private static final Logger LOGGER = Logger.getLogger(DictionaryCacheImpl.class);
	
	/**
	 * To specify name for the dictionary cache
	 */
	private String name;
	
	/**
	 * Internal cache reference which will hold all dictionary data
	 * as cache elements
	 */
	private Cache cache;
	
	/**
	 * Parameterized constructor to make cache is required.
	 * 
	 * @param	name
	 * 			Dictionary cache name to identify			
	 * 
	 * @param 	cache 
	 * 			Reference to hold data
	 * 
	 * @throws	IllegalArgumentException - Either 
	 */
	public DictionaryCacheImpl(String name, Cache cache) {
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Dictionary cache name is invalid - " + name);
		}
		if(cache == null) {
			throw new IllegalArgumentException("Cache reference should not be null");
		}
		
		this.cache = cache;
		this.name = name;
		LOGGER.debug("Constructed with name - " + name);
	}
	
	/**
	 * To retrieve value from the cache by key
	 * 
	 * @param 	key
	 * 
	 * @return 	matched value for the key
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		Element element = cache.get(key);

		// Return null if key not found or value is null, otherwise return value of matched key
		V value = (element == null || element.getObjectValue() == null)? null: (V)element.getObjectValue();
		return value;
	}

	/**
	 * To retrieve all the keys
	 * 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<K> getKeys() {
		return cache.getKeys();
	}
	
	/**
	 * To place a value to the cache
	 * 
	 * @param 	key
	 * @param	value
	 * 
	 * @return	Previous value if any with same key, otherwise null
	 */
	@Override
	public V put(K key, V value) {
		if (key == null || value == null) {
			LOGGER.debug(name + " - put - skipped - both key & value should not be null");
			return null;
		}
		
		// Retrieve previous value if any
		V existingValue = this.get(key);
		
		// Place current value to the cache
		cache.put(new Element(key, value));
		LOGGER.debug(name + " - put - placed value '" + value + "' with key '" + key + "'");
		
		return existingValue;
	}

	/**
	 * To place map value to cache
	 * 
	 * @param 	map
	 * 
	 */
	public void putAll(Map<K, V> map) {
		List<Element> elements = buildElementList(map);
		if (elements == null) {
			LOGGER.debug(name + " - putAll - skipped - map should not be null or empty");
			return;
		}
		
		cache.putAll(elements);
		LOGGER.debug(name + " - putAll - placed values - " + map);
	}
	
	/**
	 * To remove an entry from the cache
	 * 
	 * @param 	key
	 * 
	 * @return	Value which is removed from the cache, otherwise null
	 */
	@Override
	public V remove(K key) {
		if (key == null) {
			LOGGER.debug(name + " - remove - skipped - key should not be null");
			return null;
		}
		
		// Retrieve previous value if any
		V existingValue = this.get(key);
		
		cache.remove(key);
		LOGGER.debug(name + " - remove - removed value '" + existingValue + "' with key '" + key + "'");
		
		return existingValue;
	}

	/**
	 * To remove given entries from the cache using keys
	 * 
	 * @param 	keys
	 */
	public void removeAll(List<K> keys) {
		if(keys == null || keys.isEmpty()) {
			LOGGER.debug(name + " - removeAll - skipped - keys should not be null or empty");
			return;
		}
		
		cache.removeAll(keys);
		LOGGER.debug(name + " - removeAll - removed keys - " + keys);
	}
	
	/**
	 * To retrieve the size of cache
	 * 
	 * @return total elements in the cache
	 */
	@Override
	public int size() {
		return cache.getSize();
	}

	/**
	 * Helper method to convert map values into cache elements
	 * 
	 * @param 	map
	 * 
	 * @return	List of cache elements
	 */
	private List<Element> buildElementList(Map<K, V> map) {
		if(map == null || map.isEmpty()) {
			return null;
		}
		
		List<Element> elements = new ArrayList<>();
		for(Entry<K, V> entry: map.entrySet()) {
			if(entry.getKey() != null) {
				elements.add(new Element(entry.getKey(), entry.getValue()));
			}
		}
		
		return elements;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
