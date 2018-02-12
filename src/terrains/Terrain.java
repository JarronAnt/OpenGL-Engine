package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.TexturePack;
import textures.Textures;

public class Terrain {
	
	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MIN_HEIGHT = -40;
	private static final float MAX_PIXEL_COL = (float) Math.pow(256,3);


	
	private float x;
	private float z;
	private RawModel model;
	private TexturePack terrainPack;
	private Textures blendMap;
	
	public Terrain(int gridX, int gridZ, Loader loader, TexturePack terrainPack, 
			Textures blendMap, String heightMap){
		this.terrainPack = terrainPack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
	}
	
	
	
	public float getX() {
		return x;
	}



	public float getZ() {
		return z;
	}



	public RawModel getModel() {
		return model;
	}


	public TexturePack getTerrainPack() {
		return terrainPack;
	}



	public Textures getBlendMap() {
		return blendMap;
	}



	private RawModel generateTerrain(Loader loader, String heightMap){
		
		BufferedImage hm = null;
		try {
			hm = ImageIO.read(new File("res/"+heightMap+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int VERTEX_COUNT = hm.getHeight();
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = getHeight(j, i, hm);
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calcNormals(j,i,hm);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x, int y, BufferedImage image){
		/*if(x<0 || x >= image.getHeight() || y<0 || y >= image.getHeight()){
			return 0;
		}*/
		
		  if(x<0){
			  x=0;}
		  
		  if(y<0){
			  y=0;}
		  
		  if(x >= image.getWidth()){
			  x=image.getWidth()-1;}
		  
		  if(y>=image.getHeight()){
			  y=image.getHeight()-1;}
		
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COL/2f;
		height /= MAX_PIXEL_COL/2f;
		height *= MAX_HEIGHT;
		
		return height;
	}
	
	private Vector3f calcNormals(int x, int z, BufferedImage image){
		float heightL = getHeight(x-1, z, image);
		float heightR = getHeight(x+1, z, image);
		float heightU = getHeight(x, z+1, image);
		float heightD = getHeight(x, z-1, image);
		
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
		normal.normalise();
		
		return normal;
	}

}
