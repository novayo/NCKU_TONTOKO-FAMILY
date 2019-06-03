package com.main.item.entity.contra;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.entity.Entity;
import com.main.item.tile.AddScore;
import com.main.item.tile.Tile;
import com.main.item.tile.contra.Contra_Obstacle_Insects;

public class Contra_Bullet extends Entity {

	private Image contraBulletImages = null;

	public Contra_Bullet(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		contraBulletImages = new Image(Game.imageSheet, 8, 3, Id.GET_CONTRA_BULLET);
		Game.playSoundEffect("./res/Music/contra_shoot.wav");
	}

	@Override
	public void render(Graphics g) {
		if (Game.GAME_NOT_STARTED == true) Game.handler.removeEntity(this);
		// 設定圖片
		if (x > Game.contra_Obstacle_Boss.getX()) {
			Game.handler.removeEntity(this);
			Game.handler.addTile(new Contra_Obstacle_Insects(Id.ContraInsectsBullet, Game.handler, x + 10, y, 40, 40));
			Game.contra_InsectBullets++;
		}
		g.drawImage(contraBulletImages.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void update() {
		/***** 更新數據 *****/
		x += moveSpeedfloor3;

		/***** 做事 *****/
		doCollidingDetection(); // 判斷碰撞
	}

	public void doCollidingDetection() {
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.ContraInsects || tile.getId() == Id.ContraInsectsBullet) {
				if (getBounds().intersects(tile.getBounds())) {
					if (tile.getId() == Id.ContraInsectsBullet) {
						Game.handler.removeEntity(this);
						Game.handler.removeTile(tile);
					} else {
						Game.handler.removeEntity(this);
						Game.handler.removeTile(tile);
						@SuppressWarnings("unused")
						AddScore addScore = new AddScore(Id.AddScore, Game.handler, x, y, 100, 60, Game.game_bonus);
						Game.playSoundEffect("./res/Music/addscore.wav");
					}
				}
			}
		}
	}

	@Override
	public void doKeyPressed1() {

	}

	@Override
	public void doKeyPressed2() {
		// TODO Auto-generated method stub

	}

}
