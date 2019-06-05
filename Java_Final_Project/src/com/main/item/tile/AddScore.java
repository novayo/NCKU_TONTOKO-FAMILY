package com.main.item.tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;

public class AddScore extends Tile{

	private int score = 0;
	private int time = 0;
	
	public AddScore(Id id, Handler handler, int x, int y, int width, int height, int score) {
		super(id, handler, x, y, width, height);
		Game.handler.addTile(this);
		this.score = score;
		Game.game_score += 10 * Game.game_bonus;
		Game.game_bonus += 1;
		Game.playSoundEffect("addscore.wav");
	}

	@Override
	public void render(Graphics g) {
		if (time < 30) {
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("Courier", Font.BOLD, 60));
			g.drawString("10x" + score, x - 30, y);
		} else {
			Game.handler.removeTile(this);
		}
	}

	@Override
	public void update() {
		time++;
		y--;
	}

}
