package renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class Display_Manager 
{
	//constant screen vars
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS = 144;
	
	
	static ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
	
	public static void createDisplay()
	{
		try {
			//set the display attribs
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay()
	{
		//update at a ceartain frame rate
		Display.sync(FPS);
		Display.update();
	}
	
	public static void closeDisplay()
	{
		//destory display
		Display.destroy();
	}
}
