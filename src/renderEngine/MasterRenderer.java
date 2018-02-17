package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrains.Terrain;

public class MasterRenderer {
	
	//consts
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static final float R = 0.5444f;
	private static final float G = 0.62f;
	private static final float B = 0.69f;
	private static final float A = 1.0f;

	
	//create needed objects
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private SkyboxRenderer skyboxRenderer;
	
	//map and lists of models and terrains
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	
	public MasterRenderer(Loader loader){
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader,projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
	}
	
	//allow backface culling
	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	//disable backface culling
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	//render everything that needs to be rendered
	public void render(List<Light> lights,Camera camera){
		prepare();
		shader.start();
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadSkyCol(R, G, B);
		renderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadSkyCol(R, G, B);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera, R, G, B);
		terrains.clear();
		entities.clear();
	}
	
	//add terrains to a list
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera cam){
		for(Terrain t : terrains){
			processTerrain(t);
		}
		for(Entity e : entities){
			processEntity(e);
		}
		
		render(lights, cam);
	}
	
	//add entities to be batch rendered
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);		
		}
	}
	
	//clean 
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(R, G, B, A);
	}
	
	//generate a projection matrix 
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	

}
