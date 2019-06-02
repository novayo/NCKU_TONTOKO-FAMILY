package com.main;

import java.awt.Graphics;
import java.util.LinkedList;

import com.main.item.entity.Entity;
import com.main.item.entity.contra.Contra_Jump;
import com.main.item.entity.contra.Contra_Run;
import com.main.item.entity.dino.Dino_Squart;
import com.main.item.entity.dino.Dino_Stand_Run;
import com.main.item.entity.taiko.TaikoPlayer;
import com.main.item.entity.tontoko.Tontoko;
//import com.main.item.tile.Floor2;
//import com.main.item.tile.Floor3;
//import com.main.item.tile.Floor4;
import com.main.item.tile.Heart;
import com.main.item.tile.Tile;
import com.main.item.tile.contra.Contra_Obstacle_Boss;
import com.main.item.tile.floor.Floor1;
import com.main.item.tile.floor.Floor2;
import com.main.item.tile.floor.Floor2_Background;
import com.main.item.tile.floor.Floor3;
import com.main.item.tile.floor.Floor4;

public class Handler implements GameParameter {

	public LinkedList<Entity> entityLinkedList = new LinkedList<Entity>();
	public LinkedList<Tile> tileLinkedList = new LinkedList<Tile>();

	public Handler() {

	}

	@Override
	public void render(Graphics g) {
		for (int i = 0; i < tileLinkedList.size(); i++) {
			Tile tile = tileLinkedList.get(i);
			tile.render(g);
		}

		for (int i = 0; i < entityLinkedList.size(); i++) {
			Entity entity = entityLinkedList.get(i);
			entity.render(g);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < tileLinkedList.size(); i++) {
			Tile tile = tileLinkedList.get(i);
			tile.update();
		}

		for (int i = 0; i < entityLinkedList.size(); i++) {
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

		// Entity 1
		addEntity(new Tontoko(Id.Tontoko_Player, Game.handler, GameParameter.WIDTH / 7,
				GameParameter.HEIGHT * 1 / 4 - 60 - 32, 41, 60)); // 讀進來是32 * 32 => 設定成64 * 64會自動放大

		// Entity 2
		addEntity(new TaikoPlayer(Id.TAIKOPLAYER, Game.handler, 0,
				GameParameter.HEIGHT * 2 / 4 - 60 - Game.FLOOR_HEIGHT - 65, 60, 60)); // 讀進來是32 * 32 => 設定成64 *

		// Entity 3
		Game.contra_Run = new Contra_Run(Id.ContraRun, Game.handler, GameParameter.WIDTH / 7,
				GameParameter.HEIGHT * 3 / 4 - 60 - 32, 41, 60);
		Game.contra_Jump = new Contra_Jump(Id.ContraJump, Game.handler, GameParameter.WIDTH / 7,
				GameParameter.HEIGHT * 3 / 4 - 60 - 32, 40, 40);
		addEntity(Game.contra_Run); // 讀進來是32 * 32 => 設定成64 * 64會自動放大

		// Entity 4
		Game.dino_Stand_Run = new Dino_Stand_Run(Id.Dino_Stand_Run, Game.handler, GameParameter.WIDTH / 7,
				GameParameter.HEIGHT * 4 / 4 - 53 - 32, 49, 53);
		Game.dino_Squart = new Dino_Squart(Id.Dino_Squart, Game.handler, GameParameter.WIDTH / 7,
				GameParameter.HEIGHT * 4 / 4 - 29 - 32, 60, 29);
		addEntity(Game.dino_Stand_Run); // 建立站著恐龍

		// Tile 1
		addTile(new Floor1(Id.Floor1, Game.handler, 0, GameParameter.HEIGHT * 1 / 4 - Game.FLOOR_HEIGHT,
				GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，

		// Tile 2
		addTile(new Floor2(Id.Floor2, Game.handler, 0, GameParameter.HEIGHT * 2 / 4 - Game.FLOOR_HEIGHT,
				GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		Game.floor2_Background = new Floor2_Background(Id.Floor2_Background, Game.handler, 0,
				GameParameter.HEIGHT * 1 / 4 - Game.FLOOR_HEIGHT + GameParameter.FLOOR_HEIGHT, GameParameter.WIDTH,
				160);
		addTile(Game.floor2_Background);

		// Tile 3
		addTile(new Floor3(Id.Floor3, Game.handler, 0, GameParameter.HEIGHT * 3 / 4 - Game.FLOOR_HEIGHT,
				GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		Game.contra_Obstacle_Boss = new Contra_Obstacle_Boss(Id.ContraBoss, Game.handler, GameParameter.WIDTH / 2 + 300,
				GameParameter.HEIGHT * 3 / 4 - 149 - 32, 249, 149);
		addTile(Game.contra_Obstacle_Boss);

		// Tile 4
		addTile(new Floor4(Id.Floor4, Game.handler, 0, GameParameter.HEIGHT * 4 / 4 - Game.FLOOR_HEIGHT,
				GameParameter.FLOOR_WIDTH, Game.FLOOR_HEIGHT)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，

		// Heart
		Game.heartObj = new Heart(Id.Heart, Game.handler, GameParameter.WIDTH - 38 - 20, 10, 49, 38);
		addTile(Game.heartObj);
	}

	public void resetLinkedList() {
		entityLinkedList.clear();
		tileLinkedList.clear();
	}

}
