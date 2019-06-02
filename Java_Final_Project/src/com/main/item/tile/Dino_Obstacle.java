package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;

public class Dino_Obstacle extends Tile {

	public static Image dino_Obstacle[] = new Image[3];
	private int whichObstacle = 0;
	
	public Dino_Obstacle(Id id, Handler handler, int x, int y, int width, int height, int whichObstacle) {
		super(id, handler, x, y, width, height);
		
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		dino_Obstacle[0] = new Image(Game.imageSheet, 6, 1, Id.GET_DINO_DRAGON_0); // 飛龍0, whichObstacle = 0
		dino_Obstacle[1] = new Image(Game.imageSheet, 7, 1, Id.GET_DINO_DRAGON_1); // 飛龍1, whichObstacle = 0
		dino_Obstacle[2] = new Image(Game.imageSheet, 8, 1, Id.GET_ONE_OF_SHEET); // 仙人掌0, whichObstacle = 1
		
		this.whichObstacle = whichObstacle;
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		sheetLength = 2;
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (whichObstacle == 0) {
			if (animation % 2 == 0) g.drawImage(dino_Obstacle[0].getBufferedImage(), x, y, width, height, null);
			else if (animation % 2 == 1) g.drawImage(dino_Obstacle[1].getBufferedImage(), x, y, width, height, null);
		} else if (whichObstacle == 1) {
			g.drawImage(dino_Obstacle[2].getBufferedImage(), x, y, width, height, null);
		}
	}

	@Override
	public void update() {
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		
		doAnimation();
		doScoreCompute();

		if (x <= -width)
			die();
	}

}
