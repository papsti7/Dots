package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class LevelEditorCanvasRenderer implements ApplicationListener {
	LevelEditor editor_;
	SpriteBatch spriteBatch_;
	public static Map<String, Texture> textures_ = new HashMap<String, Texture>();
	
	public LevelEditorCanvasRenderer(LevelEditor editor) {
		editor_ = editor;
	}
	
	@Override
	public void resize(int width, int height) {
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
		spriteBatch_ = new SpriteBatch();

        for (String key : LevelEditor.images_.keySet()) {
            Texture texture = new Texture(Gdx.files.internal(LevelEditor.images_.get(key)));
            textures_.put(key, texture);
        }
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        spriteBatch_.begin();
        for (LevelEditorItem item : editor_.getItems()) {
            item.draw(spriteBatch_);
        }
	    spriteBatch_.end();
	}

}
