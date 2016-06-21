package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;

public class LevelEditor {
    public static final String SELECT = "Select";
    public static final String DELETE = "Delete";
    public static final String MOVE = "Move";
    public static final String PLAYER = "Player";
    public static final String TARGET = "Target";
    public static final String NORMAL_ENEMY_EASY = "NormalEnemyEasy";
    public static final String NORMAL_ENEMY_MEDIUM = "NormalEnemyMedium";
    public static final String NORMAL_ENEMY_HARD = "NormalEnemyHard";
    public static final String STATIC_ENEMY_EASY = "StaticEnemyEasy";
    public static final String STATIC_ENEMY_MEDIUM = "StaticEnemyMedium";
    public static final String STATIC_ENEMY_HARD = "StaticEnemyHard";
    public static final String CHAIN_ACTION_POINT = "ChainActionPoint";
    public static final String BACKGROUND = "Background";
	public static final String FILE_NEW = "New";
	public static final String FILE_OPEN = "Open...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_SAVE_AS = "Save As...";
    public static final String FILE_UNNAMED = "Unnamed Level";

    public static final Map<String, String> images_;
    static {
        images_ = new HashMap<String, String>();
        images_.put(PLAYER, "images/player_new.png");
        images_.put(TARGET, "images/target_health_3.png");
        images_.put(NORMAL_ENEMY_EASY, "images/enemy_health_3.png");
        images_.put(NORMAL_ENEMY_MEDIUM, "images/enemy_medium_health_3.png");
        images_.put(NORMAL_ENEMY_HARD, "images/enemy_strong_health_3.png");
        images_.put(STATIC_ENEMY_EASY, "images/enemy_static_3.png");
        images_.put(STATIC_ENEMY_MEDIUM, "images/enemy_static_medium_3.png");
        images_.put(STATIC_ENEMY_HARD, "images/enemy_static_strong_3.png");
        images_.put(CHAIN_ACTION_POINT, "images/action_point.png");
        images_.put(BACKGROUND, "images/background.png");
    }

	private JFrame frame_;
	private LwjglAWTCanvas canvas_;
    public LevelEditorCanvasRenderer canvas_renderer_;
	private JScrollPane panelProperties_;
    private JPanel panelCenter_;
	private JTree tools_;
    private JList<LevelEditorItem> itemList_;

	public Cursor cursor_ = Cursor.getDefaultCursor();

	private final LevelEditorFile file_;
    private final LevelEditorProperties properties_;

	public Map<String, Cursor> cursors_ = new HashMap<String, Cursor>();
	private Map<String, Icon> icons_ = new HashMap<String, Icon>();

