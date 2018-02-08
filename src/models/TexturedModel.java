package models;

import textures.ModelTexture;

public class TexturedModel 
{
	private RawModel rawModel; 
	private ModelTexture texture;
	
	//obejct that holds a raw model and a texture for that model
	public TexturedModel(RawModel model, ModelTexture tex)
	{
		this.rawModel = model;
		this.texture = tex; 
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
