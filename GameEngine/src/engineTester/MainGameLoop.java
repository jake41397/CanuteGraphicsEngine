package engineTester;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop
{
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
			-0.5f, 0.5f, 0,		// VO
			-0.5f, -0.5f, 0f,	// V1
			0.5f, -0.5f, -0f,	// V2
			0.5f, 0.5f, 0f		// V3
		};
		
		int[] indices = {
				0, 1, 3,		// Top Left Triangle (V0, V1, V3)
				3, 1, 2    		// Bottom Right Triangle (V3, V1, V2)
		};
		
		float[] textureCoords = {
				0,0, //V0
				0,1, //V1
				1,1, //V2
				1,0  //V3
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		while(!Display.isCloseRequested())
		{
			//game logic
			renderer.prepare();
			shader.start();
			renderer.render(texturedModel);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
