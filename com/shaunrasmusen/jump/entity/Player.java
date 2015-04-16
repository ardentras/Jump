package com.shaunrasmusen.jump.entity;

import java.util.Random;

import org.lwjgl.opengl.Display;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.effect.Effect;
import com.shaunrasmusen.jump.input.Keys;
import com.shaunrasmusen.jump.render.Graphics;
import com.shaunrasmusen.jump.render.texture.Sprite;

public class Player {
	public long systimeStart = System.currentTimeMillis();
	public short maxHealth = 150;
	public double health = maxHealth;
	public double startHealth;
	public double lavaDamage = 1.0;
	public int money = 10000;
	public double dist = 0.75;
	public boolean noclip = false;
	public boolean complete = false;
	public boolean voidAvoid = false;
	public double x, y, tempX = 0, tempY = 0;
	public int x0, x1, y0, y1;

	public Effect[] inventory = new Effect[6];
	public Effect[] inUse = new Effect[10];

	private Sprite sprite;
	private Keys input;
	private Random random = new Random();

	private double xa = 0, ya = 0;
	private int sightDist = 0;
	private int dir = 2;
	private long spaceTime;
	private long keyWait = 0;
	private int hits = 0;
	private int counter = 0;
	private int noclipWait = 30;
	private int anim, tileBreakC;
	public int idleTimer;
	private boolean charge = false;
	private boolean walking = false;
	public boolean lava = false;

	public Player(Keys input) {
		this.input = input;
		sprite = Sprite.playerb;
	}

	public void move(double xa, double ya) {
		x += xa;
		y += ya;
	}

	public void jump(double xa, double ya) {
		int countx = 0;
		int remx = (int) xa % 10;

		if (xa >= 10) countx++;
		if (xa >= 20) countx++;
		if (xa >= 30) countx++;
		if (xa >= 40) countx++;
		if (xa >= 50) countx++;
		if (xa >= 60) countx++;
		if (xa >= 70) countx++;
		if (xa >= 80) countx++;
		if (xa >= 90) countx++;

		int county = 0;
		int remy = (int) ya % 10;

		if (ya >= 10) county++;
		if (ya >= 20) county++;
		if (ya >= 30) county++;
		if (ya >= 40) county++;
		if (ya >= 50) county++;
		if (ya >= 60) county++;
		if (ya >= 70) county++;
		if (ya >= 80) county++;
		if (ya >= 90) county++;

		if (county == 0) county = countx;

		for (int anim = 0; anim <= county * 2; anim++) {
			if (anim % 2 == 0) {
				if (xa > 0) {
					if (anim == county - 1) xa = remx;
					else
						xa = 10;
				}
				if (ya > 0) {
					if (anim == county - 1) ya = remy;
					else
						ya = 10;
				}
				if (xa < 0) {
					if (anim == county - 1) xa = remx;
					else
						xa = -10;
				}
				if (ya < 0) {
					if (anim == county - 1) ya = remy;
					else
						ya = -10;
				}
				move(xa, ya);
			}

			render();
			Display.update();
			Display.sync(240);
		}

		if (Jump.jumpElec.playing()) Jump.jumpElec.stop();
	}

