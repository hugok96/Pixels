package blocks;

import models.Model;
import models.ModelBlock;

public class Block {
	public int id;
	private final int textureSideId;
	private final int textureTopId;
	private final int textureBottomId;
	private Model model;

	public Block (int textureId) {
		this(textureId, textureId, textureId);	
	}
	
	public Model getModel() {
		return model;
	}
	

	public Block (int textureSideId, int textureTopId) {
		this(textureSideId, textureTopId, textureSideId);	
	}
	
	public Block (int textureSideId, int textureTopId, int textureBottomId) {
		this.textureSideId = textureSideId;
		this.textureTopId = textureTopId;
		this.textureBottomId = textureBottomId;
		
		this.model = new ModelBlock(textureSideId, textureTopId, textureBottomId);
		this.id = Blocks.generateId();
	}
}
