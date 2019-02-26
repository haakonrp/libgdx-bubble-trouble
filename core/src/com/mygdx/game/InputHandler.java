package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.sprites.Harpoon;
import com.mygdx.game.sprites.Player;

import java.util.ArrayList;

public class InputHandler implements InputProcessor {
    private World world;
    private Player player;
    private ArrayList<Harpoon> harpoons;

    public InputHandler(World world, Player player, ArrayList<Harpoon> harpoons) {
        this.world = world;
        this.player = player;
        this.harpoons = harpoons;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                player.setMovingLeft(true);
                player.getSprite().setScale(-1, 1);
                player.getBody().setLinearVelocity(new Vector2(-1000, 0));
                break;

            case Input.Keys.RIGHT:
            case Input.Keys.D:
                player.setMovingRight(true);
                player.getSprite().setScale(1, 1);
                player.getBody().setLinearVelocity(new Vector2(1000, 0));
                break;

            case Input.Keys.SPACE:
                if(harpoons.size() < 3) {
                    harpoons.add(player.shoot(world));
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                player.setMovingLeft(false);
                if(player.getMovingRight()) {
                    player.getSprite().setScale(1, 1);
                    player.getBody().setLinearVelocity(new Vector2(1000,0));
                } else {
                    player.getBody().setLinearVelocity(new Vector2(0,0));
                }
                break;

            case Input.Keys.RIGHT:
            case Input.Keys.D:
                player.setMovingRight(false);
                if(player.getMovingLeft()) {
                    player.getSprite().setScale(-1, 1);
                    player.getBody().setLinearVelocity(new Vector2(-1000,0));
                } else {
                    player.getBody().setLinearVelocity(new Vector2(0,0));
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
