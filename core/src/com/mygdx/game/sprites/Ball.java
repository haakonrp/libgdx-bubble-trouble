package com.mygdx.game.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball implements GameSprite {
    private Sprite sprite;
    private Body body;
    private float rad;

    public Ball(World world, Vector2 pos, float vel, float rad) {
        // sprite
        sprite = new Sprite(new Texture("ball.png"));
        sprite.setPosition(pos.x, pos.y);
        sprite.setSize(rad*2, rad*2);
        sprite.setOriginCenter();
        this.rad = rad;

        // create box2d tank
        genBall(world, pos, vel, rad);
    }

    public void genBall(World world, Vector2 pos, float vel, float rad) {
        // body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x, pos.y);

        // create shapes
        CircleShape shape = new CircleShape();
        shape.setRadius(rad);

        // create fixtures
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.filter.groupIndex = -2;

        // add body to world
        body = world.createBody(bodyDef);

        // attach fixtures
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        body.setLinearVelocity(new Vector2(vel, 0));

        body.setUserData(this);

        // clean up
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getRad() {
        return rad;
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
