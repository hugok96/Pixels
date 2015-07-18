package renderEngine;

import java.util.List;
import java.util.Map;

import models.Model;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import textures.Textures;
import toolbox.Maths;
import world.Coord3d;
import blocks.Block;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1F;
	private static final float FAR_PLANE = 1;
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.16862745098039215686274509803922F, 0.6078431372549019607843137254902F, 0.86666666666666666666666666666667F, 1);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BACK);
		
	}
	
	public void render(Map<Block, List<Coord3d>> blocks) {
		for(Block block:blocks.keySet()) {
			prepareTexturedModel(block.getModel());
			for(Coord3d pos:blocks.get(block)) {
				prepareInstance(pos);
				GL11.glDrawElements(GL11.GL_TRIANGLES, block.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTextureModel();
		}
	}
	
	private void prepareTexturedModel(Model model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Textures.getTextureMapId());
	}
	
	private void unbindTextureModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Coord3d pos) {
		Matrix4f transofmationMatrix = Maths.createTransformationMatrix(pos.toVector(), 
				0,0,0, Block.SCALE);		
		shader.loadTransormationMatrix(transofmationMatrix);
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1F / Math.tan(Math.toRadians(FOV/2F))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE/NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
