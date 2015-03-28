package com.shaunrasmusen.jump.level;

import java.util.Random;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.render.Graphics;
import com.shaunrasmusen.jump.render.texture.Sprite;
import com.shaunrasmusen.jump.render.texture.Tile;

public class Level {

	public double[] tiles, tilesSave, store;
	public double[] shopSprite = new double[240];
	public int width, height;
	public boolean blindness;
	public boolean onStore = false, onStoreExit = false;

	public int level;
	protected boolean hot;

	private int lavaCounter = 0;
	private Random random = new Random();

	private static Tile nullTile = new Tile(15, 15, 16, 16, "tiles", false);

	// Level Tiles

	public static Tile platTileOn = new Tile(7, 0, 16, 16, "tiles", false);
	private static Tile platTile = new Tile(7, 2, 16, 16, "tiles", false);
	private static Tile platTileOff = new Tile(7, 1, 16, 16, "tiles", false);

	private static Tile storeTPOn = new Tile(8, 2, 16, 16, "tiles", false);
	private static Tile storeTPOff = new Tile(8, 3, 16, 16, "tiles", false);

	private static Tile platTileH10 = new Tile(8, 0, 16, 16, "tiles", false);
	private static Tile platTileH20 = new Tile(9, 0, 16, 16, "tiles", false);
	private static Tile platTileH30 = new Tile(10, 0, 16, 16, "tiles", false);

	private static Tile platTileC10 = new Tile(8, 1, 16, 16, "tiles", false);
	private static Tile platTileC30 = new Tile(9, 1, 16, 16, "tiles", false);
	private static Tile platTileC50 = new Tile(10, 1, 16, 16, "tiles", false);

	private static Tile greyTile = new Tile(0, 4, 16, 16, "tiles", true);

	private static Tile wallTile = new Tile(7, 3, 16, 16, "tiles", true);
	private static Tile wall0 = new Tile(7, 4, 16, 16, "tiles", true);
	private static Tile wall1 = new Tile(7, 5, 16, 16, "tiles", true);
	private static Tile wall2 = new Tile(7, 6, 16, 16, "tiles", true);
	private static Tile halfWall = new Tile(5, 5, 16, 16, "tiles", true);

	private static Tile lavaTile0 = new Tile(0, 0, 16, 16, "tiles", false);
	private static Tile lavaTile1 = new Tile(0, 1, 16, 16, "tiles", false);
	private static Tile lavaTile2 = new Tile(0, 2, 16, 16, "tiles", false);
	private static Tile lavaTile3 = new Tile(0, 3, 16, 16, "tiles", false);
	private static Tile lavaTile0a = new Tile(1, 0, 16, 16, "tiles", false);
	private static Tile lavaTile1a = new Tile(1, 1, 16, 16, "tiles", false);
	private static Tile lavaTile2a = new Tile(1, 2, 16, 16, "tiles", false);
	private static Tile lavaTile3a = new Tile(1, 3, 16, 16, "tiles", false);
	private static Tile lavaTile0b = new Tile(2, 0, 16, 16, "tiles", false);
	private static Tile lavaTile1b = new Tile(2, 1, 16, 16, "tiles", false);
	private static Tile lavaTile2b = new Tile(2, 2, 16, 16, "tiles", false);
	private static Tile lavaTile3b = new Tile(2, 3, 16, 16, "tiles", false);
	private static Tile lavaTile0c = new Tile(3, 0, 16, 16, "tiles", false);
	private static Tile lavaTile1c = new Tile(3, 1, 16, 16, "tiles", false);
	private static Tile lavaTile2c = new Tile(3, 2, 16, 16, "tiles", false);
	private static Tile lavaTile3c = new Tile(3, 3, 16, 16, "tiles", false);

	private static Tile voidTile0 = new Tile(4, 0, 16, 16, "tiles", false);
	private static Tile voidTileT = new Tile(4, 3, 16, 16, "tiles", false);
	private static Tile voidTileR = new Tile(4, 2, 16, 16, "tiles", false);
	private static Tile voidTileB = new Tile(4, 4, 16, 16, "tiles", false);
	private static Tile voidTileL = new Tile(4, 1, 16, 16, "tiles", false);
	private static Tile voidTileRT = new Tile(6, 4, 16, 16, "tiles", false);
	private static Tile voidTileRB = new Tile(6, 2, 16, 16, "tiles", false);
	private static Tile voidTileLT = new Tile(6, 5, 16, 16, "tiles", false);
	private static Tile voidTileLB = new Tile(6, 3, 16, 16, "tiles", false);
	private static Tile voidTileTB = new Tile(6, 1, 16, 16, "tiles", false);
	private static Tile voidTileLR = new Tile(6, 0, 16, 16, "tiles", false);
	private static Tile voidTileTRB = new Tile(5, 3, 16, 16, "tiles", false);
	private static Tile voidTileTLB = new Tile(5, 2, 16, 16, "tiles", false);
	private static Tile voidTileLTR = new Tile(5, 1, 16, 16, "tiles", false);
	private static Tile voidTileLBR = new Tile(5, 0, 16, 16, "tiles", false);
	private static Tile voidTile4 = new Tile(5, 4, 16, 16, "tiles", false);

