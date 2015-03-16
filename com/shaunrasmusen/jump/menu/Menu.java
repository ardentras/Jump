package com.shaunrasmusen.jump.menu;

import org.lwjgl.opengl.Display;

import com.shaunrasmusen.jump.render.MenuGraphics;

public class Menu {

	private boolean inMenu = true;

	public void start() {

		while (inMenu) {
			MenuGraphics.renderMenuBG();
			
			if (Display.isCloseRequested()) System.exit(0);
			
			Display.update();
			Display.sync(60);
		}
	}
}
