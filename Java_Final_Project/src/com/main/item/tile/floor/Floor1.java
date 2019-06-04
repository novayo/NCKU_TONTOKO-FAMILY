package com.main.item.tile.floor;

import com.main.Game;
import com.main.GameParameter;
import com.main.Handler;
import com.main.Id;

public class Floor1 extends Floor{
	
	public Floor1(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor1;
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeedfloor1 += 1;
		}

		three_floor_x[0]-=moveSpeedfloor1;
		three_floor_x[1]-=moveSpeedfloor1;
		three_floor_x[2]-=moveSpeedfloor1;
		
		if (three_floor_x[0] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[0] = three_floor_x[2] + GameParameter.FLOOR_WIDTH; // 接在最右邊，營造出一直向前走的假象
		} else if (three_floor_x[1] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[1] = three_floor_x[0] + GameParameter.FLOOR_WIDTH;
		} else if (three_floor_x[2] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[2] = three_floor_x[1] + GameParameter.FLOOR_WIDTH;
		}
	}
	
}
