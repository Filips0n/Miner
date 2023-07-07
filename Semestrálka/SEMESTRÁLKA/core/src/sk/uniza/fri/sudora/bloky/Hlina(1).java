
package sk.uniza.fri.sudora.bloky;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Trieda reprezentujuca hlinu.
 * @author Filip
 */
public class Hlina implements IBlok {
    
    private boolean maMinu;
    private final boolean jeZberatelny = false;
    private final boolean jeZnicitelny = true;
    private SpriteBatch batch;
    private Texture img;

    /**
     * Vytvori novu hlinu.
     */
    public Hlina() {
        this.batch = new SpriteBatch();
        this.img = new Texture("dirtv3.jpg");
        this.maMinu = false;
    }
    
    @Override
    public boolean getMaMinu() {
        return this.maMinu;
    }

    @Override
    public void setMaMinu(boolean maMinu) {
        this.maMinu = maMinu;
    }
    
    @Override
    public void setViditelnost(boolean viditelny) {
    }
    
    @Override
    public boolean getZberatelnost() {
        return this.jeZberatelny;
    }
    
    /**
     * Vykresli hlinu.
     */
    @Override
    public void vykresli(int x, int y) {
        this.batch.begin();
        this.batch.draw(this.img, x * 40, y * 40, 40 , 40);
        this.batch.end();
    }
    
    @Override
    public String getNazov() {
        return "Hlina";
    }
    
    @Override
    public boolean getZnicitelnost() {
        return this.jeZnicitelny;
    }
    
}
