package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.GameParameter;
import com.main.item.Handler;
import com.main.item.Id;

public class Floor3 extends Tile{
	
	public Floor3(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		this.three_floor_x[0] = x;
		this.three_floor_x[1] = x+GameParameter.FLOOR_WIDTH;
		this.three_floor_x[2] = x+GameParameter.FLOOR_WIDTH*2;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.floor3Image[0].getBufferedImage(), three_floor_x[0], y, width, height, null);
		g.drawImage(Game.floor3Image[1].getBufferedImage(), three_floor_x[1], y, width, height, null);
		g.drawImage(Game.floor3Image[2].getBufferedImage(), three_floor_x[2], y, width, height, null);
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeedfloor3 += 1;
		}
		
		animationDelay++;
		if (animationDelay > animation_speed) {
			three_floor_x[0]-=moveSpeedfloor3;
			three_floor_x[1]-=moveSpeedfloor3;
			three_floor_x[2]-=moveSpeedfloor3;
		}
		if (three_floor_x[0] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[0] = three_floor_x[2] + GameParameter.FLOOR_WIDTH; // 接在最右邊，營造出一直向前走的假象
		} else if (three_floor_x[1] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[1] = three_floor_x[0] + GameParameter.FLOOR_WIDTH;
		} else if (three_floor_x[2] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[2] = three_floor_x[1] + GameParameter.FLOOR_WIDTH;
		}
	}

}
