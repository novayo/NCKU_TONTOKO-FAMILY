package com.main.item.tile.floor;

import com.main.Handler;
import com.main.Id;

public class Floor2 extends Floor{
	
	public Floor2(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor2;
	}

}
