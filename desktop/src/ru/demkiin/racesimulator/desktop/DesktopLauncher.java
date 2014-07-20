package ru.demkiin.racesimulator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.demkiin.racesimulator.RaceSimulator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.useGL30 = false;
		new LwjglApplication(new RaceSimulator(), config);
	}
}
