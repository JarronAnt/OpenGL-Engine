package renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader 
{
	
	private List<Integer> vaos = new ArrayList<Integer>(); 
	private List<Integer> vbos = new ArrayList<Integer>(); 

	
	public RawModel loadToVao(float[] pos, int[] index )
	{
		//create a vao and add it to a list
		int vaoID = createVAO();
		vaos.add(vaoID);
		//send the index data to the model
		bindIndexBuffer(index);
		//store the the pos data in a vbo and put it in vao index 0
		storeDataInAttribList(0, pos);
		//deactivate the vao
		unbindVAO();
		//return the rawmodel with the vao 
		return new RawModel(vaoID, index.length);
	}
	
	private int createVAO()
	{
		//create the vao and activate it 
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
	}
	
	private void storeDataInAttribList(int attribNum, float[] data)
	{
		//create the vbo, add it to a list and activate it 
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		//populate the buffer with the data given
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		//set the buffer with its attributs 
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//desginate slot the vbo occupies in the vao, 
		//3 refers to the number of dimensions 
		//type of data that is being passed in
		//normalized or not
		//where to start reading the data from
		GL20.glVertexAttribPointer(attribNum, 3, GL11.GL_FLOAT, false, 0,0);
		//deactivating the vbo
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void bindIndexBuffer(int[] indicies)
	{
		//create a vbo and fill it with the index buffer data
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indicies);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

	}
	
	private void unbindVAO()
	{
		//deactivate the vbo
		GL30.glBindVertexArray(0);
	}
	
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		//generate the buffer
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		//place our data in the buffer
		buffer.put(data);
		//make the buffer readable
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		//same steps as float buffer but for an int
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	
	public void purge()
	{
		//loop and delete all vaos
		for (int vao:vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		//loop and delete all vbos
		for (int vbo:vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
	}
}
