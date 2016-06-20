package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by Lukas on 10/05/16.
 */
public class Parser {
    private Document file_;
    private String filename_;

    public Parser(String filename) throws IOException, ParserConfigurationException, SAXException {
        filename_ = filename;
        file_ = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Gdx.files.internal(filename_).file());
    }

    public Player parsePlayer() throws IOException
    {
        NodeList elements = file_.getDocumentElement().getElementsByTagName("player");
        if (elements.getLength() != 1) {
            throw new IOException("Exactly one player tag expected!");
        }
        Node element = elements.item(0);

        int health = Integer.parseInt(element.getAttributes().getNamedItem("health").getNodeValue());

        return new Player(health);
    }

    protected Target parseTarget(Node element) throws IOException
    {
        NamedNodeMap attributes = element.getAttributes();

        float x = Float.parseFloat(attributes.getNamedItem("x").getNodeValue());
        float y = Float.parseFloat(attributes.getNamedItem("y").getNodeValue());
        float radius = Float.parseFloat(attributes.getNamedItem("radius").getNodeValue());
        int health = Integer.parseInt(attributes.getNamedItem("health").getNodeValue());

        return new Target(x, y, radius, health);
    }

    public List<Target> parseTargets() throws IOException
    {
        List<Target> list = new ArrayList<Target>();

        NodeList elements = file_.getDocumentElement().getElementsByTagName("target");
        for (int i = 0; i < elements.getLength(); i++) {
            list.add(parseTarget(elements.item(i)));
        }

        return list;
    }

    protected NormalEnemy parseNormalEnemy(Node element) throws IOException
    {
        NamedNodeMap attributes = element.getAttributes();

        float x = Float.parseFloat(attributes.getNamedItem("x").getNodeValue());
        float y = Float.parseFloat(attributes.getNamedItem("y").getNodeValue());
        int difficulty = Integer.parseInt(attributes.getNamedItem("difficulty").getNodeValue());
        int points = Integer.parseInt(attributes.getNamedItem("points").getNodeValue());
        int points_on_death = Integer.parseInt(attributes.getNamedItem("pointsOnDeath").getNodeValue());
        int spawn_time = Integer.parseInt(attributes.getNamedItem("spawnTime").getNodeValue());

        return new NormalEnemy(x, y, EnemyAttribute.fromInt(difficulty), points, points_on_death, spawn_time);
    }

    protected StaticEnemy parseStaticEnemy(Node element) throws IOException
    {
        NamedNodeMap attributes = element.getAttributes();
        NodeList children = element.getChildNodes();

        int difficulty = Integer.parseInt(attributes.getNamedItem("difficulty").getNodeValue());
        int points = Integer.parseInt(attributes.getNamedItem("points").getNodeValue());
        int points_on_death = Integer.parseInt(attributes.getNamedItem("pointsOnDeath").getNodeValue());
        int spawn_time = Integer.parseInt(attributes.getNamedItem("spawnTime").getNodeValue());

        Vector2 start_point = null;
        Vector2 end_point = null;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equalsIgnoreCase("start")) {
                start_point = parsePosition(child);
            } else if (child.getNodeName().equalsIgnoreCase("end")) {
                end_point = parsePosition(child);
            }
        }

        return new StaticEnemy(EnemyAttribute.fromInt(difficulty), points, points_on_death, start_point, end_point, spawn_time);
    }

    protected Vector2 parsePosition(Node element) {
        NamedNodeMap attributes = element.getAttributes();

        float x = Float.parseFloat(attributes.getNamedItem("x").getNodeValue());
        float y = Float.parseFloat(attributes.getNamedItem("y").getNodeValue());

        return new Vector2(x, y);
    }

    public List<Enemy> parseEnemies() throws IOException
    {
        List<Enemy> list = new ArrayList<Enemy>();

        NodeList elements = file_.getDocumentElement().getElementsByTagName("enemy");
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if (element.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("normal")) {
                list.add(parseNormalEnemy(element));
            } else if (element.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("static")) {
                list.add(parseStaticEnemy(element));
            }
        }

        return list;
    }

    protected ActionPoint parseChainActionPoint(Node element) throws IOException
    {
        ActionPointFactory factory = new ActionPointFactory();
        ArrayList<Vector2> positions = new ArrayList<Vector2>();

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                positions.add(parsePosition(child));
            }
        }

        return factory.create("ChainAP", positions, positions.size());
    }

    public List<ActionPoint> parseActionPoints() throws IOException
    {
        List<ActionPoint> list = new ArrayList<ActionPoint>();

        NodeList elements = file_.getDocumentElement().getElementsByTagName("actionPoint");
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if (element.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("chain")) {
                list.add(parseChainActionPoint(element));
            }
        }

        return list;
    }

}

