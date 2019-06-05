package com.main.item.entity.dino;

import java.awt.Graphics;

import com.main.Game;
import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.item.entity.Entity;

public class Dino_Squart extends Entity {

	public Dino_Squart(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		sheetLength = Dino_Stand_Run.dino_Stand_Run_Images.length;

		// 閮剖����椰銝�(1,1)嚗�(sheet, x, y, 閬�撟曉��脖��)
		for (int i = 0; i < sheetLength - 3; i++)
			Dino_Stand_Run.dino_Stand_Run_Images[i + 3] = new Image(Game.imageSheet, i + 3 + 1, 1, Id.GET_DINO_SQUART);

		immutableSpeed = 64 * 2 / moveSpeedfloor4 + 1; // �� (1/animation_speed) 蝘�擃����2�摨衣�犖�
		animation_speed = (60 / moveSpeedfloor4 > 0) ? 60 / moveSpeedfloor4 : 1; // 瘥� (1/animation_speed) 蝘� 霈��甈∪�
	}

	@Override
	public void render(Graphics g) {
//		System.out.println("SQUART");
		/***** 閮剖���� *****/
		// 憒�僕銝�
		if (immutable == true && twinkling == true) {
			g.drawImage(Game.immutableSheet.getBufferedImage(), x, y, width, height, null);
			twinkling = false;
		} else {
			if (animation % 2 == 0)
				g.drawImage(Dino_Stand_Run.dino_Stand_Run_Images[3].getBufferedImage(), x, y, width, height, null);
			else if (animation % 2 == 1)
				g.drawImage(Dino_Stand_Run.dino_Stand_Run_Images[4].getBufferedImage(), x, y, width, height, null);
			twinkling = true;
		}
	}

	@Override
	public void update() {
		/***** ������ *****/
		animation_speed = (60 / moveSpeedfloor4 > 0) ? 60 / moveSpeedfloor4 : 1; // 瘥� (1/animation_speed) 蝘� 霈��甈∪�
		immutableSpeed = 64 * 2 / moveSpeedfloor4 + 1; // �� (1/animation_speed) 蝘�擃����2�摨衣�犖�

		/***** ���� *****/
		doCollidingDetection(); // ��蝣唳��
		doAnimation(); // ����
		do_check_Immutable(); // ����
	}

	@Override
	public void doKeyPressed1() {
		// 銝���
	}

	@Override
	public void doKeyPressed2() {
		Game.handler.addEntity(Game.dino_Stand_Run); // 撱箇������
		Game.handler.removeEntity(this);
//		System.out.println("Stand");
	}

}