	public void tick(int screen) {
		keyWait++;
		for (int i = 0; i < inUse.length - 1; i++) {
			if (inUse[i] == null)
				break;
			if (inUse[i].isActive()) {
				int e = (int) Math.floor(inUse[i].id);

				if (e == 1) {
					if (health == maxHealth)
						health += inUse[i].buff;
				}
				if (e == 2) {
					if (inUse[i].id == 2.3) health = inUse[i].buff;
					else
						health += inUse[i].buff;
				}
				if (e == 3)
					Jump.timer += inUse[i].buff;
				if (e == 4)
					voidAvoid = true;
				if (e == 5)
					lavaDamage = inUse[i].buff;
				if (e == 6)
					sightDist = (int) inUse[i].buff;
				// TODO if (e == 7)
				if (e == 8) {
					if (health >= maxHealth) {
						inUse[i].setActive(false);
						inUse[i].timer++;
					} else {
						health += inUse[i].buff;
					}
				}

				inUse[i].timer--;

				if (inUse[i].timer == 0) {
					if (inUse[i].isToggleable()) {
						removeInvItem(inUse[i].getActiveSlot());
					}
					inUse[i] = null;
					for (int j = i + 1; j < inUse.length - 1; j++) {
						if (inUse[j] == null)
							continue;
						inUse[j - 1] = inUse[j];
						inUse[j] = null;
					}
				}
			}
		}

		if (keyWait > 10) {
			if (input.one) use(0);
			if (input.two) use(1);
			if (input.three) use(2);
			if (input.four) use(3);
			if (input.five) use(4);
			if (input.six) use(5);
		}

		x0 = (int) x + 5;
		x1 = (int) x + 11;
		y0 = (int) y + 14;
		y1 = (int) y + 14;

		if (screen == 0) {
			xa = 0;
			ya = 0;

			if (anim < 7500) anim++;
			else
				anim = 0;

			if (hits > 0) charge = true;

			// EDGE COLLISIONS MUTHAFUCKA!
			if (input.up && y0 - dist > 2) ya -= dist;
			if (input.right && x1 + dist < Jump.level.width * 16) xa += dist;
			if (input.down && y1 + dist < Jump.level.height * 16 - 2) ya += dist;
			if (input.left && x0 - dist > 26) xa -= dist;

			spaceTime = System.currentTimeMillis() - systimeStart;

			if (input.jump) {
				counter++;
				spaceTime = System.currentTimeMillis() - systimeStart;
				if (counter < 2) {
					if (!Jump.jumpElec.playing())
						Jump.jumpElec.playAt(1.0f, 0.9f * Jump.volume, (int) x, (int) y, 1);

					hits++;
					systimeStart = System.currentTimeMillis();

					// Jump power
					if (spaceTime < 1001)
						dist += (int) ((20 * ((1500 - spaceTime) / 900) / hits) + 16);
					if (dist > 16 * 6) dist = 16 * 6; // Sets cap on jump
														// distance
				}
			} else {
				counter = 0;
			}

			if (input.currKey != input.jumpBind || spaceTime > 1000) {
				if (dist > 1) systimeStart = System.currentTimeMillis() - 750;
				if (spaceTime > 1000) {
					if (Jump.jumpElec.playing()) Jump.jumpElec.stop();
					dist = 0.75;
				} else
					dist = 0;
				charge = false;
				hits = 0;
			}

			if (!noclip) Jump.level.checkCollisions(x, y, xa, ya, x0, x1, y0, y1);

			// Collisions w/ solid blocks
			if (Jump.level.getTile(x0 >> 4, (y0 - 1) >> 4).isSolid()) {
				if (ya < 0) ya = 0;
			}
			if (Jump.level.getTile((x0 - 1) >> 4, y0 >> 4).isSolid()) {
				if (xa < 0) xa = 0;
			}
			if (Jump.level.getTile(x1 >> 4, (y1 + 1) >> 4).isSolid()) {
				if (ya > 0) ya = 0;
			}
			if (Jump.level.getTile((x1 + 1) >> 4, y1 >> 4).isSolid()) {
				if (xa > 0) xa = 0;
			}

			if (input.noclip1 && input.noclip2 && input.noclip0 && noclipWait >= 30) {
				noclip = !noclip;
				noclipWait = 0;
			} else {
				if (noclipWait > 100000000) noclipWait = 30;
				noclipWait++;
			}

			if (Jump.keys.plus && Jump.volume < 1.0f && keyWait > 10) {
				Jump.volume += 0.1f;
				noclipWait = 0;
				Jump.ingame.stop();
			}
			if (Jump.keys.minus && Jump.volume > 0.0f && keyWait > 10) {
				Jump.volume -= 0.1f;
				noclipWait = 0;
				Jump.ingame.stop();
			}

			checkItems();

			if (input.tp) {
				if (Jump.level.tryTP(xa, x0, x1, y0, y1) == 3.0) {
					complete = true;
					teleportAnim();
					calcScore();
				}
				if (Jump.level.tryTP(xa, x0, x1, y0, y1) == 3.2) {
					teleportAnim();

					Jump.genStore();
					Jump.setStore(true);
				}
			}
		}
		if (screen == 1) {
			ya = 0;
			xa = 0;

			// EDGE COLLISIONS MUTHAFUCKA!
			if (input.up && y0 - 1 > 0) ya -= 0.75;
			if (input.right && x1 + 1 < Jump.level.width * 16) xa += 0.75;
			if (input.down && y1 + 1 < Jump.level.height * 16 - 1) ya += 0.75;
			if (input.left && x0 - 1 > 0) xa -= 0.75;

			if (anim < 7500) anim++;
			else
				anim = 0;

			Jump.store.checkCollisions(x, y, xa, ya, x0, x1, y0, y1);

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

			checkItems();

			if (input.tp) {
				if (Jump.store.tryTP(xa, x0, x1, y0, y1) == 8.4) {
					teleportAnim();
					Jump.closeStore();
					Jump.setStore(false);
				}
			}
		}

		if (xa != 0 || ya != 0) {
			if (xa > 1 || ya > 1) {
				jump(xa, ya);
			}

			if (ya < 2 && xa < 2) {
				move(xa, ya);
			}
			walking = true;
		} else {
			walking = false;
		}

		if (input.up) setDir(0);
		if (input.right) setDir(1);
		if (input.down) setDir(2);
		if (input.left) setDir(3);
	}

