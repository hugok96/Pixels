//package renderEngine;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;
//import java.util.ArrayList;
//import java.util.List;
//
//import models.RawModel;
//
//import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL14;
//import org.lwjgl.opengl.GL15;
//import org.lwjgl.opengl.GL20;
//import org.lwjgl.opengl.GL30;
//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;
//
//public class ModelLoader {
//	
//	
//
//	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
//		int vaoID = createEmptyVAO();
//		bindIndicesBuffer(indices);
//		storeDataInAttributeList(0, 3, positions);
//		storeDataInAttributeList(1, 2, textureCoords);
//		storeDataInAttributeList(2, 3, normals);
//		unbindVAO();
//		return new RawModel(vaoID, indices.length);
//	}
//	
//	public void clear() {
//		for(int vao:vaoList) {
//			GL30.glDeleteVertexArrays(vao);
//		}
//		
//		for(int vbo:vboList) {
//			GL15.glDeleteBuffers(vbo);
//		}
//		
//		for(int texture:textureList) {
//			GL11.glDeleteTextures(texture);
//		}
//	}
//	
//	public static int createEmptyVAO(){
//		int vaoID = GL30.glGenVertexArrays();
//		vaoList.add(vaoID);
//		GL30.glBindVertexArray(vaoID);
//		return vaoID;
//	}
//	
//	private static int getNewVBOID() {
//		int vboID = GL15.glGenBuffers();
//		vboList.add(vboID);
//		return vboID;
//	}
//	
//	public static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
//		int vboID = getNewVBOID();
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatArrayToBuffer(data), GL15.GL_STATIC_DRAW);
//		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
//	
//	public static void unbindVAO() {
//		GL30.glBindVertexArray(0);
//	}
//	
//	public static void bindIndicesBuffer(int[] indices) {
//		int vboID = getNewVBOID();
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
//		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intArrayToBuffer(indices), GL15.GL_STATIC_DRAW);
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
//	}
//	
//	private static IntBuffer intArrayToBuffer(int[] data) {
//		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
//		buffer.put(data);
//		buffer.flip();
//		return buffer;
//	}
//	
//	private static FloatBuffer floatArrayToBuffer(float[] data) {
//		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
//		buffer.put(data);
//		buffer.flip();
//		return buffer;
//	}
//}
