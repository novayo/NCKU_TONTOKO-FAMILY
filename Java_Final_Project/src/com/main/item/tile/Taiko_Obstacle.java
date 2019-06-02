package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;

public class Taiko_Obstacle extends Tile {

	public static Image taiko_Obstacle[] = new Image[2];
	private int whichObstacle = 0;

	public Taiko_Obstacle(Id id, Handler handler, int x, int y, int width, int height, int whichObstacle) {
		super(id, handler, x, y, width, height);

		this.whichObstacle = whichObstacle;

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		taiko_Obstacle[0] = new Image(Game.imageSheet, 6, 2, Id.GET_TAIKO_RED); // 飛龍0, whichObstacle = 0
		taiko_Obstacle[1] = new Image(Game.imageSheet, 7, 2, Id.GET_TAIKO_GREEN); // 飛龍1, whichObstacle = 0
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (isScoreAdd == true) {
			if (animation % 5 == 0) y--; 
			g.drawString("10x" + (Game.game_bonus - 1), 145, y);
		} else {
			if (whichObstacle == 0) {
				g.drawImage(taiko_Obstacle[0].getBufferedImage(), x, y, width, height, null);
			} else if (whichObstacle == 1) {
				g.drawImage(taiko_Obstacle[1].getBufferedImage(), x, y, width, height, null);
			}
		}
	}

	@Override
	public void update() {
		animation++;
		animation_speed = (60 / moveSpeedfloor2 > 0) ? 60 / moveSpeedfloor2 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		doAnimation();

		if (x <= 130) {
			if (isScoreAdd == false) Game.game_bonus = 1;
			die();
		}	
	}

	public void doScoreCompute() {
		if (220 >= x && x > 200) {
			Game.game_bonus = 1;
			die();
		} else if (200 >= x && x > 130 && isScoreAdd == false) {
			Game.game_score += 10 * Game.game_bonus;
			Game.game_bonus += 1;
			isScoreAdd = true;
		} else if (130 >= x) {
			Game.game_bonus = 1;
			die();
		}
	}
	
	public void die() {
		Game.handler.removeTile(this);
		if (Game.GAME_NOT_STARTED == false) {
			if (isScoreAdd == false) Game.life--;
			Game.numOfObstacles--; // 每死掉一個障礙物，就讓值-1
		}
		
		if (Game.life <= 0) {
			Game.heartObj.update(); // 更新死亡後的愛心
			Game.GAME_NOT_STARTED = true;
		}
	}

}
