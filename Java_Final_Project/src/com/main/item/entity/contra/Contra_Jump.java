package com.main.item.entity.contra;

import java.awt.Graphics;

import com.main.Game;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.entity.Entity;

public class Contra_Jump extends Entity {

	public static Image contraJumpImages[] = new Image[4];

	private final double player_jumping_height = 8.0; // 跳躍高度
	public double getPlayer_jumping_height() {
		return player_jumping_height;
	}

	private final double jumping_speed = 0.3;
	protected double player_gravity = 0.8; // 下降重力

	public double getPlayer_gravity() {
		return player_gravity;
	}

	public void setPlayer_gravity(double player_gravity) {
		this.player_gravity = player_gravity;
	}

	public Contra_Jump(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		sheetLength = contraJumpImages.length;

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		for (int i = 0; i < contraJumpImages.length; i++)
			contraJumpImages[i] = new Image(Game.imageSheet, i + 1 + Contra_Run.contraRunImages.length, 3,
					Id.GET_CONTRA_JUMP);

		immutableSpeed = 64 * 2 / moveSpeedfloor3 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物
		animation_speed = (60 / moveSpeedfloor3 > 0) ? 60 / moveSpeedfloor3 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (jumping == true || falling == true) { // 跳躍途中
			if (animation % 4 == 0)
				g.drawImage(contraJumpImages[0].getBufferedImage(), x, y, width, height, null);
			else if (animation % 4 == 1)
				g.drawImage(contraJumpImages[1].getBufferedImage(), x, y, width, height, null);
			else if (animation % 4 == 2)
				g.drawImage(contraJumpImages[2].getBufferedImage(), x, y, width, height, null);
			else if (animation % 4 == 3)
				g.drawImage(contraJumpImages[3].getBufferedImage(), x, y, width, height, null);
		} else {
			Game.handler.addEntity(Game.contra_Run); // 建立跑步魂斗羅
			Game.handler.removeEntity(this);
		}
	}

	@Override
	public void update() {
		/***** 更新數據 *****/
		y += velY;
		immutableSpeed = 64 * 2 / moveSpeedfloor3 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物

		/***** 做事 *****/
		jump(); // 跳躍
		doCollidingDetection(); // 判斷碰撞
		doAnimation(); // 處理動畫
		do_check_Immutable(); // 處理無敵
	}

	@Override
	public void doKeyPressed1() {
		// 
	}

	@Override
	public void doKeyPressed2() {
		Game.handler.addEntity(new Contra_Bullet(Id.ContraBullet, Game.handler, x + width, y+18, 8, 8));
	}

	public void jump() {
		if (jumping == true) {
			player_gravity -= jumping_speed;
			setVelY((int) -player_gravity);

			// 到達至高點的時候
			if (player_gravity <= 0.8) {
				jumping = false;
				falling = true;
				player_gravity = 0.8;
			}
		}

		// 下降的時候
		if (falling == true) {
			player_gravity += jumping_speed;
			setVelY((int) player_gravity);

			// 到達至低點的時候
			if (y >= getStart_y() || player_gravity >= player_jumping_height) {
				jumping = false;
				falling = false;
				velY = 0;
				y = getStart_y();
			}
		}
	}

	/*
	 * Getters and Setters
	 * 
	 */
	public double getJumping_speed() {
		return jumping_speed;
	}

}
