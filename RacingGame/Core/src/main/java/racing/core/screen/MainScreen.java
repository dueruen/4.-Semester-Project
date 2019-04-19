package racing.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import racing.Core;
import racing.core.managers.GameInputProcessor;

/**
 * The main screen
 *
 */
public class MainScreen extends BasicScreen {

    public MainScreen(Core parent) {
        super(parent);
        parent.map.loadFromFile("maps/map1.txt", parent.getGameData(), parent.getWorld());
    }

    @Override
    public void show() {
        parent.getGameData().setDisplayWidth(Gdx.graphics.getWidth());
        parent.getGameData().setDisplayHeight(Gdx.graphics.getHeight());

        OrthographicCamera c = new OrthographicCamera(parent.getGameData().getDisplayWidth(), parent.getGameData().getDisplayHeight());
        c.translate(parent.getGameData().getDisplayWidth() / 2, parent.getGameData().getDisplayHeight() / 2);
        c.update();
        parent.setCam(c);

        Gdx.input.setInputProcessor(new GameInputProcessor(parent.getGameData()));
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.getGameData().setDelta(Gdx.graphics.getDeltaTime());
        parent.getGameData().getKeys().update();

        parent.update();
        parent.draw();
    }
}
