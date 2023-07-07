
package sk.uniza.fri.sudora;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;

/**
 * Predstavuje hraca, pomocou ktoreho hrac sa pohybuje po mape a vykonava rozne akcie.
 * 
 * @author Filip
 */
public class Miner {
    //obsahuje vsetky aktualne itemy v inventari
    private HashMap<String, Integer> inventar; 
    private Sprite[] sprites;
    
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture img;    
    private Sprite sprite;
    private Smer smer;
    
    private final int sirkaMapyTile = Gdx.graphics.getWidth() / this.sirkaTile;
    private final int vyskaMapyTile = Gdx.graphics.getHeight() / this.sirkaTile - 2;
    private final int sirkaTile = 40;
    
    private final int zaciatocnaPoziciaY = Gdx.graphics.getHeight() - (this.sirkaTile * 2);
    private final int zaciatocnaPoziciaX = Gdx.graphics.getWidth() / 2 - 20;
    
    private int aktuanySprite;
    
    private int posX;
    private int posY;
    
    //Udava poziciu minera vzhladom na pixel, X, Y-ova os.
    private int aktPosX;
    private int aktPosY;    
    
    private int kapacitaBatoh;
    private int zostatokNaUcte;
    private int aktualnyPocetItemov;
    
    private boolean jeVDoloch; 
    private float health;    
    private float casStravenyVDole;
    private float casStravenyHore;
    
     
    /**
     * Vytvori minera a nastavi prvotne hodnoty.
     */
    public Miner() {
        this.inventar = new HashMap<>();
        
        this.img = new Texture("minerv3.png");
        this.sprites = new Sprite[]{
            new Sprite(this.img, 0, 0, 210, 260),
            new Sprite(this.img, 0, 282, 210, 260),
            new Sprite(this.img, 0, 282 * 2, 210, 260),
            new Sprite(this.img, 0, 282 * 3, 210, 260)
        };
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.smer = Smer.DOLE;
        
        this.aktuanySprite = 0;      
                
        this.posX = 12;
        this.posY = this.vyskaMapyTile;
        this.aktPosX = this.zaciatocnaPoziciaX;
        this.aktPosY = this.zaciatocnaPoziciaY;        
               
        this.aktualnyPocetItemov = 0;
        this.kapacitaBatoh = 20;
        this.zostatokNaUcte = 0;
        
        this.health = 1f;
        this.casStravenyVDole = 0.0f;
        this.casStravenyHore = 0.0f;
        
        this.setSprite(this.sprites[this.aktuanySprite]);  
    }
    
    private void posun() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (this.aktPosY < this.sirkaTile) {
                this.aktPosY = 0;
                this.aktuanySprite = 0;
            } else {
                this.aktPosY -= this.sirkaTile;
                this.posY -= 1;
                this.smer = Smer.DOLE;
                this.aktuanySprite = 0;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (this.aktPosY > Gdx.graphics.getHeight() - (this.sirkaTile * 3)) {
                this.aktPosY = Gdx.graphics.getHeight() - (this.sirkaTile * 2);
                this.aktuanySprite = 1;
            } else {
                this.aktPosY += this.sirkaTile;
                this.posY += 1;  
                this.smer = Smer.HORE;
                this.aktuanySprite = 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (this.aktPosX < this.sirkaTile) {
                this.aktPosX = Gdx.graphics.getWidth() - this.sirkaTile;
                this.posX = this.sirkaMapyTile - 1;
                this.aktuanySprite = 2;
            } else {
                this.aktPosX -= this.sirkaTile;
                this.posX -= 1; 
                this.smer = Smer.VLAVO;
                this.aktuanySprite = 2;
            }            
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (this.aktPosX >= Gdx.graphics.getWidth() - this.sirkaTile) {
                this.aktPosX = 0;
                this.posX = 0;
                this.aktuanySprite = 3;
            } else {
                this.aktPosX += this.sirkaTile;
                this.posX += 1;   
                this.smer = Smer.VPRAVO;
                this.aktuanySprite = 3;
            }            
        } else {
            this.batch.draw(this.sprites[this.aktuanySprite], this.aktPosX, this.aktPosY, this.sirkaTile, this.sirkaTile);
        }
//        System.out.println(this.posX + " / " + this.posY);
    }
    
