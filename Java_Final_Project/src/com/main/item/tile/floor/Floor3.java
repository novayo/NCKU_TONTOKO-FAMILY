package com.main.item.tile.floor;

import com.main.Game;
import com.main.GameParameter;
import com.main.Handler;
import com.main.Id;

public class Floor3 extends Floor{

	public Floor3(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor3;
	}

	@Override
	public void update() {
		if (tmp_maxObstaclesOnScreen != Game.maxObstaclesOnScreen) {
			tmp_maxObstaclesOnScreen = Game.maxObstaclesOnScreen;
			moveSpeedfloor3 += 1;
		}

		three_floor_x[0]-=moveSpeedfloor3;
		three_floor_x[1]-=moveSpeedfloor3;
		three_floor_x[2]-=moveSpeedfloor3;
		
		if (three_floor_x[0] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[0] = three_floor_x[2] + GameParameter.FLOOR_WIDTH; // 接在最右邊，營造出一直向前走的假象
		} else if (three_floor_x[1] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[1] = three_floor_x[0] + GameParameter.FLOOR_WIDTH;
		} else if (three_floor_x[2] <= -GameParameter.FLOOR_WIDTH) {
			three_floor_x[2] = three_floor_x[1] + GameParameter.FLOOR_WIDTH;
		}
	}
	
}
