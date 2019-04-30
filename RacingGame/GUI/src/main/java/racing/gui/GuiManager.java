package racing.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.HashMap;
import java.util.Map;
import racing.Core;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.services.IGamePluginService;
import racing.gui.screen.*;
import static racing.gui.screen.GameScreen.GAME;
import static racing.gui.screen.GameScreen.MAP_EDITOR;
import static racing.gui.screen.GameScreen.MENU;

/**
 * This class is rendering the game using LibGDX
 */
public class GuiManager extends Game implements IGamePluginService { //implements IGamePluginService

    /**
     * The game camera
     */
    private OrthographicCamera cam;

    /**
     * The a asset manager
     */
    private AssetManager assetManager;

    /**
     * The Game screens
     */
    private Map<GameScreen, BasicScreen> gameScreens;

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
     * GuiManager
     */
    private static GuiManager instance;

    public GuiManager() {
        gameScreens = new HashMap<>();
        assetManager = new AssetManager();
        SkinLoader.SkinParameter p = new SkinLoader.SkinParameter("skin/uiskin.atlas");
        assetManager.load("skin/uiskin.json", Skin.class, p);
        loadImages();
        init();
        instance = this;
    }

    /**
     * Get instance of GuiManager
     *
     * @return GuiManager
     */
    public static GuiManager getInstance() {
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        assetManager.finishLoading();
        skin = assetManager.get("skin/uiskin.json");
        changeScreen(MENU);
    }

    /**
     * Instantiates the game
     */
    private void init() {
        Core.getInstance().getGameData().setDisplayHeight(600);
        Core.getInstance().getGameData().setDisplayWidth(800);
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RacingGame";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Updates the game
     */
    public void update() {
        Core.getInstance().update();
    }

    /**
     * Draw the game
     */
    public void draw() {
        batch.begin();
        for (Entity entity : Core.getInstance().getWorld().getEntities()) {
            if (entity instanceof Tile) {
                GameImage image = entity.getImage();
                Texture tex = assetManager.get(image.getImagePath(), Texture.class);
                PositionPart p = entity.getPart(PositionPart.class);
                drawSprite(new Sprite(tex), image, p);
            }
        }
        for (Entity entity : Core.getInstance().getWorld().getEntities()) {
            if (!(entity instanceof Tile)) {
                GameImage image = entity.getImage();
                Texture tex = assetManager.get(image.getImagePath(), Texture.class);
                PositionPart p = entity.getPart(PositionPart.class);
                drawSprite(new Sprite(tex), image, p);
            }
        }
        batch.end();
    }

    /**
     * Draw the sprite
     *
     * @param s the sprite
     * @param image the image
     * @param p the positionPart
     */
    private void drawSprite(Sprite s, GameImage image, PositionPart p) {
        s.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        s.rotate((float) Math.toDegrees(p.getRadians()));
        s.setX(p.getX());
        s.setY(p.getY());
        s.setSize(image.getWidth(), image.getHeight());
        s.draw(batch);
    }

    /**
     * Change screen
     *
     * @param screen value representing the screen
     */
    public void changeScreen(GameScreen screen) {
        switch (screen) {
            case MENU:
                if (gameScreens.get(MENU) == null) {
                    gameScreens.put(MENU, new MenuScreen());
                }
                this.setScreen(gameScreens.get(MENU));
                break;
            case MAP_EDITOR:
                if (gameScreens.get(MAP_EDITOR) == null) {
                    gameScreens.put(MAP_EDITOR, new MapEditor());
                }
                this.setScreen(gameScreens.get(MAP_EDITOR));
                break;
            case GAME:
                if (gameScreens.get(GAME) == null) {
                    gameScreens.put(GAME, new MainScreen());
                }
                this.setScreen(gameScreens.get(GAME));
                break;
        }
    }

    /**
     * Loads the images needed for the game
     */
    private void loadImages() {
        assetManager.load("default.png", Texture.class);

        //CARS
        assetManager.load("cars/car.png", Texture.class);
        assetManager.load("cars/car1.png", Texture.class);
        assetManager.load("cars/car2.png", Texture.class);
        assetManager.load("cars/car3.png", Texture.class);
        assetManager.load("cars/car4.png", Texture.class);
        assetManager.load("cars/car5.png", Texture.class);

        //TILES
        assetManager.load("tiles/road.png", Texture.class);
        assetManager.load("tiles/start.png", Texture.class);
        assetManager.load("tiles/grass.png", Texture.class);
        assetManager.load("tiles/water.png", Texture.class);
        assetManager.load("tiles/goal.png", Texture.class);
        assetManager.load("tiles/tree.png", Texture.class);

        assetManager.load("tiles/road_sel.png", Texture.class);
        assetManager.load("tiles/start_sel.png", Texture.class);
        assetManager.load("tiles/grass_sel.png", Texture.class);
        assetManager.load("tiles/water_sel.png", Texture.class);
        assetManager.load("tiles/goal_sel.png", Texture.class);
        assetManager.load("tiles/tree_sel.png", Texture.class);
    }

    public AssetManager getAssetManager() {
        return assetManager;
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

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        Gdx.app.exit();
    }
}
