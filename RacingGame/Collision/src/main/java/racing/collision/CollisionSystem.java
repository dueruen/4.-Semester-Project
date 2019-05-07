package racing.collision;

import java.awt.geom.Area;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.lang.Math;
import racing.common.player.Player;
import racing.common.map.Tile;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TilePart;
import racing.common.services.IPostEntityProcessingService;
import java.util.UUID;


public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        Player player = (Player) world.getEntities(Player.class).get(0);
        MovingPart playerMovingPart = player.getPart(MovingPart.class);

        for (Entity entity : world.getEntities(Tile.class)) {
            TilePart tilePart = entity.getPart(TilePart.class);

            if (!tilePart.getType().isIsStatic()) {
                continue;
            }

            PositionPart playerPosition = player.getPart(PositionPart.class);

            AffineTransform transform = new AffineTransform();
            transform.rotate(
                playerPosition.getRadians(),
                playerPosition.getX() + player.getImage().getWidth()/2,
                playerPosition.getY() + player.getImage().getHeight()/2
            );

            Shape playerShape = transform.createTransformedShape(new Rectangle(
                Math.round(playerPosition.getX()),
                Math.round(playerPosition.getY()),
                Math.round(player.getImage().getWidth()),
                Math.round(player.getImage().getHeight())
            ));

            PositionPart tilePosition = entity.getPart(PositionPart.class);
            Rectangle tileShape = new Rectangle(
                Math.round(tilePosition.getX()),
                Math.round(tilePosition.getY()),
                Math.round(entity.getImage().getWidth()),
                Math.round(entity.getImage().getHeight())
            );

            if (!playerShape.intersects(tileShape)) {
                continue;
            }

            playerMovingPart.setSpeed(0);
        }
    }
}
