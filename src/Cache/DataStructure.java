package Cache;

public interface DataStructure {
	public Object get(String key);

	public Object set(String key, String value);

	public Object searchKeys(String key);
	
	public Object getAllKeys();

	public boolean delete(String key);
}
