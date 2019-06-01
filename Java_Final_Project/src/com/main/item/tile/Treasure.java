package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;

public class Treasure extends Tile{

	private final int boxAnimationTime = 300; // 1/60秒 （設定五秒）
	private int countBoxAnimationTime = 0;
	private boolean boxAnimationFlag = false;
	
	public Treasure(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		if (countBoxAnimationTime < boxAnimationTime*3/5) {
			g.drawImage(Game.box0Image.getBufferedImage(), x, y, width, height, null);
		} else if (countBoxAnimationTime < boxAnimationTime) {
			if (boxAnimationFlag == false) {
				g.drawImage(Game.box1Image.getBufferedImage(), x, y, width, height, null);
				boxAnimationFlag = true;
			} else {
				g.drawImage(Game.box2Image.getBufferedImage(), x, y, width, height, null);
				boxAnimationFlag = false;
			}
		} else {
			g.drawImage(Game.treasureImage.getBufferedImage(), x, y, width, height, null);
		}
	}

	@Override
	public void update() {
		if (countBoxAnimationTime <= boxAnimationTime) countBoxAnimationTime++;
	}

}
