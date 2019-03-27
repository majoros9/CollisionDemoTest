package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import org.joml.Vector3f;

import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Window;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;

	// 2D Texture items
	private Texture2D gameItem;

	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		gameItem = new Texture2D();
		gameItem.CreateTexture("textures/smiley2.png");
		gameItem.setPosition(100, 100, 0);
	}

	@Override
	public void input(Window window) {

		if (window.isKeyPressed(GLFW_KEY_UP)) {
			// Update position
			Vector3f itemPos = gameItem.getPosition();
			gameItem.setPosition(itemPos.x, itemPos.y - 10.0f, itemPos.z);
		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {

			// Update position
			Vector3f itemPos = gameItem.getPosition();
			gameItem.setPosition(itemPos.x, itemPos.y + 10.0f, itemPos.z);

		} else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			// Update position
			Vector3f itemPos = gameItem.getPosition();
			gameItem.setPosition(itemPos.x - 10.0f, itemPos.y, itemPos.z);
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			// Update position
			Vector3f itemPos = gameItem.getPosition();
			gameItem.setPosition(itemPos.x + 10.0f, itemPos.y, itemPos.z);

		} else if (window.isKeyPressed(GLFW_KEY_R)) {
			float rotation = gameItem.getRotation().z + 1.0f;
			if (rotation > 360) {
				rotation = 0;
			}
			gameItem.setRotation(0, 0, rotation);
		} else if (window.isKeyPressed(GLFW_KEY_Z)) {
			// Update scale
			float scale = gameItem.getScale();
			scale += -1.0f * 0.05f;
			if (scale < 0) {
				scale = 0;
			}
			gameItem.setScale(scale);

		} else if (window.isKeyPressed(GLFW_KEY_X)) {
			// Update scale
			float scale = gameItem.getScale();
			scale += 1.0f * 0.05f;
			if (scale < 0) {
				scale = 0;
			}
			gameItem.setScale(scale);
		}
	}

	@Override
	public void update(float interval) {
	}

	@Override
	public void render(Window window) {
		renderer.render(window, gameItem);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();

		gameItem.getMesh().cleanUp();

	}
}
