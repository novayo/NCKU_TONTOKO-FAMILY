package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.item.Handler;
import com.main.item.Id;

public class Logo extends Tile{

	public Logo(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.logoImage.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		if (Game.GAME_NOT_STARTED == false) handler.removeTile(this);
	}

}
