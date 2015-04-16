package com.shaunrasmusen.jump.render;

import java.io.InputStream;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;

import com.shaunrasmusen.jump.Jump;
import com.shaunrasmusen.jump.effect.Effect;
import com.shaunrasmusen.jump.render.texture.Sprite;

public class Graphics {

	public static AngelCodeFont fonttiny, fontsmall, fontmed, fontbig;
	protected static int width, height, scale;
	public static int infoHelp = 0;

	public static void initFont() {
		try {
			fonttiny = new AngelCodeFont("src/res/textures/fonts/Jump14.fnt", new Image("src/res/textures/fonts/Jump14_0.png"));
			fontsmall = new AngelCodeFont("src/res/textures/fonts/Jump20.fnt", new Image("src/res/textures/fonts/Jump20_0.png"));
			fontmed = new AngelCodeFont("src/res/textures/fonts/Jump50.fnt", new Image("src/res/textures/fonts/Jump50_0.png"));
			fontbig = new AngelCodeFont("src/res/textures/fonts/Jump110.fnt", new Image("src/res/textures/fonts/Jump110_0.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void initGL(int width, int height, int scale) {
		Graphics.width = width;
		Graphics.height = height;
		Graphics.scale = scale;

		fullScale();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void render(double x, double y, double width, double height, double xOffset, double yOffset, SpriteSheet sheet, double angle) {
		sheet.bind();
		if (sheet.getName().equals("tiles")) {
			xOffset = (int) (xOffset) << 4;
			yOffset = (int) (yOffset) << 4;
		}

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		if (sheet.getName().equals("sprites")) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}
		
		GL11.glTranslated(xOffset, yOffset, 0);
		GL11.glRotated(angle,x,y,0.0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(x / width, y / height);
		GL11.glVertex2d(0, 0);
		GL11.glTexCoord2d((x + 1) / width, y / height);
		GL11.glVertex2d(width, 0);
		GL11.glTexCoord2d((x + 1) / width, (y + 1) / height);
		GL11.glVertex2d(width, height);
		GL11.glTexCoord2d(x / width, (y + 1) / height);
		GL11.glVertex2d(0, height);
		GL11.glEnd();
		GL11.glLoadIdentity();
	}

	public static void renderGameInfo(int dist, double health, int money, double x, double y, int l, int timer) {
		Sprite.jumpBar0.renderSprite(2, 0, 0);
		Sprite.jumpBar1.renderSprite(3, 0, 0);
		Sprite.jumpBar2.renderSprite(4, 0, 0);
		Sprite.jumpBar3.renderSprite(5, 0, 0);
		Sprite.jumpBar4.renderSprite(6, 0, 0);
		Sprite.jumpBar5.renderSprite(7, 0, 0);
		Sprite.jumpBar6.renderSprite(8, 0, 0);
		
		SpriteSheet sheet = loadSpritesheet("jumpbar");
		sheet.bind();
		GL11.glTranslated(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(40.5, 5);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(40.5 + Math.abs(dist - 1), 5);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(40.5 + Math.abs(dist - 1), 10.5);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(40.5, 10.5);
		GL11.glEnd();
		
		Sprite.healthBar0.renderSprite(9, 0, 0);
		Sprite.healthBar1.renderSprite(10, 0, 0);
		Sprite.healthBar2.renderSprite(11, 0, 0);
		Sprite.healthBar3.renderSprite(12, 0, 0);
		Sprite.healthBar4.renderSprite(13, 0, 0);
		
		double totalHealth = health;
		if (health > Jump.player.maxHealth) health = Jump.player.maxHealth;
		
		sheet = loadSpritesheet("healthbar");
		sheet.bind();
		GL11.glTranslated(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(146.5, 5);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(146.5 + health / 2, 5);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(146.5 + health / 2, 10);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(146.5, 10);
		GL11.glEnd();
		GL11.glLoadIdentity();

		noScale();
		
		if (totalHealth > health) {
			for (int i = 0; i < (int) (totalHealth - health) / 5; i++) {
				Sprite.heartPlus.renderSprite(442 + (i * 10), 38, 0);
			}
		}
		fontsmall.drawString(700, 8, "L: " + l + " | TIME: " + timer);
		fontsmall.drawString(3, Display.getHeight() - 50, "x:" + x);
		fontsmall.drawString(3, Display.getHeight() - 30, "y:" + y);
		fontsmall.drawString(1, 0, "Money");
		fontsmall.drawString(3, 1, "_____");
		fontsmall.drawString((30) - (fontsmall.getWidth(money + "cr") / 2), 22, money + "cr");
		
		if ((Jump.level.getTileVal((Jump.player.x0 + 3) >> 4, Jump.player.y1 >> 4) == 3.1 || Jump.level.getTileVal((Jump.player.x1 - 3) >> 4, Jump.player.y1 >> 4) == 3.1)) {
			infoHelp++;
			if (infoHelp > 250) {
				Graphics.fonttiny.drawString(Display.getWidth() - Graphics.fonttiny.getWidth("Press LShift to teleport "),
						(int) (y * 3) - 32, "Press LShift to teleport");
				Graphics.fonttiny.drawString(Display.getWidth() - Graphics.fonttiny.getWidth("to the next area."),
						(int) (y * 3) - 16, "to the next area.");
			}
		}

		fullScale();
	}

	public static void renderShopInfo(int money, double health) {
		Sprite.healthBar0.renderSprite(10, 15, 0);
		Sprite.healthBar1.renderSprite(11, 15, 0);
		Sprite.healthBar2.renderSprite(12, 15, 0);
		Sprite.healthBar3.renderSprite(13, 15, 0);
		Sprite.healthBar4.renderSprite(14, 15, 0);
		
		noScale();

		fontmed.drawString(108, 45, "Money:" + money + "cr");

		fullScale();

		double totalHealth = health;
		if (health > Jump.player.maxHealth) health = Jump.player.maxHealth;
		
		SpriteSheet sheet = Graphics.loadSpritesheet("healthbar");
		sheet.bind();
		GL11.glTranslated(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(162.5, 245);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(162.5 + health / 2, 245);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(162.5 + health / 2, 250);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(162.5, 250);
		GL11.glEnd();
		GL11.glLoadIdentity();
		
		if (totalHealth > health) {
			noScale();
			for (int i = 0; i < (int) (totalHealth - health) / 5; i++) {
				Sprite.heartPlus.renderSprite(500 + (i * 10), 721, 0);
			}
			fullScale();
		}
	}

	public static void renderCheckpnt() {
		noScale();
		String check = "Checkpoint Passed";
		fontbig.drawString((Display.getWidth() / 2) - (fontbig.getWidth(check) / 2), (Display.getHeight() / 3) - (fontbig.getHeight(check) / 2), check);
		fullScale();
	}

	public static void deathScreen(int s) {
		if (s == 0) loadSpritesheet("deathscreens/ds0").bind();
		if (s == 1) loadSpritesheet("deathscreens/timerds").bind();
		if (s == 2) loadSpritesheet("deathscreens/lavads0").bind();
		if (s == 3) loadSpritesheet("deathscreens/lavads1").bind();
		if (s == 4) loadSpritesheet("deathscreens/lavads2").bind();
		if (s == 5) loadSpritesheet("deathscreens/lavads3").bind();
		if (s == 6) loadSpritesheet("deathscreens/voidds").bind();

		double width = Graphics.width * 1.9;
		double height = Graphics.height * 1.9;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, 0);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(width, 0);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(width, height);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, height);
		GL11.glEnd();
		GL11.glLoadIdentity();
	}

	public static void fadeBlack() {
		for (double i = 0; i < .1; i += 0.0005) {
			GL11.glColor4d(0, 0, 0, i);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(0, 0);
			GL11.glVertex2d(Display.getWidth(), 0);
			GL11.glVertex2d(Display.getWidth(), Display.getHeight());
			GL11.glVertex2d(0, Display.getHeight());
			GL11.glEnd();
			GL11.glLoadIdentity();
			Jump.level.render();
			Display.update();
		}
	}

	public static void drawItemInfo(Effect effect) {
		noScale();
		fontsmall.drawString(100, 720, effect.name);
		fonttiny.drawString(100, 740, effect.cost + "cr");
		fonttiny.drawString(100, 756, effect.descr0);
		if (effect.descr1 != null)
			fonttiny.drawString(100, 772, effect.descr1);
		if (effect.descr2 != null)
			fonttiny.drawString(100, 788, effect.descr2);
		
		fullScale();
	}

	public static void fullScale() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public static void halfScale() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width * scale / 2, height * scale / 2, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public static void noScale() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width * scale, height * scale, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public static SpriteSheet loadSpritesheet(String sheet) {
		SpriteSheet spritesheet = null;
		InputStream ref = ResourceLoader.getResourceAsStream("./src/res/textures/" + sheet + ".png");
		try {
			spritesheet = new SpriteSheet(sheet, ref, 256, 256);
			spritesheet.setName(sheet);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		return spritesheet;
	}
}
