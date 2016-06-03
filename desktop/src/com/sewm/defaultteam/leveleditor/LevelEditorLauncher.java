package com.sewm.defaultteam.leveleditor;

import java.awt.EventQueue;

public class LevelEditorLauncher {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelEditor editor = new LevelEditor();
					editor.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
