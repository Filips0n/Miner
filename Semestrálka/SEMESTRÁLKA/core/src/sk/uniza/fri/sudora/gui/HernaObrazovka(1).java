
package sk.uniza.fri.sudora.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import sk.uniza.fri.sudora.Hra;
import sk.uniza.fri.sudora.Miner;
import sk.uniza.fri.sudora.mapa.Map;
import sk.uniza.fri.sudora.mapa.obchody.KupObchod;
import sk.uniza.fri.sudora.mapa.obchody.PredajObchod;

/**
 * Obrazovka samotnej hry, urcuje ake objekty sa vykreslia 
 */
public class HernaObrazovka implements Screen {
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture healthTexture;
    private Miner miner;
    private Map hernaMapa;
    private ShapeRenderer shape;
    private PredajObchod predajObchod;
    private KupObchod kupObchod;
    private Hra hra;
    
    private Stage stage;
    private Game game;
    
    /**
     * Vytvori novu hernu obrazovku
     * @param game2 hra, ktorej je tato obrazovka sucastou
     */
    public HernaObrazovka(Game game2) {
        this.game = game2;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();  
        this.shape = new ShapeRenderer();
        
        this.miner = new Miner();
        this.hernaMapa = new Map(this.miner);    
        this.predajObchod = new PredajObchod(80, Gdx.graphics.getHeight() -  80, this.hernaMapa);  
        this.kupObchod = new KupObchod(Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 80, this.hernaMapa);
        this.hra = new Hra(this.miner, this.hernaMapa, this.predajObchod, this.kupObchod);
        this.healthTexture = new Texture("bar3.png");
    }

    @Override
    public void render (float f) {
        float delta = Gdx.graphics.getDeltaTime();
        
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //potrebujeme zistit ci skoncila hra, ak ano tak sa prepne na zaverecnu obrazovku
        this.hra.koniecHry();        
        if (this.hra.koniecHry()) {
            HernaObrazovka.this.game.setScreen(new ZaverObrazovka(HernaObrazovka.this.game));
        }
        
        //vykreslovanie
        this.batch.begin();
        this.hernaMapa.vykresli();
        this.predajObchod.vykresli();
        this.kupObchod.vykresli();
        this.miner.vykresli();       
        this.batch.end();   
        
        //kontrola         
        this.miner.skontrolujPolohu(delta);        
        this.hra.skontrojulObchody(delta);
        this.hra.skontrolujHru();
        
        //vykreslenie obdlznika nad zoznamom aktualnych itemov
        this.shape.begin(ShapeRenderer.ShapeType.Filled);
        this.shape.setColor(Color.BLUE);
        this.shape.rect(Gdx.graphics.getWidth() / 2 - (this.miner.getInventar().size() * 40), Gdx.graphics.getHeight() - 7, this.miner.getInventar().size() * 70, 3);
        this.shape.end();
        
        //fps
        this.batch.begin();
        this.font.draw(this.batch, Gdx.graphics.getFramesPerSecond() + "fps", 10, Gdx.graphics.getHeight() - 10);
        this.batch.end();  
        
        //vykreslenie poloziek inventara
        this.batch.begin();        
        if (!this.miner.getInventar().isEmpty()) {
            int pos = Gdx.graphics.getWidth() / 2 - (this.miner.getInventar().size() * 40); 
            for (String key : this.miner.getInventar().keySet()) {                    
                String stringValue = "" + this.miner.getInventar().get(key);
                this.font.draw(this.batch, key, pos, Gdx.graphics.getHeight() - 10);
                this.font.draw(this.batch, stringValue, pos, Gdx.graphics.getHeight() - 30);
                pos += 80;                
            }    
        }
        
        //vykreslenie atributov batohu a penazi na ucte
        this.font.draw(this.batch, "Objem batohu: ", 270, Gdx.graphics.getHeight() - 10);
        this.font.draw(this.batch, this.miner.getAktualnyPocetItemov() + " / " + this.miner.getKapacitaBatoh(), 297, Gdx.graphics.getHeight() - 30);
        this.font.draw(this.batch, "Penazi na ucte: ", 600, Gdx.graphics.getHeight() - 10);
        this.font.draw(this.batch, this.miner.getZostatokNaUcte() + " $", 640, Gdx.graphics.getHeight() - 30);
        this.batch.end();
        
        //vykreslenie HP
        this.batch.begin();    
        if (this.miner.getHealth() > 0.6f) {
            this.batch.setColor(Color.GREEN);
        } else if (this.miner.getHealth() > 0.2f) {
            this.batch.setColor(Color.ORANGE);
        } else {
            this.batch.setColor(Color.RED);
        }        
        this.batch.draw(this.healthTexture, this.miner.getAktPosX() - 5, this.miner.getAktPosY() + 37, 50 * this.miner.getHealth(), 6);
        this.batch.end();
    }

    @Override
    public void dispose () {
        this.healthTexture.dispose();
        this.batch.dispose();
        this.font.dispose();
        this.shape.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }
    
    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
