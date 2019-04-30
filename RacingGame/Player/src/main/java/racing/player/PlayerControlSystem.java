package racing.player;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameKeys;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    /**
     * Process the player behaviour on every render tick
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

        }
    }
}
