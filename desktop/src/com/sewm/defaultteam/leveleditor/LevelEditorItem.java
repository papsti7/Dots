package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.leveleditor.items.ChainActionPointItem;
import com.sewm.defaultteam.leveleditor.items.NormalEnemyItem;
import com.sewm.defaultteam.leveleditor.items.StaticEnemyItem;
import com.sewm.defaultteam.leveleditor.items.TargetItem;

import javax.swing.JPanel;

public class LevelEditorItem {
	protected Vector2 position_;
    protected String name;
    protected JPanel properties_panel_;

    public static LevelEditorItem create(String name, Vector2 position) {
        if (name.equals("Target")) {
            return new TargetItem(name, position, Constants.target_radius, 3);
        } else if (name.equals("NormalEnemyEasy")) {
            return new NormalEnemyItem(name, position, 0, 0, 0, 0);
        } else if (name.equals("NormalEnemyMedium")) {
            return new NormalEnemyItem(name, position, 1, 0, 0, 0);
        } else if (name.equals("NormalEnemyHard")) {
            return new NormalEnemyItem(name, position, 2, 0, 0, 0);
        } else if (name.equals("StaticEnemyEasy")) {
            return new StaticEnemyItem(name, position, position, 0, 0, 0, 0);
        } else if (name.equals("StaticEnemyMedium")) {
            return new StaticEnemyItem(name, position, position, 1, 0, 0, 0);
        } else if (name.equals("StaticEnemyHard")) {
            return new StaticEnemyItem(name, position, position, 2, 0, 0, 0);
        } else if (name.equals("ChainActionPoint")) {
            return new ChainActionPointItem(name, position);
        }
        return new LevelEditorItem(name, position);
    }

    protected LevelEditorItem(String name, Vector2 position) {
        this.name = name;
        this.position_ = position;
        this.properties_panel_ = new JPanel();
    }

	public void draw(SpriteBatch spriteBatch) {
        Texture texture = LevelEditorCanvasRenderer.textures_.get(name);
        spriteBatch.draw(texture,
                position_.x - texture.getWidth() / 2.f,
                position_.y - texture.getHeight() / 2.f,
                texture.getWidth(), texture.getHeight());
	}

    public String getName() {
        return name;
    }

    public float getX() {
        return position_.x;
    }

    public float getY() {
        return position_.y;
    }

    public JPanel getPropertiesPanel() { return properties_panel_; }
}
