package blocks;

import textures.Textures;

public class Blocks {
	private static int blockCount = 1;
	public static final int TEXTURE_SIDE = 1;
	public static final int TEXTURE_TOP = 2;
	public static final int TEXTURE_BOTTOM = 3;

	public static Block grass;
	public static Block dirt;
	public static Block rock;
	public static Block tnt;
	
	public static void initialize() {
		grass = new Block(Textures.grass, Textures.grass_top, Textures.dirt, "grass");
		dirt = new Block(Textures.dirt, "dirt");
		rock = new Block(Textures.stone, "stone");
		tnt = new Block(Textures.tnt, Textures.tnt_top, Textures.tnt_bottom, "tnt");
	}
	
	public static int generateId() {
		return blockCount++;
	}
}
