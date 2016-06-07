package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LevelEditor {
    public static final Map<String, String> images_;
    static {
        images_ = new HashMap<String, String>();
        images_.put("Player", "images/player_new.png");
        images_.put("Target", "images/target_health_3.png");
        images_.put("NormalEnemyEasy", "images/enemy_health_3.png");
        images_.put("NormalEnemyMedium", "images/enemy_medium_health_3.png");
        images_.put("NormalEnemyHard", "images/enemy_strong_health_3.png");
        images_.put("StaticEnemyEasy", "images/enemy_static_3.png");
        images_.put("StaticEnemyMedium", "images/enemy_static_medium_3.png");
        images_.put("StaticEnemyHard", "images/enemy_static_strong_3.png");
        images_.put("ChainActionPoint", "images/action_point.png");
    }

	private JFrame frame_;
	private LwjglAWTCanvas canvas_;
	private JScrollPane panelProperties_;
	private JTree tools_;
	
	private Cursor cursor_ = Cursor.getDefaultCursor();
	
	private Map<String, Cursor> cursors_ = new HashMap<String, Cursor>();
	private Map<String, Icon> icons_ = new HashMap<String, Icon>();
	
	private List<LevelEditorItem> items_ = new ArrayList<LevelEditorItem>();

	/**
	 * Create the application.
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public LevelEditor() throws NoSuchMethodException, SecurityException {
		canvas_ = new LwjglAWTCanvas(new LevelEditorCanvasRenderer(this));
		initializeCursorsAndIcons();
		initializeGUI();
	}

	/**
	 * Initialize textures, custom cursors and icons.
	 */
	private void initializeCursorsAndIcons() {
		for (String key : images_.keySet()) {
			try {
                Toolkit kit = Toolkit.getDefaultToolkit();
                BufferedImage raw = ImageIO.read(Gdx.files.internal(images_.get(key)).file());

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
            } catch (GdxRuntimeException e) {
                System.err.println("Texture file not found! Make sure to set the right working directory!");
                throw e;
			} catch (IOException e) {
                e.printStackTrace();
            }
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void initializeGUI() throws NoSuchMethodException, SecurityException {
		frame_ = new JFrame();
		frame_.setBounds(100, 100, 800, 600);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_.getContentPane().setLayout(new BoxLayout(frame_.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPaneLeft = new JSplitPane();
		splitPaneLeft.setEnabled(false);
		frame_.getContentPane().add(splitPaneLeft);

		tools_ = new JTree(new LevelEditorTreeModel(this, icons_, cursors_));
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
		
		JLabel lblTools = new JLabel("Tools");
		lblTools.setHorizontalAlignment(SwingConstants.CENTER);
		panelLeft.setColumnHeaderView(lblTools);
		
		JSplitPane splitPaneRight = new JSplitPane();
		splitPaneRight.setResizeWeight(1.0);
		splitPaneRight.setEnabled(false);
		splitPaneLeft.setRightComponent(splitPaneRight);
		
		JPanel panelCenter = new JPanel();
		splitPaneRight.setLeftComponent(panelCenter);

		canvas_.getCanvas().addMouseListener(new LevelEditorMouseListener(this));
		panelCenter.setLayout(new BorderLayout(0, 0));
		panelCenter.add(canvas_.getCanvas(), BorderLayout.CENTER);
		
		panelProperties_ = new JScrollPane();
		JLabel lblProperties = new JLabel("Properties");
		lblProperties.setHorizontalAlignment(SwingConstants.CENTER);
		panelProperties_.setColumnHeaderView(lblProperties);
		splitPaneRight.setRightComponent(panelProperties_);
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
	
	public List<LevelEditorItem> getItems() {
		return items_;
	}

    public void addItem(LevelEditorItem item) {
        synchronized (items_) {
            items_.add(item);
        }
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
			if (value instanceof LevelEditorTool && ((LevelEditorTool) value).getIcon() != null) {
				setIcon(((LevelEditorTool) value).getIcon());
			}
			return component;
		}
	}

}
