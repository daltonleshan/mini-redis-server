import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import Cache.RMap;

public class RMapTest {
	@Test
	public void testSet() {
		RMap rMap = new RMap();
		rMap.set("one", "firstkey, firstvalue");
		rMap.set("two", "secondkey, secondvalue");
		rMap.set("one", "thirdkey, thirdvalue");
		Map<String, Map<String, String>> checker = new HashMap<>();
		Map<String, String> innerMap1 = new HashMap<>();
		Map<String, String> innerMap2 = new HashMap<>();
		innerMap1.put("thirdkey", "thirdvalue");
		innerMap2.put("secondkey", "secondvalue");
		checker.put("one", innerMap1);
		checker.put("two", innerMap2);
		assert (rMap.getCache().equals(checker));
	}

	@Test
	public void testMapGet() {
		RMap rMap = new RMap();
		rMap.set("one", "firstkey, firstvalue");
		rMap.set("two", "secondkey, secondvalue");
		Map<String, String> innerMap1 = new HashMap<>();
		Map<String, String> innerMap2 = new HashMap<>();
		innerMap1.put("firstkey", "firstvalue");
		innerMap2.put("secondkey", "secondvalue");
		assert (rMap.get("one").equals(innerMap1));
		assert (rMap.get("two").equals(innerMap2));
	}

	@Test
	public void testMapSet() {
		RMap rMap = new RMap();
		rMap.set("one", "firstkey, firstvalue");
		rMap.set("two", "secondkey, secondvalue");
		Map<String, String> innerMap1 = new HashMap<>();
		Map<String, String> innerMap2 = new HashMap<>();
		innerMap1.put("firstkey", "firstvalue");
		innerMap1.put("secondkey", "secondvalue");
		innerMap2.put("firstkey", "firstvalue");
		innerMap2.put("secondkey", "secondvalue");
		rMap.mapSet("one", "secondkey", "secondvalue");
		rMap.mapSet("two", "firstkey", "firstvalue");
		Map<String, Map<String, String>> checker = new HashMap<>();
		checker.put("one", innerMap1);
		checker.put("two", innerMap2);
		assert (rMap.getCache().equals(checker));
	}

	@Test
	public void testMapDelete() {
		RMap rMap = new RMap();
		rMap.set("one", "firstkey, firstvalue");
		rMap.set("two", "secondkey, secondvalue");
		rMap.mapDelete("one", "firstkey");
		rMap.mapDelete("two", "secondkey");
		Map<String, Map<String, String>> checker = new HashMap<>();
		checker.put("one", new HashMap<String, String>());
		checker.put("two", new HashMap<String, String>());
		assert (rMap.getCache().equals(checker));
	}

	@Test
	public void edgeCases() {
		RMap rMap = new RMap();
		assertFalse(rMap.delete("one"));
		try {
			rMap.set(null, "");
		} catch (AssertionError e) {
			assert (e.getMessage().equals("invalid key"));
		}
		try {
			rMap.mapSet("one", null, null);
		} catch (AssertionError e) {
			assert (e.getMessage().equals("invalid inner mapkey"));
		}
		try {
			rMap.mapSet("one", "two", "three");
			assertFalse(rMap.delete(""));
			rMap.set("", "");
			rMap.mapSet(null, "one", "two");
		} catch (AssertionError e) {
			assert (e.getMessage().equals("value with associted key not found"));
		}
	}
}
