/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.commonai;

import racing.common.data.Entity;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;

/**
 *
 * @author Victor Gram
 */
public interface AISPI {
    PositionPart findNextPosition();
    void setSourceAndTargetNodes(Entity p, World world);
    
}
