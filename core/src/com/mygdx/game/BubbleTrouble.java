package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.sprites.Ball;
import com.mygdx.game.sprites.GameSprite;
import com.mygdx.game.sprites.Harpoon;
import com.mygdx.game.sprites.Level;
import com.mygdx.game.sprites.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static com.badlogic.gdx.math.MathUtils.random;

public class BubbleTrouble extends ApplicationAdapter {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final String TITLE = "Bubble Trouble";
	private int levelNumber;
	private int highestLevelNumber = 1;

	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private InputHandler inputHandler;

	private ArrayList<GameSprite> gameSprites;
	private Level level;
	private Player player;
	private ArrayList<Ball> balls;
	private ArrayList<Harpoon> harpoons;
	private BitmapFont levelText;
	private BitmapFont highestLevelText;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		balls = new ArrayList<Ball>();
		harpoons = new ArrayList<Harpoon>();
		gameSprites = new ArrayList<GameSprite>();

		levelText = new BitmapFont();
		highestLevelText = new BitmapFont();
		levelNumber = 1;

		// init box2d world
		Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		debugRenderer = new Box2DDebugRenderer();

		level = new Level(world);
		player = new Player(world, new Vector2(150,12));
		balls.add(new Ball(world, new Vector2(100, 200), 10, 32));

		gameSprites.add(level);
		gameSprites.add(player);

		inputHandler = new InputHandler(world, player, harpoons);
		Gdx.input.setInputProcessor(inputHandler);
	}

	public void update() {
		handleCollisions();
		handleOutside();

		if(balls.size() == 0) {
			levelNumber++;
			if(highestLevelNumber < levelNumber) {
				highestLevelNumber = levelNumber;
			}
			for(int i = 1; i <= levelNumber; i++) {
				float rad = 36 + i*2;
				balls.add(new Ball(world, new Vector2(random(rad,WIDTH-rad), 300 + random(-50, 50)), random(10, 20), rad));
			}
		}

		for(Ball b : balls) {
			b.update();
		}

		for(Harpoon h : harpoons) {
			h.update();
		}

		for(GameSprite sprite : gameSprites) {
			sprite.update();
		}
	}

	public void handleOutside() {
		Iterator<Harpoon> iter = harpoons.iterator();
		while (iter.hasNext()) {
			Harpoon h = iter.next();
			if(h.getPosition().y > HEIGHT) {
				iter.remove();
				world.destroyBody(h.getBody());
			}
		}
	}

	public void handleCollisions() {
		Iterator<Contact> cIter = world.getContactList().iterator();
		while (cIter.hasNext()) {
			Contact c = cIter.next();
			if(c.getFixtureA() != null && c.getFixtureB() != null) {
				Body b1 = c.getFixtureA().getBody();
				Body b2 = c.getFixtureB().getBody();

				ListIterator<Ball> bIter = balls.listIterator();
				while(bIter.hasNext()) {
					Ball b = bIter.next();

					if((b1.equals(player.getBody()) || b2.equals(player.getBody())) && (b1.equals(b.getBody()) || b2.equals(b.getBody())))  {
						dispose();
						create();
					}

					Iterator<Harpoon> hIter = harpoons.iterator();
					while (hIter.hasNext()) {
						Harpoon h = hIter.next();

						if((b1.equals(h.getBody()) || b2.equals(h.getBody())) && (b1.equals(b.getBody()) || b2.equals(b.getBody())))  {
							hIter.remove();
							world.destroyBody(h.getBody());

							bIter.remove();
							world.destroyBody(b.getBody());

							if(b.getRad() > 8) {
								bIter.add(new Ball(world, b.getPosition(), 10, b.getRad()/2));
								bIter.add(new Ball(world, b.getPosition(), -10, b.getRad()/2));
							}
						}
					}

				}
			}


		}
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for(GameSprite sprite : gameSprites) {
			sprite.draw(batch);
		}

		for(Ball b : balls) {
			b.draw(batch);
		}

		for(Harpoon h : harpoons) {
			h.draw(batch);
		}
		levelText.draw(batch, "Level " + Integer.toString(levelNumber), WIDTH/2-10, HEIGHT-30);
		highestLevelText.draw(batch, "Reached level: " + Integer.toString(highestLevelNumber), WIDTH/2-42, HEIGHT-50);

		batch.end();

		// box-2d
		//debugRenderer.render(world, camera.combined);
		world.step(1/30f, 10, 10);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
