package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.List;
import racing.Core;
import racing.common.data.Entity;
import racing.common.data.entityparts.ScorePart;
import racing.common.player.Player;
import racing.common.services.ISpawnService;
import racing.gui.GuiManager;

/**
 *
 */
public class GameOverScreen extends BasicScreen {

    /**
     * The stage
     */
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        if (GuiManager.getInstance().getScore() == null) {
            Label ll = new Label("No scoresystem", GuiManager.getInstance().getSkin());
            table.add(ll);
        } else {
            List<Entity> entities = GuiManager.getInstance().getScore().getScores(Core.getInstance().getWorld());
            int count = 1;
            Label ll = new Label("", GuiManager.getInstance().getSkin());
            for (Entity e : entities) {
                if (e instanceof Player) {
                    if (count == 1) {
                        ll = new Label("Congratulations, you won", GuiManager.getInstance().getSkin());
                    } else {
                        ll = new Label("You came at place: " + count, GuiManager.getInstance().getSkin());
                    }
                    break;
                }
                count++;
            }
            table.add(ll);
            table.row();

            Label ls = new Label("Score board:", GuiManager.getInstance().getSkin());
            table.add(ls);
            table.row();
            Table inner = new Table();
            //inner.setFillParent(true);
            if (GuiManager.getInstance().getScore() != null) {
                //table.align(Align.topLeft);
                for (Entity e : entities) {
                    Image i = new Image(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(e.getImage().getImagePath(), Texture.class))));
                    inner.add(i).size(20);
                    ScorePart sp = e.getPart(ScorePart.class);
                    Label l = new Label(" : " + sp.getLabs(), GuiManager.getInstance().getSkin());
                    inner.add(l);

                    inner.row();
                }
            }
            table.add(inner);
        }

        table.row();
        TextButton menu = new TextButton("Menu", GuiManager.getInstance().getSkin());

        table.add(menu);

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                GuiManager.getInstance().changeScreen(GameScreen.MENU);
            }
        });
        GuiManager.getInstance().getScore().reset();
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
}
