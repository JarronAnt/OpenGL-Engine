package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class SkyboxRenderer  {

private static final float SIZE = 500f;
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};

	private static String[] TEXTURE_FILES = {"right","left","top","bottom","back","front"};
	private static String[] TEXTURE_FILES2 = {"nightRight","nightLeft",
			"nightTop","nightBottom","nightBack","nightFront"};

	
	private RawModel cube; 
	private int dayTex;
	private int nightTex;
	private float time = 0;

	private SkyboxShader shader; 
	
	public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix){
		cube = loader.loadToVAO(VERTICES, 3);
		dayTex = loader.loadCubeMap(TEXTURE_FILES);
		nightTex = loader.loadCubeMap(TEXTURE_FILES2);

		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
	}
	
	public void render(Camera cam, float r, float g ,float b){
		shader.start();
		shader.loadViewMatrix(cam);
		shader.loadFogCol(r, g, b);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTex();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	private void bindTex(){
		time += DisplayManager.getDelta() * 1000;
		time %= 24000;
		int texture1;
		int texture2;
		float blendFactor;		
		if(time >= 0 && time < 5000){
			texture1 = nightTex;
			texture2 = nightTex;
			blendFactor = (time - 0)/(5000 - 0);
		}else if(time >= 5000 && time < 8000){
			texture1 = nightTex;
			texture2 = dayTex;
			blendFactor = (time - 5000)/(8000 - 5000);
		}else if(time >= 8000 && time < 21000){
			texture1 = dayTex;
			texture2 = dayTex;
			blendFactor = (time - 8000)/(21000 - 8000);
		}else{
			texture1 = dayTex;
			texture2 = nightTex;
			blendFactor = (time - 21000)/(24000 - 21000);
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);


	}
}
