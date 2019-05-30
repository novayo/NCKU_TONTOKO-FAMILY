package com.main.item.tile;

import java.awt.Rectangle;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.Item;

public abstract class Tile extends Item{

	protected final int animation_speed = 20; // 每 (1/animation_speed) 秒 地板就往左走一格
	protected int animationDelay = 0;
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
		if (Game.GAME_NOT_STARTED == false) {
			Game.numOfObstacles--; // 每死掉一個障礙物，就讓值-1
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
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
