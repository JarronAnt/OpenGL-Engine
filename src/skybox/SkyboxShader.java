package skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import renderEngine.DisplayManager;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{

	
	private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader.frag";
	
	private static final float ROTATE_SPEED = 1f;
	
	private float rotation = 0;

	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColour;
	private int location_cubeMap1;	
	private int location_cubeMap2;
	private int location_blendFactor;



	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_fogColour = super.getUniformLocation("fogColour");
		location_cubeMap1 = super.getUniformLocation("cubeMap1");
		location_cubeMap2 = super.getUniformLocation("cubeMap2");
		location_blendFactor = super.getUniformLocation("blendFactor");


	}


	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadFogCol(float r, float g, float b){
		super.loadVector(location_fogColour, new Vector3f(r,g,b));
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);

	}
	
	public void loadBlendFactor(float factor){
		super.loadFloat(location_blendFactor, factor);
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_cubeMap1, 0);
		super.loadInt(location_cubeMap2, 1);

	}
	
	public void loadViewMatrix(Camera cam){
		Matrix4f viewMatrix = Maths.createViewMatrix(cam);
		
		viewMatrix.m30 = 0;
		viewMatrix.m31 = 0;
		viewMatrix.m32 = 0;
		rotation += ROTATE_SPEED * DisplayManager.getDelta();
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}
