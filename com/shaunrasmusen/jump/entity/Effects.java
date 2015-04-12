package com.shaunrasmusen.jump.entity;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.render.Graphics;
import com.shaunrasmusen.jump.render.texture.Sprite;

public class Effects {
	/** TODO
	 * 1. Random jump forward, very expensive, makes tile if void/lava beneath
	 * 3. 2x maximum jump distance 
	 */
	
	public int lavaDamageTimer = 0, sightTimer = 0, healTimer = 0;
	public int sightDist = 0, usingSight = -1, usingHeal = -1;
	private int keyWait = 0;
	public boolean runningSight = false;
	private boolean regen;
	private double heal = 0;
	
	public Effects() {	
	}
	
	public void tick() {
		if (regen)
			Jump.player.health += heal;
		
		if (lavaDamageTimer == 0) {
			Jump.player.lavaDamage = 1.0;
		} else {
			lavaDamageTimer--;
		}
		
		if (sightTimer <= 0 && usingSight > -1) {
			sightDist = 0;
			Jump.player.inventory[usingSight] = 0;
		}
		
		if (sightDist > 0)
			sightTimer--;
		
		if (healTimer <= 0 && usingHeal > -1) {
			heal = 0;
			Jump.player.inventory[usingHeal] = 0;
		}
		
		if (regen) {
			healTimer--;
		}
		
		keyWait++;
		
		// Yo, dude. This is where the side sprites are rendered, mmkay???
		if (Jump.player.voidAvoid) Sprite.jetpack.renderSprite(1, 10 * 2, 0);
		if (Jump.player.lavaDamage == 0.5)
			Sprite.fire0.renderSprite(1, 10 * 3, 0);
		if (Jump.player.lavaDamage == 0.25)
			Sprite.fire1.renderSprite(1, 10 * 3, 0);
		if (Jump.player.lavaDamage == 0)
			Sprite.fire2.renderSprite(1, 10 * 3, 0);
		if (lavaDamageTimer > 0) {
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 30 * 3 - 1, Integer.toString(lavaDamageTimer / 60));
			Graphics.fullScale();
		}
		if (sightTimer > 0) {
			Sprite.gravity.renderSprite(1, 10 * 4, 0);
			Graphics.noScale();
			if (sightDist > 0)
				Graphics.fonttiny.drawString(32, 30 * 4 - 8, "x" + sightDist);
			Graphics.fontsmall.drawString(28, 30 * 4 + 4, Integer.toString(sightTimer / 60));
			Graphics.fullScale();
		}
		if (heal == .04167)
			Sprite.nanobots0.renderSprite(1, 10 * 5, 0);
		if (heal == .11111)
			Sprite.nanobots1.renderSprite(1, 10 * 5, 0);
		if (heal == .16667)
			Sprite.nanobots2.renderSprite(1, 10 * 5, 0);
		if (healTimer > 0) {
			Graphics.noScale();
			Graphics.fontsmall.drawString(28, 30 * 5 - 1, Integer.toString(healTimer / 60));
			Graphics.fullScale();
		}
	}
	
	public void use(int i) {
		double e = Jump.player.inventory[i];
		if (e == 3.0) Jump.timer += 300;
		if (e == 3.1) Jump.timer += 600;
		if (e == 3.2) Jump.timer += 900;
		if (e == 4.0) Jump.player.voidAvoid = true;
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
		if ((e == 6.0 || e == 6.1) && sightDist > 0 && keyWait > 10) {
			sightDist = 0;
			keyWait = 0;
			usingSight = -1;
		}
		if ((e == 8.0 || e == 8.1 || e == 8.2) && regen && keyWait > 10) {
			regen = false;
			keyWait = 0;
			usingHeal = -1;
		}
		if (sightDist == 0 && sightTimer == 0 && keyWait > 10) {
			if (e == 6.0) {
				sightDist = 1;
				sightTimer = 30 * 60;
				usingSight = i;
			}
			if (e == 6.1) {
				sightDist = 3;
				sightTimer = 30 * 60;
				usingSight = i;
			}
		}
		if (!regen && healTimer == 0 && keyWait > 10) {
			if (e == 8.0) {
				regen = true;
				heal = .04167;
				healTimer = 20 * 60;
				usingHeal = i;
			}
			if (e == 8.1) {
				regen = true;
				heal = .11111;
				healTimer = 15 * 60;
				usingHeal = i;
			}
			if (e == 8.2) {
				regen = true;
				heal = .16667;
				healTimer = 10 * 60;
				usingHeal = i;
			}
		}
		if (keyWait > 10) {				
			if (e == 8.0) {
				regen = true;
				heal = .04167;
			}
			if (e == 8.1) {
				regen = true;
				heal = .11111;
			}
			if (e == 8.2) {
				regen = true;
				heal = .16667;
			}
			if (e == 6.0) {
				sightDist = 1;
			}
			if (e == 6.1) {
				sightDist = 3;
			}
			keyWait = 0;
		}
		
		if (e != 6.0 && e != 6.1 && e != 8.0 && e != 8.1 && e != 8.2)
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
		if (e == 6.0 && money > 199) {
			money -= 200;
			success = true;
		}
		if (e == 6.1 && money > 349) {
			money -= 350;
			success = true;
		}
		if (e == 8.0 && money > 99) {
			money -= 100;
			success = true;
		}
		if (e == 8.1 && money > 299) {
			money -= 300;
			success = true;
		}
		if (e == 8.2 && money > 499) {
			money -= 500;
			success = true;
		}
		if (e == 6.0 || e == 6.1) {
			for (int j = 0; j < 6; j++) {
				if (Jump.player.inventory[j] == 6.0 || Jump.player.inventory[j] == 6.1) {
					success = false;
					money = initMoney;
				}
			}
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
				Jump.store.storeSprite[i] = 0;
				Jump.store.storeSprite[i + 1] = -1;
				Jump.store.storeSprite[i + 2] = -1;
			}
		}
		Jump.player.money = money;
		Jump.player.health = health;
	}
}
