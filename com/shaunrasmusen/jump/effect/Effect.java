package com.shaunrasmusen.jump.effect;

import com.shaunrasmusen.jump.render.texture.Sprite;

public class Effect {
	public int timer;
	public double buff;
	public int cost;
	public Sprite sprite;
	public double id;
	public String name;
	public String descr0;
	public String descr1;
	public String descr2;

	private boolean active;
	private boolean toggleable;
	private int x, y, activeSlot;

	public Effect() {
	}
	
	private Effect(int timer, double buff, int cost, Sprite sprite, double id, boolean active, boolean toggleable, String name, String descr0, String descr1, String descr2) {
		this.timer = (timer * 60) + 1;
		this.buff = buff;
		this.cost = cost;
		this.sprite = sprite;
		this.id = id;
		this.active = active;
		this.toggleable = toggleable;
		this.name = name;
		this.descr0 = descr0;
		this.descr1 = descr1;
		this.descr2 = descr2;
	}
	
	public Effect getEffect(double id) {
		if (id == 1.0) return new Effect(0, 25, 50, Sprite.heartPlus, 1.0, true, false,
				"Small Health Additive",
				"Adds 25hp temporarily to max health. Once lost, cannot be regenerated without re-purchase.",
				"(*Note: Only effective when already at max health)", null);
		if (id == 1.1) return new Effect(0, 50, 75, Sprite.heartPlus, 1.1, true, false,
				"Medium Health Additive",
				"Adds 50hp temporarily to max health. Once lost, cannot be regenerated without re-purchase.",
				"(*Note: Only effective when already at max health)", null);
		if (id == 1.2) return new Effect(0, 75, 100, Sprite.heartPlus, 1.2, true, false,
				"Small Health Additive",
				"Adds 25hp temporarily to max health. Once lost, cannot be regenerated without re-purchase.",
				"(*Note: Only effective when already at max health)", null);
		if (id == 2.0) return new Effect(0, 10, 25, Sprite.heart, 2.0, true, false,
				"1 Small Health Canister",
				"Gives you a single canister that instantly heals 10hp.", null, null);
		if (id == 2.1) return new Effect(0, 25, 50, Sprite.heart, 2.1, true, false,
				"2 Small Health Canisters, +50% bonus",
				"Gives you two canisters that instantly heal 10hp each, plus an added bonus of 5hp!", null, null);
		if (id == 2.2) return new Effect(0, 50, 100, Sprite.heart, 2.2, true, false,
				"3 Medium Health Canisters",
				"Denser health canisters that each instantly heal for 25hp.", null, null);
		if (id == 2.3) return new Effect(0, 150, 200, Sprite.heart, 2.3, true, false,
				"Large Health Canister",
				"Instantly heals you to full health.", null, null);
		if (id == 3.0) return new Effect(0, 5 * 60, 75, Sprite.clock0, 3.0, true, false,
				"Basic Time Manipulation Device",
				"Warps you 5 seconds into the past, while keeping your position constant.",
				"Can only be applied once per level to avoid unwanted space-time distortion.", null);
		if (id == 3.1) return new Effect(0, 10 * 60, 150, Sprite.clock1, 3.1, true, false,
				"Standard Time Manipulation Device",
				"Warps you 10 seconds into the past, while keeping your position constant.",
				"Can only be applied once per level to avoid unwanted space-time distortion.", null);
		if (id == 3.2) return new Effect(0, 15 * 60, 250, Sprite.clock2, 3.2, true, false,
				"Advance Time Manipulation Device",
				"Warps you 15 seconds into the past, while keeping your position constant.",
				"Can only be applied once per level to avoid unwanted space-time distortion.", null);
		if (id == 4.0) return new Effect(-1, 0, 300, Sprite.jetpack, 4.0, false, false,
				"The \"Void-Avoidance 9000\"",
				"Activates automatically by detecting distress with state-of-the-art technology.",
				"Pilots you to the nearest safe platform, thereby allowing you to narrowly avoid",
				"a most unfortunate death.");
		if (id == 5.0) return new Effect(15, 0.5, 100, Sprite.fire0, 5.0, false, true,
				"Basic Lava Protection",
				"Can be activated at any time in order to create protection from the heat of lava.",
				"Will reduce damage from lava by 50% for 15 seconds.", null);
		if (id == 5.1) return new Effect(30, 0.75, 250, Sprite.fire1, 5.1, false, true,
				"Standard Lava Protection",
				"Can be activated at any time in order to create protection from the heat of lava.",
				"Will reduce damage from lava by 75% for 30 seconds.", null);
		if (id == 5.2) return new Effect(30, 0, 400, Sprite.fire2, 5.2, false, true,
				"Advanced Lava Protection",
				"Can be activated at any time in order to create protection from the heat of lava.",
				"Grants complete immunity from lava for 30 seconds.", null);
		if (id == 6.0) return new Effect(30, 1, 200, Sprite.gravity, 6.0, false, true,
				"Standard Gravitational Field Enhancement",
				"Increases RoE of your gravitational field by one tile when laboratory gravity is decreased.",
				"Can be activated and deactivated at any time over 30 seconds.", null);
		if (id == 6.1) return new Effect(30, 3, 350, Sprite.gravity, 6.1, false, true,
				"Advanced Gravitational Field Enhancement",
				"Increases RoE of your gravitational field by three tiles when laboratory gravity is decreased.",
				"Can be activated and deactivated at any time over 30 seconds.", null);
		if (id == 7.0) return new Effect(0, 0, 750, Sprite.chevron, 7.0, false, false,
				"In-Level Teleport",
				"Will teleport the player a random distance forward upon activation.",
				"Guaranteed at least a three block diagonal teleport and safe arrival on a",
				"non-damaging tile.");
		if (id == 8.0) return new Effect(20, .04167, 100, Sprite.nanobots0, 8.0, false, true,
				"Box of 25,000 Medical Nano-bots",
				"Toggleable medical nano-bots that are capable of healing for a total of",
				"50hp. Slow working, lower quality bots that will complete the task in a",
				"minimum 20s, but these are offered at an unbeatable price.");
		if (id == 8.1) return new Effect(15, .11111, 300, Sprite.nanobots1, 8.1, false, true,
				"Box of 50,000 Medical Nano-bots",
				"Toggleable medical nano-bots that are capable of healing for a total of",
				"100hp. Faster working, standard medical bots that can heal within 15s.", null);
		if (id == 8.2) return new Effect(10, .16667, 500, Sprite.nanobots2, 8.2, false, true,
				"Box of 100,000 Medical Nano-bots",
				"Toggleable medical nano-bots that will fully regenerate 150hp over the span of a",
				"minimum 10s. Despite the high cost, they are very effective. Guaranteed you won't",
				"regret the purchase!");
		return null;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getActiveSlot() {
		return activeSlot;
	}
	
	public void setActiveSlot(int activeSlot) {
		this.activeSlot = activeSlot;
	}

	public boolean isToggleable() {
		return toggleable;
	}

	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}
}
