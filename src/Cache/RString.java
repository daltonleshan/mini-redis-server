package Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RString implements DataStructure {
	private Map<String, String> cache;

	public RString() {
		cache = new HashMap<>();
	}

	@Override
	public Object get(String key) {
		return cache.get(key.trim());
	}

	@Override
	public Object set(String key, String value) {
		return cache.put(key.trim(), value.trim());
	}

	@Override
	public boolean delete(String key) {
		key = key.trim();
		if (cache.containsKey(key)) {
			cache.remove(key);
			return true;
		}
		return false;
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
