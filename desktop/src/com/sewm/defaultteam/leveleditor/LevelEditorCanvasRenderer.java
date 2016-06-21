package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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
        LevelEditorItem dragged = editor_.getDraggedItem();
        LevelEditorItem selected = editor_.getProperties().getSelectedItem();

        sprite_batch_.begin();
        sprite_batch_.draw(textures_.get(LevelEditor.BACKGROUND), 0, 0);

        if (selected != null) {
            Color c = sprite_batch_.getColor();
            sprite_batch_.setColor(c.r, c.g, c.b, 0.3f);
        }

        for (LevelEditorItem item : editor_.getFile().getItems()) {
            if (!item.equals(selected) && !item.equals(dragged)) {
                item.draw(sprite_batch_);
            }
        }

        if (selected != null) {
            Color c = sprite_batch_.getColor();
            sprite_batch_.setColor(c.r, c.g, c.b, 1f);

            if (!selected.equals(dragged)) {
                selected.draw(sprite_batch_);
            }
        }
	    sprite_batch_.end();
    }

}
