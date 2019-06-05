package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;


public class DataFile{

	// 26�ata
	private final String DATA_NAME = "./Java_Final_Project/res/data.txt";
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
		// ��Ⅱ隤ata.txt��摮
		File dataFile = new File(DATA_NAME);
		if (dataFile.exists() == false) {
			// 憒���迨瑼��遣蝡���
			try {
				dataFormatter = new Formatter(DATA_NAME); // 撱箇����
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			// 撠������
			for (int i=0; i<DATA.length; i++) {
				dataFormatter.format("%d\n", DATA[i]);
			}
			
			// ������
			dataFormatter.close();
		} 
	}
	
	public void readFile() {
		// 霈�瑼��
		try {
			dataScanner = new Scanner(new File(DATA_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		while(dataScanner.hasNext()) {
			String string = dataScanner.next();
			DATA[index++] = Integer.parseInt(string);	// ��鞈�DATA銝�
			if (Integer.parseInt(string) == 1) this.numOfTreasure++; // 蝣箄�窄�����嗾��
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
