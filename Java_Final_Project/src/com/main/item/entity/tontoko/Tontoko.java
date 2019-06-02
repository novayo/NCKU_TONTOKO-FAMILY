package com.main.item.entity.tontoko;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.entity.Entity;

public class Tontoko extends Entity {

	private Image tontoko_Images[] = new Image[6]; // 設定圖片
	private final double player_jumping_height = 8.0; // 跳躍高度
	private final double jumping_speed = 0.3; // 跳躍高度
	protected double player_gravity = 0.8; // 下降重力

	public Tontoko(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		sheetLength = 9; // 為了走路動畫對稱

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		for (int i = 0; i < tontoko_Images.length; i++)
			tontoko_Images[i] = new Image(Game.imageSheet, i + 1, 4, Id.GET_TONTOKO_PLAYER); // 最後一張是撞到之後

		immutableSpeed = 64 * 2 / moveSpeedfloor1 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
	}

	@Override
	public void render(Graphics g) {
		/***** 設定圖片 *****/

		if (immutable == true && twinkling == true) {
			g.drawImage(Game.immutableSheet.getBufferedImage(), x, y, width, height, null);
			twinkling = false;
		} else {
			if (immutable == true) {
				g.drawImage(tontoko_Images[5].getBufferedImage(), x, y, width, height, null);
			} else {
				if (jumping == true || falling == true) { // 如果在跳躍途中
					if (immutable == true && twinkling == true) {
						// jumping = false;
						// falling = false;
						twinkling = false;
					} else {
						g.drawImage(tontoko_Images[4].getBufferedImage(), x, y, width, height, null);
						twinkling = true;
					}
				} else { // 如果正常移動
					if (animation % 8 == 0)
						g.drawImage(tontoko_Images[0].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 1)
						g.drawImage(tontoko_Images[1].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 2)
						g.drawImage(tontoko_Images[2].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 3)
						g.drawImage(tontoko_Images[3].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 4)
						g.drawImage(tontoko_Images[4].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 5)
						g.drawImage(tontoko_Images[3].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 6)
						g.drawImage(tontoko_Images[2].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 7)
						g.drawImage(tontoko_Images[1].getBufferedImage(), x, y, width, height, null);
				}
			}
			twinkling = true;

		}
	}

	@Override
	public void update() {
		/***** 更新數據 *****/
		y += velY;
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 每 (1/animation_speed) 秒 變換一次動畫
		immutableSpeed = 64 * 2 / moveSpeedfloor1 + 1; // 無敵 (1/animation_speed) 秒，物體要通過2個長度的人物

		/***** 做事 *****/
		jump(); // 跳躍
		doCollidingDetection(); // 判斷碰撞
		doAnimation(); // 處理動畫
		do_check_Immutable(); // 處理無敵
	}

	@Override
	public void doKeyPressed1() {
		if (jumping == false && falling == false) {
			jumping = true;
			player_gravity = player_jumping_height;
		}
	}

	@Override
	public void doKeyPressed2() {

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
				animation = 2; // 回到站著的狀態
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