    /**
     * Vlozi do inventara vytazeny item.
     */
    public void putItem(String nazov) {
        if (this.aktualnyPocetItemov < this.kapacitaBatoh) {
            if (this.inventar.containsKey(nazov)) {
                this.inventar.put(nazov, this.inventar.get(nazov) + 1);
                this.aktualnyPocetItemov++;
            } else {
                this.inventar.put(nazov, 1);
                this.aktualnyPocetItemov++;
            }
        }                 
    }
    
    /**
     * Zistuje ci sa miner nachadza v bani. A podla toho urcuje ci sa ma
     * znizovat alebo zvysovat zdravie minera.
     */
    public void skontrolujPolohu(float delta) {    
        
        if (this.posY < this.vyskaMapyTile) {
            this.jeVDoloch = true;
            this.casStravenyHore = 0;
        } else {
            this.jeVDoloch = false;
            this.casStravenyVDole = 0;
        }
        
        if (this.jeVDoloch) {
            this.casStravenyVDole += delta; 
            if (this.casStravenyVDole > 5f) {
                this.setHealth(-0.1f);
                this.casStravenyVDole = 0;
            }
        } else {
            this.casStravenyHore += delta; 
            if (this.casStravenyHore > 5f && this.health != 1f) {
                this.setHealth(0.1f);
                this.casStravenyHore = 0;
            }
        }
    }
    
    /**
     * Vykresli minera.
     */
    public void vykresli() {
        this.batch.begin();
        this.posun();
        this.batch.end();
    }
    
    /**
     * Resetuje minera po jeho smrti na prvotne hodnoty.
     */
    public void resetujMinera() {
        this.posX = 12;
        this.posY = this.vyskaMapyTile;
        this.aktPosX = this.zaciatocnaPoziciaX;
        this.aktPosY = this.zaciatocnaPoziciaY;        
               
        this.aktualnyPocetItemov = 0;
        this.kapacitaBatoh = 20;
        this.zostatokNaUcte = 0;
        
        this.health = 1f;
        this.casStravenyVDole = 0.0f;
        this.casStravenyHore = 0.0f;
        
        this.inventar.clear();
        this.vykresli();
    }
    
    /**
     * Zvysi kapacitu batohu.
     */
    public void zvysKapacitaBatohu(int pocet) {
        this.kapacitaBatoh += pocet;
    }
    
    /**
     * Vrati X-ovu poziciu minera.
     */
    public int getPosX() {
        return this.posX;
    }
    
    /**
     * Vrati Y-ovu poziciu minera.
     */
    public int getPosY() {
        return this.posY;
    }
    
    /**
     * Vrati X-ovu poziciu minera vzhladom na pixel.
     */
    public int getAktPosX() {
        return this.aktPosX;
    }
    
    /**
     * Vrati Y-ovu poziciu minera vzhladom na pixel.
     */
    public int getAktPosY() {
        return this.aktPosY;
    }    
    
    public int getAktualnyPocetItemov() {
        return this.aktualnyPocetItemov;
    }  
    
    public int getZostatokNaUcte() {
        return this.zostatokNaUcte;
    }

    public float getHealth() {
        return this.health;
    }
    
    public int getKapacitaBatoh() {
        return this.kapacitaBatoh;
    } 
    
    public HashMap<String, Integer> getInventar() {
        return this.inventar;
    }
    
    public Smer getSmer() {
        return this.smer;
    }
    
    /**
     * Nastavi novu maximalnu kapacitu batohu.
     */
    public void setKapacitaBatoh(int kapacitaBatoh) {
        this.kapacitaBatoh = kapacitaBatoh;
    }       
    
    public void setHealth(float health) {
        this.health += health;

        if (this.health > 1f) {
            this.health = 1f;
        }
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }   
        
    public void setAktPosX(int aktPosX) {
        this.aktPosX = aktPosX;
    }

    public void setAktPosY(int aktPosY) {
        this.aktPosY = aktPosY;
    }       
       
    public void setAktualnaKapacitaBatohu(int pocet) {
        this.aktualnyPocetItemov = pocet;
    }
    
    public void setHodnotuPredanychItemov(int pocet) {
        this.zostatokNaUcte += pocet;
    }          
    
    public void setZostatokNaUcte(int zostatokNaUcte) {
        this.zostatokNaUcte = zostatokNaUcte;
    }
}
