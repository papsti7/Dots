package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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

    public Enemy parseEnemy(World world) throws IOException
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

        return  new Enemy(x, y, enemy_attribute, points, points_on_death, WorldRenderer.entities_texture_strings.get(texture));
    }
/*  TODO: uncomment when actionpoints are implemented
    public ActionPoint parseActionpoint()
    {
        Actionpoint actionpoint = new Actionpoint();

        return actionpoint;
    }
*/
}

