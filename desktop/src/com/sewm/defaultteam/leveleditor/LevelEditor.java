package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.graphics.GL20;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;

public class LevelEditor implements ApplicationListener {

	private JFrame frame_;
	private LwjglAWTCanvas canvas_;
	private Cursor cursor_ = Cursor.getDefaultCursor();
	private JTree tools_;
	private Map<String, Cursor> cursors_ = new HashMap<String, Cursor>();
	private Map<String, Icon> icons_ = new HashMap<String, Icon>();

	/**
	 * Create the application.
	 */
	public LevelEditor() {
		canvas_ = new LwjglAWTCanvas(this);
		initializeCursorsAndIcons();
		initializeGUI();
	}

	/**
	 * Initialize custom cursors and icons.
	 */
	private void initializeCursorsAndIcons() {
		Map<String, String> images = new HashMap<String, String>();
		images.put("Player", "images/player_new.png");
		images.put("Target", "images/target_health_3.png");
		images.put("NormalEnemyEasy", "images/enemy_health_3.png");
		images.put("NormalEnemyMedium", "images/enemy_medium_health_3.png");
		images.put("NormalEnemyHard", "images/enemy_strong_health_3.png");
		images.put("StaticEnemyEasy", "images/enemy_static_3.png");
		images.put("StaticEnemyMedium", "images/enemy_static_medium_3.png");
		images.put("StaticEnemyHard", "images/enemy_static_strong_3.png");
		images.put("ChainActionPoint", "images/action_point.png");

		for (String key : images.keySet()) {
			try {
				Toolkit kit = Toolkit.getDefaultToolkit();
				BufferedImage raw = ImageIO.read(Gdx.files.internal(images.get(key)).file());

				Dimension dim = kit.getBestCursorSize(32, 32);
				BufferedImage image = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
				Image scaled = raw.getScaledInstance(dim.width, dim.height, BufferedImage.SCALE_SMOOTH);
				image.getGraphics().drawImage(scaled, 0, 0, null);
				Cursor cursor = kit.createCustomCursor(image, new Point((dim.width - 1) / 2, (dim.height - 1) / 2), key);
				cursors_.put(key, cursor);

				dim = new Dimension(16, 16);
				image = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
				scaled = raw.getScaledInstance(dim.width, dim.height, BufferedImage.SCALE_SMOOTH);
				image.getGraphics().drawImage(scaled, 0, 0, null);
				Icon icon = new ImageIcon(image, key);
				icons_.put(key, icon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUI() {
		frame_ = new JFrame();
		frame_.setBounds(100, 100, 800, 600);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_.getContentPane().setLayout(new BoxLayout(frame_.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPaneLeft = new JSplitPane();
		splitPaneLeft.setOneTouchExpandable(true);
		splitPaneLeft.setEnabled(false);
		frame_.getContentPane().add(splitPaneLeft);

		tools_ = new JTree(new LevelEditorTreeModel(icons_, cursors_));
		tools_.setRootVisible(false);
		for (int i = 0; i < tools_.getRowCount(); i++) {
			tools_.expandRow(i);
		}
		tools_.setCellRenderer(new EditorTreeCellRenderer());
		tools_.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				Object value = tools_.getLastSelectedPathComponent();
				if (value instanceof LevelEditorTool) {
					LevelEditorTool item = (LevelEditorTool) value;
					cursor_ = item.getCursor();
				} else {
					cursor_ = Cursor.getDefaultCursor();
				}
			}
		});
		JScrollPane panelLeft = new JScrollPane(tools_);
		splitPaneLeft.setLeftComponent(panelLeft);
		
		JSplitPane splitPaneRight = new JSplitPane();
		splitPaneRight.setOneTouchExpandable(true);
		splitPaneRight.setEnabled(false);
		splitPaneRight.setResizeWeight(1.0);
		splitPaneLeft.setRightComponent(splitPaneRight);
		
		JPanel panelCenter = new JPanel();
		splitPaneRight.setLeftComponent(panelCenter);

		canvas_.getCanvas().addMouseListener(new LevelEditorMouseListener(this));
		panelCenter.add(canvas_.getCanvas());
		panelCenter.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		splitPaneRight.setRightComponent(scrollPane);
	}

	@Override
	public void create() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

	public JFrame getFrame() {
		return frame_;
	}

    public Canvas getCanvas() {
        return canvas_.getCanvas();
    }

	public Cursor getCursor() {
		return cursor_;
	}

	public LevelEditorTool getSelectedTool() {
		Object value = tools_.getLastSelectedPathComponent();
		if (value instanceof LevelEditorTool) {
			return (LevelEditorTool) value;
		}
		return null;
	}

	private class EditorTreeCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof LevelEditorTool) {
				setIcon(((LevelEditorTool) value).getIcon());
			}
			return component;
		}
	}

}
