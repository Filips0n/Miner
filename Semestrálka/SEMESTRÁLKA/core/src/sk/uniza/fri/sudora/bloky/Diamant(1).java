
package sk.uniza.fri.sudora.bloky;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Trieda reprezentujuca diamantovu rudu.
 * @author Filip
 */
public class Diamant implements IBlok {
    
    private boolean maMinu;
    private boolean jeViditelny;
    private final boolean jeZberatelny = true;
    private final boolean jeZnicitelny = true;
    private SpriteBatch batch;
    private Texture img;
    private Texture imgX;
    
    /**
     * Vytvori novy diamant.
     */
    public Diamant() {
        this.batch = new SpriteBatch();
        this.img = new Texture("diamond.jpg");
        this.imgX = new Texture("dirtv3.jpg");
        this.maMinu = false;
        this.jeViditelny = false;
    }
    
    @Override
    public boolean getZberatelnost() {
        return this.jeZberatelny;
    }
    
    @Override
    public void setViditelnost(boolean viditelny) {
        this.jeViditelny = viditelny;
    }
    
    /**
     * Vykresli diamant.
     */
    @Override
    public void vykresli(int x, int y) {
        this.batch.begin();
        if (this.jeViditelny) {
            this.batch.draw(this.img, x * 40, y * 40, 40 , 40);
        } else {
            this.batch.draw(this.imgX, x * 40, y * 40, 40 , 40);
        }  
        this.batch.end();
    }

    @Override
    public String getNazov() {
        return "Diamant";
    }

    @Override
    public boolean getZnicitelnost() {
        return this.jeZnicitelny;
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
