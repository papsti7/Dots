package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sewm.defaultteam.Constants;

import java.util.HashMap;
import java.util.Map;

public class LevelEditorCanvasRenderer implements ApplicationListener {
    Stage stage_;
    Viewport viewport_;
	LevelEditor editor_;
	SpriteBatch sprite_batch_;
	public static Camera camera_;
	public static Map<String, Texture> textures_ = new HashMap<String, Texture>();
	
	public LevelEditorCanvasRenderer(LevelEditor editor) {
		editor_ = editor;
	}
	
	@Override
	public void resize(int width, int height) {
        Constants.resize(width, height);
        viewport_.update(width, height, true);
        sprite_batch_.setProjectionMatrix(camera_.combined);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void create() {
		camera_ = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport_ = new StretchViewport(Constants.virtual_screen_width, Constants.virtual_screen_height, camera_);
		sprite_batch_ = new SpriteBatch();

        for (String key : LevelEditor.images_.keySet()) {
            Texture texture = new Texture(Gdx.files.internal(LevelEditor.images_.get(key)));
            textures_.put(key, texture);
        }
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        LevelEditorItem selected = editor_.getProperties().getSelectedItem();
        sprite_batch_.begin();

        if (selected != null) {
            sprite_batch_.enableBlending();
            Color c = sprite_batch_.getColor();
            sprite_batch_.setColor(c.r, c.g, c.b, 0.3f);
        }

        for (LevelEditorItem item : editor_.getFile().getItems()) {
            if (!item.equals(selected)) {
                item.draw(sprite_batch_);
            }
        }

        if (selected != null) {
            Color c = sprite_batch_.getColor();
            sprite_batch_.setColor(c.r, c.g, c.b, 1f);
            editor_.getProperties().getSelectedItem().draw(sprite_batch_);
        }
	    sprite_batch_.end();
    }

}
