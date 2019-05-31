package com.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;
import com.main.gfx.Image;
import com.main.gfx.ImageSheet;
import com.main.input.Button;
import com.main.input.KeyInput;
import com.main.item.Handler;
import com.main.item.Id;
import com.main.item.tile.Floor1_Obstacle;
import com.main.item.tile.Floor2_Obstacle;
import com.main.item.tile.Floor3_Obstacle;
import com.main.item.tile.Floor4_Obstacle;
import com.main.item.tile.Heart;
import com.main.item.tile.Tile;
import com.main.item.tile.Treasure;

@SuppressWarnings("serial") // 有個奇怪的warning，用這個可以消除
public class Game extends Canvas implements Runnable, GameParameter {

	public static DataFile dataFile = null;
	public static JFrame gameFrame = null;
	public static Button button = null;
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
	public static Image logoImage;
	public static Image logoDeathImage;
	public static Image treasureImage;
	public static boolean whenDeathOccur = false;
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
		ImageSheet imageSheet = new ImageSheet("/resSheet.png"); // 拿取圖片資源

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

		imageSheet = new ImageSheet("/Scenes/Logo.png");
		logoImage = new Image(imageSheet, 0, 0, -1);

		imageSheet = new ImageSheet("/Scenes/DeathScreen.png");
		logoDeathImage = new Image(imageSheet, 0, 0, -1);

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
			}
		}
		stop(); // 整個遊戲停止
	}

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
				break;
			case 2:
				handler.addTile(new Floor2_Obstacle(Id.Floor2_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 2 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				break;
			case 3:
				handler.addTile(new Floor3_Obstacle(Id.Floor3_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 3 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
				break;
			case 4:
				handler.addTile(new Floor4_Obstacle(Id.Floor4_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 4 / 4 - spriteSize - Game.FLOOR_HEIGHT, spriteSize, spriteSize));
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
			return;
		}

		background = bufferStrategy.getDrawGraphics();
		background.setColor(Color.WHITE); // 設定background顏色 // 也可以用 new Color(r, g, b)
		background.fillRect(0, 0, GameParameter.WIDTH, GameParameter.HEIGHT); // 設定background位置跟大小

		// 顯示得到分數
		background.setColor(Color.MAGENTA);
		background.setFont(new Font("Courier", Font.BOLD, spriteSize));
		for (int i = 0; i < Game.handler.tileLinkedList.size(); i++) {
			Tile tile = Game.handler.tileLinkedList.get(i);
			if (tile.getId() == Id.Floor1_Obstacle || tile.getId() == Id.Floor2_Obstacle
					|| tile.getId() == Id.Floor3_Obstacle || tile.getId() == Id.Floor4_Obstacle) {
				if (tile.isScoreAdd() == true) {
					background.drawString("10x" + (game_bonus - 1), tile.getX() - spriteSize / 2, tile.getY());
				}
			}
		}

		// 顯示所有的 linkedlist 的東西，所有的entity跟tile
		handler.render(background);

		// 當遊戲死亡
		if (FIRST_RUN == false && GAME_NOT_STARTED == true) {
			// 顯示總分數在最後的畫面
			// 顯示 0 POINT
			background.setColor(Color.MAGENTA);
			background.setFont(new Font("Courier", Font.BOLD, 50));
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH) {
					background.drawString(game_score + " POINT", tile.getX() + 110, tile.getY() + 70);
				}
			}

			// 顯示寶物個數
			background.setColor(Color.BLACK);
			background.setFont(new Font("Courier", Font.BOLD, 40));
			int logodeath_x = 0, logodeath_y = 0;
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH) {
					logodeath_x = tile.getX() + tile.getWidth();
					logodeath_y = tile.getY() + tile.getHeight();
					background.drawString(dataFile.getNumOfTreasure() + "/11", tile.getX() + tile.getWidth() - 150,
							tile.getY() + tile.getHeight() - 80);
				} else if (tile.getId() == Id.TREASURE) {
					tile.setX(logodeath_x-330);
					tile.setY(logodeath_y-380);
				}
			}
		}

		// 顯示REST
		background.setColor(Color.BLACK);background.setFont(new Font("Courier",Font.BOLD,(int)(GameParameter.FLOOR_HEIGHT*1.1)));background.drawString("REST",GameParameter.WIDTH-190,GameParameter.HEIGHT);
	
		// 顯示numOfObstacles
		background.setColor(Color.WHITE);background.setFont(new Font("Courier",Font.BOLD,(int)(GameParameter.FLOOR_HEIGHT*1.5)));background.drawString(Integer.toString(numOfObstacles),GameParameter.WIDTH-100,GameParameter.HEIGHT);
	
		// 顯示background
		background.dispose();bufferStrategy.show();

	}

	/*
	 * 更新數據
	 * 
	 */
	@Override
	public void update() {
		if (FIRST_RUN == true || GAME_NOT_STARTED == false) {
			// 遊戲剛開始、正在遊戲
			handler.update();
			if (FIRST_RUN == true)
				handler.addTile(
						new com.main.item.tile.Logo(Id.LOGO, Game.handler, (GameParameter.WIDTH - LOGO_WIDTH) / 2,
								(GameParameter.WIDTH - LOGO_HEIGHT) / 2 - 250, LOGO_WIDTH, LOGO_HEIGHT)); // (574, 736)
		} else if (FIRST_RUN == false && GAME_NOT_STARTED == true) {
			// 當遊戲死亡
			button.showDeathScreen();
			
			if (whenDeathOccur == false) {
				// 建立一個logo death
				handler.addTile(new com.main.item.tile.LogoDeath(Id.LOGODEATH, Game.handler,
						(GameParameter.WIDTH - LOGO_DEATH_WIDTH * 4 / 5) / 2,
						(GameParameter.WIDTH - LOGO_DEATH_HEIGHT * 4 / 5) / 2 - 180, LOGO_DEATH_WIDTH * 4 / 5,
						LOGO_DEATH_HEIGHT * 4 / 5)); // (574, 736)
				
				// 建立treasure圖案

				ImageSheet imageSheet = null;
				// 確認分數，得到寶物
				if (game_score >= 1000) {
					dataFile.gainTreasure(10);
					imageSheet = new ImageSheet("/Treasure/10.png");
				} else if (game_score >= 900) {
					dataFile.gainTreasure(9);
					imageSheet = new ImageSheet("/Treasure/9.png");
				} else if (game_score >= 800) {
					dataFile.gainTreasure(8);
					imageSheet = new ImageSheet("/Treasure/8.png");
				} else if (game_score >= 700) {
					dataFile.gainTreasure(7);
					imageSheet = new ImageSheet("/Treasure/7.png");
				} else if (game_score >= 600) {
					dataFile.gainTreasure(6);
					imageSheet = new ImageSheet("/Treasure/6.png");
				} else if (game_score >= 500) {
					dataFile.gainTreasure(5);
					imageSheet = new ImageSheet("/Treasure/5.png");
				} else if (game_score >= 400) {
					dataFile.gainTreasure(4);
					imageSheet = new ImageSheet("/Treasure/4.png");
				} else if (game_score >= 300) {
					dataFile.gainTreasure(3);
					imageSheet = new ImageSheet("/Treasure/3.png");
				} else if (game_score >= 200) {
					dataFile.gainTreasure(2);
					imageSheet = new ImageSheet("/Treasure/2.png");
				} else if (game_score >= 100) {
					dataFile.gainTreasure(1);
					imageSheet = new ImageSheet("/Treasure/1.png");
				} else {
					dataFile.gainTreasure(0);
					imageSheet = new ImageSheet("/Treasure/0.png");
				}

				treasureImage = new Image(imageSheet, 0, 0, -1);
				handler.addTile(new Treasure(Id.TREASURE, handler, (GameParameter.WIDTH - LOGO_DEATH_WIDTH * 4 / 5) / 2,
						(GameParameter.WIDTH - LOGO_DEATH_HEIGHT * 4 / 5) / 2 - 180, 200, 200));

				
				whenDeathOccur = true;
			}

			// 跑logo death的 update()
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH) {
					tile.update();
				}
			}
		}

	}

	public static void gameReset() {
		handler.resetLinkedList();
		handler.createStuff();
		game_time = 0;
		numOfObstacles = totalObstacles;
//		life = GameParameter.INIT_LIVES;
		life = 1;
		game_score = 0;
		game_bonus = 1;
		whenDeathOccur = false;
	}

	public static void main(String[] args) {
//		rnd.setSeed();
		dataFile = new DataFile();

		// 建立遊戲
		Game game = new Game();
		gameFrame = new JFrame(GameParameter.TITLE_STRING); // 建立視窗
		button = new Button();
		button.addInJFrame(gameFrame);
		gameFrame.add(game); // 把game加進去視窗當中
		gameFrame.pack(); // 讓視窗大小跟著game設定的一樣大
		gameFrame.setResizable(false); // 讓視窗不能拉大拉小
		gameFrame.setLocationRelativeTo(null); // 讓視窗預設出現在螢幕中間
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 讓視窗按下x時關閉
		gameFrame.setVisible(true); // 讓視窗顯示出來
		game.start(); // 開始thread

		gameFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dataFile.writeFile();
				System.out.println(dataFile.getNumOfTreasure());
			}
		});

		/*
		 * 用thread的wait()才能暫停音樂 http://www.javazoom.net/javalayer/javalayer.html
		 */
	}

}
