/**
 * 
 */
package com.cts.textparser.cache;

import java.util.List;
import java.util.Map;

/**
 * Interface that defines list of method the dictionary caches must implement.
 * 
 * @author 153093
 *
 */
public interface DictionaryCache<K, V> {
	
	/**
	 * To retrieve value from the cache by key
	 * 
	 * @param 	key
	 * 
	 * @return 	matched value for the key
	 */
	public V get(K key);
	
	/**
	 * To retrieve all the keys
	 * 
	 * @return 
	 */
	public List<K> getKeys();
	
	/**
	 * To place a value to the cache
	 * 
	 * @param 	key
	 * @param	value
	 * 
	 * @return	Previous value if any with same key, otherwise null
	 */
	public V put(K key, V value);
	
	/**
	 * To place map value to cache
	 * 
	 * @param 	map
	 * 
	 */
	public void putAll(Map<K, V> map);
	
	/**
	 * To remove an entry from the cache
	 * 
	 * @param 	key
	 * 
	 * @return	Value which is removed from the cache, otherwise null
	 */
	public V remove(K key);
	
	/**
	 * To remove given entries from the cache using keys
	 * 
	 * @param 	keys
	 */
	public void removeAll(List<K> keys);
	
	/**
	 * To retrieve the size of cache
	 * 
	 * @return total elements in the cache
	 */
	public int size();

	/**
	 * @return the name
	 */
	public String getName();
}
