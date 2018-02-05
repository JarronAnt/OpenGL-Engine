package shaders;

public class StaticShader extends ShaderProgram
{
	public static final String VERT = "src/shaders/vertShader.vert";
	public static final String FRAG = "src/shaders/fragShader.frag";


	public StaticShader() {
		super(VERT, FRAG);
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "pos");
	}

}
