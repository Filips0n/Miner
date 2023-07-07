
package sk.uniza.fri.sudora.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Uvodna obrazovka, obsahuje tlacitko pre zacatie hry a exit
 */
public class UvodObrazovka implements Screen {

    private Stage stage;
    private Game game;
    private Skin skin;
    private SpriteBatch batch;
    private Texture img;

    /**
     * Vytvori novu uvodnu obrazovku
     * @param game2 hra, ktorej je tato obrazovka sucastou
     */
    public UvodObrazovka(Game game2) {
        this.game = game2;
        //objekt, do ktoreho sa pridavaju buttony a label
        this.stage = new Stage(new ScreenViewport());
        
        this.skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
        this.batch = new SpriteBatch();
        this.img = new Texture("uvod_background.jpg");
        
        //nastavenie labelu
        Label title = new Label("Miner Game", this.skin, "title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        this.stage.addActor(title);
        
        //nastavenie buttonov
        TextButton playButton = new TextButton("Play!", this.skin);
        playButton.setWidth(Gdx.graphics.getWidth() / 2);
        playButton.setHeight(Gdx.graphics.getHeight() / 5);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                UvodObrazovka.this.game.setScreen(new HernaObrazovka(UvodObrazovka.this.game));
            }
        });

        TextButton exitButton = new TextButton("Exit", this.skin);
        exitButton.setWidth(Gdx.graphics.getWidth() / 3);
        exitButton.setHeight(Gdx.graphics.getHeight() / 6);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() * 2);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        this.stage.addActor(playButton);
        this.stage.addActor(exitButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
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
    public void resize(int width, int height) {

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
