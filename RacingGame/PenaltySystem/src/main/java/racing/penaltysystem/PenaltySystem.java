package racing.penaltysystem;

import java.awt.geom.Area;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.lang.Math;
import racing.common.player.Player;
import racing.common.npc.NPC;
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
import racing.common.data.TileType;

public class PenaltySystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            intersects(player, world);
        }

        for (Entity npc : world.getEntities(NPC.class)) {
            intersects(npc, world);
        }
    }

    /**
     * Check if entity intersects with statis tiles
     *
     * @param entity
     * @param world
     */
    private void intersects(Entity entity, World world) {
        MovingPart entityMovingPart = entity.getPart(MovingPart.class);

        for (Entity tileEntity : world.getEntities(Tile.class)) {
            TilePart tilePart = tileEntity.getPart(TilePart.class);
            PositionPart tilePosition = tileEntity.getPart(PositionPart.class);
            
            PositionPart entityPosition = entity.getPart(PositionPart.class);

            AffineTransform transform = new AffineTransform();
            transform.rotate(
                entityPosition.getRadians(),
                entityPosition.getX() + entity.getImage().getWidth()/2,
                entityPosition.getY() + entity.getImage().getHeight()/2
            );
            
            Shape car = transform.createTransformedShape(new Rectangle(
                Math.round(entityPosition.getX()),
                Math.round(entityPosition.getY()),
                Math.round(entity.getImage().getWidth()),
                Math.round(entity.getImage().getHeight())
            ));

            Rectangle staticTile = new Rectangle(
                Math.round(tilePosition.getX() + 20),
                Math.round(tilePosition.getY() + 20),
                Math.round(tileEntity.getImage().getWidth() - 20),
                Math.round(tileEntity.getImage().getHeight() -  20)
            );

            if (car.intersects(staticTile)) {
                entityMovingPart.setPenalty(Math.round(tilePart.getType().getPenalty()));
            } 
        }
    }
}
