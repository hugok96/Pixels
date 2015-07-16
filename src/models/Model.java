package models;

import objConverter.ModelData;
import objConverter.OBJFileLoader;
import textures.Textures;

public class Model {
	
	private int vaoID;
	private int vertexCount;
	
	public Model(String objFilename, int textureSideId, int textureTopId, int textureBottomId) {
		float[] texturePosition = Textures.getTexturePosition(textureSideId);
		ModelData data = OBJFileLoader.loadOBJ(objFilename, texturePosition);
		this.vaoID = ModelUtils.createEmptyVAO();
		ModelUtils.bindIndicesBuffer(data.getIndices());
		ModelUtils.storeDataInAttributeList(0, 3, data.getVertices());
		ModelUtils.storeDataInAttributeList(1, 2, data.getTextureCoords());
		ModelUtils.storeDataInAttributeList(2, 3, data.getNormals());
		ModelUtils.unbindVAO();
		this.vertexCount = data.getIndices().length;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
