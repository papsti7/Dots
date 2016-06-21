package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Max on 27/05/2016.
 */
public class ActionPointFactory {
    ActionPointFactory()
    {

    }
    public ActionPoint create(String type, ArrayList<Vector2> positions, int number)
    {
        if (number <= 0 || positions.size() < 0)
        {
            return null;
        }
        if (type.equals("ChainAP"))
        {
            ArrayList<ChainAP> chainAPs = new ArrayList<ChainAP>();
            if (number <= positions.size())
            {

                for (int element = 0; element < number; element++)
                {
                    if (element == 0)
                    {
                        ChainAP first = new ChainAP((int)positions.get(element).x,(int)positions.get(element).y,true,0);
                        first.setFirst(first);
                        chainAPs.add(first);
                        if (element + 1 == number)
                        {
                            first.setNext(first);
                        }
                    }

                    else
                    {
                        ChainAP current = new ChainAP((int)positions.get(element).x,(int)positions.get(element).y,false,0);
                        current.setFirst(chainAPs.get(0));
                        chainAPs.get(element-1).setNext(current);

                        if (element + 1 == number)
                        {
                            current.setNext(current);
                        }

                        chainAPs.add(current);



                    }
                }
                return chainAPs.get(0);
            }
            else if (positions.size() == 0)
            {
                for (int element = 0; element < number; element++)
                {
                    Vector2 new_pos = new Vector2(Utils.random_.nextInt((int)Constants.virtual_screen_width-64), Utils.random_.nextInt((int)Constants.virtual_screen_height-64));
                    new_pos.add(32,32);

                    if (element == 0)
                    {
                        ChainAP first = new ChainAP((int)new_pos.x,(int)new_pos.y,true,0);
                        first.setFirst(first);
                        chainAPs.add(first);
                    }
                    else
                    {
                        ChainAP current = new ChainAP((int)new_pos.x,(int)new_pos.y,false,0);
                        current.setFirst(chainAPs.get(0));
                        chainAPs.get(element - 1).setNext(current);

                        if (element + 1 == number)
                        {
                            current.setNext(current);
                        }

                        chainAPs.add(current);
                    }

                }
                return chainAPs.get(0);
            }
            else
            {
                return null;
            }
        }
        return null;
    }
}
