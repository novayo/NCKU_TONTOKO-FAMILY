package com.main.item.tile.floor;

import java.awt.Graphics;

import com.main.Game;
import com.main.GameParameter;
import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.tile.Tile;

public class Floor extends Tile{
	
	public static Image floorImage[] = new Image[3];
	protected int moveSpeed = 0;
	
	public Floor(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		for (int i = 0; i < floorImage.length; i++)
			floorImage[i] = new Image(Game.imageSheet, 1, 32, Id.GET_FLOOR);
		
		this.three_floor_x[0] = x;
		this.three_floor_x[1] = x+GameParameter.FLOOR_WIDTH;
		this.three_floor_x[2] = x+GameParameter.FLOOR_WIDTH*2;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(floorImage[0].getBufferedImage(), three_floor_x[0], y, width, height, null);
		g.drawImage(floorImage[1].getBufferedImage(), three_floor_x[1], y, width, height, null);
		g.drawImage(floorImage[2].getBufferedImage(), three_floor_x[2], y, width, height, null);
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeed += 1;
		}

		three_floor_x[0]-=moveSpeed;
		three_floor_x[1]-=moveSpeed;
		three_floor_x[2]-=moveSpeed;
		
		if (three_floor_x[0] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[0] = three_floor_x[2] + GameParameter.FLOOR_WIDTH; // 接在最右邊，營造出一直向前走的假象
		} else if (three_floor_x[1] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[1] = three_floor_x[0] + GameParameter.FLOOR_WIDTH;
		} else if (three_floor_x[2] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[2] = three_floor_x[1] + GameParameter.FLOOR_WIDTH;
		}
	}

}
