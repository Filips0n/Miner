package sk.uniza.fri.sudora;


import com.badlogic.gdx.Game;
import sk.uniza.fri.sudora.gui.UvodObrazovka;
/**
 * Nastavi do hracieho okna uvodnu obrazovku.
 * 
 * @author Filip Sudora 
 * @version v1.0.0
 */
public class MinerGame extends Game {

    @Override
    public void create () {
        this.setScreen(new UvodObrazovka(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {        
    }
}
