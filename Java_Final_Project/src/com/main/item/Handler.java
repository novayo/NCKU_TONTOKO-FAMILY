package com.main.item;

import java.awt.Graphics;
import java.util.LinkedList;

import com.main.Game;
import com.main.GameParameter;
import com.main.item.entity.Entity;
import com.main.item.entity.Player1;
import com.main.item.entity.Player2;
import com.main.item.entity.Player3;
import com.main.item.entity.Player4;
import com.main.item.tile.Floor1;
import com.main.item.tile.Floor2;
import com.main.item.tile.Floor3;
import com.main.item.tile.Floor4;
import com.main.item.tile.Heart;
import com.main.item.tile.Tile;

public class Handler implements GameParameter{

	public LinkedList<Entity> entityLinkedList = new LinkedList<Entity>();
	public LinkedList<Tile> tileLinkedList = new LinkedList<Tile>();
	
	public Handler() {
		
	}
	
	@Override
	public void render(Graphics g) {
		for (int i=0; i<tileLinkedList.size(); i++) {
			Tile tile = tileLinkedList.get(i);
			tile.render(g);
		}
		
		for (int i=0; i<entityLinkedList.size(); i++) {
			Entity entity = entityLinkedList.get(i);
			entity.render(g);
		}
	}
	
	@Override
	public void update() {
		for (int i=0; i<tileLinkedList.size(); i++) {
			Tile tile = tileLinkedList.get(i);
			tile.update();
		}
		
		for (int i=0; i<entityLinkedList.size(); i++) {
			Entity entity = entityLinkedList.get(i);
			entity.update();
		}
	}
	
	public synchronized LinkedList<Entity> getEntityLinkedList() {
		return this.entityLinkedList;
	}
	
	public synchronized LinkedList<Tile> getTileLinkedList() {
		return this.tileLinkedList;
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
	
	public void createStuff() {
		
		addEntity(new Player1(Id.Player1, Game.handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*1/4-64-32, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		addEntity(new Player2(Id.Player2, Game.handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*2/4-64-32, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		addEntity(new Player3(Id.Player3, Game.handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*3/4-64-32, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		addEntity(new Player4(Id.Player4, Game.handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*4/4-64-32, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		addTile(new Floor1(Id.Floor1, Game.handler, 0, GameParameter.HEIGHT*1/4-Game.FLOOR_HEIGHT, GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		addTile(new Floor2(Id.Floor2, Game.handler, 0, GameParameter.HEIGHT*2/4-Game.FLOOR_HEIGHT, GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		addTile(new Floor3(Id.Floor3, Game.handler, 0, GameParameter.HEIGHT*3/4-Game.FLOOR_HEIGHT, GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		addTile(new Floor4(Id.Floor4, Game.handler, 0, GameParameter.HEIGHT*4/4-Game.FLOOR_HEIGHT, GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		
		Game.heartObj = new Heart(Id.Heart, Game.handler, GameParameter.WIDTH - 32 - 10, 10, 32, 32); 
		addTile(Game.heartObj);
	}
	
	public void resetLinkedList() {
		entityLinkedList.clear();
		tileLinkedList.clear();
	}
	
}
