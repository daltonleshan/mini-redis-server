package Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RMap implements DataStructure {
	private Map<String, Map<String, String>> cache;

	public RMap() {
		cache = new HashMap<>();
	}

	@Override
	public Object get(String key) {
		assert (key != null && !key.equals("")) : "invalid key";
		return cache.get(key.trim());
	}

	@Override
	public Object set(String key, String value) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (value != null && !value.equals("")) : "invalid value";
		key = key.trim();
		value = value.trim();
		Map<String, String> toReplace = new HashMap<>();
		String[] valArray = value.split(",");
		String innerKey = valArray[0].trim();
		assert (!innerKey.equals("")) : "key to the inner map cannot be an empty string";
		String s = valArray[valArray.length - 1].trim();
		if (!s.equals("")) {
			toReplace.put(innerKey, s);
		}
		return cache.put(key, toReplace);
	}

	@Override
	public boolean delete(String key) {
		assert (key != null && !key.equals("")) : "invalid key";
		key = key.trim();
		if (cache.containsKey(key)) {
			cache.remove(key);
			return true;
		}
		return false;
	}

	public String mapGet(String key, String mapkey) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (mapkey != null && !mapkey.equals("")) : "invalid inner mapkey";
		key = key.trim();
		mapkey = mapkey.trim();
		@SuppressWarnings("unchecked")
		Map<String, String> innerMap = (Map<String, String>) get(key);
		String toReturn = "";
		if (innerMap != null) {
			if ((toReturn = innerMap.get(mapkey)) != null)
				return toReturn;
		}
		return toReturn;
	}

	public void mapSet(String key, String mapkey, String mapvalue) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (mapkey != null && !mapkey.equals("")) : "invalid inner mapkey";
		assert (mapvalue != null && !mapvalue.equals("")) : "invalid inner mapvalue";
		key = key.trim();
		mapkey = mapkey.trim();
		mapvalue = mapvalue.trim();
		@SuppressWarnings("unchecked")
		Map<String, String> innerMap = (Map<String, String>) get(key);
		assert (innerMap != null) : "value with associted key not found";
		String[] arr = mapvalue.split(",");
		String s = arr[arr.length - 1].trim();
		if (!s.equals("")) {
			innerMap.put(mapkey, s);
			cache.replace(key, innerMap);
		}
	}

	public boolean mapDelete(String key, String mapkey) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (mapkey != null && !mapkey.equals("")) : "invalid inner mapkey";
		key = key.trim();
		mapkey = mapkey.trim();
		@SuppressWarnings("unchecked")
		Map<String, String> innerMap = (Map<String, String>) get(key);
		if (innerMap.containsKey(mapkey)) {
			innerMap.remove(mapkey);
			return true;
		}
		return false;
	}

	public Map<String, Map<String, String>> getCache() {
		return cache;
	}

	@Override
	public Object searchKeys(String key) {
		Set<String> keySet = cache.keySet();
		List<String> toReturn = new ArrayList<>();
		key = key.trim();
		for (String s : keySet) {
			if (s.contains(key) || key.contains(s)) {
				toReturn.add(s);
			}
		}
		return toReturn;
	}

	@Override
	public Object getAllKeys() {
		return cache.keySet();
	}
}
