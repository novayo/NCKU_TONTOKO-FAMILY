package com.main.gfx;

import java.awt.image.BufferedImage;

/*
 * 使用圖片資源
 */

public class Image {
	
	public ImageSheet imageSheet;
	public BufferedImage bufferedImage;
	
	public Image(ImageSheet imageSheet, int x, int y, int numOfSheet) {
		bufferedImage = imageSheet.getImage(x, y, numOfSheet); // numOfSheet是一次要讀取幾格（左至右）
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
}
