package toolbox;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import entities.Player;

public class Maths {
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
			float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	
	public static float getDistance(Vector3f v1, Vector3f v2){
		
		 float width = Math.abs(v1.x - v2.x);
		  float height = Math.abs(v1.y - v2.y);
		  float depth = Math.abs(v1.z - v2.z);
		  
		 float planeDistance = (float) Math.sqrt(width * width + height * height );
		  //return planeDistance;
		  return (float) Math.sqrt( planeDistance * planeDistance + depth * depth);

	}
	
	public static List<Light>  orderLights(Light[] lights, Player player){
		List<Light> orderedLight = new ArrayList<Light>();
		
		for(int i = lights.length - 1; i >=0; i--){
			for(int j = 1;  j <= i; j++){
				if(Maths.getDistance(player.getPosition(), lights[j-1].getPosition()) >
				Maths.getDistance(player.getPosition(), lights[j].getPosition())){
					
					Light temp = lights[j-1];
					lights[j-1] = lights[j];
					lights[j] = temp;
				}
			}
		}

		//turn this into a loop when i add more lights
		orderedLight.add(0, lights[0]);
		orderedLight.add(1, lights[1]);
		orderedLight.add(2, lights[2]);

		return orderedLight;
	}
}
