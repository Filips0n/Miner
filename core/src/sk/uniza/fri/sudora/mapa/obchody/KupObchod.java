
package sk.uniza.fri.sudora.mapa.obchody;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;
import sk.uniza.fri.sudora.mapa.Map;

/**
 * Ponuka minerovi vylepsenia, ktore si moze kupit za peniaze.
 */
public class KupObchod extends Obchod {
    
    private HashMap<String, Integer> cennik;
    
    private SpriteBatch batch;
    private Texture img;
    
    private int pocetUpgradeBatoh;
    
    /**
     * Vytvori novy obchod pre kupu vylepseni.
     */
    public KupObchod(int posX, int posY, Map hernaMapa) {
        super(posX, posY, hernaMapa);
        this.img = new Texture("shop.png");
        this.batch = new SpriteBatch();        
        this.cennik = new HashMap<String, Integer>();
        this.cennik.put("Batoh", 400);
        this.pocetUpgradeBatoh = 1;
    }
    
    /**
     * Aktualizuje cennik.
     */
    public void aktualizujCennik() {
        this.cennik.put("Batoh", this.cennik.get("Batoh") + this.pocetUpgradeBatoh * 100);
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
    
    public void zvysUpgradeBatoha(int pocet) {
        this.pocetUpgradeBatoh += pocet;
    }
    
}
