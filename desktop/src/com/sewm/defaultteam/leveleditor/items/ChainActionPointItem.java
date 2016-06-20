package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorCanvasRenderer;
import com.sewm.defaultteam.leveleditor.LevelEditorFile;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

public class ChainActionPointItem extends LevelEditorItem {
    private List<Vector2> positions_;

    public ChainActionPointItem(LevelEditor editor, Vector2 position, Vector2... positions) {
        super(editor, LevelEditor.CHAIN_ACTION_POINT, position);
        positions_ = new ArrayList<Vector2>(Arrays.asList(positions));
        createPanel();
    }

    private void createPanel() {
        try {
            Box vertical = Box.createVerticalBox();

            final JFormattedTextField position_x = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_x.setText(LevelEditorFile.FLOAT.valueToString(position_.x));
            position_x.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.x = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("X-Position"));
            vertical.add(position_x);

            final JFormattedTextField position_y = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_y.setText(LevelEditorFile.FLOAT.valueToString(position_.y));
            position_y.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.y = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Y-Position"));
            vertical.add(position_y);

            Box dynamic = Box.createVerticalBox();
            for (final Vector2 position : positions_) {
                final JFormattedTextField pos_x = new JFormattedTextField(LevelEditorFile.FLOAT);
                pos_x.setText(LevelEditorFile.FLOAT.valueToString(position.x));
                pos_x.addPropertyChangeListener("value", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if (!e.getNewValue().equals(e.getOldValue())) {
                            position.x = Float.parseFloat(e.getNewValue().toString());
                            editor_.getFile().setDirty(true);
                        }
                    }
                });
                dynamic.add(new JLabel("X-Position"));
                dynamic.add(pos_x);

                final JFormattedTextField pos_y = new JFormattedTextField(LevelEditorFile.FLOAT);
                pos_y.setText(LevelEditorFile.FLOAT.valueToString(position.y));
                pos_y.addPropertyChangeListener("value", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if (!e.getNewValue().equals(e.getOldValue())) {
                            position.y = Float.parseFloat(e.getNewValue().toString());
                            editor_.getFile().setDirty(true);
                        }
                    }
                });
                dynamic.add(new JLabel("Y-Position"));
                dynamic.add(pos_y);
            }
            vertical.add(dynamic);

            final JButton button_remove = new JButton("- Position");
            button_remove.addMouseListener(new RemovePositionListener(dynamic));
            vertical.add(button_remove);

            final JButton button_add = new JButton("+ Position");
            button_add.addMouseListener(new AddPositionListener(dynamic));
            vertical.add(button_add);

            properties_panel_.add(vertical);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class RemovePositionListener implements MouseListener {
        private final Box box;

        public RemovePositionListener(Box box) {
            this.box = box;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (positions_.size() == 0) {
                return;
            }

            int last = positions_.size() - 1;
            positions_.remove(last);

            int index = last * 4;
            box.remove(index + 3);
            box.remove(index + 2);
            box.remove(index + 1);
            box.remove(index);

            box.revalidate();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }
    }

    private class AddPositionListener implements MouseListener {
        private final Box box;

        public AddPositionListener(Box box) {
            this.box = box;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            try {
                final Vector2 position = new Vector2(0, 0);
                positions_.add(position);

                final JFormattedTextField pos_x = new JFormattedTextField(LevelEditorFile.FLOAT);
                pos_x.setText(LevelEditorFile.FLOAT.valueToString(position.x));
                pos_x.addPropertyChangeListener("value", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if (!e.getNewValue().equals(e.getOldValue())) {
                            position.x = Float.parseFloat(e.getNewValue().toString());
                            editor_.getFile().setDirty(true);
                        }
                    }
                });
                box.add(new JLabel("X-Position"));
                box.add(pos_x);

                final JFormattedTextField pos_y = new JFormattedTextField(LevelEditorFile.FLOAT);
                pos_y.setText(LevelEditorFile.FLOAT.valueToString(position.y));
                pos_y.addPropertyChangeListener("value", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if (!e.getNewValue().equals(e.getOldValue())) {
                            position.y = Float.parseFloat(e.getNewValue().toString());
                            editor_.getFile().setDirty(true);
                        }
                    }
                });
                box.add(new JLabel("Y-Position"));
                box.add(pos_y);

                box.revalidate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }
    }

    @Override
    public Element toXML(Document document) {
        Element node = document.createElement("actionPoint");
        node.setAttribute("type", "chain");

        Element position = document.createElement("position");
        position.setAttribute("x", String.valueOf(getX()));
        position.setAttribute("y", String.valueOf(getY()));
        node.appendChild(position);

        for (Vector2 pos : positions_) {
            position = document.createElement("position");
            position.setAttribute("x", String.valueOf(pos.x));
            position.setAttribute("y", String.valueOf(pos.y));
            node.appendChild(position);
        }

        return node;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
        if (this.equals(editor_.getProperties().getSelectedItem())) {
            Texture texture = LevelEditorCanvasRenderer.textures_.get(name);
            for (Vector2 position : positions_) {
                spriteBatch.draw(texture,
                        position.x - texture.getWidth() / 2.f,
                        position.y - texture.getHeight() / 2.f,
                        texture.getWidth(), texture.getHeight());
            }
        }
    }
}
