package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import main.Logger;
import main.Pixels;

import org.lwjgl.util.vector.Vector3f;

import blocks.Block;
import blocks.Blocks;

public class Chunk {

	private Map<Coord3d, Block> blocks = new HashMap<Coord3d, Block>();
	private List<Coord3d> renderableBlocks = new ArrayList<Coord3d>();
	
	public static final int CHUNK_SIZE_X = 64;
	public static final int CHUNK_SIZE_Y = 64;
	public static final int CHUNK_SIZE_Z = 64;
	
	public Chunk(String heightmap) {
		generate(heightmap);
		initRenderableBlocks();		
	}
	
	public Map<Coord3d, Block> getRenderableBlocks() {
		Map<Coord3d, Block> rBlocks = new HashMap<Coord3d, Block>();
		for(Coord3d c:renderableBlocks)
			rBlocks.put(c, blocks.get(c));
			
		return rBlocks;
	}
	
	private void generate(String heightmap) {
		try {
			// get the BufferedImage, using the ImageIO class
			BufferedImage image = 
	        ImageIO.read(new File(Pixels.RES_DIRECTORY + heightmap + ".png"));
			int w = image.getWidth();
		    int h = image.getHeight();

		    for (int i = 0; i < h; i++) {
		      for (int j = 0; j < w; j++) {
		        int pixel = image.getRGB(j, i);
			    int red = (pixel >> 16) & 0xff;
			    int green = (pixel >> 8) & 0xff;
			    int blue = (pixel) & 0xff;
			    if(!(red == 255 & green == 0 && blue == 255)) {
			    	int pHeight = (int) Math.ceil((red)/8);
			    	Logger.err(new Coord3d(j, pHeight, i));
			    	blocks.put(new Coord3d(j, pHeight, i), Blocks.grass);
			    	for(int g = 0; g < pHeight; g++) {
				    	blocks.put(new Coord3d(j, pHeight-g-1, i), g >= 2 ? Blocks.rock : Blocks.dirt);
			    }
			    		
			    }
		      }
		    }
	    } catch (IOException e) {
	      Logger.err(e.getMessage(), 2);
	    }
		Logger.log("Generated chunk!", 2);
		Logger.log("Blocks in chunk: " + blocks.size(), 3);
	}
	
	public void initRenderableBlocks() {
		Logger.log("Initializing renderable blocks..", 3);
		for(Coord3d key:blocks.keySet()){
			if(true || !blocks.containsKey(new Coord3d(key.x+1, key.y, key.z)) ||
					!blocks.containsKey(new Coord3d(key.x-1, key.y, key.z)) ||
					!blocks.containsKey(new Coord3d(key.x, key.y+1, key.z)) ||
					!blocks.containsKey(new Coord3d(key.x, key.y-1, key.z)) ||
					!blocks.containsKey(new Coord3d(key.x, key.y, key.z+1)) ||
					!blocks.containsKey(new Coord3d(key.x, key.y, key.z-1))) {
				renderableBlocks.add(key);		
				}
		}
		Logger.log("Initialized renderable blocks!", 3);
		Logger.log("Renderable blocks in chunk: " + renderableBlocks.size(), 4);
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
		return getBlock(new Vector3f(x, y, z));
	}
	
	/**
	 * Gets the block at specified position
	 * 
	 * @param pos The XYZ vector
	 * @return If exists, Block at xyz, otherwise NULL
	 */
	public Block getBlock(Vector3f pos) {
		return blocks.containsKey(pos) ? blocks.get(pos) : null;
	}
}
