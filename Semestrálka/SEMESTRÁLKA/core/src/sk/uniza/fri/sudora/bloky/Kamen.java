
package sk.uniza.fri.sudora.bloky;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Trieda reprezentujuca kamen.
 * @author Filip
 */
public class Kamen implements IBlok {
    
    private boolean maMinu;
    private final boolean jeZberatelny = false;
    private final boolean jeZnicitelny = false;
    private SpriteBatch batch;
    private Texture img;

    /**
     * Vytvori novy kamen.
     */
    public Kamen() {
        this.batch = new SpriteBatch();
        this.img = new Texture("stonev2.jpg");
        this.maMinu = false;
    }
    
    @Override
    public boolean getZberatelnost() {
        return this.jeZberatelny;
    }
    
    @Override
    public void setViditelnost(boolean viditelny) {
    }
    
    /**
     * Vykresli kamen.
     */
    @Override
    public void vykresli(int x, int y) {
        this.batch.begin();
        this.batch.draw(this.img, x * 40, y * 40, 40 , 40); 
        this.batch.end();
    }

    @Override
    public String getNazov() {
        return "Kamen";
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
