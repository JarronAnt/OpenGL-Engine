package models;

public class RawModel 
{
	private int vaoID, vertexCount;
	
	//raw model object holds a vao and vertex count of the model
	public RawModel (int vaoID, int vertexCount) 
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		
	}

	public int getVaoID() 
	{
		return vaoID;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
	
	
}
