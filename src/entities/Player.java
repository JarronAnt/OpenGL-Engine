package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_HEIGHT = 30;
	public static final float TERRAIN_HEIGHT = 0;
	
	private boolean isAirborne = false;
	
	private float currSpeed = 0;
	private float currTurnSpeed = 0;
	private float upSpeed = 0;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void move(Terrain terrain){
		checkKeyboardInput();
		super.increaseRotation(0, currTurnSpeed * DisplayManager.getDelta(), 0);
		float distance = currSpeed * DisplayManager.getDelta(); 
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		upSpeed += GRAVITY * DisplayManager.getDelta(); 

		super.increasePosition(dx, 0, dz);
		super.increasePosition(0, upSpeed * DisplayManager.getDelta(), 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y <= terrainHeight){
			upSpeed = 0;
			super.getPosition().y = terrainHeight;
			isAirborne = false;
			
		}
	}
	
	private void jump(){
		this.upSpeed = JUMP_HEIGHT;
	}
	
	public void checkKeyboardInput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currSpeed = RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currSpeed = -RUN_SPEED;
		}else{
			this.currSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currTurnSpeed = TURN_SPEED;
		}else{
			this.currTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && isAirborne == false){
			isAirborne = true;
			jump();
		}
		
	}
}
