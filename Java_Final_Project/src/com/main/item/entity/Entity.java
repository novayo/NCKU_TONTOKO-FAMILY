package com.main.item.entity;

import java.awt.Rectangle;

import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.Item;

public abstract class Entity extends Item{
	
	private final int start_y; // 起始位置
	protected int velY;
	protected boolean jumping = false;
	protected boolean falling = false;

	public Entity(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		this.start_y = y;
	}

	@Override
	public void die() {
		handler.removeEntity(this);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public abstract double getPlayer_gravity();
	public abstract double getPlayer_jumping_height();
	public abstract void setPlayer_gravity(double player_gravity);
	
	/* 偵測碰撞 (還沒用到)
	 * 
	 * 原理：
	 * 今天有一個圖片的大小為 60*60
	 * 就在圖片的上下左右個插入一個大小為20*5的透明方塊（用******表示方塊位置）
	 * ----------------
	 * |    ******    |
	 * | *			* |
	 * | *			* |
	 * | *			* |
	 * |	******	  |
	 * ----------------
	 * 
	 * 用此方式去進行碰撞感測
	 */
	public Rectangle getBoundsTop() {
		return new Rectangle(x+10, y, width-20, 5);
	}
	
	public Rectangle getBoundsButtom() {
		return new Rectangle(x+10, y+height-5, width-20, 5);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y+10, 5, height-20);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle(x+width-5, y+10, 5, height-20);
	}
	
	
	
	/* Getters and Setters
	 * 
	 */
	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public int getStart_y() {
		return start_y;
	}
		
}
