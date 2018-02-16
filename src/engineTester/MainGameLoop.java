/*Author: Jarron Anthonipillai
 * Date Created: Febuary 2nd 2018
 * Decription: This is a 3D game created using Java and LWJGL
 * The project is based off of ThinMatrix's OpenGL tutorials
 * 
 * DISCLAIMER: All source code is property of Jarron Anthonipillai
 * but as per liscensing agreement source code may be used for any purpose commercial or otherwise
 * so long as proper credits are give to myself. 
 * 
 * 
 */


package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
import toolbox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {
		
		List<Light>bigLights = new ArrayList<Light>();
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
		
		//create the textures here
		ModelTexture fernTexAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTexAtlas.setNumRows(2);
		
		//create model data here
		ModelData treeData = OBJFileLoader.loadOBJ("tree");
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		ModelData playerData = OBJFileLoader.loadOBJ("person");
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");


		//create raw models here
		RawModel model = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
				treeData.getNormals(), treeData.getIndices());
		RawModel model3 = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
				fernData.getNormals(), fernData.getIndices());
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
				playerData.getNormals(), playerData.getIndices());
		RawModel lampModel = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(),
				lampData.getNormals(), lampData.getIndices());
	
		

		
		//create textured models here
		TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel fern = new TexturedModel(model3, fernTexAtlas);
		TexturedModel player = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		
		float entityY = 0;
		
		//set transparancy here
		//grass.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		
		//set specular values here
		
		//set fake lighting here
		//grass.getTexture().setUseFakeLight(true);
		lamp.getTexture().setUseFakeLight(true);
		

		
		
		//create a list of entities 
		Player myPlayer = new Player(player, new Vector3f(100,0,-50),0,180,0,0.6f);
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		Terrain terrain = new Terrain(0,-1,loader,tp,blend,"heightmap");
		Terrain terrain2 = new Terrain(1,-1,loader,tp, blend, "heightmap");
		
		
		//populate the list of entites
		for(int i=0;i<300;i++){
			
			float x = random.nextFloat()*1600 - 400;
			float z = random.nextFloat() * -600;
			
			if(x > 800 || z > 800){
				entityY = terrain2.getHeightOfTerrain(x, z);
			}else{
				entityY = terrain.getHeightOfTerrain(x, z);
			}
			
			
			entities.add(new Entity(tree, new Vector3f(x,entityY,z),0,0,0,3));
			}
		
		
		for(int i=0;i<300;i++){
			
			float x = random.nextFloat()* 1600 - 400;
			float z = random.nextFloat() * -600;
			
			if(x > 800 || z > 800){
				entityY = terrain2.getHeightOfTerrain(x, z);
			}else{
				entityY = terrain.getHeightOfTerrain(x, z);
			}
			
			entities.add(new Entity(fern, new Vector3f(x,entityY,z),0,0,0,0.6f,random.nextInt(4)));
			}
		
		//create lights here
		Light sun = new Light(new Vector3f(20000,20000,20000),new Vector3f(1,1,1));
		Light light2 = new Light(new Vector3f(185,10,-293),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f));
		Light light3 = new Light(new Vector3f(200,10,-300),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f));
		Light light4 = new Light(new Vector3f(100,20,-350),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f));
		Light light5 = new Light(new Vector3f(175,20,-247),new Vector3f(0,2,0), new Vector3f(1,0.01f,0.002f));
		Light light6 = new Light(new Vector3f(200,10,-203),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f));

		
		
		//array of lights
		Light[] lights = new Light[5];
		//List<Light> lights = new ArrayList<Light>();
		//List<Light> allLights = new ArrayList<Light>();
		
		//order lights by distance from camera
		//lights.add(sun);
		//lights.add(light2);
		//lights.add(light3);
		
		//add lights to the array
		lights[0] = light5;
		lights[1] = light6;
		lights[2] = light3;
		lights[3] = light4;
		lights[4] = light2;
		

		
		entities.add(new Entity(lamp, new Vector3f(185, terrain.getHeightOfTerrain(185, -293), -293),0,0,0,1));
		entities.add(new Entity(lamp, new Vector3f(200, terrain.getHeightOfTerrain(200, -300), -300),0,0,0,1));
		entities.add(new Entity(lamp, new Vector3f(100, terrain.getHeightOfTerrain(100, -350), -350),0,0,0,1));
		entities.add(new Entity(lamp, new Vector3f(175, terrain.getHeightOfTerrain(175, -247), -247),0,0,0,1));
		entities.add(new Entity(lamp, new Vector3f(200, terrain.getHeightOfTerrain(200, -203), -203),0,0,0,1));

		
		//generate two terrain tiles

		
		//create a camera and renderer
		Camera camera = new Camera(myPlayer);	
		
		MasterRenderer renderer = new MasterRenderer();
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		//create guis here
		GuiTexture gui1 = new GuiTexture(loader.loadTexture("health"),new Vector2f(-0.5f,0.5f),
				new Vector2f(0.25f,0.25f));
		//add guis to the list
		//any guis that can be drawn then undrawn will go in the game loop 
		guis.add(gui1);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		//main game loop
		while(!Display.isCloseRequested()){
			//pull for cam movement
			camera.move();
			
			//check which terrain the player is in an calc collision based on that
			if(myPlayer.getPosition().x > 800 || myPlayer.getPosition().z > 800){
				myPlayer.move(terrain2);
			}else{
				myPlayer.move(terrain);	
			}
			//process the terrain
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			//processPlayer
			renderer.processEntity(myPlayer);
			//process each entity in the list
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			
			//get the ordered lights and add the sun to the list of lights so that its always active
			bigLights = Maths.orderLights(lights, myPlayer);
			bigLights.add(sun);
			//render everything

			renderer.render(bigLights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		//clean
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

	
}

