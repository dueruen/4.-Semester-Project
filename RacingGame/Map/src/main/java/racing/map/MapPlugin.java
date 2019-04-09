/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.map;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.map.Map;
import racing.common.services.IGamePluginService;

/**
 *
 * @author yodamaster42
 */
public class MapPlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(new Map());
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity map : world.getEntities(Map.class)) {
            world.removeEntity(map);
        }
    }
    
}
