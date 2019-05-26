package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import racing.gui.GuiManager;

/**
 * The menu screen
 *
 */
public class MenuScreen extends BasicScreen {

    /**
     * The stage
     */
    private Stage stage;
    
    /**
     * The sound
     */
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/game-menu.mp3"));
    
    /**
     * The image
     */
    private Image i = new Image(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get("cars/car.png", Texture.class))));

    public MenuScreen() {

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton newGame = new TextButton("New Game", GuiManager.getInstance().getSkin());
        TextButton mapEditor = new TextButton("Map editor", GuiManager.getInstance().getSkin());
        TextButton exit = new TextButton("Exit", GuiManager.getInstance().getSkin());
        Label label = new Label("Welcome to Woad Wacing", GuiManager.getInstance().getSkin());
        
        sound.play(1.0f);

        table.add(i).size(180, 120);
        table.row().pad(20,0,5,0);
        table.add(label).fillX().uniformY();
        table.row().pad(30,0,5,0);
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(mapEditor).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exit).fillX().uniformX();
        
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                GuiManager.getInstance().changeScreen(GameScreen.START_GAME);
                sound.stop();
            }
        });

        mapEditor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                GuiManager.getInstance().changeScreen(GameScreen.MAP_EDITOR);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float f) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        sound.dispose();
    }
}
