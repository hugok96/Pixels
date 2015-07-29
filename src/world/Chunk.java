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
	
	private final int x;
	private final int z;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		generate(x, z);
		//initRenderableBlocks();		
	}
	
//	public Map<Coord3d, Block> getRenderableBlocks() {
//		Map<Coord3d, Block> rBlocks = new HashMap<Coord3d, Block>();
//		for(Coord3d c:renderableBlocks)
//			rBlocks.put(c, blocks.get(c));
//			
//		return rBlocks;
//	}
	
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
		
//		try {
//			// get the BufferedImage, using the ImageIO class
//			BufferedImage image = 
//	        ImageIO.read(new File(Pixels.RES_DIRECTORY + heightmap + ".png"));
//			int w = image.getWidth();
//		    int h = image.getHeight();
//
//		    for (int i = 0; i < h; i++) {
//		      for (int j = 0; j < w; j++) {
//		        int pixel = image.getRGB(j, i);
//			    int red = (pixel >> 16) & 0xff;
//			    int green = (pixel >> 8) & 0xff;
//			    int blue = (pixel) & 0xff;
//			    if(!(red == 255 & green == 0 && blue == 255)) {
//			    	int pHeight = (int) Math.ceil((red)/8);
//			    	if(blocks.containsKey(new Coord3d(j, pHeight, i)))
//		    			Logger.err("Block already exists: " + (j-(w/2)) + ", " + pHeight + ", " +  (i-(h/2)) + " - " + Blocks.grass.getName());
//		    			
//			    	blocks.put(new Coord3d(j, pHeight, i), Blocks.grass);
//			    	for(int g = 0; g < pHeight; g++) {
//			    		if(blocks.containsKey(new Coord3d(j, pHeight-g-1, i)))
//			    			Logger.err("Block already exists: " + (j-(w/2)) + ", " + pHeight + ", " +  (i-(h/2)) + " - " + Blocks.grass.getName());
//			    		blocks.put(new Coord3d(j, pHeight-g-1, i), g >= 2 ? Blocks.rock : Blocks.dirt);
//			    }
//			    		
//			    }
//		      }
//		    }
//	    } catch (IOException e) {
//	      Logger.err(e.getMessage(), 2);
//	    }
		Logger.log("Generated chunk!", 2);
		Logger.log("Blocks in chunk: " + blocks.size(), 3);
	}
	
//	public void initRenderableBlocks() {
//		Logger.log("Initializing renderable blocks..", 3);
//		for(Coord3d key:blocks.keySet()){
//			if(!blocks.containsKey(new Coord3d(key.x+1, key.y, key.z)) ||
//					!blocks.containsKey(new Coord3d(key.x-1, key.y, key.z)) ||
//					!blocks.containsKey(new Coord3d(key.x, key.y+1, key.z)) ||
//					!blocks.containsKey(new Coord3d(key.x, key.y-1, key.z)) ||
//					!blocks.containsKey(new Coord3d(key.x, key.y, key.z+1)) ||
//					!blocks.containsKey(new Coord3d(key.x, key.y, key.z-1))) {
//				renderableBlocks.add(key);		
//				}
//		}
//		Logger.log("Initialized renderable blocks!", 3);
//		Logger.log("Renderable blocks in chunk: " + renderableBlocks.size(), 4);
//	}
	
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
