
package sk.uniza.fri.sudora.bloky;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Trieda reprezentujuca oblohu.
 * @author Filip
 */
public class Obloha implements IBlok {
    
    private boolean maMinu;
    private SpriteBatch batch;
    private Texture img;
    private final boolean jeZberatelny = false;
    
    /**
     * Vytvori novu oblohu.
     */
    public Obloha() {
        this.batch = new SpriteBatch();
        this.img = new Texture("sky.jpg");
        this.maMinu = false;
    }
    
    @Override
    public boolean getZberatelnost() {
        return this.jeZberatelny;
    }
    
    /**
     * Vykresli oblohu.
     */
    @Override
    public void vykresli(int x, int y) {
        this.batch.begin();
        this.batch.draw(this.img, x * 40, y * 40, 40 , 40);
        this.batch.end();
    }

    @Override
    public void setViditelnost(boolean bln) {
    }
    
    @Override
    public String getNazov() {
        return "Obloha";
    }
    
    @Override
    public boolean getZnicitelnost() {
        return false;
    }

    @Override
    public boolean getMaMinu() {
        return this.maMinu;
    }

    @Override
    public void setMaMinu(boolean maMinu) {
        this.maMinu = maMinu;
    }
}
