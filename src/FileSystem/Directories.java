package FileSystem;

public class Directories {
	public static final String RESOURCES = "resources/";
	public static final String LOGS = "logs/";
	public static final String CACHE = "cache/";
	public static final String WORLDS = "cache/world/";
	
	public static final String BLOCKS = RESOURCES + "blocks/";
	public static final String OBJ = RESOURCES + "obj/";	

	public static final String HASHES = CACHE + "hashes/";
	
	public static String[] GetCacheableDirectories() {
		return new String[] {
				RESOURCES,
				LOGS,
				CACHE,
				BLOCKS,
				OBJ,
				HASHES,
				WORLDS
		};
	}
}
