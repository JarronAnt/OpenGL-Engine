package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import tools.Maths;

public class StaticShader extends ShaderProgram
{
	public static final String VERT = "src/shaders/vertShader.vert";
	public static final String FRAG = "src/shaders/fragShader.frag";

	private int locationTransMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;

	//test
	public StaticShader() {
		super(VERT, FRAG);
	}

	@Override
	protected void bindAttribs() {
		//take the attribs in cetain vao slot and pass to the vert shader
		super.bindAttrib(0, "pos");
		super.bindAttrib(1, "texCoords");
	}

	@Override
	//get all the uniform locations 
	protected void getAllUniformLocations() {
		locationTransMatrix = super.getUniformLocation("transformMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix )
	{
		super.loadMatrix(locationTransMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera )
	{
		Matrix4f viewMat = Maths.createViewMatrix(camera);
		super.loadMatrix(locationViewMatrix, viewMat);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationProjectionMatrix, matrix);
	}
}