	public void render() {
		// TODO
		if (Jump.level.blindness) {
			tileBreakC++;
			for (int a = 1; a < (y1 >> 4) - sightDist; a++) {
				for (int b = 2; b < Jump.level.width; b++) {
					if (tileBreakC > 35) tileBreakC = 0;
					if (((Jump.level.getTileVal(b, a) >= 2.0 && Jump.level.getTileVal(b,
							a) < 3.0)
							|| (Jump.level.getTileVal(b, a) >= 3.0 && Jump.level.getTileVal(b, a)
							< 4.0) || Jump.level.getTileVal(b, a) < 0.9) && tileBreakC < 35) {
						if (tileBreakC >= 0 && tileBreakC < 5)
							Sprite.tileBreak0.renderSprite(b, a, 0);
						if (tileBreakC >= 5 && tileBreakC < 10)
							Sprite.tileBreak1.renderSprite(b, a, 0);
						if (tileBreakC >= 10 && tileBreakC < 15)
							Sprite.tileBreak2.renderSprite(b, a, 0);
						if (tileBreakC >= 15 && tileBreakC < 20)
							Sprite.tileBreak3.renderSprite(b, a, 0);
						if (tileBreakC >= 20 && tileBreakC < 25)
							Sprite.tileBreak4.renderSprite(b, a, 0);
						if (tileBreakC >= 25 && tileBreakC < 30)
							Sprite.tileBreak5.renderSprite(b, a, 0);
						if (tileBreakC >= 30 && tileBreakC < 35) {
							Jump.level.setTileVal(b, a, 1.0);
							Sprite.tileBreak6.renderSprite(b, a, 0);
						}
					}
				}
			}
			for (int b = 2; b < (((x0 + x1) / 2) >> 4) - sightDist; b++) {
				for (int a = 1; a < Jump.level.height; a++) {
					if (tileBreakC > 35) tileBreakC = 0;
					if (((Jump.level.getTileVal(b, a) >= 2.0 && Jump.level.getTileVal(b,
							a) < 3.0)
							|| (Jump.level.getTileVal(b, a) >= 3.0 && Jump.level.getTileVal(b, a)
							< 4.0) || Jump.level.getTileVal(b, a) < 0.9) && tileBreakC < 35) {
						if (tileBreakC >= 0 && tileBreakC < 5)
							Sprite.tileBreak0.renderSprite(b, a, 0);
						if (tileBreakC >= 5 && tileBreakC < 10)
							Sprite.tileBreak1.renderSprite(b, a, 0);
						if (tileBreakC >= 10 && tileBreakC < 15)
							Sprite.tileBreak2.renderSprite(b, a, 0);
						if (tileBreakC >= 15 && tileBreakC < 20)
							Sprite.tileBreak3.renderSprite(b, a, 0);
						if (tileBreakC >= 20 && tileBreakC < 25)
							Sprite.tileBreak4.renderSprite(b, a, 0);
						if (tileBreakC >= 25 && tileBreakC < 30)
							Sprite.tileBreak5.renderSprite(b, a, 0);
						if (tileBreakC >= 30 && tileBreakC < 35) {
							Jump.level.setTileVal(b, a, 1.0);
							Sprite.tileBreak6.renderSprite(b, a, 0);
						}
					}
				}
			}
		}

		int r = random.nextInt(100);

		if (getDir() == 0) {
			if (lava) sprite = Sprite.lplayerf;
			else
				sprite = Sprite.playerf;

			if (anim % 150 > 75 && idleTimer > 150) {
				if (lava) sprite = Sprite.lplayerfw;
				else
					sprite = Sprite.playerfw;
			}

			idleTimer++;

			if (walking) {
				idleTimer = 0;
				if (anim % 40 > 30) {
					if (lava) sprite = Sprite.lplayerf1;
					else
						sprite = Sprite.playerf1;
				} else if (anim % 40 > 20) {
					if (lava) sprite = Sprite.lplayerf2;
					else
						sprite = Sprite.playerf2;
				} else if (anim % 40 > 10) {
					if (lava) sprite = Sprite.lplayerf3;
					else
						sprite = Sprite.playerf3;
				} else {
					if (lava) sprite = Sprite.lplayerf;
					else
						sprite = Sprite.playerf;
				}
			}
			if (charge) {
				if (r < 20) {
					sprite = Sprite.playerfce;
				} else {
					sprite = Sprite.playerfc;
				}
			}
		}
		if (getDir() == 1) {
			if (lava) sprite = Sprite.lplayerr;
			else
				sprite = Sprite.playerr;

			if (anim % 150 > 75 && idleTimer > 150) {
				if (lava) sprite = Sprite.lplayerrw;
				else
					sprite = Sprite.playerrw;
			}

			idleTimer++;

			if (walking) {
				idleTimer = 0;
				if (anim % 40 > 30) {
					if (lava) sprite = Sprite.lplayerr1;
					else
						sprite = Sprite.playerr1;
				} else if (anim % 40 > 20) {
					if (lava) sprite = Sprite.lplayerr2;
					else
						sprite = Sprite.playerr2;
				} else if (anim % 40 > 10) {
					if (lava) sprite = Sprite.lplayerr3;
					else
						sprite = Sprite.playerr3;
				} else {
					if (lava) sprite = Sprite.lplayerr;
					else
						sprite = Sprite.playerr;
				}
			}
			if (charge) {
				if (r < 20) {
					sprite = Sprite.playerrce;
				} else {
					sprite = Sprite.playerrc;
				}
			}
		}
		if (getDir() == 2) {
			if (lava) sprite = Sprite.lplayerb;
			else
				sprite = Sprite.playerb;

			if (anim % 150 > 75 && idleTimer > 150) {
				if (lava) sprite = Sprite.lplayerbw;
				else
					sprite = Sprite.playerbw;
			}

			idleTimer++;

			if (walking) {
				idleTimer = 0;
				if (anim % 40 > 30) {
					if (lava) sprite = Sprite.lplayerb1;
					else
						sprite = Sprite.playerb1;
				} else if (anim % 40 > 20) {
					if (lava) sprite = Sprite.lplayerb2;
					else
						sprite = Sprite.playerb2;
				} else if (anim % 40 > 10) {
					if (lava) sprite = Sprite.lplayerb3;
					else
						sprite = Sprite.playerb3;
				} else {
					if (lava) sprite = Sprite.lplayerb;
					else
						sprite = Sprite.playerb;
				}
			}
			if (charge) {
				if (r < 20) {
					sprite = Sprite.playerbce;
				} else {
					sprite = Sprite.playerbc;
				}
			}
		}
		if (getDir() == 3) {
			if (lava) sprite = Sprite.lplayerl;
			else
				sprite = Sprite.playerl;

			if (anim % 150 > 75 && idleTimer > 150) {
				if (lava) sprite = Sprite.lplayerlw;
				else
					sprite = Sprite.playerlw;
			}

			idleTimer++;

			if (walking) {
				idleTimer = 0;
				if (anim % 40 > 30) {
					if (lava) sprite = Sprite.lplayerl1;
					else
						sprite = Sprite.playerl1;
				} else if (anim % 40 > 20) {
					if (lava) sprite = Sprite.lplayerl2;
					else
						sprite = Sprite.playerl2;
				} else if (anim % 40 > 10) {
					if (lava) sprite = Sprite.lplayerl3;
					else
						sprite = Sprite.playerl3;
				} else {
					if (lava) sprite = Sprite.lplayerl;
					else
						sprite = Sprite.playerl;
				}
			}
			if (charge) {
				if (r < 20) {
					sprite = Sprite.playerlce;
				} else {
					sprite = Sprite.playerlc;
				}
			}
		}

		sprite.renderSprite(x, y, 0);
		renderEffects();
		renderInv();

		// Render counter over player
		// for (int y = 0; y < Jump.level.height; y++) {
		// for (int x = 0; x < Jump.level.height; x++) {
		// if (Jump.level.getTileVal(x, y) >= 9.1 && Jump.level.getTileVal(x, y)
		// <= 9.2) Jump.level.getTile(x, y).renderTile(x, y);
		// }
		// }
	}

