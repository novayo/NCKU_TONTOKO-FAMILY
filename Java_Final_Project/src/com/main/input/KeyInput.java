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
		
		for (Entity entity:Game.handler.entityLinkedList) {
			switch (key) {
			case KeyEvent.VK_A:
				if (entity.getId() == Id.Player1) {
					if (entity.isJumping() == false && entity.isFalling() == false) {
						entity.setJumping(true);
						entity.setPlayer_gravity(entity.getPlayer_jumping_height());
					}
				}
				break;
			case KeyEvent.VK_S:
				if (entity.getId() == Id.Player2) {
					if (entity.isJumping() == false && entity.isFalling() == false) {
						entity.setJumping(true);
						entity.setPlayer_gravity(entity.getPlayer_jumping_height());
					}
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode(); // 當我們按下Ａ的時候，key = KeyEvent.VK_A;
		
		for (Entity entity:Game.handler.entityLinkedList) {
			switch (key) {
			case KeyEvent.VK_A:
				entity.setVelY(0);
				break;

			default:
				break;
			}
		}
	}
		
}
