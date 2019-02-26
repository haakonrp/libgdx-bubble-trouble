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

public class Harpoon implements GameSprite{
    private Sprite sprite;
    private Body body;

    public Harpoon(World world, Vector2 pos) {
        // sprite
        sprite = new Sprite(new Texture("bullet.png"));
        sprite.setPosition(pos.x, pos.y);
        sprite.setSize(6, 6);
        sprite.setOriginCenter();

        // create box2d
        genHarpoon(world, pos);
    }

    public void genHarpoon(World world, Vector2 pos) {
        // body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(pos.x, pos.y);
        bodyDef.bullet = true;

        // create shapes
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2, 2);

        // create fixtures
        FixtureDef fixtureDef = new FixtureDef();

        // add body to world
        body = world.createBody(bodyDef);

        // attach fixtures
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        body.setLinearVelocity(new Vector2(0, 200));

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

    @Override
    public void update() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
