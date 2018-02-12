package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	//set display consts
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 144;
	
	private static long lastFrame; 
	private static float delta; 
	//create the display
	public static void createDisplay(){		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Code Name: Winter Rain");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0,0, WIDTH, HEIGHT);
		lastFrame = getCurrTime();
	}
	
	//update the screen
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);
		Display.update();
		long currFrameTime = getCurrTime();
		delta = (currFrameTime - lastFrame)/1000f; 
		lastFrame = currFrameTime; 
	}
	
	public static float getDelta()
	{
		return delta; 
	}
	
	//close the screen
	public static void closeDisplay(){
		
		Display.destroy();
	}
	
	private static long getCurrTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}

}
