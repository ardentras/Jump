package com.shaunrasmusen.jump.level;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.render.texture.Tile;

public class Level {

	public double[] tiles;
	public int width, height;
	public boolean blindness;
	public boolean onStore = false;

	public int level;
	protected boolean hot;

	private int lavaCounter = 0;

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

	public Level(int width, int height, int level) {
		this.width = width;
		this.height = height;
		this.level = level;
		tiles = new double[width * height];
		generateLevel();
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
				getTile(x, y).renderTile(x, y);
			}
		}
	}

	public void checkCollisions(double x, double y, double xa, double ya, int x0, int x1, int y0, int y1) {
		int money = Jump.player.money;
		double health = Jump.player.health;
		short maxHealth = Jump.player.maxHealth;

		// Lava
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) <= 0.9 || getTileVal((x1 - 3) >> 4, y1 >> 4) <= 0.9) {
			Jump.player.lava = true;
			health -= Jump.player.lavaDamage;

			if (health == 0) Jump.lavaDeath = true;
		} else {
			Jump.player.lava = false;
		}

		// Void
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 1.0 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 1.0) {
			if (Jump.player.voidAvoid) {
				findTileCirc();
			} else {
				health = 0;
				Jump.voidDeath = true;
			}
		}

		// Store Portal
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 3.3 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 3.3) {
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 3.2);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 3.2);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 3.2);

			onStore = true;
		}

		if (getTileVal((x0 + 3) >> 4, y1 >> 4) != 3.2
				|| getTileVal((x1 - 3) >> 4, y1 >> 4) != 3.2) {
			onStore = false;
		}

		// Goal Platform
		if ((getTileVal((x0 + 3) >> 4, y1 >> 4) == 3.1 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 3.1)) {
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 3.0);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 3.0);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 3.0);
		}

		// Health Tiles

		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.1 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.1) {
			if (health <= maxHealth - 10) health += 10;
			else if (health < maxHealth)
				health = maxHealth;

			if (health < maxHealth) {
				if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
				else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
				else
					setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
			}
		}
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.2 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.2) {
			if (health <= maxHealth - 20) health += 20;
			else if (health < maxHealth)
				health = maxHealth;

			if (health < maxHealth) {
				if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
				else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
				else
					setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
			}
		}
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.3 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.3) {
			if (health <= maxHealth - 30) health += 30;
			else if (health < maxHealth)
				health = maxHealth;

			if (health < maxHealth) {
				if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
				else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
				else
					setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
			}
		}

		// Money Tiles

		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.5 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.5) {
			money += 10;
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
		}
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.6 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.6) {
			money += 30;
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
		}
		if (getTileVal((x0 + 3) >> 4, y1 >> 4) == 2.7 || getTileVal((x1 - 3) >> 4, y1 >> 4) == 2.7) {
			money += 50;
			if (xa > 0) setTileVal((x1 - 3) >> 4, y1 >> 4, 2.0);
			else if (xa < 0) setTileVal((x0 + 3) >> 4, y1 >> 4, 2.0);
			else
				setTileVal(((x0 + x1) / 2) >> 4, y1 >> 4, 2.0);
		}

		// Collisions w/ solid blocks
		if (getTile(x0 >> 4, (y0 - 1) >> 4).isSolid()) {
			if (ya < 0) ya = 0;
		}
		if (getTile((x0 - 1) >> 4, y0 >> 4).isSolid()) {
			if (xa < 0) xa = 0;
		}
		if (getTile(x1 >> 4, (y1 + 1) >> 4).isSolid()) {
			if (ya > 0) ya = 0;
		}
		if (getTile((x1 + 1) >> 4, y1 >> 4).isSolid()) {
			if (xa > 0) xa = 0;
		}

		Jump.player.health = health;
		Jump.player.money = money;
	}

	public double tryTP(double xa, int x0, int x1, int y0, int y1) {
		if (xa > 0) return getTileVal((x1 - 3) >> 4, y1 >> 4);
		else if (xa < 0) return getTileVal((x0 + 3) >> 4, y1 >> 4);
		else
			return getTileVal(((x0 + x1) / 2) >> 4, y1 >> 4);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return nullTile;

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

	public void findTileCirc() {
		int diam = 2;
		int startx = (int) (Jump.player.x) >> 4;
		int starty = (int) (Jump.player.y) >> 4;
		int x = startx - 1;
		int y = starty - 1;
		if (x < 2) x = 2;
		if (y < 1) y = 1;

		while (Jump.level.getTileVal(x, y) > 4.0 || Jump.level.getTileVal(x, y) < 1.9) {
			if (x <= startx + diam) x++;
			if (x == startx + diam) {
				if (y <= starty + diam) y++;
				if (y == starty + diam) {
					diam += 2;
					x = startx - diam - 1;
					y = starty - diam - 1;
				} else {
					x -= diam - 1;
				}
			}
			if (x < 2) x = 2;
			if (y < 1) y = 1;
		}
		Jump.player.x = (x << 4);
		Jump.player.y = (y << 4);
		Jump.player.voidAvoid = false;
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
}
