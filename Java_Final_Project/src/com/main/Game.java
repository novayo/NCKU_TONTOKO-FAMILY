package com.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.main.gfx.Image;
import com.main.gfx.ImageSheet;
import com.main.input.KeyInput;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.tile.Floor1_Obstacle;
import com.main.item.tile.Floor2_Obstacle;
import com.main.item.tile.Floor3_Obstacle;
import com.main.item.tile.Floor4_Obstacle;
import com.main.item.tile.Heart;
import com.main.item.tile.Tile;

@SuppressWarnings("serial") // 有個奇怪的warning，用這個可以消除
public class Game extends Canvas implements Runnable, GameParameter {

	public static Graphics background = null;
	public static int life = GameParameter.INIT_LIVES;
	public static int game_time = 0;
	public static int numOfObstacles = totalObstacles;
	public static int maxObstaclesOnScreen = 2;
	public static int game_score = 0;
	public static int game_bonus = 1;
	public static boolean GAME_STATE = false; // 整個遊戲的狀態
	public static boolean GAME_NOT_STARTED = true; // 當一開始或死亡
	public static boolean FIRST_RUN = true;
	public static ImageSheet imageSheet; // 拿取圖片資源
	public static Image player1Image[] = new Image[5];
	public static Image player2Image[] = new Image[3];
	public static Image player3Image[] = new Image[4];
	public static Image player4Image[] = new Image[4];
	public static Image floor1Image[] = new Image[3];
	public static Image floor2Image[] = new Image[3];
	public static Image floor3Image[] = new Image[3];
	public static Image floor4Image[] = new Image[3];
	public static Image floor1_obstacleImage;
	public static Image floor2_obstacleImage;
	public static Image floor3_obstacleImage;
	public static Image floor4_obstacleImage;
	public static Heart heartObj;
	public static Image heartImage[] = new Image[2];
	public static Handler handler; // 新增所有遊戲物件
	private static Random rnd = new Random();

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
		for (int i = 0; i < player1Image.length; i++) {
			player1Image[i] = new Image(imageSheet, i + 1, 16, 1);
		}

		for (int i = 0; i < player2Image.length; i++) {
			player2Image[i] = new Image(imageSheet, i + 1, 15, 1);
		}

		for (int i = 0; i < player3Image.length; i++) {
			player3Image[i] = new Image(imageSheet, i + 1, 2, 1);
		}

		for (int i = 0; i < player4Image.length; i++) {
			player4Image[i] = new Image(imageSheet, i + 1, 1, 1);
		}

		for (int i = 0; i < floor1Image.length; i++) {
			floor1Image[i] = new Image(imageSheet, 1, 3, 4);
		}

		for (int i = 0; i < floor2Image.length; i++) {
			floor2Image[i] = new Image(imageSheet, 1, 3, 4);
		}

		for (int i = 0; i < floor3Image.length; i++) {
			floor3Image[i] = new Image(imageSheet, 1, 3, 4);
		}

		for (int i = 0; i < floor4Image.length; i++) {
			floor4Image[i] = new Image(imageSheet, 1, 3, 4);
		}

		floor1_obstacleImage = new Image(imageSheet, 1, 1, 1);
		floor2_obstacleImage = new Image(imageSheet, 1, 2, 1);
		floor3_obstacleImage = new Image(imageSheet, 1, 3, 1);
		floor4_obstacleImage = new Image(imageSheet, 1, 15, 1);

		for (int i = 0; i < heartImage.length; i++) {
			heartImage[i] = new Image(imageSheet, i + 2, 1, 1);
		}

		// 在一開始的時候新增東西
		handler = new Handler();
		handler.createStuff();
		life = GameParameter.INIT_LIVES - 1; // 還沒開始遊戲之前，命是兩條

