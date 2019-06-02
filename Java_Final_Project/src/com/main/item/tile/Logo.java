package com.main.item.tile;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;

public class Logo extends Tile{

	private Image logoImage;
	
	public Logo(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		
		ImageSheet tmpImageSheet = new ImageSheet("/Image/Scenes/Logo.png");
		logoImage = new Image(tmpImageSheet, 0, 0, Id.GET_WHOLE_SHEET);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(logoImage.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		if (Game.GAME_NOT_STARTED == false) handler.removeTile(this);
	}

}
