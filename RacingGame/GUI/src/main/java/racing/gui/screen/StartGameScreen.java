package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import racing.Core;
import racing.common.services.ISpawnService;
import racing.gui.GuiManager;

/**
 * The start game screen
 *
 */
public class StartGameScreen extends BasicScreen {

    /**
     * The stage
     */
    private Stage stage;

    /**
     * Current map name index
     */
    private int currentMap = 0;

    private static ISpawnService spawn;

    public StartGameScreen() {

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();

        table.setFillParent(true);
        table.bottom();
        stage.addActor(table);

        Table out = new Table();
        table.add(out).fillY().expandY().fillX().expandX();

        TextButton preBut = new TextButton("<", GuiManager.getInstance().getSkin());
        out.add(preBut);

        Table mid = new Table();
        out.add(mid).fillY().expandY().fillX().expandX();

        Label l = new Label(GuiManager.getInstance().getMap().getMapNames()[currentMap], GuiManager.getInstance().getSkin());
        mid.add(l);

        mid.row();
        TextButton start = new TextButton("Start", GuiManager.getInstance().getSkin());
        mid.add(start);

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                if (spawn != null) {
                    spawn.spawn(Core.getInstance().getGameData(), Core.getInstance().getWorld(), GuiManager.getInstance().getMap().getMapNames()[currentMap]);
                }
                GuiManager.getInstance().changeScreen(GameScreen.GAME);
            }
        });

        TextButton nextBut = new TextButton(">", GuiManager.getInstance().getSkin());
        out.add(nextBut);
        table.row().pad(10, 0, 10, 0);

        preBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                String[] names = GuiManager.getInstance().getMap().getMapNames();
                if (currentMap == 0) {
                    currentMap = names.length - 1;
                } else {
                    currentMap--;
                }
                l.setText(names[currentMap]);
            }
        });

        nextBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                String[] names = GuiManager.getInstance().getMap().getMapNames();
                if (currentMap == names.length - 1) {
                    currentMap = 0;
                } else {
                    currentMap++;
                }
                l.setText(names[currentMap]);
            }
        });

        table.row();
        TextButton menu = new TextButton("Menu", GuiManager.getInstance().getSkin());

        table.add(menu);

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                GuiManager.getInstance().changeScreen(GameScreen.MENU);
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
    }

    /**
     * Declarative service set spawn service
     *
     * @param spawn spawn service
     */
    public void setSpawnService(ISpawnService spawn) {
        this.spawn = spawn;
    }

    /**
     * Declarative service remove spawn service
     *
     * @param spawn spawn service
     */
    public void removeSpawnService(ISpawnService spawn) {
        this.spawn = null;
    }
}
