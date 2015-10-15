package blocks;

import java.util.HashMap;
import java.util.Map;

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
	
	private static Map<Integer, Block> BlockIndices = new HashMap<Integer, Block>();
	
	public static void initialize() {
		grass = new Block(Textures.grass, Textures.grass_top, Textures.dirt, "grass");
		dirt = new Block(Textures.dirt, "dirt");
		rock = new Block(Textures.stone, "stone");
		tnt = new Block(Textures.tnt, Textures.tnt_top, Textures.tnt_bottom, "tnt");
	}
	
	public static Block getBlockById(int id) {
		return BlockIndices.get(id);
	}
	
	public static int generateId(Block block) {
		BlockIndices.put(blockCount, block);
		return blockCount++;
	}
}
