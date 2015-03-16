package com.shaunrasmusen.jump.render.texture;

import org.newdawn.slick.SpriteSheet;

import com.shaunrasmusen.jump.render.Graphics;

public class Tile {

	private boolean solid;
	private double x, y, width, height;
	private SpriteSheet sheet;

	public Tile(double x, double y, double width, double height, String sheet, boolean solid) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sheet = Graphics.loadSpritesheet(sheet);
		this.solid = solid;
	}

	public void renderTile(int xOffset, int yOffset) {
		Graphics.render(x, y, width, height, xOffset, yOffset, sheet, 0);
	}
	
	public boolean isSolid() {
		return solid;
	}
}
