package tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	
	public static Matrix4f createTransformation(Vector3f translation, float rx, float ry, float rz, float scale)
	{
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(translation, mat, mat);
		mat.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), mat, mat);
		mat.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), mat, mat);
		mat.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), mat, mat);
		mat.scale(new Vector3f(scale, scale, scale), mat, mat);

		return mat; 
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f viewMat = new Matrix4f();
		viewMat.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMat, viewMat);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMat, viewMat);
		Vector3f camPos = camera.getPos();
		Vector3f negativeCamPos = new Vector3f(-camPos.x, -camPos.y, -camPos.z);
		Matrix4f.translate(negativeCamPos, viewMat, viewMat);
		
		return viewMat; 

	}
}
