package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer 
{
	public void prep()
	{
		//clear the screne
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(255,0,255,1);

	}
	
	public void render (RawModel model)
	{
		//activate the vao
		GL30.glBindVertexArray(model.getVaoID());
		//activate a specific slot in the vao
		GL20.glEnableVertexAttribArray(0);
		//draw the model(drawing using the index buffer
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );
		//deactivate everything
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

	}
}
