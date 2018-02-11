package textures;

public class TexturePack {
	
	private Textures bgTerrain;
	private Textures redTerrain;
	private Textures blueTerrain;
	private Textures greenTerrain;
	
	public TexturePack(Textures bgTerrain, Textures redTerrain, Textures blueTerrain, Textures greenTerrain) {
		this.bgTerrain = bgTerrain;
		this.redTerrain = redTerrain;
		this.blueTerrain = blueTerrain;
		this.greenTerrain = greenTerrain;
	}

	public Textures getBgTerrain() {
		return bgTerrain;
	}

	public Textures getRedTerrain() {
		return redTerrain;
	}

	public Textures getBlueTerrain() {
		return blueTerrain;
	}

	public Textures getGreenTerrain() {
		return greenTerrain;
	}
	
	

}
