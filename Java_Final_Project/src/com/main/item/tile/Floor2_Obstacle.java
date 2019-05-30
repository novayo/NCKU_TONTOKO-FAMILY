package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.entity.Entity;

public class Floor2_Obstacle extends Tile {

	public Floor2_Obstacle(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		g.drawImage(Game.floor2_obstacleImage.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		animationDelay++;
		if (animationDelay > animation_speed) {
			x -= moveSpeedfloor2;
		}

		if (Game.GAME_NOT_STARTED == false && isScoreAdd == false) {
			for (int i = 0; i < Game.handler.entityLinkedList.size(); i++) {
				Entity entity = Game.handler.entityLinkedList.get(i);
				if (entity.getId() == Id.Player2) {
					if (x <= entity.getX()) {
						if (isHitByPlayer == true) {
							Game.game_bonus = 1;
						} else if (isHitByPlayer == false) {
							isScoreAdd = true;
							Game.game_score += 10 * Game.game_bonus;
							Game.game_bonus += 1;
						}
					}
				}
			}
		}

		if (x <= -width)
			die();
	}

}
