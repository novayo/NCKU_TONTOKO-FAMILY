package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.GameParameter;
import com.main.item.Handler;
import com.main.item.Id;

public class Floor1 extends Tile{

	private int moveSpeed = 3;
	private final int animation_speed = 20; // 每 (1/animation_speed) 秒 地板就往左走一格
	private int animationDelay = 0;
	private int three_floor_x[] = new int[3];
	
	public Floor1(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		this.three_floor_x[0] = x;
		this.three_floor_x[1] = x+GameParameter.FLOOR_WIDTH;
		this.three_floor_x[2] = x+GameParameter.FLOOR_WIDTH*2;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.floor1Image[0].getBufferedImage(), three_floor_x[0], y, width, height, null);
		g.drawImage(Game.floor1Image[1].getBufferedImage(), three_floor_x[1], y, width, height, null);
		g.drawImage(Game.floor1Image[2].getBufferedImage(), three_floor_x[2], y, width, height, null);
	}

	@Override
	public void update() {
		animationDelay++;
		if (animationDelay > animation_speed) {
			three_floor_x[0]-=moveSpeed;
			three_floor_x[1]-=moveSpeed;
			three_floor_x[2]-=moveSpeed;
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
