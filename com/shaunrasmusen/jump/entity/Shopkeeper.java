package com.shaunrasmusen.jump.entity;

import java.util.Random;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.render.texture.Sprite;

public class Shopkeeper {

	public double x = 225, y = 200, xa, ya;
	public int x0, x1, y0, y1;

	Sprite sprite;
	Random random = new Random();

	private int dir = 2, moveDir = 0;
	private int moveTimer = 0, waitTimer = 0;
	private int anim;
	private boolean walking = false, playerNear = false;

	public Shopkeeper() {
		sprite = Sprite.shopkeeperb;
	}

	public void move(double xa, double ya) {
		if (ya > 0) setDir(2);
		if (ya < 0) setDir(0);
		if (xa > 0) setDir(1);
		if (xa < 0) setDir(3);

		x += xa;
		y += ya;
	}

	public void tick() {
		if (waitTimer > 0) {
			waitTimer--;
		} else if (moveTimer == 0) {
			moveDir = 4;
			waitTimer = random.nextInt(500);
		}

		if (moveTimer > 0) {
			moveTimer--;
		} else if (waitTimer == 0) {
			moveTimer = random.nextInt(500);
			moveDir = random.nextInt(4);
		}

		x0 = (int) x + 5;
		x1 = (int) x + 11;
		y0 = (int) y + 14;
		y1 = (int) y + 15;

		ya = 0;
		xa = 0;

		playerNear = false;

		if (y - Jump.player.y < 40 && y - Jump.player.y > 0) {
			if (x - Jump.player.x < 40 && x - Jump.player.x > 0) {
				playerNear = true;
				xa -= 0.5;
				ya -= 0.5;
			}
			if (Jump.player.x - x < 40 && Jump.player.x - x > 0) {
				playerNear = true;
				xa += 0.5;
				ya -= 0.5;
			}
		}
		if (Jump.player.y - y < 40 && Jump.player.y - y > 0) {
			if (x - Jump.player.x < 40 && x - Jump.player.x > 0) {
				playerNear = true;
				xa -= 0.5;
				ya += 0.5;
			}
			if (Jump.player.x - x < 40 && Jump.player.x - x > 0) {
				playerNear = true;
				xa += 0.5;
				ya += 0.5;
			}
		}
		
		if (!playerNear) {
			// EDGE COLLISIONS MUTHAFUCKA!
			if (y0 - 1 > 0 && moveDir == 0) ya -= 0.5;
			if (x1 + 1 < Jump.level.width * 16 && moveDir == 1) xa += 0.5;
			if (y1 + 1 < Jump.level.height * 16 && moveDir == 2) ya += 0.5;
			if (x0 - 1 > 0 && moveDir == 3) xa -= 0.5;
		}

		if (anim < 7500) anim++;
		else
			anim = 0;

		// Collisions w/ solid blocks
				if (Jump.store.getTile(x0 >> 4, (y0 - 1) >> 4).isSolid()) {
					if (ya < 0) ya = 0;
				}
				if (Jump.store.getTile((x0 - 1) >> 4, y0 >> 4).isSolid()) {
					if (xa < 0) xa = 0;
				}
				if (Jump.store.getTile(x1 >> 4, (y1 + 1) >> 4).isSolid()) {
					if (ya > 0) ya = 0;
				}
				if (Jump.store.getTile((x1 + 1) >> 4, y1 >> 4).isSolid()) {
					if (xa > 0) xa = 0;
				}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			if (playerNear) {
				
			}
			walking = false;
		}
	}

	public void render() {
		if (getDir() == 0) {
			sprite = Sprite.shopkeeperf;
			if (walking) {
				if (anim % 40 > 20) sprite = Sprite.shopkeeperf1;
				else
					sprite = Sprite.shopkeeperf2;
			}
		}
		if (getDir() == 1) {
			sprite = Sprite.shopkeeperr;
			if (walking) {
				if (anim % 40 > 20) sprite = Sprite.shopkeeperr1;
				else
					sprite = Sprite.shopkeeperr2;
			}
		}
		if (getDir() == 2) {
			sprite = Sprite.shopkeeperb;
			if (walking) {
				if (anim % 40 > 20)

				sprite = Sprite.shopkeeperb1;
				else
					sprite = Sprite.shopkeeperb2;
			}
		}
		if (getDir() == 3) {
			sprite = Sprite.shopkeeperl;
			if (walking) {
				if (anim % 40 > 20) sprite = Sprite.shopkeeperl1;
				else
					sprite = Sprite.shopkeeperl2;
			}
		}

		sprite.renderSprite((int) x, (int) y, 0);
		Sprite.info.renderSprite((int) x + 4, (int) y - 10, 0);
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int d) {
		this.dir = d;
	}
}
