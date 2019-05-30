package com.main;

import java.awt.Graphics;

public interface GameParameter {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final int FLOOR_WIDTH = WIDTH / 2;
	public static final String TITLE_STRING = "Java Project Demo";
	public abstract void render(Graphics g);
	public abstract void update();
}
