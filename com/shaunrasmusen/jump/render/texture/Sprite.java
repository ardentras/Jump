package com.shaunrasmusen.jump.render.texture;

import org.newdawn.slick.SpriteSheet;

import com.shaunrasmusen.jump.render.Graphics;

public class Sprite {
	
	public double x, y, width, height;
	public SpriteSheet sheet;
	
	public static Sprite playerf = new Sprite(0, 12, 16, 16, "player");
	public static Sprite playerb = new Sprite(2, 12, 16, 16, "player");
	public static Sprite playerl = new Sprite(3, 12, 16, 16, "player");
	public static Sprite playerr = new Sprite(1, 12, 16, 16, "player");

	public static Sprite playerf1 = new Sprite(0, 13, 16, 16, "player");
	public static Sprite playerb1 = new Sprite(2, 13, 16, 16, "player");
	public static Sprite playerl1 = new Sprite(3, 13, 16, 16, "player");
	public static Sprite playerr1 = new Sprite(1, 13, 16, 16, "player");
	
	public static Sprite playerf2 = new Sprite(0, 14, 16, 16, "player");
	public static Sprite playerb2 = new Sprite(2, 14, 16, 16, "player");
	public static Sprite playerl2 = new Sprite(3, 14, 16, 16, "player");
	public static Sprite playerr2 = new Sprite(1, 14, 16, 16, "player");

	public static Sprite playerf3 = new Sprite(0, 15, 16, 16, "player");
	public static Sprite playerb3 = new Sprite(2, 15, 16, 16, "player");
	public static Sprite playerl3 = new Sprite(3, 15, 16, 16, "player");
	public static Sprite playerr3 = new Sprite(1, 15, 16, 16, "player");
	
	public static Sprite playerfw = new Sprite(8, 12, 16, 16, "player");
	public static Sprite playerbw = new Sprite(10, 12, 16, 16, "player");
	public static Sprite playerlw = new Sprite(11, 12, 16, 16, "player");
	public static Sprite playerrw = new Sprite(9, 12, 16, 16, "player");
	
	public static Sprite lplayerf = new Sprite(4, 12, 16, 16, "player");
	public static Sprite lplayerb = new Sprite(6, 12, 16, 16, "player");
	public static Sprite lplayerl = new Sprite(7, 12, 16, 16, "player");
	public static Sprite lplayerr = new Sprite(5, 12, 16, 16, "player");

	public static Sprite lplayerf1 = new Sprite(4, 13, 16, 16, "player");
	public static Sprite lplayerb1 = new Sprite(6, 13, 16, 16, "player");
	public static Sprite lplayerl1 = new Sprite(7, 13, 16, 16, "player");
	public static Sprite lplayerr1 = new Sprite(5, 13, 16, 16, "player");
	
	public static Sprite lplayerf2 = new Sprite(4, 14, 16, 16, "player");
	public static Sprite lplayerb2 = new Sprite(6, 14, 16, 16, "player");
	public static Sprite lplayerl2 = new Sprite(7, 14, 16, 16, "player");
	public static Sprite lplayerr2 = new Sprite(5, 14, 16, 16, "player");

	public static Sprite lplayerf3 = new Sprite(4, 15, 16, 16, "player");
	public static Sprite lplayerb3 = new Sprite(6, 15, 16, 16, "player");
	public static Sprite lplayerl3 = new Sprite(7, 15, 16, 16, "player");
	public static Sprite lplayerr3 = new Sprite(5, 15, 16, 16, "player");
	
	public static Sprite lplayerfw = new Sprite(12, 12, 16, 16, "player");
	public static Sprite lplayerbw = new Sprite(14, 12, 16, 16, "player");
	public static Sprite lplayerlw = new Sprite(15, 12, 16, 16, "player");
	public static Sprite lplayerrw = new Sprite(13, 12, 16, 16, "player");

	public static Sprite playerfc = new Sprite(0, 11, 16, 16, "player");
	public static Sprite playerbc = new Sprite(2, 11, 16, 16, "player");
	public static Sprite playerlc = new Sprite(3, 11, 16, 16, "player");
	public static Sprite playerrc = new Sprite(1, 11, 16, 16, "player");

	public static Sprite playerfce = new Sprite(0, 10, 16, 16, "player");
	public static Sprite playerbce = new Sprite(2, 10, 16, 16, "player");
	public static Sprite playerlce = new Sprite(3, 10, 16, 16, "player");
	public static Sprite playerrce = new Sprite(1, 10, 16, 16, "player");
	
	public static Sprite playertp0 = new Sprite(4, 11, 16, 16, "player");
	public static Sprite playertp1 = new Sprite(5, 11, 16, 16, "player");
	public static Sprite playertp2 = new Sprite(6, 11, 16, 16, "player");
	public static Sprite playertp3 = new Sprite(7, 11, 16, 16, "player");
	public static Sprite playertp4 = new Sprite(7, 10, 16, 16, "player");
	public static Sprite playertp5 = new Sprite(6, 10, 16, 16, "player");
	public static Sprite playertp6 = new Sprite(5, 10, 16, 16, "player");
	public static Sprite playertp7 = new Sprite(4, 10, 16, 16, "player");
	
