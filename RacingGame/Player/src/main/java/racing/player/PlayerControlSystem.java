package racing.player;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameKeys;
import racing.common.data.World;
import racing.common.data.entityparts.LifePart;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ShootingPart;
import racing.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            shootingPart.setIsShooting(gameData.getKeys().isDown(GameKeys.SPACE));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);
            lifePart.process(gameData, player);

            updateShape(player);

        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 1);
        shapey[0] = (float) (y + Math.sin(radians) * 1);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 1);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 1);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 1 * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 1 * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 1);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 1);
    }

}
