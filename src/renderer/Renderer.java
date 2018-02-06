package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import models.TexturedModel;

public class Renderer 
{
	public void prep()
	{
		//clear the screne
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(255,0,255,1);

	}
	
	public void render (TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();
		//activate the vao
		GL30.glBindVertexArray(model.getVaoID());
		//activate a specific slot in the vao
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		//draw the model(drawing using the index buffer
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );
		//deactivate everything
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		//bind the vaos 
		GL30.glBindVertexArray(0);
	}
}