	// Shop Sprites
	
	public static Sprite shopkeeperf = new Sprite(0, 13, 16, 16, "shopkeeper");
	public static Sprite shopkeeperb = new Sprite(2, 13, 16, 16, "shopkeeper");
	public static Sprite shopkeeperl = new Sprite(3, 13, 16, 16, "shopkeeper");
	public static Sprite shopkeeperr = new Sprite(1, 13, 16, 16, "shopkeeper");

	public static Sprite shopkeeperf1 = new Sprite(0, 14, 16, 16, "shopkeeper");
	public static Sprite shopkeeperb1 = new Sprite(2, 14, 16, 16, "shopkeeper");
	public static Sprite shopkeeperl1 = new Sprite(3, 14, 16, 16, "shopkeeper");
	public static Sprite shopkeeperr1 = new Sprite(1, 14, 16, 16, "shopkeeper");

	public static Sprite shopkeeperf2 = new Sprite(0, 15, 16, 16, "shopkeeper");
	public static Sprite shopkeeperb2 = new Sprite(2, 15, 16, 16, "shopkeeper");
	public static Sprite shopkeeperl2 = new Sprite(3, 15, 16, 16, "shopkeeper");
	public static Sprite shopkeeperr2 = new Sprite(1, 15, 16, 16, "shopkeeper");
	
	// Misc. Sprites
	
	public static Sprite heart = new Sprite(1, 0, 8, 8, "sprites");
	public static Sprite heartPlus = new Sprite(1, 2, 8, 8, "sprites");
	public static Sprite clock0 = new Sprite(0, 1, 8, 8, "sprites");
	public static Sprite clock1 = new Sprite(0, 2, 8, 8, "sprites");
	public static Sprite clock2 = new Sprite(0, 3, 8, 8, "sprites");
	public static Sprite jetpack = new Sprite(2, 0, 8, 8, "sprites");
	
	public static Sprite fire0 = new Sprite (2, 1, 8, 8, "sprites");
	public static Sprite fire1 = new Sprite (2, 2, 8, 8, "sprites");
	public static Sprite fire2 = new Sprite (2, 3, 8, 8, "sprites");
	
	public static Sprite info = new Sprite(1, 1, 8, 8, "sprites");
	public static Sprite coin = new Sprite(0, 0, 8, 8, "sprites");
	
	public static Sprite gravity = new Sprite(0, 4, 8, 8, "sprites");
	public static Sprite chevron = new Sprite(1, 4, 8, 8, "sprites");
	public static Sprite nanobots0 = new Sprite(3, 1, 8, 8, "sprites");
	public static Sprite nanobots1 = new Sprite(3, 2, 8, 8, "sprites");
	public static Sprite nanobots2 = new Sprite(3, 3, 8, 8, "sprites");
	
	public static Sprite jumpBar0 = new Sprite(0, 6, 16, 16, "tiles");
	public static Sprite jumpBar1 = new Sprite(1, 6, 16, 16, "tiles");
	public static Sprite jumpBar2 = new Sprite(2, 6, 16, 16, "tiles");
	public static Sprite jumpBar3 = new Sprite(3, 6, 16, 16, "tiles");
	public static Sprite jumpBar4 = new Sprite(4, 6, 16, 16, "tiles");
	public static Sprite jumpBar5 = new Sprite(5, 6, 16, 16, "tiles");
	public static Sprite jumpBar6 = new Sprite(6, 6, 16, 16, "tiles");

	public static Sprite healthBar0 = new Sprite(0, 5, 16, 16, "tiles");
	public static Sprite healthBar1 = new Sprite(1, 5, 16, 16, "tiles");
	public static Sprite healthBar2 = new Sprite(2, 5, 16, 16, "tiles");
	public static Sprite healthBar3 = new Sprite(3, 5, 16, 16, "tiles");
	public static Sprite healthBar4 = new Sprite(4, 5, 16, 16, "tiles");
	
	public static Sprite inventorySlot = new Sprite(2, 4, 16, 16, "tiles");
	public static Sprite textBack = new Sprite(3, 4, 16, 16, "tiles");

	public static Sprite tileBreak0 = new Sprite(0, 7, 16, 16, "tiles");
	public static Sprite tileBreak1 = new Sprite(1, 7, 16, 16, "tiles");
	public static Sprite tileBreak2 = new Sprite(2, 7, 16, 16, "tiles");
	public static Sprite tileBreak3 = new Sprite(3, 7, 16, 16, "tiles");
	public static Sprite tileBreak4 = new Sprite(4, 7, 16, 16, "tiles");
	public static Sprite tileBreak5 = new Sprite(5, 7, 16, 16, "tiles");
	public static Sprite tileBreak6 = new Sprite(6, 7, 16, 16, "tiles");
	
	public Sprite(double x, double y, double width, double height, String sheet) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sheet = Graphics.loadSpritesheet(sheet);
	}
	
	public void renderSprite(double xOffset, double yOffset, double angle) {
		Graphics.render(x, y, width, height, xOffset, yOffset, sheet, angle);
	}
}
