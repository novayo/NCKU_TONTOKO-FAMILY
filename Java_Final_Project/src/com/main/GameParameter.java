package com.main;

import java.awt.Graphics;

public interface GameParameter {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final int FLOOR_WIDTH = WIDTH / 2; // 地板長度
	public static final int FLOOR_HEIGHT = 32; // 地板長度
	
	public static final int INIT_LIVES = 3; // 初始生命
	public static final String TITLE_STRING = "Java Project Demo";
	public static int totalObstacles = 100; // 障礙物總數
	public static int difficulty = 15; // 過了幾個障礙物時，難度增加
	public static int spriteSize = 48;
	public abstract void render(Graphics g);
	public abstract void update();
}
