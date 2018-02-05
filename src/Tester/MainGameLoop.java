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
		
		//create array of the verts
		 float[] vertices = {
				   -0.5f,0.5f,0, //V1
				   -0.5f,-0.5f,0, //V2
				   0.5f,-0.5f,0, //V3
				   0.5f,0.5f,0 //V4
				  };
		 //array to tell opengl which order to connect verts 
		 int[] indicies = {
				 0,1,3, //triangle 1
				 1,3,2 //triangle 2
		 };
		 
		//create a model by loading the verts into a vao
		RawModel testModel = loader.loadToVao(vertices, indicies);
		
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
