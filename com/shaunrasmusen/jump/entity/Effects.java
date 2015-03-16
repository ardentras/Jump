package com.shaunrasmusen.jump.entity;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.render.Graphics;
import com.shaunrasmusen.jump.render.texture.Sprite;

public class Effects {
	/** TODO
	 * 1. Random jump forward, very expensive, makes tile if void/lava beneath
	 * 3. 2x maximum jump distance 
	 * 4. Passive health regeneration (Nano-bots)
	 * 5. Larger "sight" distance with blindness (Vision Enhancement)
	 */
	
	public int lavaDamageTimer = 0, sightTimer = 0;
	public int sightDist = 0;
	public boolean voidAvoid = false;
	
	public Effects() {	
	}
	
	public void tick() {
		if (voidAvoid) Sprite.jetpack.renderSprite(1, 20, 0);
		if (Jump.player.lavaDamage == 0.5) {
			Sprite.fire0.renderSprite(1, 30, 0);
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 31 * 3, Integer.toString(lavaDamageTimer / 60));
			Graphics.fullScale();
		}
		if (Jump.player.lavaDamage == 0.25) {
			Sprite.fire1.renderSprite(1, 30, 0);
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 31 * 3, Integer.toString(lavaDamageTimer / 60));
			Graphics.fullScale();
		}
		if (Jump.player.lavaDamage == 0) {
			Sprite.fire2.renderSprite(1, 30, 0);
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 31 * 3, Integer.toString(lavaDamageTimer / 60));
			Graphics.fullScale();
		}
		if (sightDist > 0) {
			Sprite.sight.renderSprite(1, 40, 0);
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 41 * 3, Integer.toString(sightTimer / 60));
			Graphics.fullScale();
		}
	}
	
	public void use(int i) {
		double e = Jump.player.inventory[i];
		if (e == 3.0) Jump.timer += 300;
		if (e == 3.1) Jump.timer += 600;
		if (e == 3.2) Jump.timer += 900;
		if (e == 4.0) voidAvoid = true;
		if (e == 5.0) {
			Jump.player.lavaDamage = 0.5;
			lavaDamageTimer = 15 * 60;
		}
		if (e == 5.1) {
			Jump.player.lavaDamage = 0.25;
			lavaDamageTimer = 30 * 60;
		}
		if (e == 5.2) {
			Jump.player.lavaDamage = 0;
			lavaDamageTimer = 30 * 60;
		}
		if (e == 6.0) {
			sightDist = 1;
			sightTimer = 30 * 60;
		}
		if (e == 6.1) {
			sightDist = 3;
			sightTimer = 30 * 60;
		}
		
		Jump.player.inventory[i] = 0;
//		for (int j = 0; j < Jump.player.inventory.length - 1; j++) {
//			if (Jump.player.inventory[j] == 0) {
//				Jump.player.inventory[j] = Jump.player.inventory[j + 1];
//				Jump.player.inventory[j + 1] = 0;
//			}
//		}
	}
	
	public void buy(double e, int i, int money, double health, int maxHealth) {
		int initMoney = money;
		boolean success = false;
		/**
		 * 2.0: +10hp 2.1: +25hp 2.2: +75hp 2.3: Full Health 3.0: +5s 3.1: +10s
		 * 3.2: +15s 4.0: Void-Avoidance 9000 5.0: Lava Protection 50%, 15s 5.1:
		 * Lava Protection 75%, 30s 5.2: Lava Protection 100%, 30s
		 */

		if (e == 1.0 && money > 49 && health == maxHealth) {
			money -= 50;
			health += 25;
			success = true;
		}
		if (e == 1.1 && money > 74 && health == maxHealth) {
			money -= 75;
			health += 50;
			success = true;
		}
		if (e == 1.2 && money > 99 && health == maxHealth) {
			money -= 100;
			health += 75;
			success = true;
		}
		if (e == 2.0 && money > 24) {
			if (health + 10 > maxHealth) health = maxHealth;
			else
				health += 10;
			money -= 25;
			success = true;
		}
		if (e == 2.1 && money > 49) {
			if (health + 25 > maxHealth) health = maxHealth;
			else
				health += 25;
			money -= 50;
			success = true;
		}
		if (e == 2.2 && money > 99) {
			if (health + 75 > maxHealth) health = maxHealth;
			else
				health += 75;
			money -= 100;
			success = true;
		}
		if (e == 2.3 && money > 199) {
			health = maxHealth;
			money -= 200;
			success = true;
		}
		if (e == 3.0 && money > 74) {
			money -= 75;
			success = true;
		}
		if (e == 3.1 && money > 149) {
			money -= 150;
			success = true;
		}
		if (e == 3.2 && money > 249) {
			money -= 250;
			success = true;
		}
		if (e == 4.0 && money > 299) {
			money -= 300;
			success = true;
		}
		if (e == 5.0 && money > 99) {
			money -= 100;
			success = true;
		}
		if (e == 5.1 && money > 249) {
			money -= 250;
			success = true;
		}
		if (e == 5.2 && money > 399) {
			money -= 400;
			success = true;
		}
		if (e == 6.0 && money > -1) {
			money -= 0;
			success = true;
		}
		if (e == 6.1 && money > -1) {
			money -= 0;
			success = true;
		}

		if (success) {
			int j = 0;
			while (j < 6) {
				if (Jump.player.inventory[j] == 0) break;
				j++;
			}
			
			if (j > 5) {
				money = initMoney;
				Jump.player.inventoryFull = true;
			} else {
				if (e >= 3) Jump.player.inventory[j] = e;
				Jump.level.shopSprite[i] = 0;
				Jump.level.shopSprite[i + 1] = -1;
				Jump.level.shopSprite[i + 2] = -1;
			}
		}
		Jump.player.money = money;
		Jump.player.health = health;
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
		voidAvoid = false;
	}
}
