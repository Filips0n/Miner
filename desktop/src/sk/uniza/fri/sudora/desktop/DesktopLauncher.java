package sk.uniza.fri.sudora.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sk.uniza.fri.sudora.MinerGame;

/**
 * Main.
 * 
 * @author Filip Sudora 
 * @version v1.0.0
 */
public class DesktopLauncher {
    /**
     * Nastaví hracie okno na predom zadané hodnoty.
     */
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Miner Game";
        config.width = 1920 / 2 + 40;
        config.height = 1080 / 2 + 20;
        config.resizable = false;
        config.fullscreen = false;
        new LwjglApplication(new MinerGame(), config);
    }
}
