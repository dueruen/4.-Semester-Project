/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

public class ItemPart implements EntityPart {

    private boolean isUsing;
    private int chargesLeft;
    //private String ID;

    public ItemPart() {//String id
////        this.ID = id;
    }

    public boolean isUsing() {
        return this.isUsing;
    }

    public void setIsUsing(boolean b) {
        this.isUsing = b;
    }

//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
