package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		
		//create the display and loader
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		//load the models in as a raw model
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		RawModel model2 = OBJLoader.loadObjModel("grassModel",loader);
		RawModel model3 = OBJLoader.loadObjModel("fern",loader);
		
		//create texture model using raw models and a texture
		TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(model2, new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(model3, new ModelTexture(loader.loadTexture("fern")));
		
		//set transparancy here
		grass.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		
		//set specular values here
		
		//set fake lighting here
		grass.getTexture().setUseFakeLight(true);
		
		//create a list of entities 
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		//populate the list of entites
		for(int i=0;i<300;i++){
			entities.add(new Entity(tree, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,0.6f));

			
		}
		
		//create a light source
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		//generate two terrain tiles
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		
		//create a camera and renderer
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		//main game loop
		while(!Display.isCloseRequested()){
			//pull for cam movement
			camera.move();
			
			//process the terrain
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			//process each entity in the list
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			//render everything
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		//clean
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
