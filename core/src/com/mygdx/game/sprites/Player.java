package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Player implements GameSprite {
    private Sprite sprite;
    private Body body;
    private boolean movingLeft;
    private boolean movingRight;

    public Player(World world, Vector2 pos) {
        // sprite
        sprite = new Sprite(new Texture("player.png"));
        sprite.setSize(15, 45);
        sprite.setPosition(pos.x, pos.y);
        sprite.setOriginCenter();

        // create box2d tank
        movingLeft = false;
        movingRight = false;
        genPlayer(world, pos);
    }

    public void genPlayer(World world, Vector2 pos) {
        // body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x, pos.y);
        bodyDef.fixedRotation = true;

        // create shapes
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7f, 20f);

        // create fixtures
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        // add body to world
        body = world.createBody(bodyDef);

        // attach fixtures
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        // clean up
        shape.dispose();
    }

    public Harpoon shoot(World world) {
        return new Harpoon(world, new Vector2(body.getPosition().x, body.getPosition().y * 2));
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setMovingLeft(boolean left) {
        movingLeft = left;
    }

    public void setMovingRight(boolean right) {
        movingRight = right;
    }

    public boolean getMovingLeft() {
        return movingLeft;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void update() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
    }

    @Override
    public void draw(SpriteBatch sb) {
        sprite.draw(sb);
    }
}
