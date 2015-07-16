package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelUtils {

	private static List<Integer> vaoList = new ArrayList<Integer>();
	private static List<Integer> vboList = new ArrayList<Integer>();
	
	public static void clear() {
		for(int vao:vaoList) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo:vboList) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	public static int createEmptyVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaoList.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public static int getNewVBOID() {
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		return vboID;
	}
	
	public static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = getNewVBOID();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatArrayToBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public static void bindIndicesBuffer(int[] indices) { 
		int vboID = getNewVBOID();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intArrayToBuffer(indices), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
	}
	
	private static IntBuffer intArrayToBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer floatArrayToBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
