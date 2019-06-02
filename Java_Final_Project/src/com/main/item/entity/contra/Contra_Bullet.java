package com.main.item.entity.contra;

import java.awt.Graphics;

import com.main.Game;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.entity.Entity;

public class Contra_Bullet extends Entity {

	public static Image contraBulletImages = null;

	public Contra_Bullet(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		contraBulletImages = new Image(Game.imageSheet, 8, 3, Id.GET_CONTRA_BULLET);
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (x > Game.contra_Obstacle_Boss.getX()) {
			Game.handler.removeEntity(this);
			// Contra_Boss生出更多昆蟲
		}
		g.drawImage(contraBulletImages.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		/***** 更新數據 *****/
		x += moveSpeedfloor3;
		
		/***** 做事 *****/
		doCollidingDetection(); // 判斷碰撞
	}

	@Override
	public void doKeyPressed1() {
		
	}

	@Override
	public void doKeyPressed2() {
		// TODO Auto-generated method stub

	}

}
