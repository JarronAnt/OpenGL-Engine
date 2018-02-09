package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f pos;
	private Vector3f col;
	
	public Light(Vector3f pos, Vector3f col) {
		this.pos = pos;
		this.col = col;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getCol() {
		return col;
	}

	public void setCol(Vector3f col) {
		this.col = col;
	}
	
	
	
	
}
