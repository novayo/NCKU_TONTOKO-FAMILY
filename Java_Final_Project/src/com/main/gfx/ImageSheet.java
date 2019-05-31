package com.main.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * 取得圖片資源
 */

public class ImageSheet {

	private final int perImageSize = 32; // 設定會抓到每32*32為一個圖片
	private final int offset = 2; // 抓的位置可能會不精準，用這個參數去調整
	private BufferedImage imageSheet;
	
	public ImageSheet(String path) {
		// 拿到image的整個檔案
		try {
			imageSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(int x, int y, int numOfSheet) {
		if (numOfSheet == -1) return imageSheet; // 拿到整張圖片
		else return imageSheet.getSubimage(x*perImageSize-perImageSize+offset, y*perImageSize-perImageSize+offset, perImageSize*numOfSheet-offset, perImageSize-offset);
	}
	
}
