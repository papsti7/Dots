package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlWriter;
import com.sewm.defaultteam.ActionPoint;
import com.sewm.defaultteam.ChainAP;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.Enemy;
import com.sewm.defaultteam.NormalEnemy;
import com.sewm.defaultteam.Parser;
import com.sewm.defaultteam.Player;
import com.sewm.defaultteam.StaticEnemy;
import com.sewm.defaultteam.Target;
import com.sewm.defaultteam.WorldRenderer;
import com.sewm.defaultteam.leveleditor.items.ChainActionPointItem;
import com.sewm.defaultteam.leveleditor.items.NormalEnemyItem;
import com.sewm.defaultteam.leveleditor.items.PlayerItem;
import com.sewm.defaultteam.leveleditor.items.StaticEnemyItem;
import com.sewm.defaultteam.leveleditor.items.TargetItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelEditorFile {
	private LevelEditor editor_;
	private String path_ = null;
	private boolean dirty_ = false;
	private List<LevelEditorItem> items_ = new ArrayList<LevelEditorItem>();

    public static final NumberFormatter FLOAT = new NumberFormatter(NumberFormat.getNumberInstance(Locale.ENGLISH));
    public static final NumberFormatter INT = new NumberFormatter(NumberFormat.getIntegerInstance(Locale.ENGLISH));
	
	public LevelEditorFile(LevelEditor editor) {
		editor_ = editor;
		createNewFile();
	}
	
	public void createNewFile() {
		items_.clear();
        items_.add(new PlayerItem(editor_, 3));
		path_ = null;
		setDirty(false);
        editor_.getPropertiesPanel().getViewport().removeAll();
        editor_.getFrame().revalidate();
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
		
		String filename;
		JFileChooser chooser = new JFileChooser(Gdx.files.getLocalStoragePath() + "levels");
		chooser.setFileFilter(new FileNameExtensionFilter("Level File", "lvl"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			filename = chooser.getSelectedFile().getPath();
		} else {
			return;
		}
		
		try {
			Parser parser = new Parser(filename);

            WorldRenderer.entities_texture_strings = mock(HashMap.class);
            WorldRenderer.entities_textures = mock(HashMap.class);
            when(WorldRenderer.entities_texture_strings.get(any())).thenReturn(mock(ArrayList.class));
            when(WorldRenderer.entities_textures.get(any())).thenReturn(mock(Texture.class));

	        Player player = parser.parsePlayer();
	        List<Target> targets = parser.parseTargets();
	        List<Enemy> enemies = parser.parseEnemies();
	        List<ActionPoint> aps = parser.parseActionPoints();
	        
	        createNewFile();
            addItem(new PlayerItem(editor_, (int) player.getHealth()));
            for (Target target : targets) {
                Circle body = (Circle) target.getBody();
                addItem(new TargetItem(editor_, new Vector2(body.x, body.y), Constants.target_radius, (int) target.getHealth()));
            }
            for (Enemy enemy : enemies) {
                if (enemy instanceof NormalEnemy) {
                    NormalEnemy normal_enemy = (NormalEnemy) enemy;
                    Rectangle body = (Rectangle) normal_enemy.getBody();
                    addItem(new NormalEnemyItem(editor_, new Vector2(body.x, body.y), enemy.getDifficulty(), enemy.getPoints(), enemy.getPointsOnDeath(), enemy.getSpawnTime()));
                } else if (enemy instanceof StaticEnemy) {
                    StaticEnemy static_enemy = (StaticEnemy) enemy;
                    addItem(new StaticEnemyItem(editor_, static_enemy.getStartPos(), static_enemy.getEndPos(), enemy.getDifficulty(), enemy.getPoints(), enemy.getPointsOnDeath(), enemy.getSpawnTime()));
                }
            }
            for (ActionPoint action_point : aps) {
                if (action_point instanceof ChainAP) {
                    ChainAP ap = (ChainAP) action_point;
                    Rectangle body = (Rectangle) ap.getBody();
                    List<Vector2> positions = new ArrayList<Vector2>();

                    ActionPoint current = action_point;
                    ActionPoint next = current.getNext();
                    while (!current.equals(next)) {
                        body = (Rectangle) next.getBody();
                        positions.add(new Vector2(body.x, body.y));
                        current = next;
                        next = current.getNext();
                    }

                    addItem(new ChainActionPointItem(editor_, new Vector2(body.x, body.y), positions.toArray(new Vector2[positions.size()])));
                }
            }

            path_ = filename;
            setDirty(false);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
			JOptionPane.showMessageDialog(null, "File '" + filename + "' could not be found!", "File not found!", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
            e.printStackTrace();
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
		File file;
		if (path == null) {
			JFileChooser chooser = new JFileChooser(Gdx.files.getLocalStoragePath() + "levels");
			chooser.setFileFilter(new FileNameExtensionFilter("Level File", "lvl"));
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
                path = file.getPath();
                if (!path.endsWith(".lvl")) {
                    path += ".lvl";
                }
                if (new File(path).isFile()) {
                    if (JOptionPane.showConfirmDialog(chooser, "File already exists. Do you wish to overwrite it?", "Overwrite?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
			} else {
				return;
			}
		} else {
			file = new File(path);
		}

        try {
            synchronized (this) {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Element root = document.createElement("level");
                document.appendChild(root);

                for (LevelEditorItem item : items_) {
                    Element node = item.toXML(document);
                    if (node != null) {
                        root.appendChild(node);
                    }
                }

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(file);
                Source input = new DOMSource(document);

                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                transformer.transform(input, output);
                path_ = path;
                setDirty(false);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<LevelEditorItem> getItems() {
		return new ArrayList<LevelEditorItem>(items_);
	}

	public synchronized void removeItem(LevelEditorItem item) {
		if (item instanceof PlayerItem) {
            return; // not allowed to delete the player
        }
		items_.remove(item);
		setDirty(true);
	}

    public synchronized void addItem(LevelEditorItem item) {
        items_.add(item);
        setDirty(true);
    }

	public boolean isDirty() {
		return dirty_;
	}

	public void setDirty(boolean dirty) {
		dirty_ = dirty;
        String title = (dirty ? "*" : "") + (path_ == null ? "Unnamed Level" : new File(path_).getName());
        editor_.getFrame().setTitle(title);
	}
}
