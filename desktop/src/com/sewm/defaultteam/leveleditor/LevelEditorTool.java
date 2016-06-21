package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.math.Vector2;

import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

public class LevelEditorTool extends DefaultMutableTreeNode {
	protected LevelEditor editor_;
	protected String name_;
	protected ACTION action_;
	protected Icon icon_;
	protected Cursor cursor_;

	private LevelEditorItem selected_item_;

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

	public LevelEditorItem getSelectedItem() {
		return selected_item_;
	}
	
	public void clicked(Vector2 pos) {
        switch (action_) {
        case SELECT:
            System.out.println("Select at " + pos);
			LevelEditorItem hit = null;
			for (LevelEditorItem item : editor_.getFile().getItems()) {
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
			for (LevelEditorItem item : editor_.getFile().getItems()) {
				if(((item.getX() - 40) < pos.x) && ((item.getX() + 40) > pos.x) &&
						((item.getY() - 40) < pos.y) && ((item.getY() + 40) > pos.y)) {
					System.out.println("item selected for deletion: " + item.getName());
					editor_.getFile().removeItem(item);
					break;
				}
			}
			break;

        case PLACE:
            System.out.println("Place at " + pos);
            LevelEditorItem item = LevelEditorItem.create(editor_, name_, pos);
			if (item != null) {
				editor_.getFile().addItem(item);
			}
            break;


        default:
            break;
        }
	}

	public void draggedAndDropped(Vector2 pos, Boolean start) {
		switch (action_) {
			case MOVE:
				if(start) {
					for (LevelEditorItem item : editor_.getFile().getItems()) {
						if(((item.getX() - 40) < pos.x) && ((item.getX() + 40) > pos.x) &&
								((item.getY() - 40) < pos.y) && ((item.getY() + 40) > pos.y)) {
							System.out.println("item selected for movement: " + item.getName());
							selected_item_ = item;
							editor_.getFrame().setCursor(editor_.cursors_.get(item.getName()));

							break;
						}
					}
				} else {
					System.out.println("move to: " + pos);
					if (selected_item_  != null) {
						selected_item_.move(pos);
						selected_item_ = null;
						editor_.getFrame().setCursor(editor_.getCursor());
					}
				}
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
