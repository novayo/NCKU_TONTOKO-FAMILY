package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;

public class LogoDeath extends Tile{

	private final int target_Y;
	private boolean isAnimation = true;
	private int i=0;
	
	public LogoDeath(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		target_Y = y;
		this.y = y - 500;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.logoDeathImage.getBufferedImage(), x, this.y, width, height, null);
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
