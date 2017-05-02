Known Bugs:
	I wrote this program in a rush but was sure to test it thoroughly and use assert statements
	to avoid most edge cases. There are not any bugs that I know of: please let me know if you
	find one :)
	
Design Choice:
	I used a cache to save user data. This means that user data exists for as long as the server 
	is running. Otherwise, all the data that the user inputs is lost. This therefore means that 
	the program is basically a temporary storage for user data. An improvement to this program
	would be replacing the cache in DataStructure with a SQL Database for long term storage.
	I have 3 DataStructures: RString, RList and RMap that basically act as temporary storage for
	user data. These DataStructures support various functionalities including:
###
	RString:
	
    * GET key: Return the String value identified by key
    * SET key value: Instantiate or overwrite a String identified by key with value value
    * DELETE key: Delete the String identified by key
###
	RList:
	
    * GET key: Return the List value identified by key
    * SET key value: Instantiate or overwrite a List identified by key with value value
    * DELETE key: Delete the List identified by key
    * APPEND key value: Append a String value to the end of the List identified by key
    * POP key: Remove the last element in the List identified by key, and return that element.
###
	RMap:
	
    * GET key: Return the Map value identified by key
    * SET key value: Instantiate or overwrite a Map identified by key with value value
    * DELETE key: Delete the Map identified by key
    * MAPGET key mapkey: Return the String identified by mapkey from within the Map identified by key.
    * MAPSET key mapkey mapvalue: Add the mapping mapkey -> mapvalue to the Map identified by key.
    * MAPDELETE key mapkey: Delete the value identified by mapkey from the Map identified by key.
###
Operations on Keys

	SEARCH-KEYS query: Returns a List<String> containing all keys matching the query
	GET-KEYS: Returns a List containing all keys in the cache
	
###
JUnit Testing:
	My JUnit tests cover all functionalities required for the assignment and also tests for possible
	edge cases.
###
Additional Features:
	To append to a List, you just need to type in a key and however many values you want to be 
	associated with this key.
	SEARCH-KEYS works in such a way that all keys that are substrings of the given key (or vice
	-versa) are returned.
###
How to Run:
	Install the project in Eclipse.
	Go to Run Configurations and add --gui as an argument
	Run the program as a Java Application
	Visit http://localhost:4567/redis
