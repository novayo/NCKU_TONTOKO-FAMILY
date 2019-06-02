package com.main.item.tile.contra;

import java.awt.Graphics;

import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;
import com.main.item.tile.Tile;

public class Contra_Obstacle_Boss extends Tile {

	private Image contra_Obstacle_Boss[] = new Image[3];

	public Contra_Obstacle_Boss(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		ImageSheet tmpImageSheet = new ImageSheet("/Scenes/Contra_Monster0.png");
		contra_Obstacle_Boss[0] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 魂斗羅怪物0
		tmpImageSheet = new ImageSheet("/Scenes/Contra_Monster1.png");
		contra_Obstacle_Boss[1] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 魂斗羅怪物1
		tmpImageSheet = new ImageSheet("/Scenes/Contra_Monster2.png");
		contra_Obstacle_Boss[2] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 魂斗羅怪物2

		moveSpeed = moveSpeedfloor3;
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		sheetLength = contra_Obstacle_Boss.length;
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (animation % 3 == 0)
			g.drawImage(contra_Obstacle_Boss[0].getBufferedImage(), x, y, width, height, null);
		else if (animation % 3 == 1)
			g.drawImage(contra_Obstacle_Boss[1].getBufferedImage(), x, y, width, height, null);
		else if (animation % 3 == 2)
			g.drawImage(contra_Obstacle_Boss[2].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫

		doAnimation();
		doScoreCompute();
	}
	
	public void doAnimation() {
		animationDelay++;

		if (animationDelay >= animation_speed / 2) {
			animation++;
			if (animation >= sheetLength) {
				animation = 0;
			}
			animationDelay = 0;
		}
	}

}
