package blocks;

import models.Model;
import models.ModelBlock;

public class Block {
	public int id;
	public final static float SCALE = 0.5F;
	private final int textureSideId;
	private final int textureTopId;
	private final int textureBottomId;
	private final String name;
	private Model model;

	public Block (int textureId, String name) {
		this(textureId, textureId, textureId, name);	
	}
	
	public Model getModel() {
		return model;
	}
	

	public Block (int textureSideId, int textureTopId, String name) {
		this(textureSideId, textureTopId, textureSideId, name);	
	}
	
	public Block (int textureSideId, int textureTopId, int textureBottomId, String name) {
		this.textureSideId = textureSideId;
		this.textureTopId = textureTopId;
		this.textureBottomId = textureBottomId;
		this.name = "blocks." + name;
		
		this.model = new ModelBlock(textureSideId, textureTopId, textureBottomId);
		this.id = Blocks.generateId();
	}
	
	public String getName() {
		return this.name;
	}
}
