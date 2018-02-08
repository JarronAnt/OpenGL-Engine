package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {
	
	//vars of an entity
	private TexturedModel model;
	private Vector3f pos; 
	private float rotX, rotY, rotZ; 
	private float scale;
	
	//consturctor
	public Entity(TexturedModel model, Vector3f pos, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.pos = pos;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	//change entity location in the world
	public void modifyPos(float dx, float dy, float dz)
	{
		this.pos.x+= dx;
		this.pos.y+= dy;
		this.pos.z+= dz;
	}
	
	//change entity rotation
	public void modifyRot(float d_rotX, float d_rotY, float d_rotZ)
	{
		this.rotX += d_rotX;
		this.rotY += d_rotY;
		this.rotZ += d_rotZ; 
	}


	//getters and setters
	public TexturedModel getModel() {
		return model;
	}


	public void setModel(TexturedModel model) {
		this.model = model;
	}


	public Vector3f getPos() {
		return pos;
	}


	public void setPos(Vector3f pos) {
		this.pos = pos;
	}


	public float getRotX() {
		return rotX;
	}


	public void setRotX(float rotX) {
		this.rotX = rotX;
	}


	public float getRotY() {
		return rotY;
	}


	public void setRotY(float rotY) {
		this.rotY = rotY;
	}


	public float getRotZ() {
		return rotZ;
	}


	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}


	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	

}
