package Tester;

import org.lwjgl.opengl.Display;

import renderer.Display_Manager;

public class MainGameLoop {

	public static void main(String[] args)
	{
		Display_Manager.createDisplay();
		
		while(!Display.isCloseRequested()) 
		{
			//logic, render and stuff
			Display_Manager.updateDisplay();
			
		}
		
		Display_Manager.closeDisplay();
	}

}
