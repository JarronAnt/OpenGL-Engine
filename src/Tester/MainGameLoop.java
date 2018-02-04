package Tester;

import org.lwjgl.opengl.Display;

import renderer.Display_Manager;
import renderer.ModelLoader;
import renderer.RawModel;
import renderer.Renderer;

public class MainGameLoop {

	public static void main(String[] args)
	{
		//create the display
		Display_Manager.createDisplay();
		//create the loader and renderer object
		ModelLoader loader = new ModelLoader();
		Renderer renderer = new Renderer();
		
		//create verts for two triganles that makes a quad
		 float[] vertices = {
				    -0.5f, 0.5f, 0f,
				    -0.5f, -0.5f, 0f,
				    0.5f, -0.5f, 0f,
				    0.5f, -0.5f, 0f,
				    0.5f, 0.5f, 0f,
				    -0.5f, 0.5f, 0f
				  };
		 
		//create a model by loading the verts into a vao
		RawModel testModel = loader.loadToVao(vertices);
		
		while(!Display.isCloseRequested()) 
		{
			//clear the screen and set the background color 
			renderer.prep();
			
			//logic, render and stuff
			//draw the model
			renderer.render(testModel);
			Display_Manager.updateDisplay();
			
		}
		
		//clean everything up
		loader.purge();
		Display_Manager.closeDisplay();
	}

}
