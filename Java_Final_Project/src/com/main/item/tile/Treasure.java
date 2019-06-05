package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;

public class Treasure extends Tile{

	
	private Image boxImage[] = new Image[3];
	private final int boxAnimationTime = 300; // 1/60秒 （設定五秒）
	private int countBoxAnimationTime = 0;
	private boolean boxAnimationFlag = false;
	private boolean doOnce = true;
	
	public Treasure(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		ImageSheet tmpImageSheet = new ImageSheet("/Image/Treasure/box0.png");
		boxImage[0] = new Image(tmpImageSheet, 0, 0, Id.GET_WHOLE_SHEET);

		tmpImageSheet = new ImageSheet("/Image/Treasure/box1.png");
		boxImage[1] = new Image(tmpImageSheet, 0, 0, Id.GET_WHOLE_SHEET);

		tmpImageSheet = new ImageSheet("/Image/Treasure/box2.png");
		boxImage[2] = new Image(tmpImageSheet, 0, 0, Id.GET_WHOLE_SHEET);
		
	}

	@Override
	public void render(Graphics g) {
		if (countBoxAnimationTime < boxAnimationTime*3/5) {
			g.drawImage(boxImage[0].getBufferedImage(), x, y, width, height, null);
		} else if (countBoxAnimationTime < boxAnimationTime) {
			if (doOnce == true) {
				Game.playSoundEffect("drum.wav");
				doOnce = false;
			}
			if (boxAnimationFlag == false) {
				g.drawImage(boxImage[1].getBufferedImage(), x, y, width, height, null);
				boxAnimationFlag = true;
			} else {
				g.drawImage(boxImage[2].getBufferedImage(), x, y, width, height, null);
				boxAnimationFlag = false;
			}
		} else {
			if (doOnce == false) Game.playSoundEffect("openbox.wav");
			g.drawImage(Game.treasureImage.getBufferedImage(), x, y, width, height, null);
			doOnce = true;
		}
	}

	@Override
	public void update() {
		if (countBoxAnimationTime <= boxAnimationTime) countBoxAnimationTime++;
	}

}