	public void renderEffects() {
		for (int i = 0; i < inUse.length - 1; i++) {
			if (inUse[i] != null) {
				inUse[i].getSprite().renderSprite(1, 10 * (i + 2), 0);
				Graphics.noScale();
				if (inUse[i].timer > 0 && inUse[i].isActive())
					Graphics.fonttiny.drawString(28, 30 * (i + 2) + 8, Integer.toString(inUse[i].timer / 60));
				if ((int) Math.floor(inUse[i].id) == 6)
					Graphics.fonttiny.drawString(28, 30 * (i + 2) - 5, "x" + (int) inUse[i].buff);
				Graphics.fullScale();
			}
		}
	}

	public void checkItems() {
		int xs = (((x0 + x1) / 2) >> 4), xr = (((x0 + x1) / 2) >> 4) + 1, ys = (y1 >> 4), yb = (y1 >> 4) + 1, epx = 0, epy = 0;

		for (int i = 0; i < Jump.store.item.length - 1; i++) {
			if (Jump.store.item[i] == null)
				continue;
			epx = Jump.store.item[i].getX();
			epy = Jump.store.item[i].getY();

			if (xr == epx && ys == epy && getDir() == 1) {
				Graphics.drawItemInfo(Jump.store.item[i]);
				if (input.tp && keyWait > 10) buy(Jump.store.item[i], i);
			}
			if (yb == epy && xs == epx && getDir() == 2) {
				Graphics.drawItemInfo(Jump.store.item[i]);
				if (input.tp && keyWait > 10) buy(Jump.store.item[i], i);
			}
		}
	}

