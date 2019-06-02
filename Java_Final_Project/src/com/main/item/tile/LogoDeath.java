package com.main.item.tile;

import java.awt.Graphics;

import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;

public class LogoDeath extends Tile{

	private Image logoDeathImage;
	
	private final int target_Y;
	private boolean isAnimation = true;
	private int i=0;
	
	public LogoDeath(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		ImageSheet tmpImageSheet = new ImageSheet("/Scenes/DeathScreen.png");
		logoDeathImage = new Image(tmpImageSheet, 0, 0, Id.GET_WHOLE_SHEET);
		
		target_Y = y;
		this.y = y - 500;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(logoDeathImage.getBufferedImage(), x, this.y, width, height, null);
	}

	@Override
	public void update() {
		if (isAnimation == true) {
			animation();
		}
	}
	
	public void animation() {
		if (this.y < target_Y) {
			this.y += 4;
		}
		else if (this.y < target_Y + 30) {
			this.y += 1;
		}
		else {
			if (i < 4) i++;
			else{
				this.y = target_Y;
				isAnimation = false;
			}
		}
	}

}
