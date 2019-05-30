package com.main.item;

import java.awt.Graphics;
import java.util.LinkedList;

import com.main.GameParameter;
import com.main.item.entity.Entity;
import com.main.item.tile.Tile;

public class Handler implements GameParameter{

	public LinkedList<Entity> entityLinkedList = new LinkedList<Entity>();
	public LinkedList<Tile> tileLinkedList = new LinkedList<Tile>();
	
	public Handler() {
		
	}
	
	@Override
	public void render(Graphics g) {
		for(Entity entity:entityLinkedList) {
			entity.render(g);
		}
		
		for(Tile tile:tileLinkedList) {
			tile.render(g);
		}
	}
	
	@Override
	public void update() {
		for(Entity entity:entityLinkedList) {
			entity.update();
		}
		
		for(Tile tile:tileLinkedList) {
			tile.update();
		}
	}
	
	
	public void addEntity(Entity entity) {
		entityLinkedList.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entityLinkedList.remove(entity);
	}
	
	public void addTile(Tile tile) {
		tileLinkedList.add(tile);
	}
	
	public void removeTile(Tile tile) {
		tileLinkedList.remove(tile);
	}
	
}
