package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Lukas on 10/05/16.
 */
public class Parser {
    private BufferedReader file_;
    private String filename_;

    public Parser(String filename) throws FileNotFoundException
    {
        filename_ = filename;
        file_ = new BufferedReader(Gdx.files.internal(filename_).reader());
    }

    public void parseTextures() throws IOException
    {
        for (String line = file_.readLine(); line != null; line = file_.readLine())
        {
            if (line.contains(";"))
                return;

            String[] tokens = line.split(",");
            List<String> textures = new ArrayList<String>();

            for(int i = 1; i < tokens.length; i++) {
                String texture_filename = tokens[i];
                WorldRenderer.entities_textures.put(texture_filename, new Texture(Gdx.files.internal(texture_filename)));
                textures.add(texture_filename);
            }
            WorldRenderer.entities_texture_strings.put(tokens[0], textures);

        }

        file_.close();
        file_ = new BufferedReader(Gdx.files.internal(filename_).reader());
    }

    public Player parsePlayer() throws IOException
    {
        Scanner s = new Scanner(file_.readLine()).useDelimiter("[;,\\n]");
        int health = s.nextInt();
        String texture_filename = s.next();
        Player player = new Player(health, texture_filename);
        WorldRenderer.entities_textures.put(texture_filename, new Texture(Gdx.files.internal(texture_filename)));
        file_.readLine();
        return player;
    }

    protected Target parseTarget() throws IOException
    {
        String line = file_.readLine();
        if (line.contains(";"))
        {
            return null;
        }
        Scanner s = new Scanner(line).useDelimiter("[,]");
        return new Target(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
    }

    public List<Target> parseTargets() throws IOException
    {
        List<Target> list = new ArrayList<Target>();
        Target target;
        while ((target = parseTarget()) != null) {
            list.add(target);
        }
        return list;
    }

    protected NormalEnemy parseNormalEnemy() throws IOException
    {
        String line = file_.readLine();
        if (line.contains(";"))
        {
            return null;
        }
        Scanner s = new Scanner(line).useDelimiter("[,]");
        int x = s.nextInt();
        int y = s.nextInt();
        int difficulty = s.nextInt();
        int points = s.nextInt();
        int points_on_death = s.nextInt();
        String texture = s.next();
        int spawn_time = s.nextInt();
        EnemyAttribute enemy_attribute;
        switch(difficulty) {
            case 0:
                enemy_attribute = World.enemy_easy_;
                break;
            case 1:
                enemy_attribute = World.enemy_medium_;
                break;
            case 2:
                enemy_attribute = World.enemy_hard_;
                break;
            default:
                enemy_attribute = World.enemy_easy_;
        }

        return new NormalEnemy(x, y, enemy_attribute, points, points_on_death, spawn_time);
    }

    protected List<NormalEnemy> parseNormalEnemies() throws IOException
    {
        List<NormalEnemy> list = new ArrayList<NormalEnemy>();
        NormalEnemy enemy;
        while ((enemy = parseNormalEnemy()) != null) {
            list.add(enemy);
        }
        return list;
    }

    protected StaticEnemy parseStaticEnemy() throws IOException
    {
        String line = file_.readLine();
        if (line.contains(";"))
        {
            return null;
        }
        Scanner s = new Scanner(line).useDelimiter("[,]");

        int difficulty = s.nextInt();
        int points = s.nextInt();
        int points_on_death = s.nextInt();
        String texture = s.next();

        Vector2 start_point = new Vector2(s.nextInt(), s.nextInt());
        Vector2 end_point = new Vector2(s.nextInt(), s.nextInt());
        int spawn_time = s.nextInt();

        EnemyAttribute enemy_attribute;
        switch(difficulty) {
            case 0:
                enemy_attribute = World.enemy_easy_;
                break;
            case 1:
                enemy_attribute = World.enemy_medium_;
                break;
            case 2:
                enemy_attribute = World.enemy_hard_;
                break;
            default:
                enemy_attribute = World.enemy_easy_;
        }

        return  new StaticEnemy(enemy_attribute, points, points_on_death, start_point, end_point, spawn_time);
    }

    protected List<StaticEnemy> parseStaticEnemies() throws IOException
    {
        List<StaticEnemy> list = new ArrayList<StaticEnemy>();
        StaticEnemy enemy;
        while ((enemy = parseStaticEnemy()) != null) {
            list.add(enemy);
        }
        return list;
    }

    public List<Enemy> parseEnemies() throws IOException
    {
        List<Enemy> list = new ArrayList<Enemy>();
        list.addAll(parseNormalEnemies());
        list.addAll(parseStaticEnemies());
        return list;
    }

    protected ActionPoint parseActionPoint() throws IOException
    {
        String line = file_.readLine();
        if (line.contains(";"))
        {
            return null;
        }
        Scanner s = new Scanner(line).useDelimiter("[,]");

        int x = s.nextInt();
        int y = s.nextInt();
        boolean active = s.nextInt() != 0;
        int number = s.nextInt();
        String textures = s.next();

        return new ChainAP(x, y, active, number, textures);
    }

    public List<ActionPoint> parseActionPoints() throws IOException
    {
        List<ActionPoint> action_points = new ArrayList<ActionPoint>();

        for (String line = file_.readLine(); line != null; line = file_.readLine()) {
            if (line.contains(";")) {
                return action_points;
            }

            int ap_count = Integer.valueOf(line);
            if (ap_count > 0) {
                ActionPoint first = parseActionPoint();
                if (first == null) {
                    throw new IOException("Parser expected at least one ActionPoint!");
                }
                first.setFirst(first);
                first.setNext(first);
                action_points.add(first);

                for (ap_count--; ap_count > 0; ap_count--) {
                    ActionPoint ap = parseActionPoint();
                    if (ap == null) {
                        throw new IOException("Parser expected another ActionPoint!");
                    }
                    ap.setFirst(first);
                    action_points.get(action_points.size() - 1).setNext(ap);
                    if (ap_count == 1) {
                        ap.setNext(ap);
                    }
                    action_points.add(ap);
                }
            }
        }

        return action_points;
    }

}

