package com.sewm.defaultteam.desktop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.imageio.plugins.common.ImageUtil;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JTree;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.CardLayout;

public class LevelEditor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelEditor window = new LevelEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LevelEditor() {
		initializeCursors();
		initialize();
	}
	
	/**
	 * Initialize custom cursors.
	 */
	private void initializeCursors() {
		Map<String, String> cursors = new HashMap<String, String>();
		cursors.put("Player", "images/player_new.png");
		cursors.put("NormalEnemyEasy", "images/enemy_health_1.png");
		cursors.put("NormalEnemyMedium", "images/enemy_medium_health_1.png");
		cursors.put("NormalEnemyHard", "images/enemy_strong_health_1.png");
		
		for (String cursorName : cursors.keySet()) {
			try {
				Toolkit kit = Toolkit.getDefaultToolkit();
			    Dimension dim = kit.getBestCursorSize(48, 48);
				BufferedImage buffered = ImageIO.read(Gdx.files.internal(cursors.get(cursorName)).file());
			    Image image = buffered.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);
			    Cursor cursor = kit.createCustomCursor(image, new Point((dim.width - 1) / 2, (dim.height - 1) / 2), cursorName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.1);
		frame.getContentPane().add(splitPane);
		
		JPanel panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		panelLeft.setLayout(new CardLayout(0, 0));
		
		JTree tree = new JTree(new EditorTreeModel());
		tree.setRootVisible(false);
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				System.out.println("selected " + tree.getLastSelectedPathComponent());
			}
		});
		panelLeft.add(tree);
		
		JPanel panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		
		Canvas canvas = new Canvas();
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panelRight.add(canvas);
	}
	
	public class EditorTreeModel extends DefaultTreeModel {
		public EditorTreeModel() {
			super(new DefaultMutableTreeNode("Dots"));
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();
			
			DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Player");
			folder.add(new EditorItem("Player"));
			root.add(folder);
			
			folder = new DefaultMutableTreeNode("Targets");
			folder.add(new EditorItem("Target"));
			root.add(folder);
			
			folder = new DefaultMutableTreeNode("Enemies");
			DefaultMutableTreeNode subfolder = new DefaultMutableTreeNode("Normal Enemies");
			subfolder.add(new EditorItem("Easy"));
			subfolder.add(new EditorItem("Medium"));
			subfolder.add(new EditorItem("Hard"));
			folder.add(subfolder);
			subfolder = new DefaultMutableTreeNode("StaticEnemy");
			subfolder.add(new EditorItem("Easy"));
			subfolder.add(new EditorItem("Medium"));
			subfolder.add(new EditorItem("Hard"));
			root.add(folder);
			
			folder = new DefaultMutableTreeNode("ActionPoints");
			folder.add(new EditorItem("ChainActionPoint"));
			root.add(folder);
		}
	}
	
	public class EditorTreeCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof EditorItem) {
				//setIcon(((EditorItem) value).getIcon());
			}
			return component;
		}
	}

}
