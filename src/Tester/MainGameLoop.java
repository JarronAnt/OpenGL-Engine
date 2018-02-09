package Tester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderer.Display_Manager;
import renderer.ModelLoader;
import renderer.ObjLoader;
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
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		//create a model by loading the verts into a vao
		RawModel testModel = ObjLoader.loadOBJ("dragon", loader);
		ModelTexture tex = new ModelTexture(loader.loadTexture("whiteTex"));
		TexturedModel texModel = new TexturedModel(testModel, tex);
		Entity entity = new Entity(texModel, new Vector3f(0,-5,-25),0,0,0,1.0f);
		
		Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1.0f, 1.0f, 1.0f));
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) 
		{
			
			entity.modifyRot(0, 1, 0);
			camera.move();
			//clear the screen and set the background color 
			renderer.prep();
			shader.start();
			//logic, render and stuff
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			//draw the model
			renderer.render(entity, shader);
			shader.stop();
			Display_Manager.updateDisplay();
			
		}
		
		//clean everything up
		shader.purge();
		loader.purge();
		Display_Manager.closeDisplay();
	}

}
