package blocks;

import main.Pixels;
import textures.Textures;

public class Blocks {
	private static int blockCount = 1;
	public static final int TEXTURE_SIDE = 1;
	public static final int TEXTURE_TOP = 2;
	public static final int TEXTURE_BOTTOM = 3;

	public static Block grass;
	public static Block dirt;
	public static Block rock;
	
	public static void initialize() {
		grass = new Block(Textures.grass_top);
		dirt = new Block(Textures.dirt);
		rock = new Block(Textures.stone);
	}
	
	public static int generateId() {
		return blockCount++;
	}
}