	/**
	 * Create the application.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public LevelEditor() throws NoSuchMethodException, SecurityException {
        canvas_renderer_ = new LevelEditorCanvasRenderer(this);
		canvas_ = new LwjglAWTCanvas(canvas_renderer_);
		initializeCursorsAndIcons();
		initializeGUI();
        file_ = new LevelEditorFile(this);
        properties_ = new LevelEditorProperties(this);
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
		frame_.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelHelperLeft = new JPanel();
		frame_.getContentPane().add(panelHelperLeft, BorderLayout.WEST);
		panelHelperLeft.setLayout(new BorderLayout(0, 0));
		
		tools_ = new JTree(new LevelEditorTreeModel(this, icons_, cursors_));
		tools_.setVisibleRowCount(15);
		tools_.setRootVisible(false);
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
		for (int i = 0; i < tools_.getRowCount(); i++) {
			tools_.expandRow(i);
		}

		JScrollPane panelTools = new JScrollPane(tools_);
		panelHelperLeft.add(panelTools, BorderLayout.NORTH);

		JLabel lblTools = new JLabel("Tools");
		lblTools.setLabelFor(tools_);
		lblTools.setHorizontalAlignment(SwingConstants.CENTER);
		panelTools.setColumnHeaderView(lblTools);
		
		JScrollPane panelItems = new JScrollPane();
		panelHelperLeft.add(panelItems, BorderLayout.CENTER);
		
		itemList_ = new JList<LevelEditorItem>();
		panelItems.setViewportView(itemList_);
		
		JLabel lblItems = new JLabel("Items");
		lblItems.setHorizontalAlignment(SwingConstants.CENTER);
		lblItems.setLabelFor(itemList_);
		panelItems.setColumnHeaderView(lblItems);

		panelCenter_ = new JPanel();
		frame_.getContentPane().add(panelCenter_, BorderLayout.CENTER);

		canvas_.getCanvas().addMouseListener(new LevelEditorMouseListener(this));
		panelCenter_.setLayout(new BorderLayout(0, 0));
		panelCenter_.add(canvas_.getCanvas(), BorderLayout.CENTER);
		
		JPanel panelHelperRight = new JPanel();
		frame_.getContentPane().add(panelHelperRight, BorderLayout.EAST);
		panelHelperRight.setLayout(new BorderLayout(0, 0));
		
        panelProperties_ = new JScrollPane();
        panelHelperRight.add(panelProperties_, BorderLayout.CENTER);
				
        JLabel lblProperties = new JLabel("Properties");
        lblProperties.setHorizontalAlignment(SwingConstants.CENTER);
        panelProperties_.setColumnHeaderView(lblProperties);

        Component horizontalStrut = Box.createHorizontalStrut(170);
        panelHelperRight.add(horizontalStrut, BorderLayout.SOUTH);

		JMenuBar menuBar = new JMenuBar();
		frame_.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem menuItemNew = new JMenuItem(new NewFileAction(FILE_NEW));
		fileMenu.add(menuItemNew);
		JMenuItem menuItemOpen = new JMenuItem(new OpenFileAction(FILE_OPEN));
		fileMenu.add(menuItemOpen);
		JMenuItem menuItemSave = new JMenuItem(new SaveFileAction(FILE_SAVE));
		fileMenu.add(menuItemSave);
		JMenuItem menuItemSaveAs = new JMenuItem(new SaveAsFileAction(FILE_SAVE_AS));
		fileMenu.add(menuItemSaveAs);
	}

	public JFrame getFrame() {
		return frame_;
	}

    public JScrollPane getPropertiesPanel() { return panelProperties_; }

    public Canvas getCanvas() {
        return canvas_.getCanvas();
    }

	public Cursor getCursor() {
		return cursor_;
	}

    public LevelEditorProperties getProperties() {
        return properties_;
    }

    public LevelEditorFile getFile() {
        return file_;
    }

    public JList<LevelEditorItem> getItemList() {
        return itemList_;
    }

    public LevelEditorItem getDraggedItem() {
        Object tool = tools_.getModel().getChild(tools_.getModel().getRoot(), 1);
        if (tool instanceof LevelEditorTool) {
            return ((LevelEditorTool) tool).getSelectedItem();
        }
        return null;
    }

	public LevelEditorTool getSelectedTool() {
		Object value = tools_.getLastSelectedPathComponent();
		if (value instanceof LevelEditorTool) {
            return (LevelEditorTool) value;
		}
		return null;
	}
	
	private class NewFileAction extends AbstractAction {
		public NewFileAction(String name) {
			super(name);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("new file...");
			file_.newFile();
		}
	}
	
	private class OpenFileAction extends AbstractAction {
		public OpenFileAction(String name) {
			super(name);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("open file...");
			file_.openFile();
			
		}
	}
	
	private class SaveFileAction extends AbstractAction {
		public SaveFileAction(String name) {
			super(name);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save file...");
			file_.save();
		}
	}
	
	private class SaveAsFileAction extends AbstractAction {
		public SaveAsFileAction(String name) {
			super(name);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save as file...");
			file_.saveAs();
		}
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
