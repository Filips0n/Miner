
package sk.uniza.fri.sudora.mapa;

import sk.uniza.fri.sudora.bloky.Hlina;
import sk.uniza.fri.sudora.bloky.IBlok;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import sk.uniza.fri.sudora.Miner;
import sk.uniza.fri.sudora.bloky.Diamant;
import sk.uniza.fri.sudora.bloky.Kamen;
import sk.uniza.fri.sudora.bloky.Obloha;
import sk.uniza.fri.sudora.bloky.Soil;
import sk.uniza.fri.sudora.bloky.Trava;
import sk.uniza.fri.sudora.bloky.Uhlie;
import sk.uniza.fri.sudora.bloky.Zlato;

/**
 * Generuje mapu, pozadie a aktualizuje bloky v nej.
 * @author Filip
 */
public class Map {    
    
    private IBlok[][] hernaMapa = new IBlok[Gdx.graphics.getWidth() / this.sirkaTile][Gdx.graphics.getHeight() / this.sirkaTile - 2];
    private IBlok[][] pozadie = new IBlok[Gdx.graphics.getWidth() / this.sirkaTile][Gdx.graphics.getHeight() / this.sirkaTile];    
    private SpriteBatch batch;
    private Miner miner;
    
    private final int sirkaTile = 40;
    
    /**
     * Vytvori novu mapu a pozadie.
     */    
    public Map(Miner miner) {
        this.miner = miner;
        for (int i = 0; i < this.pozadie.length; i++) {
            for (int j = 0; j < this.pozadie[i].length; j++) {
                if (j < this.pozadie[i].length - 2) {
                    this.pozadie[i][j] = new Soil();
                } else {
                    this.pozadie[i][j] = new Obloha();
                }
            }
        }
        System.out.println("Rozmer mapy " + this.hernaMapa.length + " x " + this.hernaMapa[0].length);
        this.vygenerujMapu();
    }
    
    private void vygenerujMapu() {
        for (int i = 0; i < this.hernaMapa.length; i++) {
            for (int j = 0; j < this.hernaMapa[i].length; j++) {
                Random rand = new Random();
                int nahoda = rand.nextInt(100);
                if (j == this.hernaMapa[i].length - 1) {
                    this.hernaMapa[i][j] = new Trava();
                } else {
                    if (j < this.hernaMapa[j].length / 3) {
                        if (nahoda < 15) {
                            this.hernaMapa[i][j] = new Diamant();
                        } else if (nahoda >= 15 && nahoda < 30) {
                            this.hernaMapa[i][j] = new Zlato();
                        } else if (nahoda >= 30 && nahoda < 50) {
                            this.hernaMapa[i][j] = new Uhlie();
                        } else if (nahoda >= 50 && nahoda < 75) {
                            this.hernaMapa[i][j] = new Kamen();
                        } else {
                            this.hernaMapa[i][j] = new Hlina(); 
                        }
                    } else {
                        if (nahoda < 2) {
                            this.hernaMapa[i][j] = new Diamant();
                        } else if (nahoda >= 2 && nahoda < 10) {
                            this.hernaMapa[i][j] = new Zlato();
                        } else if (nahoda >= 10 && nahoda < 30) {
                            this.hernaMapa[i][j] = new Uhlie();
                        } else if (nahoda >= 30 && nahoda < 35) {
                            this.hernaMapa[i][j] = new Kamen();
                        } else {
                            this.hernaMapa[i][j] = new Hlina();
                        }
                    }
                }
            }
        }
        
        int pocetMinVMape = 0;
        while (pocetMinVMape < 2) {
            Random rand = new Random();
            int posX = rand.nextInt(this.hernaMapa.length - 2) + 1;
            int posY = rand.nextInt(this.hernaMapa[0].length - 2) + 1;
            if (this.hernaMapa[posX][posY] instanceof Hlina) {
                this.hernaMapa[posX][posY].setMaMinu(true);
                pocetMinVMape++;
                System.out.println("Poziacia miny na mape " + posX + " / " + posY);
            }
        }
    }

