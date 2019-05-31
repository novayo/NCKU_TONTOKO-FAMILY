package com.main.input;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.main.Game;
import com.main.GameParameter;

@SuppressWarnings("serial")
public class Button extends JButton implements ActionListener {

	public JButton button;
	private ImageIcon grayIcon = new ImageIcon(getClass().getResource("/Button/Play_gray.png"));
	private ImageIcon redIcon = new ImageIcon(getClass().getResource("/Button/Play_red.png"));
	private ImageIcon retry_gray = new ImageIcon(getClass().getResource("/Button/Retry_gray.png"));
	private ImageIcon retry_red = new ImageIcon(getClass().getResource("/Button/Retry_red.png"));
	
	public Button() {
		// 轉換圖案大小
		grayIcon = transformSize(grayIcon, 351, 75); // 原本是 (468, 100)
		redIcon = transformSize(redIcon, 351, 75);
		retry_gray = transformSize(retry_gray, 351, 75); // 原本是 (468, 100)
		retry_red = transformSize(retry_red, 351, 75); // 原本是 (468, 100)
		
		// 建立按鍵，並且設定圖案在button上
		button = new JButton(grayIcon);
		button.setBackground(Color.WHITE); // 設定背景是白色
		button.setOpaque(true); // 設定 要用背景顏色=true
		button.setVisible(true); // 設定看得到

		// 設定滑鼠hovering的時候會切換圖案
		button.setRolloverIcon(redIcon);
		
		// 設定button位置
		button.setBounds(GameParameter.WIDTH / 2 - redIcon.getIconWidth() / 2,
				GameParameter.HEIGHT / 2 - redIcon.getIconHeight() / 2 + 70, redIcon.getIconWidth(),
				redIcon.getIconHeight());
		
		// 設定 addActionListener
		button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		click();
	}
	
	public void addInJFrame(JFrame jFrame) {
		jFrame.add(button);
	}
	
	public void showDeathScreen() {
		button.setVisible(true);
		button.setIcon(retry_gray);
		
		// 設定滑鼠hovering的時候會切換圖案
		button.setRolloverIcon(retry_red);
		
		button.setBounds(GameParameter.WIDTH / 2 - redIcon.getIconWidth() / 2,
				GameParameter.HEIGHT / 2 - redIcon.getIconHeight() / 2 + 300, redIcon.getIconWidth(),
				redIcon.getIconHeight());
	}

	public void click() {
		Game.FIRST_RUN = false;
		Game.GAME_NOT_STARTED = false;
		Game.gameReset();
		button.setVisible(false);
		//Game.logo.setVisible(false);
	}
	
	public ImageIcon transformSize(ImageIcon imageIcon, int width, int height) {
		java.awt.Image image = imageIcon.getImage(); // 從ImageIcon轉成Image
		java.awt.Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // 改變大小
		imageIcon = new ImageIcon(newimg);  // 從Image轉回ImageIcon
		return imageIcon;
	}

}
