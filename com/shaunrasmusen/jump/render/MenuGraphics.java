package com.shaunrasmusen.jump.render;

import org.lwjgl.opengl.GL11;

public class MenuGraphics extends Graphics {

	public static void renderMenuBG() {
		loadSpritesheet("mainmenu").bind();
		
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
	
	public static void mainMenu() {
		
	}
}
