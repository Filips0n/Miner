
package sk.uniza.fri.sudora.mapa.obchody;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;
import sk.uniza.fri.sudora.mapa.Map;

/**
 * Ponuka minerovi peniaze, ktore ziska za predaj itemov.
 */
public class PredajObchod extends Obchod {
    
    private SpriteBatch batch;
    private Texture img;
    private HashMap<String, Integer> cennik; 
    
    /**
     * Vytvori novy obchod pre predaj itemov.
     */
    public PredajObchod(int posX, int posY, Map hernaMapa) {
        super(posX, posY, hernaMapa);
        this.img = new Texture("shop.png");
        this.batch = new SpriteBatch();
        
        this.cennik = new HashMap<String, Integer>();
        this.cennik.put("Diamant", 40);
        this.cennik.put("Zlato", 20);
        this.cennik.put("Uhlie", 10);
    }

    /**
     * Vykresli obchod.
     */
    @Override
    public void vykresli() {
        this.batch.begin();
        this.batch.draw(this.img, super.getPosX(), super.getPosY(), 120, 80);
        this.batch.end();
    }

    public HashMap<String, Integer> getCennik() {
        return this.cennik;
    }
}
