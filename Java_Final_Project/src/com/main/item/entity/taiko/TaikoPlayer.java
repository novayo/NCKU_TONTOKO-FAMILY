package com.main.item.entity.taiko;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;
import com.main.item.entity.Entity;
import com.main.item.tile.Tile;

public class TaikoPlayer extends Entity{
	
	private Image taiko_Showing[] = new Image[5];
	private Image nowImage = null;
	private boolean isHit = false;
	
	public TaikoPlayer(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);
		// 設定圖片，左上角是(1,1)，(sheet, x, y, 要讀幾個進來)
		taiko_Showing[0] = new Image(Game.imageSheet, 3, 2, Id.GET_ONE_OF_SHEET); // 金色笑臉
		taiko_Showing[1] = new Image(Game.imageSheet, 4, 2, Id.GET_ONE_OF_SHEET); // 白色笑臉
		taiko_Showing[2] = Game.immutableSheet; // 透明
		ImageSheet tmpImageSheet = new ImageSheet("/Image/Scenes/Taiko_obj2_effect.png"); // 金色笑臉 煙火
		taiko_Showing[3] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET);
		tmpImageSheet = new ImageSheet("/Image/Scenes/Taiko_obj3_effect.png"); // 白色笑臉 煙火
		taiko_Showing[4] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET);
		
		nowImage = taiko_Showing[2];
		animation_speed = 100; // (animation_speed/500)秒
	}

	@Override
	public void render(Graphics g) {
		if (nowImage == taiko_Showing[2]) g.drawImage(nowImage.getBufferedImage(), 175, y, width, height, null);
		else if (nowImage == taiko_Showing[0]) {
			g.drawImage(nowImage.getBufferedImage(), 175, y + 3, width, height, null);
			g.drawImage(taiko_Showing[3].getBufferedImage(), 105, y - 68, 200, 200, null);
		} else if (nowImage == taiko_Showing[1]) {
			g.drawImage(nowImage.getBufferedImage(), 175, y + 3, width, height, null);
			g.drawImage(taiko_Showing[4].getBufferedImage(), 105, y - 68, 200, 200, null);
		}
		
		if (isHit == true) {
			animation++;
			if (animation >= animation_speed) {
				nowImage = taiko_Showing[2];
				animation = 0;
				isHit = false;
			}
		}
	}

	@Override
	public void update() {
		// do nothing
	}

	@Override
	public void doKeyPressed1() {
		Game.playSoundEffect("./res/Music/taiko_hitred.wav");
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Taiko_Obstacle_RED) {
				// dead   good     great    good     dead
				// ~130   131~159  160~180  181~200  200~220
				if (220 >= tile.getX() && tile.getX() > 200) {
					nowImage = taiko_Showing[2];
					tile.doScoreCompute();
				} else if (200 >= tile.getX() && tile.getX() > 180) {
					nowImage = taiko_Showing[1];
					isHit = true;
					tile.doScoreCompute();
				} else if (180 >= tile.getX() && tile.getX() > 160) {
					nowImage = taiko_Showing[0];
					Game.game_bonus += 1;
					isHit = true;
					tile.doScoreCompute();
				} else if (160 >= tile.getX() && tile.getX() > 130) {
					nowImage = taiko_Showing[1];
					isHit = true;
					tile.doScoreCompute();
				} else if (130 >= tile.getX()) {
					nowImage = taiko_Showing[2];
					isHit = true;
					tile.doScoreCompute();
				} 
			} else if ((220 >= tile.getX() && tile.getX() > 130) && tile.getId() == Id.Taiko_Obstacle_BLUE){
				tile.die();
			}
		}
	}

	@Override
	public void doKeyPressed2() {
		Game.playSoundEffect("./res/Music/taiko_hitblue.wav");
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Taiko_Obstacle_BLUE) {
				// dead   good     great    good     dead
				// ~130   131~159  160~180  181~200  200~220
				if (220 >= tile.getX() && tile.getX() > 200) {
					nowImage = taiko_Showing[2];
					tile.doScoreCompute();
				} else if (200 >= tile.getX() && tile.getX() > 180) {
					nowImage = taiko_Showing[1];
					isHit = true;
					tile.doScoreCompute();
				} else if (180 >= tile.getX() && tile.getX() > 160) {
					nowImage = taiko_Showing[0];
					Game.game_bonus += 1;
					isHit = true;
					tile.doScoreCompute();
				} else if (160 >= tile.getX() && tile.getX() > 130) {
					nowImage = taiko_Showing[1];
					isHit = true;
					tile.doScoreCompute();
				} else if (130 >= tile.getX()) {
					nowImage = taiko_Showing[2];
					isHit = true;
					tile.doScoreCompute();
				} 
			} else if ((220 >= tile.getX() && tile.getX() > 130) && tile.getId() == Id.Taiko_Obstacle_RED){
				tile.die();
			}
		}
	}

}
