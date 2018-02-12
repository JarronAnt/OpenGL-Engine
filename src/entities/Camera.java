package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float zoom = 50;
	private float horizontalAngle = 0;
	
	//cam vars
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;
	private float yaw = 0 ;
	private float roll;
	
	private Player player;
	
	public Camera(Player player){
		this.player = player;
	}
	
	//move the cam
	public void move(){
		calcZoom();
		calcPitch();
		calcAngle();
		float horDistance = calcHorDistance();
		float vertDistance = calcVertDistance();
		calcCamPos(horDistance, vertDistance);
		this.yaw = 180 - (player.getRotY() + horizontalAngle);
	}
	
	private void calcCamPos(float hDistance, float vDistance){
	
		
		float theta = player.getRotY() + horizontalAngle;
		float xOffset = (float) (hDistance * Math.sin(Math.toRadians(theta)));
		float zOffset = (float) (hDistance * Math.cos(Math.toRadians(theta)));
		

		
		position.x = player.getPosition().x - xOffset; 
		position.y = player.getPosition().y + vDistance;
		if(position.y < 0){
			   position.y = 5;
			  } 
		position.z = player.getPosition().z - zOffset; 
		
		


	}
	
	//getters
	public Vector3f getPosition() {
		return position;
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
	
	private void calcZoom(){

		float zoomLvl = Mouse.getDWheel() * 0.1f;
		zoom -= zoomLvl;
		System.out.println(zoom);
		

	}
	
	private void calcPitch(){
		if(Mouse.isButtonDown(2)){
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calcAngle(){
		if(Mouse.isButtonDown(1)){
			float hChange = Mouse.getDX() * 0.3f;
			horizontalAngle -= hChange;
		}
	}
	
	
	private float calcHorDistance(){
		return (float) (zoom * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calcVertDistance(){
		return (float) (zoom * Math.sin(Math.toRadians(pitch)));
	}


}
