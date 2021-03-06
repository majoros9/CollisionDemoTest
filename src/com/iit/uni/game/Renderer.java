package com.iit.uni.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;

import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Utils;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.graph.ShaderProgram;
import com.iit.uni.engine.graph.Transformation;

/**
 * Simple renderer class
 * 
 * @author Mileff Peter
 *
 */
public class Renderer {

	private final Transformation transformation;

	// Our texture drawing shader
	private ShaderProgram shaderProgram;

	// Our line drawing shader
	public static ShaderProgram lineShader;

	// Orthogonal projection Matrix
	public static Matrix4f projectionMatrix;

	public Renderer() {
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {

		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadFile("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFile("shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for world and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
		shaderProgram.createUniform("texture_sampler");

		lineShader = new ShaderProgram();
		lineShader.createVertexShader(Utils.loadFile("shaders/line.vs"));
		lineShader.createFragmentShader(Utils.loadFile("shaders/line.fs"));
		lineShader.link();

		// Create uniforms for world and projection matrices and texture
		lineShader.createUniform("projectionMatrix");
		lineShader.createUniform("modelMatrix");
		lineShader.createUniform("linecolor");

		// Update orthogonal projection Matrix
		projectionMatrix = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window, Texture2D gameItem) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		// Activate texture shader
		shaderProgram.bind();
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		shaderProgram.setUniform("texture_sampler", 0);

		Matrix4f worldMatrix = gameItem.getWorldMatrix();
		shaderProgram.setUniform("worldMatrix", worldMatrix);

		// Render the sprite
		gameItem.render();

		// Deactivate texture shader
		shaderProgram.unbind();

		// Render bounding box
		gameItem.getTransformedBoundingBox().Draw();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
