package com.main.item.entity;

import java.awt.Rectangle;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.Item;

public abstract class Entity extends Item{
	
	private final int start_y; // 起始位置
	protected int velY;
	protected int animation = 0;
	protected int animationDelay = 0;
	protected int immutableDelay = 0;
	protected boolean jumping = false;
	protected boolean falling = false;
	protected boolean immutable = false; // 無敵狀態

	public Entity(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		this.start_y = y;
	}

	public void fallDown() {
		if (immutable == false) {
			Game.life--;
			if (Game.life <= 0) {
				Game.heartObj.update(); // 更新死亡後的愛心
				Game.GAME_NOT_STARTED = true;
			}
			immutable = true;
		}
	}
	
	public abstract void doKeyPressed();
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
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
