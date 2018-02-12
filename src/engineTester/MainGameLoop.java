package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TexturePack;
import textures.Textures;

public class MainGameLoop {

	public static void main(String[] args) {
		
		//create the display and loader
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//terrain textures go here
		Textures bg = new Textures(loader.loadTexture("grass"));
		Textures r = new Textures(loader.loadTexture("dirt"));
		Textures g = new Textures(loader.loadTexture("pinkFlowers"));
		Textures b = new Textures(loader.loadTexture("path"));
		Textures blend = new Textures(loader.loadTexture("blendMap"));
		
		TexturePack tp = new TexturePack(bg,r,g,b);

		
		//create model data here
		ModelData treeData = OBJFileLoader.loadOBJ("tree");
		ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		ModelData playerData = OBJFileLoader.loadOBJ("person");
		
		//create raw models here
		RawModel model = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
				treeData.getNormals(), treeData.getIndices());
		RawModel model2 = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(),
				grassData.getNormals(), grassData.getIndices());
		RawModel model3 = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
				treeData.getNormals(), fernData.getIndices());
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
				playerData.getNormals(), playerData.getIndices());

		
		//create textured models here
		TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(model2, new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(model3, new ModelTexture(loader.loadTexture("fern")));
		TexturedModel player = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		
		
		//set transparancy here
		grass.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		
		//set specular values here
		
		//set fake lighting here
		grass.getTexture().setUseFakeLight(true);
		
		//create a list of entities 
		Player myPlayer = new Player(player, new Vector3f(100,0,-50),0,180,0,0.6f);
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
		Terrain terrain = new Terrain(0,-1,loader,tp,blend,"heightmap");
		Terrain terrain2 = new Terrain(1,-1,loader,tp, blend, "heightmap");
		
		//create a camera and renderer
		Camera camera = new Camera(myPlayer);	
		MasterRenderer renderer = new MasterRenderer();
		
		//main game loop
		while(!Display.isCloseRequested()){
			//pull for cam movement
			camera.move();
			myPlayer.move();
			//process the terrain
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			//processPlayer
			renderer.processEntity(myPlayer);
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
