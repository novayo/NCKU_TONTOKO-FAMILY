package com.main.item.tile.tontoko;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.tile.Tile;

public class Tontoko_Obstacle extends Tile {

	private Image tontoko_Obstacle = null;
	private int tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
	
	public Tontoko_Obstacle(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		tontoko_Obstacle = new Image(Game.imageSheet, 7, 4, Id.GET_TONTOKO_OBSTACLE); // 石頭
		moveSpeed = moveSpeedfloor1;
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		sheetLength = 2;
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		g.drawImage(tontoko_Obstacle.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeed += 1;
		}
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		
		doAnimation();
		doScoreCompute();

		if (x <= -width)
			die();
	}

}
