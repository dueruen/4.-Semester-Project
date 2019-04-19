package racing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.services.IEntityProcessingService;
import racing.common.services.IGamePluginService;
import racing.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import racing.common.data.GameImage;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.core.screen.*;

/**
 * The main class of the game
 *
 */
public class Core extends Game {

    /**
     * The game camera
     */
    private OrthographicCamera cam;

    /**
     * The game data
     */
    private final GameData gameData = new GameData();

    /**
     * The world data
     */
    private final World world = new World();

    /**
     * A list of all registered EntityProcessingService
     */
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();

    /**
     * A list of all registered GamePluginService
     */
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();

    /**
     * A list of all registered PostEntityProcessingService
     */
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    /**
     * The a asset manager
     */
    public AssetManager assetManager = new AssetManager();

    /**
     * Menu screen
     */
    private MenuScreen menuScreen;

    /**
     * Main screen
     */
    private MainScreen mainScreen;

    /**
     * Constance used by gui
     */
    public final static int MENU = 0;

    /**
     * Constance used by gui
     */
    public final static int APPLICATION = 2;

    /**
     * MapSPI instate
     */
    public MapSPI map;

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /**
     * Bitmap font
     */
    private BitmapFont font;

    /**
     * Skin
     */
    private Skin skin;

    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;
    }

    /**
     * Declarative service remove map service
     */
    public void removeMapService() {
        this.map = null;
    }

    public Core() {
        SkinParameter p = new SkinParameter("skin/uiskin.atlas");
        assetManager.load("skin/uiskin.json", Skin.class, p);
        loadImages();
        init();
    }

    /**
     * Instantiates the game
     */
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

    /**
     * Change screen
     * @param screen value representing the screen 
     */
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
        font = new BitmapFont();
        assetManager.finishLoading();
        skin = assetManager.get("skin/uiskin.json");
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Updates the game
     */
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

    /**
     * Draw the game
     */
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

    /**
     * Loads the images needed for the game
     */
    private void loadImages() {
        assetManager.load("default.png", Texture.class);

        //TILES
        assetManager.load("tiles/road.png", Texture.class);
        assetManager.load("tiles/grass.png", Texture.class);
        assetManager.load("tiles/water.png", Texture.class);
        assetManager.load("tiles/goal.png", Texture.class);
    }

    public GameData getGameData() {
        return gameData;
    }

    public World getWorld() {
        return world;
    }

    public MapSPI getMap() {
        return map;
    }

    public BitmapFont getFont() {
        return font;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }

    public Skin getSkin() {
        return skin;
    }
}