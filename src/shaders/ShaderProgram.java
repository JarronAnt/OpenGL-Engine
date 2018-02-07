package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram 
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuff = BufferUtils.createFloatBuffer(16);
	
	//construct the shader
	public ShaderProgram(String vertFile, String fragFile)
	{
		vertexShaderID = loadShader(vertFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttribs();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName)
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	protected void loadFloat(int location, float value)
	{
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vec)
	{
		GL20.glUniform3f(location, vec.x , vec.y, vec.z);
	}
	
	protected void loadBool(int location , boolean value)
	{
		float temp = 0;
		if(value == true)
		{
			temp = 1;
		}
		
		GL20.glUniform1f(location, temp);
	}
	
	protected void loadMatrix(int location, Matrix4f mat)
	{
		mat.store(matrixBuff);
		matrixBuff.flip();
		GL20.glUniformMatrix4(location, false, matrixBuff);
	}
	
	public void start()
	{
		GL20.glUseProgram(programID);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);

	}
	
	public void purge()
	{
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);

	}
	
	protected abstract void bindAttribs();
	
	//get attrib from vao slot and feed to the shader program
	protected void bindAttrib(int attrib, String varName)
	{
		GL20.glBindAttribLocation(programID, attrib, varName);
	}
	
	
	private static int loadShader(String file, int type)
	{
		
		StringBuilder shaderSource = new StringBuilder();
		 try
		 {
		   BufferedReader reader = new BufferedReader(new FileReader(file));
		   String line;
		   while((line = reader.readLine())!=null)
		   {
			   shaderSource.append(line).append("//\n");
		   }
		   reader.close();
		  }catch(IOException e){
		   e.printStackTrace();
		   System.exit(-1);
		  }
		 
		  int shaderID = GL20.glCreateShader(type);
		  GL20.glShaderSource(shaderID, shaderSource);
		  GL20.glCompileShader(shaderID);
		  
		  if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE)
		  {
			  System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			  System.err.println("Could not compile shader!");
			  System.exit(-1);
		  }
		  
		  return shaderID;
	}
	
}
