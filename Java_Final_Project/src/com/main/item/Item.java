package com.main.item;

import java.awt.Rectangle;

import com.main.GameParameter;
import com.main.Handler;
import com.main.Id;

public abstract class Item implements GameParameter{
	
	protected int x, y, width, height;
	protected Id id;
	protected Handler handler;
	protected int sheetLength = 0; // 動畫格數的長度
	protected int animation = 0;
	protected int animationDelay = 0;
	protected int animation_speed = 0;
	protected int velY;
	public static int moveSpeedfloor1 = 5;
	public static int moveSpeedfloor2 = 4;
	public static int moveSpeedfloor3 = 6;
	public static int moveSpeedfloor4 = 3;
	
	public Item(Id id, Handler handler, int x, int y, int width, int height) {
		this.id = id;
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract Rectangle getBounds(); // 碰撞感測
	
	/* Getters and Setters
	 * 
	 */
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
