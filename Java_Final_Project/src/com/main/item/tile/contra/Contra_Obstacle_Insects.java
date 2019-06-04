package com.main.item.tile.contra;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.tile.Tile;

public class Contra_Obstacle_Insects extends Tile {

	private Image contra_Obstacle_Insects[] = new Image[4];

	public Contra_Obstacle_Insects(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		contra_Obstacle_Insects[0] = new Image(Game.imageSheet, 9, 3, Id.GET_CONTRA_INSECTS); // 昆蟲0
		contra_Obstacle_Insects[1] = new Image(Game.imageSheet, 10, 3, Id.GET_CONTRA_INSECTS); // 昆蟲1
		contra_Obstacle_Insects[2] = new Image(Game.imageSheet, 11, 3, Id.GET_CONTRA_INSECTS); // 昆蟲2
		contra_Obstacle_Insects[3] = new Image(Game.imageSheet, 12, 3, Id.GET_CONTRA_INSECTS); // 昆蟲3

		moveSpeed = moveSpeedfloor3;
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		sheetLength = contra_Obstacle_Insects.length;
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (animation % 4 == 0)
			g.drawImage(contra_Obstacle_Insects[0].getBufferedImage(), x, y, width, height, null);
		else if (animation % 4 == 1)
			g.drawImage(contra_Obstacle_Insects[1].getBufferedImage(), x, y, width, height, null);
		else if (animation % 4 == 2)
			g.drawImage(contra_Obstacle_Insects[2].getBufferedImage(), x, y, width, height, null);
		else if (animation % 4 == 3)
			g.drawImage(contra_Obstacle_Insects[3].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeed += 1;
		}
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (1/animation_speed) 秒 變換一次動畫

		doAnimation();

		if (x <= -width)
			die();
	}

	public void doAnimation() {
		animationDelay++;
		x -= moveSpeed;

		if (animationDelay >= animation_speed / 2) {
			animation++;
			if (animation >= sheetLength) {
				animation = 0;
			}
			animationDelay = 0;
		}
	}

}
