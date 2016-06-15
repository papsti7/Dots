package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlWriter;
import com.sewm.defaultteam.ActionPoint;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.Enemy;
import com.sewm.defaultteam.Parser;
import com.sewm.defaultteam.Player;
import com.sewm.defaultteam.Target;
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
        items_.add(new PlayerItem(editor_, "Player", new Vector2(Constants.virtual_screen_width/2f, Constants.virtual_screen_height/2f), 3));
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
		} catch (Exception e) {
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
