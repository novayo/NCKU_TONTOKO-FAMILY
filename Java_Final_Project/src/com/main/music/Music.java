package com.main.music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import com.main.Game;
import com.main.Id;

public class Music extends Thread {

	private String filename;
	private Id id;

	public Music(String wavfile, Id id) {
		filename = wavfile;
		this.id = id;
	}

	public void run() {

		File soundFile = new File(filename);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
		

		if (id == Id.Music_Background) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			while (Game.GAME_NOT_STARTED == false || Game.FIRST_RUN == true) {
				if (Game.FIRST_RUN == true) {
					
				}
				System.out.print("");
			}
			clip.stop();
		} else if (id == Id.Music_SoundEffect) {
			
		}
		
			
		
		

		/*
		 * 
		 * AudioFormat format = audioInputStream.getFormat(); SourceDataLine auline =
		 * null; DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		 * 
		 * try { auline = (SourceDataLine) AudioSystem.getLine(info);
		 * auline.open(format); } catch (Exception e) { e.printStackTrace(); return; }
		 * 
		 * auline.start(); int nBytesRead = 0; byte[] abData = new byte[512];
		 * 
		 * try { while (nBytesRead != -1) { nBytesRead = audioInputStream.read(abData,
		 * 0, abData.length); if (nBytesRead >= 0) auline.write(abData, 0, nBytesRead);
		 * } } catch (IOException e) { e.printStackTrace(); return; } finally {
		 * auline.drain(); auline.close(); }
		 */

	}

}