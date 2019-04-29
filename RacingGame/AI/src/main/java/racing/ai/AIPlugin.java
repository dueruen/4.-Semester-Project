package racing.ai;

import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.map.MapSPI;
import racing.common.services.IGamePluginService;
import racing.commonai.AISPI;

/**
 *
 * @author Victor Gram
 */
public class AIPlugin implements IGamePluginService, AISPI {
    
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
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
    
}
