package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;

public class LevelEditorItem {
	Vector2 position_;
	String name_;

    public LevelEditorItem(String name, Vector2 position) {
        name_ = name;
        position_ = position;
    }

	public void draw(SpriteBatch spriteBatch) {
        Texture texture = LevelEditorCanvasRenderer.textures_.get(name_);
        spriteBatch.draw(texture,
                position_.x * Gdx.graphics.getWidth() / Constants.virtual_screen_width - texture.getWidth() / 2.f,
                position_.y * Gdx.graphics.getHeight() / Constants.virtual_screen_height - texture.getHeight() / 2.f);
	}
}
