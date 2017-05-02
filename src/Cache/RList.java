package Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RList implements DataStructure {
	private Map<String, List<String>> cache;

	public RList() {
		cache = new HashMap<>();
	}

	@Override
	public Object get(String key) {
		assert (key != null && !key.equals("")) : "invalid key";
		return cache.get(key);
	}

	@Override
	public Object set(String key, String value) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (value != null && !value.equals("")) : "invalid value";
		key = key.trim();
		value = value.trim();
		List<String> toReplace = new ArrayList<>();
		String[] valArray = value.split(",");
		for (String s : valArray) {
			if (!s.equals("")) {
				toReplace.add(s);
			}
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

	public void append(String key, String value) {
		assert (key != null && !key.equals("")) : "invalid key";
		assert (value != null && !value.equals("")) : "invalid value";
		key = key.trim();
		value = value.trim();
		List<String> inCache = cache.get(key);
		String[] arr = value.split(",");
		List<String> toAdd = new ArrayList<String>();
		for (String s: arr){
			if (inCache == null) {
				toAdd.add(s.trim());
				cache.put(key, toAdd);
			} else {
				inCache.add(s.trim());
				cache.replace(key, inCache);
			}
		}
	}

	public String pop(String key) {
		assert (key != null && !key.equals("")) : "invalid key";
		key = key.trim();
		String toReturn = "";
		if (cache.containsKey(key)) {
			List<String> toRemove = cache.get(key);
			if (toRemove.size() > 0) {
				toReturn = toRemove.remove(toRemove.size() - 1);
			}
			cache.put(key, toRemove);
		}
		return toReturn;
	}

	public Map<String, List<String>> getCache() {
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
