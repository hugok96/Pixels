package world;

import java.util.HashMap;
import java.util.Map;

import main.Logger;
import blocks.Block;

public class World {

	private Map<Coord2d, Chunk> chunks = new HashMap<Coord2d, Chunk>();
	private Map<Coord3d, Block> renderableBlocks = new HashMap<Coord3d, Block>();
	private static final int CHUNK_RENDER_DISTANCE = 1; //TODO: IMPLEMENT THIS!
	
	public World() {
		generate();
	}
	
	private void generate() {
		Logger.log("Generating chunks..", 1);
		chunks.put(new Coord2d(0, 0), new Chunk("heightmap_1"));
		//chunks.put(new Coord2d(1, 0), new Chunk("heightmap_2"));
		//chunks.put(new Coord2d(0, 1), new Chunk("heightmap_3"));
		//chunks.put(new Coord2d(1, 1), new Chunk("heightmap_4"));
		Logger.log("Chunks generated!", 1);
		Logger.log("Initializing renderable blocks..", 1);
		initRenderableBlocks();
		Logger.log("Done initializing renderable blocks!", 1);
		Logger.log("Renderable blocks in world: " + renderableBlocks.size(), 2);
	}
	
	private void initRenderableBlocks() {
		Logger.log("Chunk count: " + chunks.size(), 2);
		for(Coord2d c:chunks.keySet()) {
			Logger.log("Chunk X:" + c.x + " Y:" + c.y + ".. ", 2);
			Map<Coord3d, Block> cBlocks = chunks.get(c).getRenderableBlocks();
			for(Coord3d cC:cBlocks.keySet()) {
				renderableBlocks.put(new Coord3d(c.x * Chunk.CHUNK_SIZE_X + cC.x, cC.y, c.y * Chunk.CHUNK_SIZE_Z + cC.z), cBlocks.get(cC));

				Logger.log("x:"+ (cC.x) +" y:"+ cC.y +" z:"+ c.y , 3);
			}
			
			Logger.log("Done!", 3);
		}
	}
	
	public Map<Coord3d, Block> getRenderableBlocks() {
		return renderableBlocks;
	}
}
