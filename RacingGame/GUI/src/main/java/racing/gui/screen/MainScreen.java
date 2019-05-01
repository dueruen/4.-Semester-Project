package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import racing.Core;
import racing.common.data.Entity;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ScorePart;
import racing.common.map.MapSPI;
import racing.common.player.PlayerSPI;
import racing.common.services.IScoreService;
import racing.gui.input.GameInputProcessor;
import racing.gui.GuiManager;

/**
 * The main screen
 *
 */
public class MainScreen extends BasicScreen {

    /**
     * OrthographicCamera
     */
    private OrthographicCamera c;

    /**
     * Stage
     */
    private Stage stage;

    /**
     * Table
     */
    private Table table;

    /**
     * MapSPI
     */
    private MapSPI map;

    /**
     * PlayerSPI
     */
    private static PlayerSPI player;

    /**
     * IScoreService
     */
    private static IScoreService score;

    @Override
    public void show() {
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        c = new OrthographicCamera(Core.getInstance().getGameData().getDisplayWidth(), Core.getInstance().getGameData().getDisplayHeight());
        GuiManager.getInstance().setCam(c);

        Gdx.input.setInputProcessor(new GameInputProcessor(Core.getInstance().getGameData()));
    }

    @Override
    public void render(float f) {
        GuiManager.getInstance().getBatch().setProjectionMatrix(c.combined);

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Core.getInstance().getGameData().setDelta(Gdx.graphics.getDeltaTime());
        Core.getInstance().getGameData().getKeys().update();

        GuiManager.getInstance().update();
        GuiManager.getInstance().draw();

        if (player != null) {
            PositionPart p = player.getPosition();
            c.position.set(p.getX(), p.getY(), 0);
            c.update();
        }

        table.reset();
        if (score != null) {
            table.align(Align.topLeft);
            for (Entity e : score.getScores(Core.getInstance().getWorld())) {
                Image i = new Image(new TextureRegionDrawable(new TextureRegion(GuiManager.getInstance().getAssetManager().get(e.getImage().getImagePath(), Texture.class))));
                table.add(i).size(20);
                ScorePart sp = e.getPart(ScorePart.class);
                Label l = new Label(" : " + sp.getLabs(), GuiManager.getInstance().getSkin());
                table.add(l);

                table.row();
            }
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;
        this.map.loadFromFile("maps/map1.txt", Core.getInstance().getGameData(), Core.getInstance().getWorld());
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

    /**
     * Declarative service set player service
     *
     * @param player player service
     */
    public void setPlayerService(PlayerSPI player) {
        this.player = player;
    }

    /**
     * Declarative service remove player service
     *
     * @param player player service
     */
    public void removePlayerService(PlayerSPI player) {
        this.player = null;
    }

    /**
     * Declarative service set score service
     *
     * @param score score service
     */
    public void setScoreService(IScoreService score) {
        this.score = score;
    }

    /**
     * Declarative service remove score service
     *
     * @param score score service
     */
    public void removeScoreService(IScoreService score) {
        this.score = null;
    }
}
