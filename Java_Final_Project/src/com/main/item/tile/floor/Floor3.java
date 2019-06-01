package com.main.item.tile.floor;

import com.main.item.Handler;
import com.main.item.Id;

public class Floor3 extends Floor{

	public Floor3(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor3;
	}

}
