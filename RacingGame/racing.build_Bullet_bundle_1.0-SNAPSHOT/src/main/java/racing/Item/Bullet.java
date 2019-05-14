package racing.Item;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.item.Item;
import racing.common.item.ItemSPI;
import racing.common.services.IEntityProcessingService;


/**
 * Plugin used to control the map
 *
 */
public class Bullet implements IEntityProcessingService, ItemSPI {

    public Bullet() {
    }
    
    private boolean canShoot = false;

    @Override
    public void process(GameData gameData, World world) {
              
        

    }

    @Override
    public Entity createItem(Entity arg0, GameData arg1) {
     Entity bulletItem = new Bullet();
    }

}