	public void buy(Effect effect, int i) {
		keyWait = 0;
		int initMoney = money;
		boolean success = false;
		/**
		 * 2.0: +10hp 2.1: +25hp 2.2: +75hp 2.3: Full Health 3.0: +5s 3.1: +10s
		 * 3.2: +15s 4.0: Void-Avoidance 9000 5.0: Lava Protection 50%, 15s 5.1:
		 * Lava Protection 75%, 30s 5.2: Lava Protection 100%, 30s
		 */

		if (money >= Jump.store.item[i].cost) {
			money -= Jump.store.item[i].cost;
			success = true;
		}

		if (success) {
			int j = 0;
			while (j < inventory.length) {
				if (inventory[j] == null) {
					inventory[j] = effect;
					Jump.store.item[i] = null;
					break;
				}
				j++;
			}

			if (j >= inventory.length)
				money = initMoney;
		}
	}

	public void use(int i) {
		keyWait = 0;
		Effect effect = inventory[i];
		for (int j = 0; j < inUse.length - 1; j++) {
			if (inventory[i] == null)
				break;
			if (inUse[j] != null) {
				// Toggles on and off
				if (inUse[j].getActiveSlot() == i) {
					if (inUse[j].id == effect.id)
						inUse[j].setActive(!inUse[j].isActive());
					break;
				}
				// Replaces current effect with one of greater or equal strength
				if (inUse[j].id <= effect.id) {
					int as0 = inUse[j].getActiveSlot();
					removeInvItem(as0);

					inUse[j] = effect;
					inUse[j].setActive(true);
					inUse[j].setActiveSlot(as0);
					if (!inUse[j].isToggleable()) {
						removeInvItem(i);
					}
					break;
				}
				if (inUse[j].id > effect.id)
					break;
			} else {
				// Initializes new effect
				inUse[j] = effect;
				inUse[j].setActive(true);
				inUse[j].setActiveSlot(i);
				if (!inUse[j].isToggleable()) {
					removeInvItem(i);
				}
				break;
			}
		}
	}

