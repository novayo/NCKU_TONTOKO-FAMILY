package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;

public class Treasure extends Tile{

	public Treasure(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.treasureImage.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		
	}

}