		// 加入偵測鍵盤
		addKeyListener(new KeyInput());
		requestFocus(); // 讓我們的按下的按鍵都會被我們的程式讀取到
	}

	/*
	 * THEAD
	 * 
	 * synchronized 是為了保證thread同步 implements Runnable 是因為 thread會自己跑 run函數
	 */
	private Thread thread;

	public synchronized void start() {
		if (GAME_STATE == true)
			return; // 確保使用game.start()的時候，Main.GAME_RUNNING是false
		else
			GAME_STATE = true;

		thread = new Thread(this, "Thread");
		thread.start(); // thread 開始
	}

	private synchronized void stop() {
		if (GAME_STATE == false)
			return; // 確保使用game.stop()的時候，Main.GAME_RUNNING是true
		else
			GAME_STATE = false;

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
		// 設定每秒可以跑幾次 render() ＆ update()
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int frames = 0;
		int updates = 0;

		while (GAME_STATE == true) {
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / ns;
			lastTime = nowTime;

			// 每1/60秒
			while (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render(background);
			frames++;

			// 每一秒
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("每一秒跑  " + frames + "次render()  " + updates + "次update()");
				frames = 0;
				updates = 0;
				buildObstacles();
				if (GAME_NOT_STARTED == false)
					game_time++;
				// System.out.println("numOfObstacles : " + numOfObstacles);
				System.out.println("game_score: " + game_score);
			}
		}
		stop(); // 整個遊戲停止
	}

	////////////////////////////// 未解決，不知道為什麼礙物會重疊＝＝
	/*
	 * 隨機出障礙物 總共的陷阱個數為100個（numObstacle = 100） 一開始畫面中只會出先最多2個障礙物（maxObstaclesOnScreen
	 * = 3） 之後每過20個就會多增加1格障礙物（difficulty = 15）
	 * 
	 * Game.handler.tileLinkedList.size() 初始為5個
	 */
	public void buildObstacles() {
		if (numOfObstacles > totalObstacles - 1 * difficulty)
			maxObstaclesOnScreen = 2;
		else if (numOfObstacles > totalObstacles - 2 * difficulty)
			maxObstaclesOnScreen = 3;
		else if (numOfObstacles > totalObstacles - 3 * difficulty)
			maxObstaclesOnScreen = 4;
		else if (numOfObstacles > totalObstacles - 4 * difficulty)
			maxObstaclesOnScreen = 5;
		else if (numOfObstacles > totalObstacles - 5 * difficulty)
			maxObstaclesOnScreen = 6;
		else
			maxObstaclesOnScreen = 8;

		// System.out.println("----------");
		int tmp = handler.tileLinkedList.size() - 5;
		while (tmp < maxObstaclesOnScreen) {
			int randomFloor = rnd.nextInt(4) + 1;
			int randomPos = rnd.nextInt(100) + spriteSize;

			for (Tile tile : handler.tileLinkedList) {
				if (tile.getId() == Id.Floor1_Obstacle) {
					if (tile.getY() == GameParameter.HEIGHT * 1 / 4 - spriteSize - 32) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < 300)
							randomPos += 300;
					} // 若出現的位置太相近就break掉
				} else if (tile.getId() == Id.Floor2_Obstacle) {
					if (randomFloor == 2 && tile.getY() == GameParameter.HEIGHT * 2 / 4 - spriteSize - 32) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < 300)
							randomPos += 300;
					} // 若出現的位置太相近就break掉
				} else if (tile.getId() == Id.Floor3_Obstacle) {
					if (randomFloor == 3 && tile.getY() == GameParameter.HEIGHT * 3 / 4 - spriteSize - 32) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < 300)
							randomPos += 300;
					} // 若出現的位置太相近就break掉
				} else if (tile.getId() == Id.Floor4_Obstacle) {
					if (randomFloor == 4 && tile.getY() == GameParameter.HEIGHT * 4 / 4 - spriteSize - 32) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < 300)
							randomPos += 300;
					} // 若出現的位置太相近就break掉
				}
			}

			switch (randomFloor) {
			case 1:
				handler.addTile(new Floor1_Obstacle(Id.Floor1_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 1 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				// System.out.println("CREATE 1");
				break;
			case 2:
				handler.addTile(new Floor2_Obstacle(Id.Floor2_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 2 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				// System.out.println("CREATE 2");
				break;
			case 3:
				handler.addTile(new Floor3_Obstacle(Id.Floor3_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 3 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				// System.out.println("CREATE 3");
				break;
			case 4:
				handler.addTile(new Floor4_Obstacle(Id.Floor4_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 4 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				// System.out.println("CREATE 4");
				break;
			default:
				break;
			}
			tmp++;
		}
	}

	/*
	 * 顯示圖片
	 * 
	 */
	@Override
	public void render(Graphics background) {
		// 因為render跑很快，但我們需要一直更新畫面，但會一直輸出畫面呀，會一直load進不一樣的圖案，這些都是需要時間的花費的
		// 因此我們利用 bufferStrategy 去建立圖層，他會先去抓第一個圖層，做好事情之後自動去抓第二個圖層來跑，並用show()來顯示已經跑好的圖層
		// 可以讓我們的畫面看起來更流暢
		BufferStrategy bufferStrategy = getBufferStrategy(); // 拿到圖層
		if (bufferStrategy == null) { // 如果沒有
			createBufferStrategy(3); // 建立三個圖層

			// 因為第一次執行 background.drawString 會卡頓，因此在程式碼一開始執行的時候執行一次
			bufferStrategy = getBufferStrategy();
			background = bufferStrategy.getDrawGraphics();
			background.setColor(Color.WHITE);
			background.setFont(new Font("Courier", Font.BOLD, 50));
			background.drawString("456", -100, -100);
			return;
		}
		
		background = bufferStrategy.getDrawGraphics();
		background.setColor(Color.WHITE); // 設定background顏色 // 也可以用 new Color(r, g, b)
		background.fillRect(0, 0, GameParameter.WIDTH, GameParameter.HEIGHT); // 設定background位置跟大小

		background.setColor(Color.MAGENTA);
		background.setFont(new Font("Courier", Font.BOLD, spriteSize));
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Floor1_Obstacle || tile.getId() == Id.Floor2_Obstacle || tile.getId() == Id.Floor3_Obstacle || tile.getId() == Id.Floor4_Obstacle) {
				if (tile.isScoreAdd() == true) {
					background.drawString("10x" + (game_bonus-1), tile.getX()-spriteSize/2, tile.getY());
				}
			}
		}

		handler.render(background); // 顯示所有的 linkedlist 的東西，所有的entity跟tile
		background.dispose(); // 顯示background
		bufferStrategy.show();
	}

	/*
	 * 更新數據
	 * 
	 */
	@Override
	public void update() {
		if (FIRST_RUN == true || GAME_NOT_STARTED == false)
			handler.update();
	}

	public static void gameReset() {
		handler.resetLinkedList();
		handler.createStuff();
		game_time = 0;
		numOfObstacles = totalObstacles;
		life = GameParameter.INIT_LIVES * 200;
		game_score = 0;
		game_bonus = 1;
	}

	public static void main(String[] args) {
//		rnd.setSeed();
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
