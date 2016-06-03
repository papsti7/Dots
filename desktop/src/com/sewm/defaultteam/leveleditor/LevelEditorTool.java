package com.sewm.defaultteam.leveleditor;

import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

public class LevelEditorTool extends DefaultMutableTreeNode {
	protected Icon icon_;
	protected Cursor cursor_;

	public LevelEditorTool(String display_name, Icon icon, Cursor cursor) {
		super(display_name);
		icon_ = icon;
		cursor_ = cursor;
	}
	
	public Icon getIcon() {
		return icon_;
	}
	public Cursor getCursor() {
		return cursor_;
	}
}
