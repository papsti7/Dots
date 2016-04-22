package com.sewm.defaultteam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sewm.defaultteam.StartPoint;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new StartPoint(), config);
	}
}
