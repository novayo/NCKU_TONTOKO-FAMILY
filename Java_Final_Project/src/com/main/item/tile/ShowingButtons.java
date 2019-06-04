package com.main.item.tile;

import java.awt.Graphics;

import com.main.Handler;
import com.main.Id;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;

public class ShowingButtons extends Tile {

	private Image showingButtonsImages[] = new Image[14];
	private Image nowImage[] = new Image[7];

	public ShowingButtons(Id id, Handler handler, int x, int y, int width, int height) {
		super(id, handler, x, y, width, height);

		ImageSheet tmpImageSheet = new ImageSheet("/Image/Button/A-1.png");
		showingButtonsImages[0] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色Ａ
		tmpImageSheet = new ImageSheet("/Image/Button/A-2.png");
		showingButtonsImages[1] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色Ａ
		tmpImageSheet = new ImageSheet("/Image/Button/S-1.png");
		showingButtonsImages[2] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色S
		tmpImageSheet = new ImageSheet("/Image/Button/S-2.png");
		showingButtonsImages[3] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色S
		tmpImageSheet = new ImageSheet("/Image/Button/X-1.png");
		showingButtonsImages[4] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色X
		tmpImageSheet = new ImageSheet("/Image/Button/X-2.png");
		showingButtonsImages[5] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色X
		tmpImageSheet = new ImageSheet("/Image/Button/D-1.png");
		showingButtonsImages[6] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色D
		tmpImageSheet = new ImageSheet("/Image/Button/D-2.png");
		showingButtonsImages[7] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色D
		tmpImageSheet = new ImageSheet("/Image/Button/C-1.png");
		showingButtonsImages[8] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色C
		tmpImageSheet = new ImageSheet("/Image/Button/C-2.png");
		showingButtonsImages[9] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色C 
		tmpImageSheet = new ImageSheet("/Image/Button/F-1.png");
		showingButtonsImages[10] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色F
		tmpImageSheet = new ImageSheet("/Image/Button/F-2.png");
		showingButtonsImages[11] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色F 
		tmpImageSheet = new ImageSheet("/Image/Button/V-1.png");
		showingButtonsImages[12] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 灰色V
		tmpImageSheet = new ImageSheet("/Image/Button/V-2.png");
		showingButtonsImages[13] = new Image(tmpImageSheet, 1, 1, Id.GET_WHOLE_SHEET); // 紅色V 
		
		nowImage[0] = showingButtonsImages[0]; // A
		nowImage[1] = showingButtonsImages[2]; // S
		nowImage[2] = showingButtonsImages[4]; // X
		nowImage[3] = showingButtonsImages[6]; // D
		nowImage[4] = showingButtonsImages[8]; // C
		nowImage[5] = showingButtonsImages[10]; // F
		nowImage[6] = showingButtonsImages[12]; // V
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(nowImage[0].getBufferedImage(), x, y, width, height, null);
		g.drawImage(nowImage[1].getBufferedImage(), x, y + 150, width, height, null);
		g.drawImage(nowImage[2].getBufferedImage(), x, y + 201, width, height, null);
		g.drawImage(nowImage[3].getBufferedImage(), x, y + 360, width, height, null);
		g.drawImage(nowImage[4].getBufferedImage(), x, y + 410, width, height, null);
		g.drawImage(nowImage[5].getBufferedImage(), x, y + 550, width, height, null);
		g.drawImage(nowImage[6].getBufferedImage(), x, y + 600, width, height, null);
	}

	@Override
	public void update() {

	}

	public void AButton(int key) {
		nowImage[0] = showingButtonsImages[0 + key]; // A
	}
	
	public void SButton(int key) {
		nowImage[1] = showingButtonsImages[2 + key]; // S
	}
	
	public void XButton(int key) {
		nowImage[2] = showingButtonsImages[4 + key]; // X
	}
	
	public void DButton(int key) {
		nowImage[3] = showingButtonsImages[6 + key]; // D
	}
	
	public void CButton(int key) {
		nowImage[4] = showingButtonsImages[8 + key]; // C
	}
	
	public void FButton(int key) {
		nowImage[5] = showingButtonsImages[10 + key]; // F
	}
	
	public void VButton(int key) {
		nowImage[6] = showingButtonsImages[12 + key]; // V
	}
	
}
