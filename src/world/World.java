package world;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import FileSystem.Directories;
import FileSystem.Extensions;
import FileSystem.FileSystem;
import FileSystem.Files;
import main.Logger;
import blocks.Block;
import blocks.Blocks;

public class World {

	private static Map<Coord2d, Chunk> chunks = new HashMap<Coord2d, Chunk>();
	private static ConcurrentHashMap<Coord3d, Block> renderableBlocks = new ConcurrentHashMap<Coord3d, Block>();
	private static final int CHUNK_RENDER_DISTANCE = 1; //TODO: IMPLEMENT THIS!
		
	public static void generate() {
    	Logger.log("Generating chunks..", 1);
    	for(int i = -4; i < 4; i ++)
    		for(int j =-4; j<4;j++)
				chunks.put(new Coord2d(i, j), new Chunk(i, j));
		Logger.log("Chunks generated!", 1);
		Logger.log("Initializing renderable blocks..", 1);
		initRenderableBlocks();
	}
	
	public static boolean save() {
		StringBuilder map = new StringBuilder();
		for(Entry<Coord2d, Chunk> entry : chunks.entrySet()) {
			map.append(entry.getKey().x + ",");
			map.append(entry.getKey().z + ";");
			for(Entry<Coord3d, Block> e : entry.getValue().getBlocks().entrySet()) {
				map.append(e.getKey().x + ",");
				map.append(e.getKey().y + ",");
				map.append(e.getKey().z + ",");
				map.append(e.getValue().id + ";");
			}
			map.append("/");
		}
		File file = FileSystem.CreateNewFile(Files.WORLD_TEST_WORLD, "Test World", true);
		if(file != null && FileSystem.WriteFile(file, map.toString(), "Test World"))
			return true;
		return false;
	}
	
	public static boolean load(String worldName) {
		String mapString = FileSystem.ReadFile(Directories.WORLDS + worldName + Extensions.WORLDS, 3);
		if(mapString != null) {
	    	Logger.log("Generating chunks..", 1);
			String[] chunks = mapString.split("/");
			for(String c : chunks) {
				String[] lines = c.split(";");
				Coord2d coord = new Coord2d(0, 0);
				Map<Coord3d, Block> blocks = new HashMap<Coord3d, Block>();
				int i = 0;
				for(String data : lines) {
					String[] values = data.split(",");
					if(i++ == 0) {
						coord = new Coord2d(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					} else {
						blocks.put(new Coord3d(Integer.parseInt(values[0]), 
								Integer.parseInt(values[1]), 
								Integer.parseInt(values[2])),
								Blocks.getBlockById(Integer.parseInt(values[3])));
					}
				}
				World.chunks.put(coord, new Chunk(coord.x, coord.z, blocks));
			}
			Logger.log("Chunks generated!", 1);
			Logger.log("Initializing renderable blocks..", 1);
			initRenderableBlocks();
			return true;
		}
		return false;
	}
	
	private static void initRenderableBlocks() {
		new Thread(new Runnable() {
		    public void run() {
		    	Logger.log("Chunk count: " + chunks.size(), 2);
				for(Coord2d c:chunks.keySet()) {
					Logger.log("Chunk X:" + c.x + " Y:" + c.z + ".. ", 2);
					Map<Coord3d, Block> cBlocks = chunks.get(c).getBlocks();
					for(Coord3d key:cBlocks.keySet()) {
						Coord3d absPos = getAbsolutePosition(c, key);
						if(blockSeeable(absPos)) {
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
		
	private static Coord2d getBlockChunkPosition(Coord3d pos) {
		return new Coord2d((int) (pos.x >= 0 ? Math.floor(pos.x / Chunk.CHUNK_SIZE) : -1 + Math.ceil((pos.x+1) / Chunk.CHUNK_SIZE)), 
							(int) (pos.z >= 0 ? Math.floor(pos.z / Chunk.CHUNK_SIZE) : -1 + Math.ceil((pos.z+1) / Chunk.CHUNK_SIZE)));
	}
	
	private static Coord3d getBlockInChunkPosition(Coord3d pos) {
		pos.x = pos.x >= 0 ? pos.x % Chunk.CHUNK_SIZE : Chunk.CHUNK_SIZE - ((Math.abs(pos.x+1) % Chunk.CHUNK_SIZE)+1);
		pos.z = pos.z >= 0 ? pos.z % Chunk.CHUNK_SIZE : Chunk.CHUNK_SIZE - ((Math.abs(pos.z+1) % Chunk.CHUNK_SIZE)+1);
		return pos;
	}
	
	private static Coord3d getAbsolutePosition(Coord2d chunkPos, Coord3d inChunkPos) {
		Coord3d absPos = new Coord3d(0, 0, 0);
		absPos.x = (chunkPos.x * Chunk.CHUNK_SIZE) + inChunkPos.x;
		absPos.y = inChunkPos.y;
		absPos.z = (chunkPos.z * Chunk.CHUNK_SIZE) + inChunkPos.z;
		return absPos;
	}
	
	private static boolean blockSeeable(Coord3d absPos) {
		return !blockExists(absPos.x+1, absPos.y, absPos.z) ||
				!blockExists(absPos.x-1, absPos.y, absPos.z) ||
				!blockExists(absPos.x, absPos.y+1, absPos.z) ||
				!blockExists(absPos.x, absPos.y-1, absPos.z) ||
				!blockExists(absPos.x, absPos.y, absPos.z+1) ||
				!blockExists(absPos.x, absPos.y, absPos.z-1);
	}

	public static boolean blockExists(Coord3d pos) { 			
		return blockExists(getBlockChunkPosition(pos), getBlockInChunkPosition(pos));
	}
	
	public static boolean blockExists(int x, int y, int z) {
		return blockExists(new Coord3d(x, y, z)); 
	}
	
	private static boolean blockExists(Coord2d chunkPos, Coord3d blockPos) {
		if(blockPos.x < 0 || blockPos.z < 0)
			Logger.err("Whoopsy daisy");
		return !chunks.containsKey(chunkPos) ? false : chunks.get(chunkPos).blockExists(blockPos);
	}
}
