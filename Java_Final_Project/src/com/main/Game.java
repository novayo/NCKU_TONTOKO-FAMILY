package com.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.main.gfx.Image;
import com.main.gfx.ImageSheet;
import com.main.input.KeyInput;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.entity.Player1;
import com.main.item.entity.Player2;
import com.main.item.tile.Floor1;
import com.main.item.tile.Floor2;


@SuppressWarnings("serial") // 有個奇怪的warning，用這個可以消除
public class Game extends Canvas implements Runnable, GameParameter{
	
	public static boolean GAME_RUNNING = false;
	public static ImageSheet imageSheet; // 拿取圖片資源
	public static Image player1Image[] = new Image[5];
	public static Image player2Image[] = new Image[3];
	public static Image stoneImage;
	public static Image floor1Image[] = new Image[3];
	public static Image floor2Image[] = new Image[3];
	public static Handler handler; // 新增所有遊戲物件
	
	public Game() {
		Dimension size = new Dimension(GameParameter.WIDTH, GameParameter.HEIGHT);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}

	private void initialize() {
		// 拿到圖片資源
		imageSheet = new ImageSheet("/resSheet.png");
		
		// 設定圖片，左上角是(1,1)
		for (int i=0; i<player1Image.length; i++) {
			player1Image[i] = new Image(imageSheet, i+1, 16, 1);
		}
		
		for (int i=0; i<player2Image.length; i++) {
			player2Image[i] = new Image(imageSheet, i+1, 15, 1);
		}
		
		stoneImage = new Image(imageSheet, 1, 1, 1);
		
		for (int i=0; i<floor1Image.length; i++) {
			floor1Image[i] = new Image(imageSheet, 1, 3, 4);
		}
		
		for (int i=0; i<floor2Image.length; i++) {
			floor2Image[i] = new Image(imageSheet, 1, 3, 4);
		}
		
		// 在一開始的時候新增東西
		handler = new Handler();
		handler.addEntity(new Player1(Id.Player1, handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*1/4-64, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		handler.addEntity(new Player2(Id.Player2, handler, GameParameter.WIDTH/7, GameParameter.HEIGHT*2/4-64, 64, 64));  // 讀進來是32 * 32 => 設定成64 * 64會自動放大
		handler.addTile(new Floor1(Id.Floor1, handler, 0, GameParameter.HEIGHT*1/4, GameParameter.FLOOR_WIDTH, 32)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		handler.addTile(new Floor2(Id.Floor2, handler, 0, GameParameter.HEIGHT*2/4, GameParameter.FLOOR_WIDTH, 32)); // 地板要設定3個，每個長度都要大於等於視窗程度的一半(400)，
		
		// 加入偵測鍵盤
		addKeyListener(new KeyInput());
		requestFocus(); // 讓我們的按下的按鍵都會被我們的程式讀取到
	}
	
	/* THEAD
	 * 
	 * synchronized 是為了保證thread同步
	 * implements Runnable 是因為 thread會自己跑 run函數
	 */
	private Thread thread;
	public synchronized void start() {
		if (GAME_RUNNING == true) return; // 確保使用game.start()的時候，Main.GAME_RUNNING是false
		else GAME_RUNNING = true;
		
		thread = new Thread(this, "Thread");
		thread.start(); // thread 開始
	}
	
	private synchronized void stop() {
		if (GAME_RUNNING == false) return; // 確保使用game.stop()的時候，Main.GAME_RUNNING是true
		else GAME_RUNNING = false;
		
		try {
			thread.join(); // thread 停止
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Thread 會自己跑這裡
	// 這裡就是跑所有game的地方
	public void run() {
		initialize();
		Graphics background = null;
		// 設定每秒可以跑幾次 render() ＆ update()
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0/60.0;
		double delta = 0.0;
		int frames = 0;
		int updates = 0;
		
		while(GAME_RUNNING == true) {
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime)/ns;
			lastTime = nowTime;
			
			// 每1/60秒
			while(delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render(background);
			frames++;
			
			// 每一秒
			if (System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
				System.out.println("每一秒跑  " + frames + "次render()  " + updates + "次update()");
				frames = 0;
				updates = 0;
			}
		}
		stop(); // 整個遊戲停止
	}
	
	/* 顯示圖片
	 * 
	 */
	@Override
	public void render(Graphics background) {
		// 因為render跑很快，但我們需要一直更新畫面，但會一直輸出畫面呀，會一直load進不一樣的圖案，這些都是需要時間的花費的
		// 因此我們利用 bufferStrategy 去建立圖層，他會先去抓第一個圖層，做好事情之後自動去抓第二個圖層來跑，並用show()來顯示已經跑好的圖層
		// 可以讓我們的畫面看起來更流暢
		BufferStrategy bufferStrategy = getBufferStrategy(); // 拿到圖層
		if (bufferStrategy == null) { // 如果沒有
			createBufferStrategy(3);  // 建立三個圖層
			return;
		}
		
		background = bufferStrategy.getDrawGraphics();
		background.setColor(Color.WHITE); // 設定background顏色 // 也可以用 new Color(r, g, b)
		background.fillRect(0, 0, GameParameter.WIDTH, GameParameter.HEIGHT); // 設定background位置跟大小
		handler.render(background); // 顯示所有的 linkedlist 的東西，所有的entity跟tile
		background.dispose(); // 顯示background
		bufferStrategy.show();
	}
	
	/* 更新數據
	 * 
	 */
	@Override
	public void update() {
		handler.update();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame gameFrame = new JFrame(GameParameter.TITLE_STRING); // 建立視窗
		gameFrame.add(game); // 把game加進去視窗當中
		gameFrame.pack(); // 讓視窗大小跟著game設定的一樣大
		gameFrame.setResizable(false); // 讓視窗不能拉大拉小
		gameFrame.setLocationRelativeTo(null); // 讓視窗預設出現在螢幕中間
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 讓視窗按下x時關閉
		gameFrame.setVisible(true); // 讓視窗顯示出來
		
		game.start(); // 開始thread
	}
	
}









