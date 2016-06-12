package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlWriter;
import com.sewm.defaultteam.ActionPoint;
import com.sewm.defaultteam.Enemy;
import com.sewm.defaultteam.Parser;
import com.sewm.defaultteam.Player;
import com.sewm.defaultteam.Target;
import com.sewm.defaultteam.leveleditor.items.ChainActionPointItem;
import com.sewm.defaultteam.leveleditor.items.NormalEnemyItem;
import com.sewm.defaultteam.leveleditor.items.StaticEnemyItem;
import com.sewm.defaultteam.leveleditor.items.TargetItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LevelEditorFile {
	private String path_ = null;
	private boolean dirty_ = false;
	private List<LevelEditorItem> items_ = new ArrayList<LevelEditorItem>();
	
	public LevelEditorFile() {
		createNewFile();
	}
	
	public void createNewFile() {
		items_.clear();
		path_ = null;
		dirty_ = false;
	}
	
	public void newFile() {
		if (!saveChanges()) {
			return;
		}
		createNewFile();
	}

	public void openFile() {
		if (!saveChanges()) {
			return;
		}
		
		String filename = null;
		JFileChooser chooser = new JFileChooser(Gdx.files.getLocalStoragePath() + "levels");
		chooser.setFileFilter(new FileNameExtensionFilter("Level File", "lvl"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			filename = chooser.getSelectedFile().getPath();
		} else {
			return;
		}
		
		try {
			Parser parser = new Parser(filename);
			parser.parseTextures();

	        Player player = parser.parsePlayer();
	        List<Target> targets = parser.parseTargets();
	        List<Enemy> enemies = parser.parseEnemies();
	        List<ActionPoint> aps = parser.parseActionPoints();
	        
	        createNewFile();
	        //TODO: populate new file
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File '" + filename + "' could not be found!", "File not found!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File '" + filename + "' could not be loaded!", "File not loaded!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveAs() {
		save(null);
	}
	
	public void save() {
		save(path_);
	}
	
	private boolean saveChanges() {
		if (dirty_) {
			int result = JOptionPane.showConfirmDialog(null, "Save changes to current file?", "Save changes?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				save();
			} else if (result == JOptionPane.CANCEL_OPTION) {
				return false;
			}
		}
		return true;
	}
	
	private void save(String path) {
		File file = null;
		if (path_ == null) {
			JFileChooser chooser = new JFileChooser(Gdx.files.getLocalStoragePath() + "levels");
			chooser.setFileFilter(new FileNameExtensionFilter("Level File", "lvl"));
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
			} else {
				return;
			}
		} else {
			file = new File(path);
		}

        try {
            synchronized (this) {
                XmlWriter writer = new XmlWriter(new FileWriter(file));
                writer.element("level");
                writer.element("player").element("health", 3).pop();

                writer.element("targets");
                for (LevelEditorItem item : items_) {
                    if (item instanceof TargetItem) {
                        TargetItem target = (TargetItem) item;
                        writer.element("target");

                        writer.element("position");
                        writer.element("x", target.getX());
                        writer.element("y", target.getY());
                        writer.pop();

                        writer.element("radius", target.getRadius());
                        writer.element("health", target.getHealth());

                        writer.pop();
                    }
                }
                writer.pop();

				writer.element("enemies");
				for (LevelEditorItem item : items_) {
					if (item instanceof NormalEnemyItem) {
						NormalEnemyItem enemy = (NormalEnemyItem) item;
						writer.element("enemy").attribute("type", "normal");

						writer.element("position");
						writer.element("x", enemy.getX());
						writer.element("y", enemy.getY());
						writer.pop();

						writer.element("difficulty", enemy.getDifficulty());
						writer.element("points", enemy.getPoints());
						writer.element("pointsOnDeath", enemy.getPointsOnDeath());
						writer.element("spawnTime", enemy.getSpawnTime());

						writer.pop();
					} else if (item instanceof StaticEnemyItem) {
						StaticEnemyItem enemy = (StaticEnemyItem) item;
						writer.element("enemy").attribute("type", "static");

						writer.element("position");
						writer.element("x", enemy.getStartX());
						writer.element("y", enemy.getStartY());
						writer.pop();

						writer.element("position");
						writer.element("x", enemy.getEndX());
						writer.element("y", enemy.getEndY());
						writer.pop();

						writer.element("difficulty", enemy.getDifficulty());
						writer.element("points", enemy.getPoints());
						writer.element("pointsOnDeath", enemy.getPointsOnDeath());
						writer.element("spawnTime", enemy.getSpawnTime());

						writer.pop();
					}
				}
				writer.pop();

				writer.element("actionPoints");
				for (LevelEditorItem item : items_) {
					if (item instanceof ChainActionPointItem) {
                        ChainActionPointItem ap = (ChainActionPointItem) item;
						writer.element("chainActionPoint").attribute("type", "normal");

						writer.element("position");
						writer.element("x", ap.getX());
						writer.element("y", ap.getY());
						writer.pop();

						writer.pop();
					}
				}
				writer.pop();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public synchronized List<LevelEditorItem> getItems() {
		return new ArrayList<LevelEditorItem>(items_);
	}

    public synchronized void addItem(LevelEditorItem item) {
        items_.add(item);
        dirty_ = true;
    }

	public boolean isDirty() {
		return dirty_;
	}

	public void setDirty(boolean dirty) {
		dirty_ = dirty;
	}
}
