/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.item.services;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameKeys;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IEntityProcessingService;
import racing.common.item.Item;

/**
 *
 * @author jeppe
 */
public class ItemProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Item.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.SPACE));
            
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
                
                
            }
        } 
        
    }
    

