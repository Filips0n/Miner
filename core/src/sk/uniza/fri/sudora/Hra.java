
package sk.uniza.fri.sudora;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Collections;
import java.util.HashMap;
import sk.uniza.fri.sudora.bloky.Kamen;
import sk.uniza.fri.sudora.mapa.Map;
import sk.uniza.fri.sudora.mapa.obchody.KupObchod;
import sk.uniza.fri.sudora.mapa.obchody.PredajObchod;

/**
 * Zabezpecuje interakciu minera s obchodmi a mapou.
 * 
 * @author Filip
 */
public class Hra {
    
    private PredajObchod predajObchod;
    private KupObchod kupObchod;
    private Miner miner;
    private SpriteBatch batch;
    private Map hernaMapa;
    
    private float obchodTimer;    
    
    /**
     * Vytvoti instanciu triedy Hra pre interakciu minera.
     */
    public Hra(Miner miner, Map hernaMapa, PredajObchod predajObchod, KupObchod kupObchod) {
        this.miner = miner;
        this.hernaMapa = hernaMapa;
        this.predajObchod = predajObchod;
        this.kupObchod = kupObchod;
        this.obchodTimer = 0;
        this.batch = new SpriteBatch();
    }
    
    /**
     * Skontroluje ci sa pozicia minera zhodu s poziciou obchodu.
     */
    public void skontrojulObchody(float delta) {
        //zvysuje sa hodnota obchodTimer, nech je delay medzi nakupmi
        this.obchodTimer += delta;
        if (this.miner.getAktPosX() == this.predajObchod.getPosX() + 40 && this.miner.getAktPosY() == this.predajObchod.getPosY()) {
            HashMap<String, Integer> cennik = this.predajObchod.getCennik();
            HashMap<String, Integer> inventar = this.miner.getInventar();
            int hodnotaItememov = 0;
            for (String nazov : inventar.keySet()) {
                for (String nazov1 : cennik.keySet()) {
                    if (nazov.equals(nazov1)) {
                        this.miner.setAktualnaKapacitaBatohu(0);
                        hodnotaItememov += inventar.get(nazov) * cennik.get(nazov1);                 
                    }
                }
            }
            inventar.clear();
            this.miner.setHodnotuPredanychItemov(hodnotaItememov);
        } else if (this.miner.getAktPosX() == this.kupObchod.getPosX() + 40 && this.miner.getAktPosY() == this.kupObchod.getPosY()) {
            if (this.obchodTimer > 1) {
                HashMap<String, Integer> cennik = this.kupObchod.getCennik();
                int minHodnota = Collections.min(cennik.values());
                if (this.miner.getZostatokNaUcte() >= minHodnota) {
                    this.miner.setZostatokNaUcte(this.miner.getZostatokNaUcte() - cennik.get("Batoh"));
                    this.miner.zvysKapacitaBatohu(10);
                    this.kupObchod.zvysUpgradeBatoha(1);
                    this.kupObchod.aktualizujCennik();
                }
                this.obchodTimer = 0;
            }
            
        }
    }    
    
    /**
     * Ubera zivot minerovi ak narazi do kamena, stupi na minu, pridava itemi do minerovho inventara.
     */
    public void skontrolujHru() {
        for (int i = 0; i < this.hernaMapa.getWidth(); i++) {
            for (int j = 0; j < this.hernaMapa.getHeight(); j++) {
                if (this.hernaMapa.getIBlok(i, j) != null) {
                    if (this.hernaMapa.getIBlok(i, j) instanceof Kamen && this.miner.getPosX() == i && this.miner.getPosY() == j) {
                        this.miner.setHealth(-0.1f);
                    }                    
                }                
            }
        }
        
        for (int i = 0; i < this.hernaMapa.getWidth(); i++) {
            for (int j = 0; j < this.hernaMapa.getHeight(); j++) {
                if (this.hernaMapa.getIBlok(i, j) != null) {
                    if (this.hernaMapa.getIBlok(i, j).getMaMinu() && this.miner.getPosX() == i && this.miner.getPosY() == j) {
                        this.miner.setHealth(-0.3f);
                        this.hernaMapa.odpalMinu(i, j);
                    }                    
                }                
            }
        }
        
        for (int i = 0; i < this.hernaMapa.getWidth(); i++) {
            for (int j = 0; j < this.hernaMapa.getHeight(); j++) {
                if (this.miner.getPosX() == i && this.miner.getPosY() == j) {
                    if (this.hernaMapa.getIBlok(i, j) != null) {
                        if (!this.hernaMapa.getIBlok(i, j).getZnicitelnost()) {
                            switch (this.miner.getSmer()) {
                                case HORE:
                                    this.miner.setAktPosY(this.miner.getAktPosY() - this.hernaMapa.getSirkaTile());
                                    this.miner.setPosY(this.miner.getPosY() - 1);
                                    break;
                                case DOLE:
                                    this.miner.setAktPosY(this.miner.getAktPosY() + this.hernaMapa.getSirkaTile());
                                    this.miner.setPosY(this.miner.getPosY() + 1);
                                    break;
                                case VLAVO:
                                    if (this.miner.getPosX() == (this.hernaMapa.getWidth() - 1)) {
                                        this.miner.setPosX(0);
                                        this.miner.setAktPosX(0);
                                    } else {
                                        this.miner.setAktPosX(this.miner.getAktPosX() + this.hernaMapa.getSirkaTile());
                                        this.miner.setPosX(this.miner.getPosX() + 1);
                                    }
                                    break;
                                case VPRAVO:
                                    if (this.miner.getPosX() == 0) {
                                        this.miner.setPosX(this.hernaMapa.getWidth() - 1);
                                        this.miner.setAktPosX((this.hernaMapa.getWidth() - 1) * this.hernaMapa.getSirkaTile());
                                    } else {
                                        this.miner.setAktPosX(this.miner.getAktPosX() - this.hernaMapa.getSirkaTile());
                                        this.miner.setPosX(this.miner.getPosX() - 1);
                                    }                                    
                                    break;
                            }
                        }
                    }
                }
            }
        }      

        for (int i = 0; i < this.hernaMapa.getWidth(); i++) {
            for (int j = 0; j < this.hernaMapa.getHeight(); j++) {
                if (this.miner.getPosX() == i && this.miner.getPosY() == j) {
                    if (this.hernaMapa.getIBlok(i, j) != null) {
                        if (this.hernaMapa.getIBlok(i, j).getZberatelnost()) {
                            this.miner.putItem(this.hernaMapa.getIBlok(i, j).getNazov());
                        }
                    }
                    this.hernaMapa.setIBlokNaNull(i, j);
                }
            }
        }
    }    
    
    /**
     * Skontroluje ci sa ma restartovat hra.
     */
    public boolean koniecHry() {
        return this.miner.getHealth() < 0;        
    }
}
