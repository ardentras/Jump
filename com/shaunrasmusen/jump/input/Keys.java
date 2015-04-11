package com.shaunrasmusen.jump.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class Keys {
	public boolean up, left, down, right, jump, reset, tp, pause, one, two, three, four, five, six, plus, minus;
	public boolean noclip1, noclip2, noclip0;
	public int upBind, leftBind, downBind, rightBind, jumpBind, resetBind, tpBind, pauseBind;
	public int currKey = 0;
	public int heldKeys = 0;
	public int lastKey = 0;
	public char typedKey = ' ';

	public Keys() {
		File file = new File("./src/res/settings/keybinds.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			upBind = Integer.parseInt(br.readLine());
			leftBind = Integer.parseInt(br.readLine());
			downBind = Integer.parseInt(br.readLine());
			rightBind = Integer.parseInt(br.readLine());
			jumpBind = Integer.parseInt(br.readLine());
			resetBind = Integer.parseInt(br.readLine());
			tpBind = Integer.parseInt(br.readLine());
			pauseBind = Integer.parseInt(br.readLine());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		up = Keyboard.isKeyDown(upBind);
		left = Keyboard.isKeyDown(leftBind);
		down = Keyboard.isKeyDown(downBind);
		right = Keyboard.isKeyDown(rightBind);
		jump = Keyboard.isKeyDown(jumpBind);
		reset = Keyboard.isKeyDown(resetBind);
		tp = Keyboard.isKeyDown(tpBind);
		pause = Keyboard.isKeyDown(pauseBind);
		plus = Keyboard.isKeyDown(Keyboard.KEY_EQUALS);
		minus = Keyboard.isKeyDown(Keyboard.KEY_MINUS);
		
		if (!up && !left && !down && !right) {
			if (jump) currKey = jumpBind;
		} else {
			currKey = 1000;
		}
		
		one = Keyboard.isKeyDown(Keyboard.KEY_1);
		two = Keyboard.isKeyDown(Keyboard.KEY_2);
		three = Keyboard.isKeyDown(Keyboard.KEY_3);
		four = Keyboard.isKeyDown(Keyboard.KEY_4);
		five = Keyboard.isKeyDown(Keyboard.KEY_5);
		six = Keyboard.isKeyDown(Keyboard.KEY_6);

		// Shhhh..... don't tell anyone....
		noclip0 = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		noclip1 = Keyboard.isKeyDown(Keyboard.KEY_N);
		noclip2 = Keyboard.isKeyDown(Keyboard.KEY_0);
	}

	public boolean getReset() {
		return reset;
	}
}
