package world;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.Logger;
import blocks.Block;

public class World {

	private static Map<Coord2d, Chunk> chunks = new HashMap<Coord2d, Chunk>();
	private static ConcurrentHashMap<Coord3d, Block> renderableBlocks = new ConcurrentHashMap<Coord3d, Block>();
	private static final int CHUNK_RENDER_DISTANCE = 1; //TODO: IMPLEMENT THIS!
		
	public static void generate() {
    	Logger.log("Generating chunks..", 1);
    	for(int i =0; i < 2; i ++) {
    		for(int j=0; j < 2; j++) {
    			chunks.put(new Coord2d(-1+i, -1+j), new Chunk("heightmap"));
    		}
    	}
		Logger.log("Chunks generated!", 1);
		Logger.log("Initializing renderable blocks..", 1);
		initRenderableBlocks();
	}
	
	private static void initRenderableBlocks() {
		new Thread(new Runnable() {
		    public void run() {
		    	Logger.log("Chunk count: " + chunks.size(), 2);
				for(Coord2d c:chunks.keySet()) {
					Logger.log("Chunk X:" + c.x + " Y:" + c.z + ".. ", 2);
					Map<Coord3d, Block> cBlocks = chunks.get(c).getBlocks();
					for(Coord3d key:cBlocks.keySet()) {
						Coord3d absPos = new Coord3d(c.x * Chunk.CHUNK_SIZE_X + key.x, key.y, c.z * Chunk.CHUNK_SIZE_Z + key.z);
						if(!blockExists(new Coord3d(absPos.x+1, absPos.y, absPos.z)) ||
								!blockExists(new Coord3d(absPos.x-1, absPos.y, absPos.z)) ||
								!blockExists(new Coord3d(absPos.x, absPos.y+1, absPos.z)) ||
								!blockExists(new Coord3d(absPos.x, absPos.y-1, absPos.z)) ||
								!blockExists(new Coord3d(absPos.x, absPos.y, absPos.z+1)) ||
								!blockExists(new Coord3d(absPos.x, absPos.y, absPos.z-1))) {
							renderableBlocks.put(absPos, cBlocks.get(key));
							}
					}
					
					Logger.log("Done!", 3);
				}

				Logger.log("Done initializing renderable blocks!", 1);
				Logger.log("Renderable blocks in world: " + renderableBlocks.size(), 2);
		    }
		}).start();
	}
	
	public static Map<Coord3d, Block> getRenderableBlocks() {
		return renderableBlocks;
	}
	
	public static Block getBlock(int x, int y, int z) {
		return getBlock(new Coord3d(x, y, z));
	}
	
	public static Block getBlock(Coord3d pos) {
		return blockExists(pos) ? chunks.get(getBlockChunkPosition(pos)).getBlock(getBlockInChunkPosition(pos)) : null;
	}
	
	public static boolean blockExists(int x, int y, int z) {
		return blockExists(new Coord3d(x, y, z)); 
	}
		
	private static Coord2d getBlockChunkPosition(Coord3d pos) {
		return new Coord2d((int) (pos.x >= 0 ? Math.floor(pos.x / Chunk.CHUNK_SIZE_X) : -1 + Math.ceil(pos.x / Chunk.CHUNK_SIZE_X)), 
							(int) (pos.z >= 0 ? Math.floor(pos.z / Chunk.CHUNK_SIZE_Z) : -1 + Math.ceil(pos.z / Chunk.CHUNK_SIZE_Z)));
	}

	public static boolean blockExists(Coord3d pos) { 			
		return blockExists(getBlockChunkPosition(pos), getBlockInChunkPosition(pos));
	}
	
	private static boolean blockExists(Coord2d chunkPos, Coord3d blockPos) {
		if(blockPos.x < 0 || blockPos.z < 0)
			Logger.err("Whoopsy daisy");
		return !chunks.containsKey(chunkPos) ? false : chunks.get(chunkPos).blockExists(blockPos);
	}
	
	private static Coord3d getBlockInChunkPosition(Coord3d pos) {
		pos.x = pos.x >= 0 ? pos.x % Chunk.CHUNK_SIZE_X : Chunk.CHUNK_SIZE_X - ((Math.abs(pos.x+1) % Chunk.CHUNK_SIZE_X)+1);
		pos.z = pos.z >= 0 ? pos.z % Chunk.CHUNK_SIZE_Z : Chunk.CHUNK_SIZE_Z - ((Math.abs(pos.z+1) % Chunk.CHUNK_SIZE_Z)+1);
		return pos;
	}
}
