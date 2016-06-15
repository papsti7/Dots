package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.leveleditor.items.ChainActionPointItem;
import com.sewm.defaultteam.leveleditor.items.NormalEnemyItem;
import com.sewm.defaultteam.leveleditor.items.PlayerItem;
import com.sewm.defaultteam.leveleditor.items.StaticEnemyItem;
import com.sewm.defaultteam.leveleditor.items.TargetItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class LevelEditorItem {
    protected LevelEditor editor_;
	protected Vector2 position_;
    protected String name;
    protected JPanel properties_panel_;

    public static LevelEditorItem create(LevelEditor e, String name, Vector2 position) {
        if (name.equals("Player")) {
            for (LevelEditorItem item : e.getItems()) {
                if (item instanceof PlayerItem) {
                    return null; // only one PlayerItem allowed
                }
            }
            return new PlayerItem(e, name, position, 3);
        } else if (name.equals("Target")) {
            return new TargetItem(e, name, position, Constants.target_radius, 3);
        } else if (name.equals("NormalEnemyEasy")) {
            return new NormalEnemyItem(e, name, position, 0, 0, 0, 0);
        } else if (name.equals("NormalEnemyMedium")) {
            return new NormalEnemyItem(e, name, position, 1, 0, 0, 0);
        } else if (name.equals("NormalEnemyHard")) {
            return new NormalEnemyItem(e, name, position, 2, 0, 0, 0);
        } else if (name.equals("StaticEnemyEasy")) {
            return new StaticEnemyItem(e, name, position, position, 0, 0, 0, 0);
        } else if (name.equals("StaticEnemyMedium")) {
            return new StaticEnemyItem(e, name, position, position, 1, 0, 0, 0);
        } else if (name.equals("StaticEnemyHard")) {
            return new StaticEnemyItem(e, name, position, position, 2, 0, 0, 0);
        } else if (name.equals("ChainActionPoint")) {
            return new ChainActionPointItem(e, name, position);
        }
        return new LevelEditorItem(e, name, position);
    }

    protected LevelEditorItem(LevelEditor editor, String name, Vector2 position) {
        this.editor_ = editor;
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

    public Element toXML(Document document) {
        return null;
    }
}
