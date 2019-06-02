package com.main.item.tile.floor;

import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;

public class Floor2 extends Floor{

	public static Image floor2_BackgroundImages[] = new Image[3]; // 設定圖片
	
	public Floor2(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor2;
	}

}
