package com.main.item.entity;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.tile.Tile;

public class Player2 extends Entity{

	private final double player_jumping_height = 5.0; // 跳躍高度
	private final double jumping_speed = 0.1;
	private final int animation_speed = 15; // 每 (1/animation_speed) 秒 變換一次動畫
	protected double player_gravity = 0.8; // 下降重力
	private int animation = 0;
	private int animationDelay = 0;
	
	public Player2(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		// 設定圖片
		if (jumping == true || falling == true) g.drawImage(Game.player2Image[Game.player2Image.length-1].getBufferedImage(), x, y, width, height, null); // player2Image[4] 是跳躍動作
		else g.drawImage(Game.player2Image[animation].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		y += velY;
		
		// 判斷碰撞
		for (Tile tile:handler.tileLinkedList) {
			if (tile.getId() == Id.Stone) {
				if (getBounds().intersects(tile.getBounds())) {
					// 生命 - 1
					// 做出跌倒的動畫
					System.out.println("碰到石頭");
				}
			}
		}
		
		// 跳躍
		if (jumping == true) {
			player_gravity -= jumping_speed;
			setVelY((int)-player_gravity);
			
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
			setVelY((int)player_gravity);
			
			// 到達至低點的時候
			if (y >= getStart_y() || player_gravity >= player_jumping_height) {
				jumping = false;
				falling = false;
				velY = 0;
				y = getStart_y();
			}
		}
		
		// 處理動畫
		animationDelay++;
		if (animationDelay >= animation_speed) {
			animation++;
			if (animation >= Game.player2Image.length - 1) {
				animation = 0;
			}
			animationDelay = 0;
		}
	}

	
	
	/* Getters and Setters
	 * 
	 */
	public double getJumping_speed() {
		return jumping_speed;
	}

	@Override
	public double getPlayer_gravity() {
		return player_gravity;
	}

	@Override
	public double getPlayer_jumping_height() {
		return player_jumping_height;
	}

	@Override
	public void setPlayer_gravity(double player_gravity) {
		this.player_gravity = player_gravity;
	}
	
}
