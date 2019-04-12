package racing;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.services.IEntityProcessingService;
import racing.common.services.IGamePluginService;
import racing.common.services.IPostEntityProcessingService;
import racing.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import racing.common.data.GameImage;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.core.screen.*;

public class Core extends Game {

    public static OrthographicCamera cam;
    public ShapeRenderer sr;
    public final GameData gameData = new GameData();
    public static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    public AssetManager assetManager = new AssetManager();

    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;

    public final static int MENU = 0;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;

    public MapSPI map;
    private SpriteBatch batch;

    //TODO: Dependency injection via Declarative Services
    public void setMapService(MapSPI map) {
        this.map = map;
    }

    public void removeMapService() {
        this.map = null;
    }

    public Core() {

        SkinParameter p = new SkinParameter("skin/uiskin.atlas");
        assetManager.load("skin/uiskin.json", Skin.class, p);
        loadImages();
        init();
    }

    public void init() {
        gameData.setDisplayHeight(600);
        gameData.setDisplayWidth(800);
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RacingGame";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

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
        batch = new SpriteBatch();
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    public void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    public void draw() {
        batch.begin();
        for (Entity entity : world.getEntities()) {
            GameImage image = entity.getImage();
            Texture tex = assetManager.get(image.getImagePath(), Texture.class);
            PositionPart p = entity.getPart(PositionPart.class);
            Sprite sprite = new Sprite(tex);
            
            batch.draw(sprite, p.getX(), p.getY(), image.getWidth(), image.getHeight());
        }
        batch.end();
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
    
    private void loadImages() {
        assetManager.load("default.png", Texture.class);
        
        //TILES
        assetManager.load("tiles/road.png", Texture.class);
        assetManager.load("tiles/grass.png", Texture.class);
        assetManager.load("tiles/water.png", Texture.class);
        assetManager.load("tiles/goal.png", Texture.class);
    }
}
