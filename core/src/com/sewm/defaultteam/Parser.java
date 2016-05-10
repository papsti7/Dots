package com.sewm.defaultteam;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Lukas on 10/05/16.
 */
public class Parser {
    private BufferedReader file_;

    public Parser(String filename) throws FileNotFoundException
    {
        file_ = new BufferedReader(new FileReader(filename));
    }

    public Player parsePlayer()
    {
        Scanner s = new Scanner(file_).useDelimiter("[;,\\n]");
        Player player = new Player(s.nextInt(),s.nextInt(),s.nextInt(), s.next());
        return player;
    }


    public Target parseTarget()
    {
        Target target = new Target();

        return target;
    }

    public Enemy parseEnemy()
    {
        Enemy enemy = new Enemy();

        return enemy;
    }
/*  TODO: uncomment when actionpoints are implemented
    public ActionPoint parseActionpoint()
    {
        Actionpoint actionpoint = new Actionpoint();

        return actionpoint;
    }
*/
}

