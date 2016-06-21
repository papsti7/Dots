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

import javax.swing.JPanel;

import static com.sewm.defaultteam.leveleditor.LevelEditor.CHAIN_ACTION_POINT;
import static com.sewm.defaultteam.leveleditor.LevelEditor.NORMAL_ENEMY_EASY;
import static com.sewm.defaultteam.leveleditor.LevelEditor.NORMAL_ENEMY_HARD;
import static com.sewm.defaultteam.leveleditor.LevelEditor.NORMAL_ENEMY_MEDIUM;
import static com.sewm.defaultteam.leveleditor.LevelEditor.PLAYER;
import static com.sewm.defaultteam.leveleditor.LevelEditor.STATIC_ENEMY_EASY;
import static com.sewm.defaultteam.leveleditor.LevelEditor.STATIC_ENEMY_HARD;
import static com.sewm.defaultteam.leveleditor.LevelEditor.STATIC_ENEMY_MEDIUM;
import static com.sewm.defaultteam.leveleditor.LevelEditor.TARGET;

public class LevelEditorItem {
    protected LevelEditor editor_;
	protected Vector2 position_;
    protected String name;
    protected JPanel properties_panel_;

    public static LevelEditorItem create(LevelEditor e, String name, Vector2 position) {
        if (name.equals(PLAYER)) {
            for (LevelEditorItem item : e.getFile().getItems()) {
                if (item instanceof PlayerItem) {
                    return null; // only one PlayerItem allowed
                }
            }
            return new PlayerItem(e, 3);
        } else if (name.equals(TARGET)) {
            return new TargetItem(e, position, Constants.target_radius, 3);
        } else if (name.equals(NORMAL_ENEMY_EASY)) {
            return new NormalEnemyItem(e, position, 1, 0, 0, 0);
        } else if (name.equals(NORMAL_ENEMY_MEDIUM)) {
            return new NormalEnemyItem(e, position, 2, 0, 0, 0);
        } else if (name.equals(NORMAL_ENEMY_HARD)) {
            return new NormalEnemyItem(e, position, 3, 0, 0, 0);
        } else if (name.equals(STATIC_ENEMY_EASY)) {
            return new StaticEnemyItem(e, position, position.cpy(), 1, 0, 0, 0);
        } else if (name.equals(STATIC_ENEMY_MEDIUM)) {
            return new StaticEnemyItem(e, position, position.cpy(), 2, 0, 0, 0);
        } else if (name.equals(STATIC_ENEMY_HARD)) {
            return new StaticEnemyItem(e, position, position.cpy(), 3, 0, 0, 0);
        } else if (name.equals(CHAIN_ACTION_POINT)) {
            return new ChainActionPointItem(e, position);
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

    public void move(Vector2 to) {
        position_.set(to);
        properties_panel_ = new JPanel();
        createPanel();
        editor_.getProperties().setSelectedItem(editor_.getProperties().getSelectedItem());
    }

    protected void createPanel() {
        // nothing by default
    }

    @Override
    public String toString() {
        return name;
    }
}
