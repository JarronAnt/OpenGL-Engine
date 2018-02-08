package renderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class ObjLoader {
	
	public static RawModel loadOBJ(String fileName, ModelLoader loader)
	{
		//read the obj file
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load "+fileName);
			e.printStackTrace();
		}
		
		//create all the vars needed
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> verts = new ArrayList<Vector3f>();
		List<Vector2f> texs = new ArrayList<Vector2f>();
		List<Vector3f> norms = new ArrayList<Vector3f>();
		List<Integer> indicies = new ArrayList<Integer>();
		float[] vertsArray = null;
		float[] texsArray = null;
		float[] normsArray = null;
		int[] indexArray = null; 
		
		try{
			//read through the obj file 
			while(true){
				line = reader.readLine();
				String[] currLine = line.split(" ");
				
				//check if line contains a vertex
				if(line.startsWith("v ")){
					Vector3f vertex =  new Vector3f(Float.parseFloat(currLine[1]), 
							Float.parseFloat(currLine[2]), Float.parseFloat(currLine[3]));
					
					verts.add(vertex);
				//check if line containes texture coord
				}else if(line.startsWith("vt ")){
					Vector2f textures = new Vector2f(Float.parseFloat(currLine[1]), 
							Float.parseFloat(currLine[2]));
					
					texs.add(textures);
				//check if line contains a normal
				}else if(line.startsWith("vn ")){
					Vector3f normals =  new Vector3f(Float.parseFloat(currLine[1]), 
							Float.parseFloat(currLine[2]), Float.parseFloat(currLine[3]));
					
					norms.add(normals);
				//check if line contains a face
				}else if(line.startsWith("f ")){
					texsArray = new float[verts.size()*2];
					normsArray = new float[verts.size()*3];
					break;
					
				}
				
			}
			//go through faces
			while(line!= null){
				if(!line.startsWith("f ")){
					line = reader.readLine();
					continue;
				}
				
				//create the verts of each triangle
				String[] currLine = line.split(" ");
				String[] vert1 = currLine[1].split("/");
				String[] vert2 = currLine[2].split("/");
				String[] vert3 = currLine[3].split("/");
				
				//process the verts 
				processVert(vert1, indicies, texs, norms, texsArray, normsArray);
				processVert(vert2, indicies, texs, norms, texsArray, normsArray);
				processVert(vert3, indicies, texs, norms, texsArray, normsArray);
				
				line = reader.readLine();

			}
			reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		vertsArray = new float[verts.size()*3];
		indexArray = new int[indicies.size()];
		
		int vertexPointer = 0;
		
		
		for(Vector3f vert:verts)
		{
			vertsArray[vertexPointer++] = vert.x;
			vertsArray[vertexPointer++] = vert.y;
			vertsArray[vertexPointer++] = vert.z;

		}
		
		for (int i=0; i < indicies.size(); i++)
		{
			indexArray[i] = indicies.get(i);
			
		}

		return loader.loadToVao(vertsArray, texsArray, indexArray);
	}
	
	private static void processVert(String[] vertData, List<Integer> indicies, 
			List<Vector2f> texs, List<Vector3f> norms, float[] texsArray, float[] normsArray){
		
		int currentVertPointer = Integer.parseInt(vertData[0])-1;
		indicies.add(currentVertPointer);
		Vector2f currTex = texs.get(Integer.parseInt(vertData[1])-1);
		texsArray[currentVertPointer*2] = currTex.x;
		texsArray[currentVertPointer*2+1] = 1- currTex.y;
		Vector3f currNorm = norms.get(Integer.parseInt(vertData[2])-1);
		normsArray[currentVertPointer*3] = currNorm.x;
		normsArray[currentVertPointer*3+1] = currNorm.y;
		normsArray[currentVertPointer*3+2] = currNorm.z;

		
	}
}
