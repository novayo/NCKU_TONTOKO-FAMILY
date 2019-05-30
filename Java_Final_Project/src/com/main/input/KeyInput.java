package com.main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.main.Game;
import com.main.item.Id;
import com.main.item.entity.Entity;

public class KeyInput implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); // 當我們按下Ａ的時候，key = KeyEvent.VK_A;
		
		if (Game.GAME_NOT_STARTED == false) { // 遊戲開始時，才能接收asdf
			for (int i=0; i<Game.handler.entityLinkedList.size(); i++) {
				Entity entity = Game.handler.entityLinkedList.get(i);
				switch (key) {
				case KeyEvent.VK_A:
					if (entity.getId() == Id.Player1) {
						entity.doKeyPressed();
					}
					break;
				case KeyEvent.VK_S:
					if (entity.getId() == Id.Player2) {
						entity.doKeyPressed();
					}
					break;
				case KeyEvent.VK_D:
					if (entity.getId() == Id.Player3) {
						entity.doKeyPressed();
					}
					break;
				case KeyEvent.VK_F:
					if (entity.getId() == Id.Player4) {
						entity.doKeyPressed();
					}
					break;
				default:
					break;
				}
			}
		}
		
		if (key == KeyEvent.VK_SPACE) {
			if (Game.GAME_NOT_STARTED == true) {
				Game.FIRST_RUN = false;
				Game.GAME_NOT_STARTED = false;
				Game.gameReset();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode(); // 當我們按下Ａ的時候，key = KeyEvent.VK_A;
		
		for (int i=0; i<Game.handler.entityLinkedList.size(); i++) {
			Entity entity = Game.handler.entityLinkedList.get(i);
			switch (key) {
			case KeyEvent.VK_A:
				if (entity.getId() == Id.Player1) {
					entity.setVelY(0);
				}
				break;
			case KeyEvent.VK_S:
				if (entity.getId() == Id.Player2) {
					entity.setVelY(0);
				}
				break;
			case KeyEvent.VK_D:
				if (entity.getId() == Id.Player3) {
					entity.setVelY(0);
				}
				break;
			case KeyEvent.VK_F:
				if (entity.getId() == Id.Player4) {
					entity.setVelY(0);
				}
				break;
			default:
				break;
			}
		}
	}
		
}
