package com.main.item.tile;

import java.awt.Rectangle;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.item.Item;
import com.main.item.entity.Entity;

public abstract class Tile extends Item {

	protected int moveSpeed = 0;
	protected int three_floor_x[] = new int[3];
	protected int tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
	protected boolean isHitByPlayer = false;
	private boolean done = false;

	public Tile(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	public void die() {
		handler.removeTile(this);
	}

	public void doScoreCompute() {
		if (Game.GAME_NOT_STARTED == false && done == false) {
			for (int i = 0; i < Game.handler.entityLinkedList.size(); i++) {
				Entity entity = Game.handler.entityLinkedList.get(i);
				if (entity.getId() == Id.Dino_Stand_Run || entity.getId() == Id.Dino_Squart || entity.getId() == Id.Tontoko_Player) {
					if (x <= entity.getX() - entity.getWidth() - 40) {
						if (isHitByPlayer == true) {
							Game.game_bonus = 1;
						} else if (isHitByPlayer == false) {
							@SuppressWarnings("unused")
							AddScore addScore = new AddScore(Id.AddScore, Game.handler, x, y, 100, 60, Game.game_bonus);
							Game.playSoundEffect("./res/Music/addscore.wav");
						}
						done = true;
					}
				}
			}
		}
	}

	public void doAnimation() {
		animationDelay++;
		x -= moveSpeed;

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

	public boolean isHitByPlayer() {
		return isHitByPlayer;
	}

	public void setHitByPlayer(boolean isHitByPlayer) {
		this.isHitByPlayer = isHitByPlayer;
	}

	public int getAnimation_speed() {
		return animation_speed;
	}

}
