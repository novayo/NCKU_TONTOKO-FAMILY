package com.main.item.entity.tontoko;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.entity.Entity;
import com.main.item.tile.Tile;

public class Tontoko extends Entity {

	private Image tontoko_Images[] = new Image[6]; // 閮剖����
	private final double player_jumping_height = 8.0; // 頝唾��漲
	private final double jumping_speed = 0.3; // 頝唾��漲
	protected double player_gravity = 0.8; // 銝����

	public Tontoko(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		sheetLength = 9; // �鈭粥頝臬�撠迂

		// 閮剖����椰銝�(1,1)嚗�(sheet, x, y, 閬�撟曉��脖��)
		tontoko_Images[0] = new Image(Game.imageSheet, 1, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��
		tontoko_Images[1] = new Image(Game.imageSheet, 2, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��
		tontoko_Images[2] = new Image(Game.imageSheet, 3, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��
		tontoko_Images[3] = new Image(Game.imageSheet, 4, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��
		tontoko_Images[4] = new Image(Game.imageSheet, 5, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��
		tontoko_Images[5] = new Image(Game.imageSheet, 6, 4, Id.GET_TONTOKO_PLAYER); // ��敺�撘菜��銋��

		immutableSpeed = 64 * 2 / moveSpeedfloor1 + 1; // �� (1/animation_speed) 蝘�擃����2�摨衣�犖�
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 瘥� (1/animation_speed) 蝘� 霈��甈∪�
	}

	@Override
	public void render(Graphics g) {
		/***** 閮剖���� *****/

		if (immutable == true && twinkling == true) {
			g.drawImage(Game.immutableSheet.getBufferedImage(), x, y, width, height, null);
			twinkling = false;
		} else {
			if (immutable == true) {
				g.drawImage(tontoko_Images[5].getBufferedImage(), x, y, width, height, null);
			} else {
				if (jumping == true || falling == true) { // 憒�頝唾��葉
					if (immutable == true && twinkling == true) {
						twinkling = false;
					} else {
						g.drawImage(tontoko_Images[4].getBufferedImage(), x, y, width, height, null);
						twinkling = true;
					}
				} else { // 憒�迤撣貊宏���
					if (animation % 8 == 0)
						g.drawImage(tontoko_Images[0].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 1)
						g.drawImage(tontoko_Images[1].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 2)
						g.drawImage(tontoko_Images[2].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 3)
						g.drawImage(tontoko_Images[3].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 4)
						g.drawImage(tontoko_Images[4].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 5)
						g.drawImage(tontoko_Images[3].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 6)
						g.drawImage(tontoko_Images[2].getBufferedImage(), x, y, width, height, null);
					else if (animation % 8 == 7)
						g.drawImage(tontoko_Images[1].getBufferedImage(), x, y, width, height, null);
				}
			}
			twinkling = true;

		}
	}

	@Override
	public void update() {
		/***** ������ *****/
		y += velY;
		animation_speed = (60 / moveSpeedfloor1 > 0) ? 60 / moveSpeedfloor1 : 1; // 瘥� (animation_speed/60) 蝘� 霈��甈∪�
		immutableSpeed = 64 * 2 / moveSpeedfloor1 + 1; // �� (1/animation_speed) 蝘�擃����2�摨衣�犖�

		/***** ���� *****/
		jump(); // 頝唾��
		doCollidingDetection(); // ��蝣唳��
		doAnimation(); // ����
		do_check_Immutable(); // ����
	}

	@Override
	public void doKeyPressed1() {
		if (jumping == false && falling == false) {
			jumping = true;
			player_gravity = player_jumping_height;
			Game.playSoundEffect("tontoko_jump.wav");
		}
	}

	@Override
	public void doKeyPressed2() {

	}

	public void jump() {
		if (jumping == true) {
			player_gravity -= jumping_speed;
			setVelY((int) -player_gravity);

			// ���擃�����
			if (player_gravity <= 0.8) {
				jumping = false;
				falling = true;
				player_gravity = 0.8;
			}
		}

		// 銝�����
		if (falling == true) {
			player_gravity += jumping_speed;
			setVelY((int) player_gravity);

			// ���雿�����
			if (y >= getStart_y() || player_gravity >= player_jumping_height) {
				jumping = false;
				falling = false;
				velY = 0;
				y = getStart_y();
				animation = 2; // ��蝡������
			}
		}
	}
	
	
	public void doCollidingDetection() {
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Tontoko_Obstacle) {
				if (getBounds().intersects(tile.getBounds())) {
					// ��頝���
					if (immutable == false && ((falling == false) || (falling == true && (tile.getX() + tile.getWidth()/2 - x - 27)>0))) {
						fallDown(); // ��������������� - 1
						tile.setHitByPlayer(true);
						Game.game_bonus = 1;
					}
				}
			}
		}
	}
	

	/*
	 * Getters and Setters
	 * 
	 */
	public double getJumping_speed() {
		return jumping_speed;
	}

}
