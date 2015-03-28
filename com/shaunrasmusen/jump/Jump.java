package com.shaunrasmusen.jump;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.shaunrasmusen.jump.entity.Player;
import com.shaunrasmusen.jump.entity.Shopkeeper;
import com.shaunrasmusen.jump.input.Keys;
import com.shaunrasmusen.jump.level.Level;
import com.shaunrasmusen.jump.level.RandomLevel;
import com.shaunrasmusen.jump.render.Graphics;

public class Jump {

	public int height = 272;
	public int width = height + 16;
	public int scale = 3;
	public static int timer = 1860;
	public static float volume;
	
	public static Level level;
	public static Player player;
	public static Shopkeeper shopkeeper;
	public static Keys keys;
	public static Sound ingame, ingameChords, jumpElec, teleport;
	
	public static boolean timerDeath = false, lavaDeath = false, voidDeath = false;

	private int musicTimer = 5001;
	private int pausedWait = 0;
	private int lDefault = 1, l;
	private boolean reset = false;
	private boolean hot = false;
	
	private static boolean paused = false;
	private static boolean store = false;
	// TODO Remove var assignment
	private String playerName = "default";

	private Random random = new Random();
	private File file, dir;

	public void start() {
		createEnvironment();
		
//		Menu menu = new Menu();
//		menu.start();
		
		//TODO
		volume = 1.0f;
		try {
			ingame = new Sound("res/sounds/ingame.wav");
			ingameChords = new Sound("res/sounds/ingameChords.wav");
			jumpElec = new Sound("res/sounds/electric.wav");
			teleport = new Sound("res/sounds/teleport.wav");
		} catch (SlickException e) {
			System.out.println("Sound creation failed");
		}
		playerName += ".txt";

		dir = new File("./src/res/saves/");
		file = new File("./src/res/saves/" + playerName);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		if (!file.exists()) {
			l = 1;
			saveGame(file, true);
		}
		
		keys = new Keys();
		player = new Player(keys);

		loadGame();
		
		player.x = 31;
		player.y = 9;

		level = new RandomLevel(18, 17, l, hot);
		
		level.generateStore();
		
		while (!Display.isCloseRequested()) {
			if (!isPaused()) {
				musicTimer++;
			}
			
			if (isStore()) renderStore();
			else if (isPaused()) pause();
			else if (!Display.isActive()) pause();
			else
				renderGame();
			
			if (musicTimer > 1250 && !ingameChords.playing()) {
				int r = random.nextInt(5000);
				if (r == 0) {
					ingameChords.play(1.0f, 0.6f * volume);
					if (musicTimer < 7200) musicTimer = 7200;
				}
			}
			
			if (musicTimer > 9400 || !ingame.playing()) {
				musicTimer = 0;
				ingame.play(0.5f, 0.9f * volume);
			}

			if (reset || player.complete) {
				reGen();
			}

			Display.sync(60);
		}

		quitGame();
	}
	
	public static void genStore() {
		shopkeeper = new Shopkeeper();
		
		player.tempX = player.x;
		player.tempY = player.y;
		level.loadStore();
		player.x = 47;
		player.y = 42;
	}
	
	public static void renderStore() {
		level.render();
		
		keys.tick();
		player.tick(1);
		shopkeeper.tick();
		
		player.render();
		shopkeeper.render();
		Graphics.renderShopInfo(player.money, player.health);
		player.renderInv();
		
		Display.update();
	}
	
	public static void closeStore() {
		player.x = player.tempX;
		player.y = player.tempY;
		
		level.closeStore();
	}

	public void renderGame() {	
		keys.tick();
		player.tick(0);
		level.render();
		Graphics.renderGameInfo((int) player.dist, player.health, player.money, player.x, player.y, l, timer / 60);
		player.render();
		player.renderInv();

		if (level.tiles[level.width - 1 + (level.height - 1) * level.width] != 3.0) timer--;
		if (timer <= 0) {
			timerDeath = true;
			player.health = 0;
		}
		if (player.health == 0) reset = true;

		Display.update();
		
		if (pausedWait > 5 && keys.pause) {
			Graphics.fadeBlack();
			setPaused(true);
			pausedWait = 0;
		} else {
			if (pausedWait > 100000000) pausedWait = 21;
			else pausedWait++;
		}
	}

