package com.main.item.tile;

import java.awt.Rectangle;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.item.Item;
import com.main.item.entity.Entity;

public abstract class Tile extends Item {

	protected int moveSpeed = 0;
	protected int sheetLength = 0;
	protected int animation_speed = 0; // 每 (animation_speed/60) 秒 地板就往左走一格
	protected int animationDelay = 0;
	protected int animation = 0;
	protected int three_floor_x[] = new int[3];
	protected int tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
	protected boolean isHitByPlayer = false;
	protected boolean isScoreAdd = false;
	private int velY;

	public Tile(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	public void die() {
		handler.removeTile(this);
	}

	public void doScoreCompute() {
		if (Game.GAME_NOT_STARTED == false && isScoreAdd == false) {
			for (int i = 0; i < Game.handler.entityLinkedList.size(); i++) {
				Entity entity = Game.handler.entityLinkedList.get(i);
				if (entity.getId() == Id.Dino_Stand_Run || entity.getId() == Id.Dino_Squart || entity.getId() == Id.Tontoko_Player) {
					if (x <= entity.getX() - entity.getWidth() - 10) {
						if (isHitByPlayer == true) {
							Game.game_bonus = 1;
						} else if (isHitByPlayer == false) {
							Game.game_score += 10 * Game.game_bonus;
							Game.game_bonus += 1;
							isScoreAdd = true;
						}
					}
				}
			}
		}
	}

	public void doAnimation() {
		animationDelay++;
		x -= moveSpeedfloor1;

		if (animationDelay >= animation_speed / 2) {
			animation++;
			if (animation >= sheetLength) {
				animation = 0;
			}
			animationDelay = 0;
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

	public int getAnimationDelay() {
		return animationDelay;
	}

	public void setAnimationDelay(int animationDelay) {
		this.animationDelay = animationDelay;
	}

	public int[] getThree_floor_x() {
		return three_floor_x;
	}

	public void setThree_floor_x(int[] three_floor_x) {
		this.three_floor_x = three_floor_x;
	}

	public int getTmp_maxObstaclesOnScreen() {
		return tmp_maxObstaclesOnScreen;
	}

	public void setTmp_maxObstaclesOnScreen(int tmp_maxObstaclesOnScreen) {
		this.tmp_maxObstaclesOnScreen = tmp_maxObstaclesOnScreen;
	}

	public boolean isHitByPlayer() {
		return isHitByPlayer;
	}

	public void setHitByPlayer(boolean isHitByPlayer) {
		this.isHitByPlayer = isHitByPlayer;
	}

	public boolean isScoreAdd() {
		return isScoreAdd;
	}

	public void setScoreAdd(boolean isScoreAdd) {
		this.isScoreAdd = isScoreAdd;
	}

	public int getAnimation_speed() {
		return animation_speed;
	}

}
