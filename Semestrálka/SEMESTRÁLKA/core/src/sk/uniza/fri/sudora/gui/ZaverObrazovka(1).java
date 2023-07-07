
package sk.uniza.fri.sudora.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Zaverecna obrazovka, moznost ukoncit alebo spustit novu hru
 */
public class ZaverObrazovka implements Screen {

    private Stage stage;
    private Game game;
    private Skin skin;
    private SpriteBatch batch;
    private Texture img;
    
    /**
     * Vytvori novu zaverecnu obrazovku
     * @param game2 hra, ktorej je tato obrazovka sucastou
     */
    public ZaverObrazovka(Game game2) {
        this.game = game2;
        //objekt, do ktoreho sa pridavaju buttony
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
        this.batch = new SpriteBatch();
        this.img = new Texture("RIP2.jpg");

        //nastavenie buttonov
        TextButton newGameButton = new TextButton("New Game", this.skin);
        newGameButton.setWidth(Gdx.graphics.getWidth() / 2);
        newGameButton.setHeight(Gdx.graphics.getHeight() / 5);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - newGameButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - newGameButton.getHeight());
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                ZaverObrazovka.this.game.setScreen(new HernaObrazovka(ZaverObrazovka.this.game));
            }
        });
        
        TextButton exitButton = new TextButton("Exit", this.skin);
        exitButton.setWidth(Gdx.graphics.getWidth() / 3);
        exitButton.setHeight(Gdx.graphics.getHeight() / 6);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() * 2 - 20);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        this.stage.addActor(newGameButton);
        this.stage.addActor(exitButton);
    }

    
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //vykreslenie
        this.stage.act();
        this.stage.getBatch().begin();
        this.stage.getBatch().draw(this.img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage.getBatch().end();
        this.stage.draw();
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

    @Override
    public void dispose() {
        this.stage.dispose();
        this.batch.dispose();
    }    
}
