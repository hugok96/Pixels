package shaders;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.Maths;
import entities.EntityCamera;
import entities.EntityLight;

public class StaticShader extends ShaderProgram {

	private static final String VERTEXSHADER_FILE = "src/shaders/vertexShader.glsl";
	private static final String FRAGMENTSHADER_FILE = "src/shaders/fragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	
	public StaticShader() {
		super(VERTEXSHADER_FILE, FRAGMENTSHADER_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
		super.bindAttributes(1, "textureCoords");
		super.bindAttributes(2, "normal");
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUnifomLocation("transformationMatrix");
		location_projectionMatrix = super.getUnifomLocation("projectionMatrix");
		location_viewMatrix = super.getUnifomLocation("viewMatrix");
		location_lightPosition = super.getUnifomLocation("lightPosition");
		location_lightColour = super.getUnifomLocation("lightColour");
		
	}
	
	public void loadLight(EntityLight light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadTransormationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);; 
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);; 
	}
	
	public void loadViewMatrix(EntityCamera camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}

}
