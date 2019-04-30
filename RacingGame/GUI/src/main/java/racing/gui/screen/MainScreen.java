package racing.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import racing.Core;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.player.PlayerSPI;
import racing.gui.input.GameInputProcessor;
import racing.gui.GuiManager;

/**
 * The main screen
 *
 */
public class MainScreen extends BasicScreen {

    OrthographicCamera c;
    
    /**
     * MapSPI
     */
    private MapSPI map;

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
     * PlayerSPI
     */
    private static PlayerSPI player;

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

    @Override
    public void show() {
        Core.getInstance().getGameData().setDisplayWidth(Gdx.graphics.getWidth());
        Core.getInstance().getGameData().setDisplayHeight(Gdx.graphics.getHeight());

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
        
        PositionPart p = player.getPosition();
        c.position.set(p.getX(), p.getY(), 0);
        c.update();
    }
}
