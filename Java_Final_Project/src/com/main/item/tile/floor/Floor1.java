package com.main.item.tile.floor;

import com.main.Handler;
import com.main.Id;

public class Floor1 extends Floor{

	public Floor1(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor1;
	}

}
