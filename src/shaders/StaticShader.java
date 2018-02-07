package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram
{
	public static final String VERT = "src/shaders/vertShader.vert";
	public static final String FRAG = "src/shaders/fragShader.frag";

	private int locationTransMatrix;

	public StaticShader() {
		super(VERT, FRAG);
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "pos");
		super.bindAttrib(1, "texCoords");
	}

	@Override
	//get all the uniform locations 
	protected void getAllUniformLocations() {
		locationTransMatrix = super.getUniformLocation("transformMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix )
	{
		super.loadMatrix(locationTransMatrix, matrix);
	}

}
