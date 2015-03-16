package com.shaunrasmusen.jump.level;

import java.util.Random;

public class RandomLevel extends Level {

	private boolean storeTPExists = false;
	private static final Random random = new Random();
	private int b = 0, m, h, coin = 0, heart = 0;

	public RandomLevel(int width, int height, int level, boolean hot) {
		super(width, height, level);
		generateLevel();
	}
	
	protected void generateLevel() {
		storeTPExists = false;
		
		if (level <= 5) {
			blindness = false;
			coin = 400;
			m = 30;
			h = 65;
		} else if (level <= 10) {
			blindness = false;
			coin = 350;
			m = 40;
			h = 70;
		} else if (level <= 15) {
			blindness = false;
			coin = 350;
			m = 40;
			h = 70;
		} else if (level <= 20) {
			blindness = false;
			heart = 250;
			coin = 300;
			m = 45;
			h = 75;
		} else if (level <= 25) {
			blindness = true;
			heart = 225;
			coin = 250;
			m = 55;
			h = 75;
		} else if (level <= 30) {
			blindness = true;
			heart = 200;
			coin = 200;
			m = 65;
			h = 75;
		} else if (level <= 35) {
			blindness = false;
			heart = 200;
			coin = 200;
			m = 70;
			h = 80;
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				int r = random.nextInt(100 - b) + b;

				if (r < m) {
					tiles[x + y * width] = 1.0; // Void
				}
				if (hot) {
					if (r >= m && r < h) {
						tiles[x + y * width] = 0.0; // Hot Lava
					}
				} else {
					if (r >= m && r < h) {
						int r1 = random.nextInt(4);

						if (r1 == 0) tiles[x + y * width] = 0.0; // Lava
						if (r1 == 1) tiles[x + y * width] = 0.1;
						if (r1 == 2) tiles[x + y * width] = 0.2;
						if (r1 == 3) tiles[x + y * width] = 0.3;
					}
				}
				if (r >= h && r < 100) {
					tiles[x + y * width] = 2.0; // Platform
					if (heart > 0) {
						if (random.nextInt(heart) == 0) tiles[x + y * width] = 2.3; // H30
						if (random.nextInt(heart) < 2) tiles[x + y * width] = 2.2; // H20
						if (random.nextInt(heart) < 5) tiles[x + y * width] = 2.1; // H10
					}
					if (coin > 0) {
						if (level > 10) {
							if (random.nextInt(coin) == 0) tiles[x + y * width] = 2.7; // C50
						}
						if (random.nextInt(coin) < 3) tiles[x + y * width] = 2.6; // C30
						if (random.nextInt(coin) < 7) tiles[x + y * width] = 2.5; // C10
					}
					
					if (x > 1 && y > 0 && (level - 1) % 5 == 0 && random.nextInt(10) == 0 && !storeTPExists) {
						tiles[x + y * width] = 3.3;
						storeTPExists = true;
					}
				}

				if (y == 0) tiles[x + y * width] = 5.0; // Wall
				if (x == 0) tiles[x + y * width] = 5.9; // Blank Tile
				if (x == 1) tiles[x + y * width] = 5.1; // Half Wall
			}
		}
		
		tiles[14] = 5.2;
		tiles[15] = 5.3;
		tiles[16] = 5.3;
		tiles[17] = 5.4;

		tiles[2 + 1 * width] = 3.0; // Platform Start
		tiles[width - 1 + (height - 1) * width] = 3.1; // Platform End
	}
}
