package com.main.item.tile;

import java.awt.Rectangle;

import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.Item;

public abstract class Tile extends Item{

	private int velY;
	
	public Tile(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
	}
	
	@Override
	public void die() {
		handler.removeTile(this);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	
	
	/* Getters and Setters
	 * 
	 */
	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

}
