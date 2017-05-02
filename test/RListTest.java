import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import Cache.RList;

public class RListTest {

	@Test
	public void testAppend() {
		RList rList = new RList();
		Map<String, List<String>> checker = new HashMap<>();
		rList.append("one", "This is the first value");
		rList.append("two", "This is the second value");
		rList.append("three", "This is the third value");
		rList.append("four", "This is the fourth value");
		List<String> first = new ArrayList<>();
		first.add("This is the first value");
		checker.put("one", first);
		List<String> second = new ArrayList<>();
		second.add("This is the second value");
		checker.put("two", second);
		List<String> third = new ArrayList<>();
		third.add("This is the third value");
		checker.put("three", third);
		List<String> fourth = new ArrayList<>();
		fourth.add("This is the fourth value");
		checker.put("four", fourth);
		assert (rList.getCache().equals(checker));
	}

	@Test
	public void testPop() {
		RList rList = new RList();
		Map<String, List<String>> checker = new HashMap<>();
		rList.append("one", "This is the first value");
		rList.append("one", "This is the second value");
		checker.put("one", new ArrayList<>());
		String popped1 = rList.pop("one");
		String popped2 = rList.pop("one");
		assert (popped1.equals("This is the second value"));
		assert (popped2.equals("This is the first value"));
		assert (rList.getCache().equals(checker));
	}

	@Test
	public void testSet() {
		RList rList = new RList();
		rList.append("one", "This is the first value");
		rList.append("two", "This is the second value");
		rList.set("one", "This is the second value");
		rList.set("two", "This is the first value");
		List<String> testList = new ArrayList<>();
		testList.add("This is the second value");
		assert (rList.get("one").equals(testList));
		testList.clear();
		testList.add("This is the first value");
		assert (rList.get("two").equals(testList));
	}

	@Test
	public void testDelete() {
		RList rList = new RList();
		rList.append("one", "This is the first value");
		rList.append("two", "This is the second value");
		rList.delete("one");
		rList.delete("two");
		assert (rList.getCache().equals(new HashMap<>()));
	}

	@Test
	public void edgeCases() {
		RList rList = new RList();
		try {
			rList.append("", "This is the first value");
		} catch (AssertionError e) {
			assert (e.getMessage().equals("invalid key"));
		}
		try {
			rList.append("one", "");
		} catch (AssertionError e) {
			assert (e.getMessage().equals("invalid value"));
		}
		try {
			rList.append(null, null);
		} catch (AssertionError e) {
			assert (e.getMessage().equals("invalid key"));
		}
	}
}