	public void reGen() {
		if (random.nextInt(100) == 1) hot = true;
		else
			hot = false;
		
		if (reset) {			
			int s = 0;

			if (voidDeath) s = 6;
			if (timerDeath) s = 1;
			if (lavaDeath) s = 2;
			
			timerDeath = false;
			lavaDeath = false;
			voidDeath = false;

			for (double i = 0; i < .05; i += 0.0005) {
				GL11.glColor4d(0, 0, 0, i);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2d(0, 0);
				GL11.glVertex2d(Display.getWidth(), 0);
				GL11.glVertex2d(Display.getWidth(), Display.getHeight());
				GL11.glVertex2d(0, Display.getWidth());
				GL11.glEnd();
				GL11.glLoadIdentity();
				level.render();
				Display.update();
			}

			GL11.glColor4d(1, 1, 1, 1);

			int lavaCounter = 0;
			
			player.health = (short) (player.maxHealth / 2);
			player.money = 0;

			while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				
				if (s == 2) {
					if (lavaCounter == 180) lavaCounter = 0;
					else
						lavaCounter++;

					if (lavaCounter < 31) s = 2;
					else if (lavaCounter < 61) s = 3;
					else if (lavaCounter < 91) s = 4;
					else if (lavaCounter < 121) s = 5;
					else if (lavaCounter < 151) s = 4;
					else if (lavaCounter < 181) s = 3;
				}

				Graphics.deathScreen(s);
				Display.update();

				if (Display.isCloseRequested()) {
					quitGame();
				}
				Display.sync(60);
			}
		}

		player.x = 31;
		player.y = 9;
		timer = 1860;
		if (player.complete) {
			try {
				l++;
				if ((l - 1) % 5 == 0) {
					lDefault = l;
					saveGame(file, false);
					Graphics.renderCheckpnt();
					Display.update();
					Thread.sleep(1500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (reset) l = lDefault;

		level = new RandomLevel(18, 17, l, hot);
		player.complete = false;
		reset = false;
		
		renderGame();
		GL11.glColor4d(1, 1, 1, 1);
		
		player.startHealth = player.health;
		
		level.generateStore();
	}

	public void createEnvironment() {
		Display.setTitle("Jump");
		
		try {
			Display.setDisplayMode(new DisplayMode(width * scale, height * scale));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Graphics.initGL(width, height, scale);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		Graphics.initFont();
	}

	public void loadGame() {
		Path file = FileSystems.getDefault().getPath("src", "res", "saves", playerName);
		Charset charset = Charset.forName("UTF-8");
		try {
			BufferedReader br = Files.newBufferedReader(file, charset);
			lDefault = Integer.parseInt(br.readLine());
			l = Integer.parseInt(br.readLine());
			player.health = Double.parseDouble(br.readLine());
			player.money = Integer.parseInt(br.readLine());
			for (int i = 0; i < 6; i++) {
				player.inventory[i] = Double.parseDouble(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveGame(File filePath, boolean firstSave) {
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
			
			if (firstSave) {
				write.write("1");
				write.newLine();
				write.write("1");
				write.newLine();
				write.write("150");
				write.newLine();
				write.write("0");
				for (int i = 0; i < 6; i++) {
					write.newLine();
					write.write("0");
				}
				write.close();
			} else {
				write.write(Integer.toString(lDefault));
				write.newLine();
				write.write(Integer.toString(l));
				write.newLine();
				write.write(Double.toString(player.health));
				write.newLine();
				write.write(Integer.toString(player.money));
				for (int i = 0; i < 6; i++) {
					write.newLine();
					write.write(Double.toString(player.inventory[i]));
				}
				write.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		ingameChords.stop();
		ingame.stop();
		keys.tick();
		
		if (pausedWait > 5 && keys.pause) {
			setPaused(false);
			pausedWait = 0;
		} else {
			if (pausedWait > 100000000) pausedWait = 21;
			else pausedWait++;
		}
		
		Graphics.loadSpritesheet("pause").bind();
		
		double width = Display.getWidth() / 1.6;
		double height = Display.getHeight() / 1.6;
		
		GL11.glColor4d(1, 1, 1, 1);
		
		GL11.glTranslated(-3, 0, 0);
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
		Display.update();
	}
	
	public void quitGame() {
		saveGame(file, false);
		Display.destroy();
		AL.destroy();
	}

	public static void main(String[] argv) {
		Jump jump = new Jump();
		jump.start();
	}

	public static boolean isStore() {
		return store;
	}

	public static void setStore(boolean store) {
		Jump.store = store;
	}
	
	public static boolean isPaused() {
		return paused;
	}
	
	public static void setPaused(boolean b) {
		Jump.paused = b;
	}
}
