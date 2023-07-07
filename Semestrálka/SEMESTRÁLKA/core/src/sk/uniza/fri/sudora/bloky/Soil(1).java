
package sk.uniza.fri.sudora.bloky;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Trieda reprezentujuca podu.
 * @author Filip
 */
public class Soil implements IBlok {
    
    private boolean maMinu;
    private SpriteBatch batch;
    private Texture img;
    private final boolean jeZberatelny = false;
    
    /**
     * Vytvori novu podu.
     */
    public Soil() {
        this.batch = new SpriteBatch();
        this.img = new Texture("soil.jpg");
        this.maMinu = false;
    }
    
    @Override
    public boolean getZberatelnost() {
        return this.jeZberatelny;
    }
    
    /**
     * Vykresli podu.
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
        return "Soil";
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