	private void removeInvItem(int i) {
		inventory[i] = null;
		for (int k = i + 1; k < 6; k++) {
			inventory[k - 1] = inventory[k];
			inventory[k] = null;
		}
	}

	public void calcScore() {
		int time = Jump.timer;
		float addCoins = (float) ((health / 50) + (Jump.level.level / 10)) * (time / 120);
		money += addCoins;

		int addHealth = (int) (startHealth - health) / 2;
		if (addHealth > 0) health += addHealth;

		System.out.println("Coins: " + addCoins + ", Health: " + addHealth);
	}

	public void findTileCirc() {
		int diam = 2;
		int startx = (int) (this.x) >> 4;
		int starty = (int) (this.y) >> 4;
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
		this.x = (x << 4);
		this.y = (y << 4);
		voidAvoid = false;

		for (int i = 0; i < inUse.length - 1; i++) {
			if (inUse[i] == null)
				break;
			if (inUse[i].id == 4.0) {
				inUse[i] = null;
				for (int j = i + 1; j < inUse.length - 1; j++) {
					if (inUse[j] == null)
						continue;
					inUse[j - 1] = inUse[j];
					inUse[j] = null;
				}
			}
		}
	}

	public void renderInv() {
		Sprite.inventorySlot.renderSprite(0, 10, 0);
		Sprite.inventorySlot.renderSprite(0, 11, 0);
		Sprite.inventorySlot.renderSprite(0, 12, 0);
		Sprite.inventorySlot.renderSprite(0, 13, 0);
		Sprite.inventorySlot.renderSprite(0, 14, 0);
		Sprite.inventorySlot.renderSprite(0, 15, 0);

		Graphics.noScale();
		Graphics.fonttiny.drawString(3, 10 * 48 + 26, "1");
		Graphics.fonttiny.drawString(3, 11 * 48 + 26, "2");
		Graphics.fonttiny.drawString(3, 12 * 48 + 26, "3");
		Graphics.fonttiny.drawString(3, 13 * 48 + 26, "4");
		Graphics.fonttiny.drawString(3, 14 * 48 + 26, "5");
		Graphics.fonttiny.drawString(3, 15 * 48 + 26, "6");
		Graphics.fullScale();

		for (int i = 0; i < 6; i++) {
			if (inventory[i] != null)
				inventory[i].getSprite().renderSprite(4, (i + 10) * 16 + 4, 0);
		}
	}

	public void teleportAnim() {
		Jump.teleport.play(1.0f, 1.0f * Jump.volume);
		for (int i = 0; i < 35; i++) {
			if (i == 0) sprite = Sprite.playertp0;
			if (i == 4) sprite = Sprite.playertp1;
			if (i == 8) sprite = Sprite.playertp2;
			if (i == 12) sprite = Sprite.playertp3;
			if (i == 16) sprite = Sprite.playertp4;
			if (i == 24) sprite = Sprite.playertp5;
			if (i == 28) sprite = Sprite.playertp6;
			if (i == 32) sprite = Sprite.playertp7;

			if (Jump.isStore()) {
				Jump.store.render();
				Jump.shopkeeper.render();
				Graphics.renderShopInfo(money, health);
			}
			if (!Jump.isStore()) {
				Jump.level.render();
				Graphics.renderGameInfo((int) dist, health, money, (int) x, (int) y, Jump.level.level, Jump.timer / 60);
			}
			sprite.renderSprite(x, y, 0);
			renderEffects();
			renderInv();

			Display.update();
			Display.sync(60);
		}

		Graphics.fadeBlack();

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Jump.teleport.stop();
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
}
