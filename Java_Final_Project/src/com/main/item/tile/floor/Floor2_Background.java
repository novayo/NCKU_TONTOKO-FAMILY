package com.main.item.tile.floor;

import java.awt.Graphics;

import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;

public class Floor2_Background extends Floor2 {

	private Image nowImage = null;
	private boolean isHit = false;

	public Floor2_Background(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		ImageSheet tmpImageSheet = new ImageSheet("/Scenes/Taiko_map0.png"); // 正常沒打的
		floor2_BackgroundImages[0] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET);
		tmpImageSheet = new ImageSheet("/Scenes/Taiko_map1.png"); // 打紅色
		floor2_BackgroundImages[1] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET);
		tmpImageSheet = new ImageSheet("/Scenes/Taiko_map2.png"); // 打藍色
		floor2_BackgroundImages[2] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET);

		nowImage = floor2_BackgroundImages[0];
		animation_speed = 20; // (animation_speed/500)秒
	}

	public void render(Graphics g) {
		g.drawImage(nowImage.getBufferedImage(), x, y, width, height, null);

		if (isHit == true) {
			animation++;
			if (animation >= animation_speed) {
				nowImage = floor2_BackgroundImages[0];
				animation = 0;
				isHit = false;
			}
		}
	}

	public void update() {

	}

	public void hitRed() {
		if (isHit == false) {
			nowImage = floor2_BackgroundImages[1];
			isHit = true;
		}
	}

	public void hitBlue() {
		if (isHit == false) {
			nowImage = floor2_BackgroundImages[2];
			isHit = true;
		}
	}

}