    /**
     * Nastavuje viditelnost blokom, pri ktorych sa aktualne miner nachadza.
     * Zistuje ci uz miner vykopal vsetky hodnotne bloky.
     */  
    private void spravujMapu() {
        for (int i = 0; i < this.hernaMapa.length; i++) {
            for (int j = 0; j < this.hernaMapa[i].length; j++) {
                if (this.hernaMapa[i][j] != null) {
                    this.hernaMapa[i][j].setViditelnost(false);
                }
            }
        }

        for (int i = 0; i < this.hernaMapa.length; i++) {
            for (int j = 0; j < this.hernaMapa[i].length; j++) {
                if (this.miner.getPosX() == i && this.miner.getPosY() == j)     {
                    if (i < (this.hernaMapa.length - 1)) {
                        if (this.hernaMapa[i + 1][j] != null) {
                            this.hernaMapa[i + 1][j].setViditelnost(true);
                        }
                    }

                    if (j < (this.hernaMapa[i].length - 2)) {
                        if (this.hernaMapa[i][j + 1] != null) {
                            this.hernaMapa[i][j + 1].setViditelnost(true);
                        }
                    }

                    if (i > (0)) {
                        if (this.hernaMapa[i - 1][j] != null) {
                            this.hernaMapa[i - 1][j].setViditelnost(true);
                        }
                    }

                    if (j > (0)) {
                        if (this.hernaMapa[i][j - 1] != null) {
                            this.hernaMapa[i][j - 1].setViditelnost(true);
                        }
                    }
                }
            }
        }
        
        int pocetPrazdnychPoli = 0;
        for (int i = 0; i < this.hernaMapa.length; i++) {
            for (int j = 0; j < this.hernaMapa[i].length; j++) {
                if (this.hernaMapa[i][j] == null) {
                    pocetPrazdnychPoli++;
                } else {
                    if (!this.hernaMapa[i][j].getZberatelnost()) {
                        pocetPrazdnychPoli++;                        
                    }                
                }
                if (pocetPrazdnychPoli == (this.hernaMapa.length * this.hernaMapa[0].length) && this.miner.getPosY() > 11) {
                    this.vygenerujMapu();
                }
            }
        }
    }
    
    /**
     * Odpali bloky v dosahu vybuchu.
     */  
    public void odpalMinu(int posX, int posY) {        
        int polomerVybuchu = 1;
        
        for (int m = posX - polomerVybuchu; m <= posX + polomerVybuchu; m++) {
            for (int n = posY - polomerVybuchu; n <= posY + polomerVybuchu; n++) {
                this.hernaMapa[m][n] = null;
            }
        }
    }
    
    /**
     * Znovu vygeneruje mapu.
     */  
    public void resetujMapu() {
        this.vygenerujMapu();
        this.vykresli();
    }
    
    /**
     * Vykresli mapu.
     */  
    public void vykresli() {
        this.spravujMapu();
        for (int i = 0; i < this.pozadie.length; i++) {
            for (int j = 0; j < this.pozadie[i].length; j++) {
                this.pozadie[i][j].vykresli(i, j);
            }
        }
        for (int i = 0; i < this.hernaMapa.length; i++) {
            for (int j = 0; j < this.hernaMapa[i].length; j++) {
                if (this.hernaMapa[i][j] != null) {
                    this.hernaMapa[i][j].vykresli(i, j);
                }
            }
        }
    }
    
    public int getHeight() {
        return this.hernaMapa[0].length;
    }

    public int getWidth() {
        return this.hernaMapa.length;
    }

    public IBlok[][] getHernaMapa() {
        return this.hernaMapa;
    }

    public IBlok getIBlok(int i, int j) {
        return this.hernaMapa[i][j];
    }

    public int getSirkaTile() {
        return this.sirkaTile;
    } 
        
    public void setIBlokNaNull(int i, int j) {
        this.hernaMapa[i][j] = null;
    }    
}
