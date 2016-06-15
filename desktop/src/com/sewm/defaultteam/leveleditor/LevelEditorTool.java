package com.sewm.defaultteam.leveleditor;

import java.awt.Cursor;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LevelEditorTool extends DefaultMutableTreeNode {
	protected LevelEditor editor_;
	protected String name_;
	protected ACTION action_;
	protected Icon icon_;
	protected Cursor cursor_;

	public LevelEditorTool(LevelEditor editor, String name, Icon icon, Cursor cursor, ACTION action) {
		super(name);
		editor_ = editor;
		icon_ = icon;
		cursor_ = cursor;
		action_ = action;
		name_ = name;
	}
	
	public Icon getIcon() {
		return icon_;
	}
	
	public Cursor getCursor() {
		return cursor_;
	}
	
	public void clicked(Vector2 pos) {
        switch (action_) {
        case SELECT:
            System.out.println("Select at " + pos);
			LevelEditorItem hit = null;
			for (LevelEditorItem item : editor_.getItems()) {
				//TODO maybe adjust bounding box
				if(((item.getX() - 40) < pos.x) && ((item.getX() + 40) > pos.x) &&
						((item.getY() - 40) < pos.y) && ((item.getY() + 40) > pos.y)) {
					System.out.println("item selected: " + item.getName());
					hit = item;
					break;
				}
			}
			editor_.getProperties().setSelectedItem(hit);
            break;

		case DELETE:
			for (LevelEditorItem item : editor_.getItems()) {
				if(((item.getX() - 40) < pos.x) && ((item.getX() + 40) > pos.x) &&
						((item.getY() - 40) < pos.y) && ((item.getY() + 40) > pos.y)) {
					System.out.println("item selected for deletion: " + item.getName());
					editor_.getItems().remove(item);
					//TODO redraw
					//editor_.canvas_renderer_.render();
					break;
				}
			}
			break;

        case PLACE:
            System.out.println("Place at " + pos);
            LevelEditorItem item = LevelEditorItem.create(editor_, name_, pos);
			if (item != null) {
				editor_.addItem(item);
			}
            break;

        default:
            break;
        }
	}
	
	public void draggedAndDropped(Point from, Point to) {
		switch (action_) {
			case MOVE:
				System.out.println("Moved from " + from + " to " + to);
				break;
	
			default:
				break;
		}
	}
	
	public enum ACTION {
		SELECT,
		MOVE,
		PLACE,
		DELETE
	}
}
