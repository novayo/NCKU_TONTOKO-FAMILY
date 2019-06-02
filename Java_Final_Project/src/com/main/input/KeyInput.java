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

	private boolean hitOnce = false;
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); // 當我們按下Ａ的時候，key = KeyEvent.VK_A;
		if (Game.GAME_NOT_STARTED == false) { // 遊戲開始時，才能接收asdf
			for (int i=0; i<Game.handler.entityLinkedList.size(); i++) {
				if (hitOnce == false) {
					Entity entity = Game.handler.entityLinkedList.get(i);
					switch (key) {
					// Player1
					case KeyEvent.VK_A:
						if (entity.getId() == Id.Dino_Stand_Run) {
							entity.doKeyPressed1();
							hitOnce = true;
						}
						break;
					case KeyEvent.VK_Z:
						if (entity.getId() == Id.Dino_Stand_Run) {
							entity.doKeyPressed2();
							hitOnce = true;
						} else if (entity.getId() == Id.Dino_Squart) {
							entity.doKeyPressed2();
							hitOnce = true;
						}
						break;
						
					// Player2
					case KeyEvent.VK_S:
						
						Game.floor2_Background.hitRed();
						if (entity.getId() == Id.TAIKOPLAYER) {
							entity.doKeyPressed1();
							hitOnce = true;
						}
						break;
					case KeyEvent.VK_X:
						Game.floor2_Background.hitBlue();
						if (entity.getId() == Id.TAIKOPLAYER) {
							entity.doKeyPressed2();
							hitOnce = true;
						}
						break;
						
					// Player3
					case KeyEvent.VK_D:
						if (entity.getId() == Id.ContraRun) {
							entity.doKeyPressed1();
							hitOnce = true;
						}
						break;
					case KeyEvent.VK_C:
						if (entity.getId() == Id.ContraRun || entity.getId() == Id.ContraJump) {
							entity.doKeyPressed2();
							hitOnce = true;
						}
						break;
					case KeyEvent.VK_F:
						if (entity.getId() == Id.Player4) {
							entity.doKeyPressed1();
							hitOnce = true;
						}
						break;
					default:
						break;
					}
				}
			}
			hitOnce = false;
		}
		
		if (key == KeyEvent.VK_SPACE) {
			if (Game.GAME_NOT_STARTED == true) {
				Game.button.click();
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
				if (entity.getId() == Id.Dino_Stand_Run) {
					entity.setVelY(0);
				}
				break;
			case KeyEvent.VK_Z:
				//
				break;
			case KeyEvent.VK_D:
				if (entity.getId() == Id.ContraJump) {
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
