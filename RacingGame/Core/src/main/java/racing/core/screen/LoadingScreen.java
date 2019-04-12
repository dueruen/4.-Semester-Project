/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.core.screen;

import com.badlogic.gdx.Screen;
import racing.Core;

/**
 *
 * @author yodamaster42
 */
public class LoadingScreen implements Screen {

    private Core parent;

    public LoadingScreen(Core parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        parent.changeScreen(Core.MENU);
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
}
