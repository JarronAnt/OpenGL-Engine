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
		
		ModelTexture fernTexAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTexAtlas.setNumRows(2);
		
		//create model data here
		ModelData treeData = OBJFileLoader.loadOBJ("tree");
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		ModelData playerData = OBJFileLoader.loadOBJ("person");
		
		//create raw models here
		RawModel model = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
				treeData.getNormals(), treeData.getIndices());
		RawModel model3 = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
				treeData.getNormals(), fernData.getIndices());
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
				playerData.getNormals(), playerData.getIndices());

		
		//create textured models here
		TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel fern = new TexturedModel(model3, fernTexAtlas);
		TexturedModel player = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		
		float entityY = 0;
		
		//set transparancy here
		//grass.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		
		//set specular values here
		
		//set fake lighting here
		//grass.getTexture().setUseFakeLight(true);
		
		//create a list of entities 
		Player myPlayer = new Player(player, new Vector3f(100,0,-50),0,180,0,0.6f);
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		Terrain terrain = new Terrain(0,-1,loader,tp,blend,"heightmap");
		Terrain terrain2 = new Terrain(1,-1,loader,tp, blend, "heightMap");
		
		
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
			
			float x = random.nextFloat()*1600 - 400;
			float z = random.nextFloat() * -600;
			
			if(x > 800 || z > 800){
				entityY = terrain2.getHeightOfTerrain(x, z);
			}else{
				entityY = terrain.getHeightOfTerrain(x, z);
			}
			
			entities.add(new Entity(fern, new Vector3f(x,entityY,z),0,0,0,0.6f,random.nextInt(4)));
			}
		
		//create a light source
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		//generate two terrain tiles

		
		//create a camera and renderer
		Camera camera = new Camera(myPlayer);	
		MasterRenderer renderer = new MasterRenderer();
		
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

