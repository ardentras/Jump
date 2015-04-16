package com.shaunrasmusen.jump.level;

import java.util.Random;

import com.shaunrasmusen.jump.effect.Effect;
import com.shaunrasmusen.jump.render.Graphics;
import com.shaunrasmusen.jump.render.texture.Tile;

public class Store {

	public int width, height;
	public double[] tiles;
	public Effect[] item = new Effect[14];
	public boolean onStoreExit = false, showBank = false;

	private Random random = new Random();
	private Effect effect;

	private static Tile nullTile = new Tile(15, 15, 16, 16, "tiles", false);

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

	public Store(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new double[width * height];
		effect = new Effect();
	}

	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!onStoreExit) {
					if (getTileVal(x, y) == 8.4) setTileVal(x, y, 8.3);
				}
				getTile(x, y).renderTile(x, y);
			}
		}

		renderSprite();
	}

	public void generateStore() {
		int yCount = 0, xCount = 0, i = 0;
		double[] tiles =
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
				if (tiles[x + y * width] == 9.1 && tiles[(x + 2) + y * width] == 8.5 && yCount < 7) {
					if (random.nextInt(10) < 5) {
						yCount++;
						tiles[x + y * width] = 8.1;
						tiles[(x + 1) + y * width] = 8.1;
						tiles[x + (y + 1) * width] = 9.1;
						tiles[(x + 1) + (y + 1) * width] = 9.2;
					}
				}
				if (tiles[x + y * width] == 9.1 && tiles[x + (y + 2) * width] == 8.5 && xCount < 5) {
					if (random.nextInt(10) < 5) {
						xCount++;
						tiles[x + y * width] = 8.1;
						tiles[x + (y + 1) * width] = 8.1;
						tiles[(x + 1) + y * width] = 9.1;
						tiles[(x + 1) + (y + 1) * width] = 9.0;
					}
				}
				if (tiles[x + y * width] == 9.0 || tiles[x + y * width] == 9.2) {
					int r;

					switch (random.nextInt(9)) {
					case 1:
						r = random.nextInt(3);
						if (r == 0) item[i] = effect.getEffect(1.0);
						if (r == 1) item[i] = effect.getEffect(1.1);
						if (r == 2) item[i] = effect.getEffect(1.2);
						break;
					case 2:
						r = random.nextInt(4);
						if (r == 0) item[i] = effect.getEffect(2.0);
						if (r == 1) item[i] = effect.getEffect(2.1);
						if (r == 2) item[i] = effect.getEffect(2.2);
						if (r == 3) item[i] = effect.getEffect(2.3);
						break;
					case 3:
						r = random.nextInt(3);
						if (r == 0) item[i] = effect.getEffect(3.0);
						if (r == 1) item[i] = effect.getEffect(3.1);
						if (r == 2) item[i] = effect.getEffect(3.2);
						break;
					case 4:
						item[i] = effect.getEffect(4.0);
						break;
					case 5:
						r = random.nextInt(3);
						if (r == 0) item[i] = effect.getEffect(5.0);
						if (r == 1) item[i] = effect.getEffect(5.1);
						if (r == 2) item[i] = effect.getEffect(5.2);
						break;
					case 6:
						r = random.nextInt(2);
						if (r == 0) item[i] = effect.getEffect(6.0);
						if (r == 1) item[i] = effect.getEffect(6.1);
						break;
					case 7:
						item[i] = effect.getEffect(7.0);
						break;
					case 8:
						r = random.nextInt(3);
						if (r == 0) item[i] = effect.getEffect(8.0);
						if (r == 1) item[i] = effect.getEffect(8.1);
						if (r == 2) item[i] = effect.getEffect(8.2);
						break;
					}

					if (item[i] != null) {
						item[i].setX(x);
						item[i].setY(y);
					}
					i++;
				}
			}
		}		
		
		if (random.nextInt(100) > 0)
			showBank = true;

		this.tiles = tiles.clone();
	}

	public void renderSprite() {
		Graphics.halfScale();
		for (int i = 0; i < item.length - 1; i++) {
			if (item[i] != null)
				item[i].getSprite().renderSprite(item[i].getX() * 24 + 7, item[i].getY() * 24 + 6, 0);
		}
		Graphics.fullScale();
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return nullTile;

		if (tiles[x + y * width] == 8.0) return blackTile;
		if (tiles[x + y * width] == 8.1) return floorGreen;
		if (tiles[x + y * width] == 8.2) return floorWood;
		if (tiles[x + y * width] == 8.5) return storeWall;

		if (tiles[x + y * width] == 8.3) return storeTPoutOff;
		if (tiles[x + y * width] == 8.4) return storeTPoutOn;

		if (tiles[x + y * width] == 9.0) return counterSide;
		if (tiles[x + y * width] == 9.1) return counterCornerOut;
		if (tiles[x + y * width] == 9.2) return counterTop;
		if (tiles[x + y * width] == 9.3) return counterCornerIn;

		return nullTile;
	}

	public void checkCollisions(double x, double y, double xa, double ya, int x0, int x1, int y0, int y1) {
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 8.3 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 8.3) {
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 8.4);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 8.4);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 8.4);

			onStoreExit = true;
		}
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) != 8.4 || getTileVal((x1 - 3) >> 4, y1 >> 4) != 8.4) {
			onStoreExit = false;
		}
	}

	public double tryTP(double xa, int x0, int x1, int y0, int y1) {
		if (xa > 0) return getTileVal((x1 - 3) >> 4, y1 >> 4);
		else if (xa < 0) return getTileVal((x0 + 3) >> 4, y1 >> 4);
		else
			return getTileVal(((x0 + x1) / 2) >> 4, y1 >> 4);
	}

	public double getTileVal(int x, int y) {
		return tiles[x + y * width];
	}

	public void setTileVal(int x, int y, double tile) {
		tiles[x + y * width] = tile;
	}

}
