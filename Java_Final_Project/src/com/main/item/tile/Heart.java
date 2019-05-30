package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;

public class Heart extends Tile{

	private int numOfHeart[] = new int[3];
	
	public Heart(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.heartImage[numOfHeart[0]].getBufferedImage(), x, y, width, height, null); // 最右邊的
		g.drawImage(Game.heartImage[numOfHeart[1]].getBufferedImage(), x-32-10, y, width, height, null); // -32是因為愛心大小為32
		g.drawImage(Game.heartImage[numOfHeart[2]].getBufferedImage(), x-32*2-20, y, width, height, null);
	}

	@Override
	public void update() {
		if (Game.life >= 3) {
			numOfHeart[0] = 0; // 0是紅色愛心
			numOfHeart[1] = 0; 
			numOfHeart[2] = 0;
		} else if (Game.life >= 2) {
			numOfHeart[0] = 1; // 1是空的愛心
			numOfHeart[1] = 0;
			numOfHeart[2] = 0;
		} else if (Game.life >= 1) {
			numOfHeart[0] = 1;
			numOfHeart[1] = 1;
			numOfHeart[2] = 0;
		} else {
			numOfHeart[0] = 1;
			numOfHeart[1] = 1;
			numOfHeart[2] = 1;
		}
	}

	
	
	/* Getter and Setter
	 * 
	 */
	
	public int getMoveSpeedfloor1() {
		return moveSpeedfloor1;
	}
	
	public int getMoveSpeedfloor2() {
		return moveSpeedfloor2;
	}
	
	public int getMoveSpeedfloor3() {
		return moveSpeedfloor3;
	}
	
	public int getMoveSpeedfloor4() {
		return moveSpeedfloor4;
	}
	
}
