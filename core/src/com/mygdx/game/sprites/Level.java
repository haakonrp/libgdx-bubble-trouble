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
import com.mygdx.game.BubbleTrouble;

import java.util.ArrayList;

public class Level implements GameSprite{
    private ArrayList<Sprite> sprites;
    private Body body;

    public Level(World world) {
        // sprite
        Sprite bg = new Sprite(new Texture("bg.png"));
        Sprite floorSprite = new Sprite(new Texture("floor.png"));
        Sprite roofSprite = new Sprite(new Texture("roof.png"));
        Sprite wallSprite1 = new Sprite(new Texture("walls.png"));
        Sprite wallSprite2 = new Sprite(new Texture("walls.png"));
        floorSprite.setPosition(0, 0);
        roofSprite.setPosition(0, BubbleTrouble.HEIGHT-roofSprite.getHeight()/2);
        wallSprite1.setPosition(0, 0);
        wallSprite2.setPosition(BubbleTrouble.WIDTH-wallSprite2.getWidth()/2, 0);

        bg.setSize(BubbleTrouble.WIDTH, BubbleTrouble.HEIGHT);
        floorSprite.setSize(BubbleTrouble.WIDTH, 5);
        roofSprite.setSize(BubbleTrouble.WIDTH, 5);
        wallSprite1.setSize(5, BubbleTrouble.HEIGHT);
        wallSprite2.setSize(5, BubbleTrouble.HEIGHT);

        sprites = new ArrayList<Sprite>();
        sprites.add(bg);
        sprites.add(wallSprite1);
        sprites.add(wallSprite2);
        sprites.add(floorSprite);
        sprites.add(roofSprite);

        // create box2d
        Vector2 sizeHorizontal = new Vector2(BubbleTrouble.WIDTH/2, 5f);
        Vector2 sizeVertical = new Vector2(5f, BubbleTrouble.HEIGHT/2);

        Vector2 posBottom = new Vector2(BubbleTrouble.WIDTH/2, 0);
        Vector2 posTop = new Vector2(BubbleTrouble.WIDTH/2, BubbleTrouble.HEIGHT);

        Vector2 posLeft = new Vector2(0, BubbleTrouble.HEIGHT/2);
        Vector2 posRight = new Vector2(BubbleTrouble.WIDTH, BubbleTrouble.HEIGHT/2);

        genWall(world, posBottom, sizeHorizontal);
        genWall(world, posTop, sizeHorizontal);

        genWall(world, posLeft, sizeVertical);
        genWall(world, posRight, sizeVertical);

    }

    public void genWall(World world, Vector2 pos, Vector2 size) {
        // body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x, pos.y);

        // create shapes
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

        // create fixtures
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        // add body to world
        body = world.createBody(bodyDef);

        // attach fixtures
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        // clean up
        shape.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch sb) {
        for(Sprite sprite : sprites) {
            sprite.draw(sb);
        }
    }
}
