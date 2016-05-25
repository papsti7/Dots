package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
            ArrayList<String> textures = new ArrayList<String>();

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

    public Target parseTarget() throws IOException
    {
        String line = file_.readLine();
        if (line.contains(";"))
        {
            return null;
        }
        Scanner s = new Scanner(line).useDelimiter("[,]");
        return new Target(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), WorldRenderer.entities_texture_strings.get(s.next()));
    }

    public NormalEnemy parseNormalEnemy(World world) throws IOException
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
                enemy_attribute = world.enemy_easy_;
                break;
            case 1:
                enemy_attribute = world.enemy_medium_;
                break;
            case 2:
                enemy_attribute = world.enemy_hard_;
                break;
            default:
                enemy_attribute = world.enemy_easy_;
        }

        return  new NormalEnemy(x, y, enemy_attribute, points, points_on_death, WorldRenderer.entities_texture_strings.get(texture), spawn_time);
    }

    public StaticEnemy parseStaticEnemy(World world) throws IOException
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
                enemy_attribute = world.enemy_easy_;
                break;
            case 1:
                enemy_attribute = world.enemy_medium_;
                break;
            case 2:
                enemy_attribute = world.enemy_hard_;
                break;
            default:
                enemy_attribute = world.enemy_easy_;
        }

        return  new StaticEnemy(x, y, enemy_attribute, points, points_on_death, WorldRenderer.entities_texture_strings.get(texture), start_point, end_point, spawn_time);
    }

    public ArrayList<ActionPoint> parseActionpoints() throws IOException
    {
        HashMap<String, ChainAP> actionpoints = new HashMap<String, ChainAP>();
        ArrayList<ActionPoint> actionpoints_list = new ArrayList<com.sewm.defaultteam.ActionPoint>();

        for (String line = file_.readLine(); line != null; line = file_.readLine()) {
            if (line.contains(";"))
                break;

            Scanner s = new Scanner(line).useDelimiter("[,]");

            String ap_name = s.next();
            String first_ap = s.next();
            String next_ap = s.next();
            int x = s.nextInt();
            int y = s.nextInt();
            boolean active = s.nextInt() != 0;
            int number = s.nextInt();
            String textures = s.next();
            ChainAP actionpoint = new ChainAP(x, y, active, number, WorldRenderer.entities_texture_strings.get(textures), first_ap, next_ap);
            actionpoints.put(ap_name, actionpoint);
            actionpoints_list.add(actionpoint);
        }

        for (ChainAP chainAP : actionpoints.values()) {
            chainAP.setFirst_(actionpoints.get(chainAP.first_ap_));
            chainAP.setNext(actionpoints.get(chainAP.next_ap_));
        }

        return actionpoints_list;
    }

}

