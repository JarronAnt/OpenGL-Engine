package models;

public class RawModel {
	
	//raw model vars
	private int vaoID;
	private int vertexCount;
	
	//set model vars
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	//getters
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	

}
