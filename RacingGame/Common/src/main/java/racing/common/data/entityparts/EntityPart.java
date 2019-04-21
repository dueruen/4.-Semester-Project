/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {

    void process(GameData gameData, Entity entity);
}
