package Tester;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TexturedModel;
import renderer.Display_Manager;
import renderer.ModelLoader;
import renderer.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args)
	{
		//create the display
		Display_Manager.createDisplay();
		//create the loader and renderer object
		ModelLoader loader = new ModelLoader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		
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
		 
		 //uv coords to attach the texture to the model
		 float[] textureCoords = {
				    0,0, //V0
				    0,1, //V1
				    1,1, //V2
				    1,0 //V3
				  };
		 
		//create a model by loading the verts into a vao
		RawModel testModel = loader.loadToVao(vertices, textureCoords, indicies);
		ModelTexture tex = new ModelTexture(loader.loadTexture("image"));
		TexturedModel texModel = new TexturedModel(testModel, tex);
		
		while(!Display.isCloseRequested()) 
		{
			//clear the screen and set the background color 
			renderer.prep();
			shader.start();
			//logic, render and stuff
			//draw the model
			renderer.render(texModel);
			shader.stop();
			Display_Manager.updateDisplay();
			
		}
		
		//clean everything up
		shader.purge();
		loader.purge();
		Display_Manager.closeDisplay();
	}

}
