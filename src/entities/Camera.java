package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	//camera vars
	private Vector3f pos = new Vector3f(0,0,0);
	private float pitch; 
	private float yaw;
	private float roll; 
	
	
	public Camera()
	{
	}
	
	//move the camera using the keyboard
	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			pos.z+= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			pos.x+= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			pos.x-= 0.02f;
		}
	}


	//getters
	public Vector3f getPos() {
		return pos;
	}


	public float getPitch() {
		return pitch;
	}


	public float getYaw() {
		return yaw;
	}


	public float getRoll() {
		return roll;
	}
	
	
}
