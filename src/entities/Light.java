package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	//light vars
	private Vector3f position;
	private Vector3f colour;
	
	//set vars on creation
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}

	//getters and setters
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	

}
