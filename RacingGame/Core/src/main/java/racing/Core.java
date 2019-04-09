package racing;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.services.IEntityProcessingService;
import racing.common.services.IGamePluginService;
import racing.common.services.IPostEntityProcessingService;
import racing.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import racing.core.screen.*;

public class Core extends Game {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;

    public final static int MENU = 0;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;

    public Core() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RacingGame";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        //new LwjglApplication(this, cfg);
        new LwjglApplication(this, cfg);
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) {
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                break;
            case APPLICATION:
                if (mainScreen == null) {
                    mainScreen = new MainScreen(this);
                }
                this.setScreen(mainScreen);
                break;
        }
    }

    @Override
    public void create() {
        //loadingScreen = new LoadingScreen(this);
        //setScreen(loadingScreen);
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
//        gameData.setDisplayWidth(Gdx.graphics.getWidth());
//        gameData.setDisplayHeight(Gdx.graphics.getHeight());
//
//        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
//        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
//        cam.update();
//
//        sr = new ShapeRenderer();
//
//        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

    }

    @Override
    public void render() {
//        // clear screen to black
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        gameData.setDelta(Gdx.graphics.getDeltaTime());
//        gameData.getKeys().update();
//
//        update();
//        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
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
    public void dispose() {
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }
}
