package racing.player;

import racing.common.player.Player;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IGamePluginService;
import java.util.UUID;
import racing.common.data.entityparts.ScorePart;
import racing.common.player.PlayerSPI;

public class PlayerPlugin implements IGamePluginService, PlayerSPI {

    /**
     * Player
     */
    private Entity player;
    private boolean haveItem;

    /**
     * Start the plugin, initilize a new player and add it to the world of
     * entities
     *
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayerCar(gameData);
        world.addEntity(player);
    }

    /**
     * Helper method to create a new player car
     * @param  gameData
     * @return player
     */
    private Entity createPlayerCar(GameData gameData) {
        float deacceleration = 10;
        float acceleration = 10;
        float maxSpeed = 400;
        float rotationSpeed = 3;
        float x = 500;
        float y = 800;
        float radians = 3.1415f / 2;
        Entity playerCar = new Player();
        GameImage img = new GameImage("cars/car.png", 100, 50);
        playerCar.setImage(img);
        playerCar.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        playerCar.add(new PositionPart(x, y, radians));
        playerCar.add(new ScorePart());
        UUID uuid = UUID.randomUUID();
        

        return playerCar;
    }
    public void checkForItem() {
        
    
}

    /**
     * Remove entity from the world if the bundle is removed
     * @param gameData
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

    @Override
    public PositionPart getPosition() {
        return player.getPart(PositionPart.class);
    }
}
