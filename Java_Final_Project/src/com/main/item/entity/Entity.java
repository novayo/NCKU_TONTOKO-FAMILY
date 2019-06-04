package com.main.item.entity;

import java.awt.Rectangle;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.item.Item;
import com.main.item.tile.Tile;

public abstract class Entity extends Item {

	private final int start_y; // 起始位置
	protected boolean jumping = false;
	protected boolean falling = false;
	protected int immutableDelay = 0;
	protected boolean immutable = false; // 無敵狀態
	protected boolean twinkling = false; // 是否要閃爍
	protected int immutableSpeed = 0;

	public Entity(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		this.start_y = y;
	}

	public void fallDown() {
		if (immutable == false) {
			if (Game.GAME_NOT_STARTED == false)
				Game.life--;
			if (Game.life <= 0) {
				Game.heartObj.update(); // 更新死亡後的愛心
				Game.GAME_NOT_STARTED = true;
			}
			immutable = true;
		}
		Game.playSoundEffect("./res/Music/falldown.wav");
	}

	public abstract void doKeyPressed1();
	public abstract void doKeyPressed2();

	public void do_check_Immutable() {
		if (immutable == true) {
			twinkling = true;
			immutableDelay++;
			if (immutableDelay >= immutableSpeed) {
				immutable = false;
				immutableDelay = 0;
			}
		}
	}

	public void doAnimation() {
		animationDelay++;
		if (animationDelay >= animation_speed) {
			animation++;
			if (animation >= sheetLength - 1) {
				animation = 0;
			}
			animationDelay = 0;
		}
	}

	public void doCollidingDetection() {
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Tontoko_Obstacle || tile.getId() == Id.Dino_Obstacle
					|| tile.getId() == Id.Dino_Obstacle0 || tile.getId() == Id.ContraInsects
					|| tile.getId() == Id.ContraInsectsBullet) {
				if (getBounds().intersects(tile.getBounds())) {
					// 做出跌倒的動畫
					if (immutable == false) {
						fallDown(); // 遊戲開始後，非無敵狀態，生命 - 1
						tile.setHitByPlayer(true);
						Game.game_bonus = 1;
					}
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/*
	 * Getters and Setters
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
