package racing.collision;

import java.awt.geom.Area;
import java.awt.Rectangle;
import java.awt.Shape;
import java.lang.Math;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IPostEntityProcessingService;
import java.util.UUID;

public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID())) {
                   continue;
                }

                PositionPart positionA = e.getPart(PositionPart.class);
                Area a = new Area(new Rectangle(
                    Math.round(e.getImage().getWidth()),
                    Math.round(e.getImage().getWidth()),
                    Math.round(positionA.getX()),
                    Math.round(positionA.getY())
                ));

                PositionPart positionB = f.getPart(PositionPart.class);
                Area b = new Area(new Rectangle(
                    Math.round(f.getImage().getWidth()),
                    Math.round(f.getImage().getWidth()),
                    Math.round(positionB.getX()),
                    Math.round(positionB.getY())
                ));

                a.intersect(b);

                if (a.isEmpty()) {
                    continue;
                }

                // Stop entity
            }
        }
    }
}
