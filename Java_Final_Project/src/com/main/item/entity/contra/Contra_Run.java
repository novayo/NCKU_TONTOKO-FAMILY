package com.main.item.entity.contra;

import java.awt.Graphics;

import com.main.Game;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.entity.Entity;

public class Contra_Run extends Entity {

	public static Image contraRunImages[] = new Image[3];

	public Contra_Run(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		sheetLength = contraRunImages.length+1; // 因為沒有站著停止的圖片
		
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		for (int i = 0; i < contraRunImages.length; i++)
			contraRunImages[i] = new Image(Game.imageSheet, i + 1, 3, Id.GET_CONTRA_RUN);
		
		immutableSpeed = 64 * 2 / moveSpeedfloor3 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物
		animation_speed = (60 / moveSpeedfloor3 > 0) ? 60 / moveSpeedfloor3 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		// 走路
		if (animation % 3 == 0)
			g.drawImage(contraRunImages[0].getBufferedImage(), x, y, width, height, null);
		else if (animation % 3 == 1)
			g.drawImage(contraRunImages[1].getBufferedImage(), x, y, width, height, null);
		else if (animation % 3 == 2)
			g.drawImage(contraRunImages[2].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		/***** 更新數據 *****/
		immutableSpeed = 64 * 2 / moveSpeedfloor3 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物

		/***** 做事 *****/
		doCollidingDetection(); // 判斷碰撞
		doAnimation(); // 處理動畫
		do_check_Immutable(); // 處理無敵
	}

	@Override
	public void doKeyPressed1() {
		if (jumping == false && falling == false) {
			Game.handler.addEntity(Game.contra_Jump); // 建立跳起魂斗羅
			Game.contra_Jump.setJumping(true);
			Game.contra_Jump.setPlayer_gravity(Game.contra_Jump.getPlayer_jumping_height());
			Game.handler.removeEntity(this);
		}
	}

	@Override
	public void doKeyPressed2() {
		Game.handler.addEntity(new Contra_Bullet(Id.ContraBullet, Game.handler, x + width, y+18, 8, 8));
	}

}
