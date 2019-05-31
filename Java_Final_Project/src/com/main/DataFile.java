package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;


public class DataFile{

	// 26個data
	private final String DATA_NAME = "./res/data.txt";
	private int DATA[] = new int[11];
	private int numOfTreasure = 0;
	private Formatter dataFormatter;
	private Scanner dataScanner;
	
	public DataFile() {
		for (int i=0; i<DATA.length; i++) DATA[i] = 0;
		checkFileExist();
		readFile();
	}
	
	public void checkFileExist() {
		// 先確認data.txt是否存在
		File dataFile = new File(DATA_NAME);
		if (dataFile.exists() == false) {
			// 如果沒有此檔案，建立檔案
			try {
				dataFormatter = new Formatter(DATA_NAME); // 建立檔案
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			// 將檔案初始化
			for (int i=0; i<DATA.length; i++) {
				dataFormatter.format("%d\n", DATA[i]);
			}
			
			// 關掉檔案
			dataFormatter.close();
		} 
	}
	
	public void readFile() {
		// 讀檔案
		try {
			dataScanner = new Scanner(new File(DATA_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		while(dataScanner.hasNext()) {
			String string = dataScanner.next();
			DATA[index++] = Integer.parseInt(string);	// 更新資訊到DATA上
			if (Integer.parseInt(string) == 1) this.numOfTreasure++; // 確認寶物目前有幾個
		}
		
		dataScanner.close();
	}
	
	public void writeFile() {
		try {
			dataFormatter = new Formatter(DATA_NAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < DATA.length; i++) {
			dataFormatter.format("%d\n", DATA[i]);
		}
		
		dataFormatter.close();
	}
	
	public void gainTreasure(int index) {
		if (this.DATA[index] == 0) {
			this.DATA[index] = 1;
			this.numOfTreasure++;
		}
	}

	public void printAllDATA() {
		for(int i=0; i<DATA.length; i++) System.out.println(DATA[i]);
	}

	public void increaseNumOfTreasure() {
		this.numOfTreasure += 1;
	}
	
	public int getNumOfTreasure() {
		return numOfTreasure;
	}
	
}
