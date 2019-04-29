package racing;

import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.services.IEntityProcessingService;
import racing.common.services.IGamePluginService;
import racing.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The main class of the game
 *
 */
public class Core {

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
    private final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();

    /**
     * A list of all registered GamePluginService
     */
    private final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();

    /**
     * A list of all registered PostEntityProcessingService
     */
    private final List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private static Core instance = null;

    public Core() {
        instance = this;
    }

    public static Core getInstance() {
        return instance;
    }

    /**
     * Updates the game, this method must be called in the bundle where the
     * update of the game takes place
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

    public List<IEntityProcessingService> getEntityProcessorList() {
        return entityProcessorList;
    }

    public List<IGamePluginService> getGamePluginList() {
        return gamePluginList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessorList() {
        return postEntityProcessorList;
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

    public GameData getGameData() {
        return gameData;
    }

    public World getWorld() {
        return world;
    }
}
