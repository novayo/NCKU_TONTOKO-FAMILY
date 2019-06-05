package com.main.item.tile.taiko;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.tile.AddScore;
import com.main.item.tile.Tile;

public class Taiko_Obstacle extends Tile {

	private Image taiko_Obstacle[] = new Image[2];
	private int whichObstacle = 0;

	public Taiko_Obstacle(Id id, Handler handler, int x, int y, int width, int height, int whichObstacle) {
		super(id, handler, x, y, width, height);

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		taiko_Obstacle[0] = new Image(Game.imageSheet, 1, 2, Id.GET_TAIKO_RED); // 紅音符
		taiko_Obstacle[1] = new Image(Game.imageSheet, 2, 2, Id.GET_TAIKO_GREEN); // 藍音符

		this.whichObstacle = whichObstacle;
		moveSpeed = moveSpeedfloor2;
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (whichObstacle == 0) {
			g.drawImage(taiko_Obstacle[0].getBufferedImage(), x, y, width, height, null);
		} else if (whichObstacle == 1) {
			g.drawImage(taiko_Obstacle[1].getBufferedImage(), x, y, width, height, null);
		}

	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeed += 1;
		}
		animation++;
		animation_speed = (60 / moveSpeed > 0) ? 60 / moveSpeed : 1; // 每 (animation_speed/60) 秒 變換一次動畫
		doAnimation();

		if (x <= 130) {
			Game.game_bonus = 1;
			die();
		}
	}

	public void doScoreCompute() {
		if (220 >= x && x > 200) {
			die();
		} else if (200 >= x && x > 130) {
			@SuppressWarnings("unused")
			AddScore addScore = new AddScore(Id.AddScore, Game.handler, x, y, 100, 60, Game.game_bonus);
			Game.handler.removeTile(this);
		} else if (130 >= x) {
			die();
		}
	}

	public void die() {
		Game.playSoundEffect("falldown.wav");
		Game.handler.removeTile(this);
		Game.game_bonus = 1;
		if (Game.GAME_NOT_STARTED == false) {
			Game.life--;
		}

		if (Game.life <= 0) {
			Game.heartObj.update(); // 更新死亡後的愛心
			Game.GAME_NOT_STARTED = true;
		}
	}

}
