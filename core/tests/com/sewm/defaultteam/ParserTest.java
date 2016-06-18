package com.sewm.defaultteam;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParserTest extends GdxTest {
    @Test
    public void testReadValidLevelFile() {
        try {
            Parser parser = new Parser("../android/assets/tests/valid.lvl");
            parser.parseTextures();
            Player player = parser.parsePlayer();
            List<Target> targets = parser.parseTargets();
            List<Enemy> enemies = parser.parseEnemies();
            List<ActionPoint> action_points = parser.parseActionPoints();

            assertEquals("Expected to load 2 targets", 2, targets.size());
            assertEquals("Expected to load 4 enemies", 4, enemies.size());
            assertEquals("Expected to load 3 chain action points", 3, action_points.size());

            assertEquals("Expected player to have 7 lifes", 7, (int) player.getHealth());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed parsing a valid level file!");
        }
    }

    @Test
    public void testParsedValues() {
        try {
            Parser parser = new Parser("../android/assets/tests/valid.lvl");
            parser.parseTextures();
            Player player = parser.parsePlayer();
            List<Target> targets = parser.parseTargets();
            List<Enemy> enemies = parser.parseEnemies();
            List<ActionPoint> action_points = parser.parseActionPoints();

            assertEquals(7, (int) player.getHealth());

            Target t1 = targets.get(0);
            assertEquals(3, (int) t1.getHealth());

            Target t2 = targets.get(1);
            assertEquals(5, (int) t2.getHealth());

            NormalEnemy ne1 = (NormalEnemy) enemies.get(0);
            // assertEquals(1, ne1.getSpawnTime());
            // assertEquals(1, ne1.getPoints());
            // assertEquals(1, ne1.getPointsOnDeath());

            NormalEnemy ne2 = (NormalEnemy) enemies.get(1);
            // assertEquals(4, ne2.getSpawnTime());
            // assertEquals(2, ne2.getPoints());
            // assertEquals(2, ne2.getPointsOnDeath());

            NormalEnemy ne3 = (NormalEnemy) enemies.get(2);
            // assertEquals(7, ne3.getSpawnTime());
            // assertEquals(3, ne3.getPoints());
            // assertEquals(3, ne3.getPointsOnDeath());

            StaticEnemy se1 = (StaticEnemy) enemies.get(3);
            // assertEquals(new Vector2(800, 300), se1.getStartPos());
            // assertEquals(new Vector2(600, 300), se1.getEndPos());
            // assertEquals(7, se1.getSpawnTime());
            // assertEquals(3, se1.getPoints());
            // assertEquals(3, se1.getPointsOnDeath());

            ActionPoint ap1 = action_points.get(0);
            // assertEquals(1, ap1.getNumber());
            // assertEquals(true, ap1.isActive());

            ActionPoint ap2 = action_points.get(1);
            // assertEquals(2, ap2.getNumber());
            // assertEquals(false, ap2.isActive());

            ActionPoint ap3 = action_points.get(2);
            // assertEquals(3, ap3.getNumber());
            // assertEquals(false, ap3.isActive());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed parsing a valid level file!");
        }
    }

    @Test
    public void testReadInvalidLevelFile() {
        try {
            new Parser("../android/assets/tests/invalid.lvl");
            fail("Shouldn't reach that point!");
        } catch (Exception e) {
            assertTrue("Expect to catch the IOException during parsing!", true);
        }
    }
}
