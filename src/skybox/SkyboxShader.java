package skybox;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{

	
	private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader.frag";
	
	private int location_projectionMatrix;
	private int location_viewMatrix;

	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");

	}


	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);

	}
	
	public void loadViewMatrix(Camera cam){
		Matrix4f viewMatrix = Maths.createViewMatrix(cam);
		
		viewMatrix.m30 = 0;
		viewMatrix.m31 = 0;
		viewMatrix.m32 = 0;
		
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}
