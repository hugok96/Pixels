package world;

import java.util.HashMap;
import java.util.Map;

import main.Logger;
import blocks.Block;
import blocks.Blocks;

public class Chunk {

	private Map<Coord3d, Block> blocks = new HashMap<Coord3d, Block>();
	
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_SIZE_Y = 64;
	public static final int CHUNK_AMPLIFICATION = 10;
	public static final float CHUNK_FEATURES = 0.003f;
	public static final float CHUNK_SMOOTHNESS = 1.5F;
	
	private final int x;
	private final int z;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		generate(x, z);	
	}
	
	private void generate(int x, int z) {
		float[][] noise = ChunkGenerator.generateNoise(x, z);
		for(int i = 0; i < noise.length; i++) {
			for(int j = 0; j < noise[i].length; j++) {
		    	int pHeight = Chunk.CHUNK_AMPLIFICATION + 4 + ((int) Math.round(noise[i][j]));
				blocks.put(new Coord3d(j, pHeight, i), Blocks.grass);
		    	for(int g = 0; g < pHeight; g++) {
		    		blocks.put(new Coord3d(j, pHeight-g-1, i), g >= 2 ? Blocks.rock : Blocks.dirt);
		    	}
			}
		}
		Logger.log("Generated chunk!", 2);
		Logger.log("Blocks in chunk: " + blocks.size(), 3);
	}
	
	
	/**
	 * Gets the block at specified position
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @return If exists, Block at xyz, otherwise NULL
	 */
	public Block getBlock(int x, int y, int z) {
		return getBlock(new Coord3d(x, y, z));
	}
	
	/**
	 * Gets the block at specified position
	 * 
	 * @param pos The XYZ coord
	 * @return If exists, Block at xyz, otherwise NULL
	 */
	public Block getBlock(Coord3d pos) {
		return blockExists(pos) ? blocks.get(pos) : null;
	}
	
	/**
	 * Checks if a block exists at given x,y,z
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @return True if exists, false otherwise
	 */
	public boolean blockExists(int x, int y, int z) {
		return blockExists(new Coord3d(x, y, z));
	}
	
	/**
	 * Checks if a block exists at given position
	 * 
	 * @param pos The XYZ coord
	 * @return True if exists, false otherwise
	 */
	public boolean blockExists(Coord3d pos) {
		return blocks.containsKey(pos);
	}
	
	public Map<Coord3d, Block> getBlocks() {
		return this.blocks;
	}
}