	// Shop Tiles

	private static Tile blackTile = new Tile(1, 4, 16, 16, "tiles", true);

	private static Tile floorGreen = new Tile(11, 0, 16, 16, "tiles", false);
	private static Tile floorWood = new Tile(11, 1, 16, 16, "tiles", false);
	private static Tile storeWall = new Tile(13, 0, 16, 16, "tiles", true);
	
	private static Tile storeTPoutOn = new Tile(11, 2, 16, 16, "tiles", false);
	private static Tile storeTPoutOff = new Tile(11, 3, 16, 16, "tiles", false);

	private static Tile counterSide = new Tile(12, 0, 16, 16, "tiles", true);
	private static Tile counterTop = new Tile(12, 2, 16, 16, "tiles", true);
	private static Tile counterCornerOut = new Tile(12, 1, 16, 16, "tiles", true);
	private static Tile counterCornerIn = new Tile(12, 3, 16, 16, "tiles", true);

	public Level(int width, int height, int level) {
		this.width = width;
		this.height = height;
		this.level = level;
		tiles = new double[width * height];
		tilesSave = new double[width * height];
		store = new double[width * height];
		generateLevel();
	}

	public void loadStore() {
		tilesSave = tiles.clone();
		tiles = store.clone();
	}
	
	public void generateStore() {
		int yCount = 0, xCount = 0, i = 0;
		double[] store = 
			{ 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0,
		      8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.3, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.1, 9.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 9.1, 9.2, 9.2, 9.2, 9.2, 9.2, 9.3, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.1, 8.1, 8.1, 8.1, 9.0, 8.2, 8.2, 8.2, 8.2, 8.2, 8.2, 8.2, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.5, 8.0, 8.0,
		      8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0,
		      8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0 };
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (store[x + y * width] == 9.1 && store[(x + 2) + y * width] == 8.5 && yCount < 7) {
					if (random.nextInt(10) < 5) {
						yCount++;
						store[x + y * width] = 8.1;
						store[(x + 1) + y * width] = 8.1;
						store[x + (y + 1) * width] = 9.1;
						store[(x + 1) + (y + 1) * width] = 9.2;
					}
				}
				if (store[x + y * width] == 9.1 && store[x + (y + 2) * width] == 8.5 && xCount < 5) {
					if (random.nextInt(10) < 5) {
						xCount++;
						store[x + y * width] = 8.1;
						store[x + (y + 1) * width] = 8.1;
						store[(x + 1) + y * width] = 9.1;
						store[(x + 1) + (y + 1) * width] = 9.0;
					}
				}
				if (store[x + y * width] == 9.0 || store[x + y * width] == 9.2) {
					int r;
					shopSprite[i + 1] = x;
					shopSprite[i + 2] = y;
					
					switch (random.nextInt(9)) {
					case 1:
						r = random.nextInt(3);
						if (r == 0) shopSprite[i] = 1.0;
						if (r == 1) shopSprite[i] = 1.1;
						if (r == 2) shopSprite[i] = 1.2;
						break;
					case 2:
						r = random.nextInt(4);
						if (r == 0) shopSprite[i] = 2.0;
						if (r == 1) shopSprite[i] = 2.1;
						if (r == 2) shopSprite[i] = 2.2;
						if (r == 3) shopSprite[i] = 2.3;
						break;
					case 3:
						r = random.nextInt(3);
						if (r == 0) shopSprite[i] = 3.0;
						if (r == 1) shopSprite[i] = 3.1;
						if (r == 2) shopSprite[i] = 3.2;
						break;
					case 4:
						shopSprite[i] = 4.0;
						break;
					case 5:
						r = random.nextInt(3);
						if (r == 0) shopSprite[i] = 5.0;
						if (r == 1) shopSprite[i] = 5.1;
						if (r == 2) shopSprite[i] = 5.2;
						break;
					case 6:
						r = random.nextInt(2);
						if (r == 0) shopSprite[i] = 6.0;
						if (r == 1) shopSprite[i] = 6.1;
						break;
					case 7:
						shopSprite[i] = 7.0;
						break;
					case 8:
						r = random.nextInt(3);
						if (r == 0) shopSprite[i] = 8.0;
						if (r == 1) shopSprite[i] = 8.1;
						if (r == 2) shopSprite[i] = 8.2;
						break;
					}
					i += 3;
				}
			}
		}
		
		this.store = store.clone();
	}

	protected void generateLevel() {
	}

	public void render() {
		if (lavaCounter < 240) lavaCounter++;
		else
			lavaCounter = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!onStore) {
					if (getTileVal(x, y) == 3.2) setTileVal(x, y, 3.3);
				}
				if (!onStoreExit) {
					if (getTileVal(x, y) == 8.3) setTileVal(x, y, 8.4);
				}
				getTile(x, y).renderTile(x, y);
			}
		}
		
		if (Jump.isStore()) {
			renderStoreSprite();
		}
	}
	
	public void renderStoreSprite() {		
		Graphics.halfScale();
		for (int i = 0; i < shopSprite.length - 1; i +=3) {
			if (shopSprite[i] > 0) {
				int x = (int) ((shopSprite[i + 1] * 24) + 6);
				int y = (int) ((shopSprite[i + 2] * 24) + 6);

				if (shopSprite[i] == 1.0) Sprite.heartPlus.renderSprite(x, y, 0);
				if (shopSprite[i] == 1.1) {
					Sprite.heartPlus.renderSprite(x - 3, y - 3, 0);
					Sprite.heartPlus.renderSprite(x + 3, y + 3, 0);
				}
				if (shopSprite[i] == 1.2) {
					Sprite.heartPlus.renderSprite(x - 4, y - 4, 0);
					Sprite.heartPlus.renderSprite(x + 4, y - 4, 0);
					Sprite.heartPlus.renderSprite(x, y + 4, 0);
				}
				if (shopSprite[i] == 2.0) Sprite.heart.renderSprite(x, y, 0);
				if (shopSprite[i] == 2.1) {
					Sprite.heart.renderSprite(x - 3, y - 3, 0);
					Sprite.heart.renderSprite(x + 3, y + 3, 0);
				}
				if (shopSprite[i] == 2.2) {
					Sprite.heart.renderSprite(x - 4, y - 4, 0);
					Sprite.heart.renderSprite(x + 4, y - 4, 0);
					Sprite.heart.renderSprite(x, y + 4, 0);
				}
				if (shopSprite[i] == 2.3) {
					Graphics.fullScale();
					Sprite.heart.renderSprite((x / 1.5) - 1, (y / 1.5) - 1, 0);
					Graphics.halfScale();
				}
				if (shopSprite[i] == 3.0) Sprite.clock0.renderSprite(x, y, 0);
				if (shopSprite[i] == 3.1) Sprite.clock1.renderSprite(x, y, 0);
				if (shopSprite[i] == 3.2) Sprite.clock2.renderSprite(x, y, 0);
				if (shopSprite[i] == 4.0) Sprite.jetpack.renderSprite(x, y, 0);
				if (shopSprite[i] == 5.0) Sprite.fire0.renderSprite(x, y, 0);
				if (shopSprite[i] == 5.1) Sprite.fire1.renderSprite(x, y, 0);
				if (shopSprite[i] == 5.2) Sprite.fire2.renderSprite(x, y, 0);
				if (shopSprite[i] == 6.0) Sprite.gravity.renderSprite(x, y, 0);
				if (shopSprite[i] == 6.1) {
					Sprite.gravity.renderSprite(x - 4, y, 0);
					Sprite.gravity.renderSprite(x + 4, y, 0);
				}
				if (shopSprite[i] == 7.0) Sprite.chevron.renderSprite(x, y, 0);
				if (shopSprite[i] == 8.0) Sprite.nanobots0.renderSprite(x, y, 0);
				if (shopSprite[i] == 8.1) Sprite.nanobots1.renderSprite(x, y, 0);
				if (shopSprite[i] == 8.2) Sprite.nanobots2.renderSprite(x, y, 0);
			}
		}
		Graphics.fullScale();
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return nullTile;

		if (tiles[x + y * width] == 8.0) return blackTile;
		if (tiles[x + y * width] == 8.1) return floorGreen;
		if (tiles[x + y * width] == 8.2) return floorWood;
		if (tiles[x + y * width] == 8.5) return storeWall;

		if (tiles[x + y * width] == 8.3) return storeTPoutOn;
		if (tiles[x + y * width] == 8.4) return storeTPoutOff;

		if (tiles[x + y * width] == 9.0) return counterSide;
		if (tiles[x + y * width] == 9.1) return counterCornerOut;
		if (tiles[x + y * width] == 9.2) return counterTop;
		if (tiles[x + y * width] == 9.3) return counterCornerIn;

		if (tiles[x + y * width] == 2.0) return platTile;

		if (tiles[x + y * width] == 2.1) return platTileH10;
		if (tiles[x + y * width] == 2.2) return platTileH20;
		if (tiles[x + y * width] == 2.3) return platTileH30;

		if (tiles[x + y * width] == 2.5) return platTileC10;
		if (tiles[x + y * width] == 2.6) return platTileC30;
		if (tiles[x + y * width] == 2.7) return platTileC50;

		if (tiles[x + y * width] == 3.0) return platTileOn;
		if (tiles[x + y * width] == 3.1) return platTileOff;

		if (tiles[x + y * width] == 3.2) return storeTPOn;
		if (tiles[x + y * width] == 3.3) return storeTPOff;

		if (tiles[x + y * width] == 5.9) return greyTile;
		if (tiles[x + y * width] == 5.0) return wallTile;
		if (tiles[x + y * width] == 5.1) return halfWall;
		if (tiles[x + y * width] == 5.2) return wall0;
		if (tiles[x + y * width] == 5.3) return wall1;
		if (tiles[x + y * width] == 5.4) return wall2;

		if (tiles[x + y * width] == 8.0) return blackTile;

		if (hot) {
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 31) return
			// lavaTile0h;
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 61) return
			// lavaTile1h;
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 91) return
			// lavaTile2h;
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 121) return
			// lavaTile3h;
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 151) return
			// lavaTile2h;
			// if (tiles[x + y * width] == 0.0 && lavaCounter < 181) return
			// lavaTile1h;
		} else {
			if (tiles[x + y * width] == 0.0 && lavaCounter < 41) return lavaTile0;
			if (tiles[x + y * width] == 0.0 && lavaCounter < 81) return lavaTile1;
			if (tiles[x + y * width] == 0.0 && lavaCounter < 121) return lavaTile2;
			if (tiles[x + y * width] == 0.0 && lavaCounter < 161) return lavaTile3;
			if (tiles[x + y * width] == 0.0 && lavaCounter < 201) return lavaTile2;
			if (tiles[x + y * width] == 0.0 && lavaCounter < 241) return lavaTile1;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 41) return lavaTile0a;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 81) return lavaTile1a;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 121) return lavaTile2a;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 161) return lavaTile3a;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 201) return lavaTile2a;
			if (tiles[x + y * width] == 0.1 && lavaCounter < 241) return lavaTile1a;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 41) return lavaTile0b;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 81) return lavaTile1b;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 121) return lavaTile2b;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 161) return lavaTile3b;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 201) return lavaTile2b;
			if (tiles[x + y * width] == 0.2 && lavaCounter < 241) return lavaTile1b;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 41) return lavaTile0c;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 81) return lavaTile1c;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 121) return lavaTile2c;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 161) return lavaTile3c;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 201) return lavaTile2c;
			if (tiles[x + y * width] == 0.3 && lavaCounter < 241) return lavaTile1c;
		}
		if (x > 0 && x < width - 1 && y < height - 1 && y > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTile4;
		}
		if (x > 0 && x < width - 1 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9) return voidTileLBR;
		}
		if (x > 0 && x < width - 1 && y > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTileLTR;
		}
		if (x > 0 && y > 0 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTileTLB;
		}
		if (x < width - 1 && y > 0 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTileTRB;
		}
		if (x > 0 && x < width - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[(x + 1) + y * width] <= 0.9) return voidTileLR;
		}
		if (y > 0 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[x + (y - 1) * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9) return voidTileTB;
		}
		if (x > 0 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9) return voidTileLB;
		}
		if (x > 0 && y > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTileLT;
		}
		if (x < width - 1 && y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y + 1) * width] <= 0.9) return voidTileRB;
		}
		if (x < width - 1 && y > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[(x + 1) + y * width] <= 0.9 && tiles[x + (y - 1) * width] <= 0.9) return voidTileRT;
		}
		if (x > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[(x - 1) + y * width] <= 0.9) return voidTileL;
		}
		if (x < width - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[(x + 1) + y * width] <= 0.9) return voidTileR;
		}
		if (y > 0) {
			if (tiles[x + y * width] == 1.0 && tiles[x + (y - 1) * width] <= 0.9) return voidTileT;
		}
		if (y < height - 1) {
			if (tiles[x + y * width] == 1.0 && tiles[x + (y + 1) * width] <= 0.9) return voidTileB;
		}
		if (tiles[x + y * width] == 1.0) return voidTile0;

		return nullTile;
	}

	public double getTileVal(int x, int y) {
		return tiles[x + y * width];
	}

	public void setTileVal(int x, int y, double tile) {
		tiles[x + y * width] = tile;
	}

	public boolean getHot() {
		return hot;
	}
	
	public void closeStore() {
		store = tiles.clone();
		tiles = tilesSave.clone();
	}
}
