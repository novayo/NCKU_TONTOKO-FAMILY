package com.main.item.tile.floor;

import com.main.gfx.Image;
import com.main.item.Handler;
import com.main.item.Id;

public class Floor2 extends Floor{

	public static Image floor2_BackgroundImages[] = new Image[3]; // 設定圖片
	
	public Floor2(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		moveSpeed = moveSpeedfloor2;
	}

}
