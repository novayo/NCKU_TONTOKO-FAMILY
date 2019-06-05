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
import com.main.item.Item;
import com.main.item.entity.contra.Contra_Jump;
import com.main.item.entity.contra.Contra_Run;
import com.main.item.entity.dino.Dino_Squart;
import com.main.item.entity.dino.Dino_Stand_Run;
import com.main.item.tile.Heart;
import com.main.item.tile.ShowingButtons;
import com.main.item.tile.Tile;
import com.main.item.tile.Treasure;
import com.main.item.tile.contra.Contra_Obstacle_Boss;
import com.main.item.tile.contra.Contra_Obstacle_Insects;
import com.main.item.tile.dino.Dino_Obstacle;
import com.main.item.tile.floor.Floor2_Background;
import com.main.item.tile.taiko.Taiko_Obstacle;
import com.main.item.tile.tontoko.Tontoko_Obstacle;
import com.main.music.Music;

@SuppressWarnings("serial") // �����芰�arning嚗��隞交�
public class Game extends Canvas implements Runnable, GameParameter {

	public static DataFile dataFile = null;
	public static JFrame gameFrame = null;
	public static Button button = null;
	public static Graphics background = null;
	public static int initialNumTile = 0;
	public static int life = GameParameter.INIT_LIVES;
	public static int game_time = 0;
	public static int numOfObstacles = totalObstacles;
	public static int maxObstaclesOnScreen = 2;
	public static int game_score = 0;
	public static int game_bonus = 1;
	public static int deathlogo = 0;
	public static boolean GAME_STATE = false; // ���������
	public static boolean GAME_NOT_STARTED = true; // �銝�����香鈭�
	public static boolean FIRST_RUN = true;
	public static ImageSheet imageSheet = null;
	public static Image immutableSheet = null; // 霈����＊蝷箇��
	public static Dino_Stand_Run dino_Stand_Run = null;
	public static Dino_Squart dino_Squart = null;
	public static Contra_Run contra_Run = null;
	public static Contra_Jump contra_Jump = null;
	public static int contra_InsectBullets = 0;
	public static Contra_Obstacle_Boss contra_Obstacle_Boss = null;
	public static Floor2_Background floor2_Background = null;
	public static Heart heartObj;
	public static ShowingButtons showingButtons = null;
	public static Image treasureImage;
	public static boolean runOnce = false;
	public static Handler handler; // �憓�����隞�
	private static Random rnd = new Random();

	public Game() {
		Dimension size = new Dimension(GameParameter.WIDTH, GameParameter.HEIGHT);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}

	private void initialize() {
		// ��������
		imageSheet = new ImageSheet("/Image/ResSheet.png"); // ��������
		immutableSheet = new Image(imageSheet, 32, 32, Id.GET_ONE_OF_SHEET);

		// �銝�������憓镼�
		handler = new Handler();
		handler.createStuff();
		initialNumTile = handler.tileLinkedList.size(); // 1���logo
		life = GameParameter.INIT_LIVES - 1; // ������銋����璇�

		// ���皜祇�
		addKeyListener(new KeyInput());
		requestFocus(); // 霈����������◤���������
	}

	/*
	 * THEAD
	 * 
	 * synchronized ��鈭��hread��郊 implements Runnable ��� thread��撌梯��
	 * run��
	 */
	private Thread threadUpdate;
	private Thread threadRender;
	private int controlThread = 0;
	private int frames = 0;

	public synchronized void start() {
		if (GAME_STATE == true)
			return; // 蝣箔�蝙�game.start()�����ain.GAME_RUNNING�false
		else
			GAME_STATE = true;

		threadRender = new Thread(this, "ThreadRender");
		threadUpdate = new Thread(this, "threadUpdate");
		threadUpdate.start(); // thread ����
	}

	private synchronized void stop() {
		if (GAME_STATE == false)
			return; // 蝣箔�蝙�game.stop()�����ain.GAME_RUNNING�true
		else
			GAME_STATE = false;

		try {
			threadUpdate.join(); // thread ��迫
			threadRender.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Thread ��撌梯��ㄐ
	// �ㄐ撠望頝���ame���
	public void run() {
		if (controlThread == 1) {
			while (GAME_STATE == true) {
				frames++;
				render(background);
			}
		}

		initialize();
		controlThread++;
		threadRender.start(); // 靽����pdate���ender
		// ��瘥�隞亥�嗾甈� render() 嚗� update()
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;

		while (GAME_STATE == true) {
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / ns;
			lastTime = nowTime;

			// 瘥�1/60蝘�
			while (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}

			// 瘥�蝘�
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("瘥�蝘��  " + frames + "甈〉ender()  " + updates + "甈「pdate()");
				frames = 0;
				updates = 0;
				buildObstacles();
				if (GAME_NOT_STARTED == false)
					game_time++;
			}
		}
		stop(); // �����迫
	}

	/*
	 * 憿舐內����
	 * 
	 */
	@Override
	public void render(Graphics background) {
		// ��render頝�翰嚗����閬������嚗����頛詨����嚗���load�脖��璅�����������閬���鞎餌��
		// ��迨���� bufferStrategy
		// �撱箇��惜嚗�����洵銝���惜嚗�末鈭�������洵鈭��惜靘��蒂�show()靘＊蝷箏歇蝬�末���惜
		// �隞亥�������絲靘瘚
		BufferStrategy bufferStrategy = getBufferStrategy(); // ����惜
		if (bufferStrategy == null) { // 憒����
			createBufferStrategy(4); // 撱箇����惜
			return;
		}

		background = bufferStrategy.getDrawGraphics();
		background.setColor(Color.WHITE); // 閮剖�ackground憿 // 銋隞亦 new Color(r, g, b)
		background.fillRect(0, 0, GameParameter.WIDTH, GameParameter.HEIGHT); // 閮剖�ackground雿蔭頝之撠�

		// 憿舐內������ linkedlist ��镼選�����ntity頝ile
		handler.render(background);

		// ���甇颱滿
		if (FIRST_RUN == false && GAME_NOT_STARTED == true) {
			// 憿舐內蝮賢����敺��
			// 憿舐內 0 POINT
			background.setColor(Color.MAGENTA);
			background.setFont(new Font("Courier", Font.BOLD, 50));
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH) {
					background.drawString(game_score + " POINT", tile.getX() + 110, tile.getY() + 70);
				}
			}

			// 憿舐內撖嗥
			background.setColor(Color.BLACK);
			background.setFont(new Font("Courier", Font.BOLD, 40));
			int logodeath_x = 0, logodeath_y = 0;
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH) {
					// 憿舐內撖嗥�
					logodeath_x = tile.getX() + tile.getWidth();
					logodeath_y = tile.getY() + tile.getHeight();
					background.drawString(dataFile.getNumOfTreasure() + "/11", tile.getX() + tile.getWidth() - 150,
							tile.getY() + tile.getHeight() - 80);
				} else if (tile.getId() == Id.TREASURE) {
					// 憿舐內撖嗥����
					tile.setX(logodeath_x - 330);
					tile.setY(logodeath_y - 400);
				}
			}
		}

		// 憿舐內REST
		background.setColor(Color.BLACK);
		background.setFont(new Font("Courier", Font.BOLD, (int) (GameParameter.FLOOR_HEIGHT * 1.1)));
		background.drawString("REST", GameParameter.WIDTH - 190, GameParameter.HEIGHT);

		// 憿舐內numOfObstacles
		background.setColor(Color.WHITE);
		background.setFont(new Font("Courier", Font.BOLD, (int) (GameParameter.FLOOR_HEIGHT * 1.5)));
		background.drawString(Integer.toString(numOfObstacles), GameParameter.WIDTH - 100, GameParameter.HEIGHT);

		// 憿舐內background
		background.dispose();
		bufferStrategy.show();
	}

	/*
	 * ������
	 * 
	 */
	@Override
	public void update() {
		if (FIRST_RUN == true || GAME_NOT_STARTED == false) {
			// �������迤���
			handler.update();
			if (FIRST_RUN == true && runOnce == false) {
				handler.addTile(
						new com.main.item.tile.Logo(Id.LOGO, Game.handler, (GameParameter.WIDTH - LOGO_WIDTH) / 2,
								(GameParameter.WIDTH - LOGO_HEIGHT) / 2 - 250, LOGO_WIDTH, LOGO_HEIGHT)); // (574, 736)
				runOnce = true;
			}
		} else if (FIRST_RUN == false && GAME_NOT_STARTED == true) {
			// ���甇颱滿
			button.showDeathScreen();

			if (runOnce == false) {
				// 撱箇���ogo death
				handler.addTile(new com.main.item.tile.LogoDeath(Id.LOGODEATH, Game.handler,
						(GameParameter.WIDTH - LOGO_DEATH_WIDTH * 4 / 5) / 2,
						(GameParameter.WIDTH - LOGO_DEATH_HEIGHT * 4 / 5) / 2 - 180, LOGO_DEATH_WIDTH * 4 / 5,
						LOGO_DEATH_HEIGHT * 4 / 5)); // (574, 736)
				Game.playSoundEffect("showdeathscene.wav");

				// 撱箇�reasure����
				ImageSheet imageSheet = null;
				// 蝣箄��嚗�撖嗥
				if (game_score >= 2000) {
					dataFile.gainTreasure(10);
					imageSheet = new ImageSheet("/Image/Treasure/10.png");
				} else if (game_score >= 1800) {
					dataFile.gainTreasure(9);
					imageSheet = new ImageSheet("/Image/Treasure/9.png");
				} else if (game_score >= 1600) {
					dataFile.gainTreasure(8);
					imageSheet = new ImageSheet("/Image/Treasure/8.png");
				} else if (game_score >= 1400) {
					dataFile.gainTreasure(7);
					imageSheet = new ImageSheet("/Image/Treasure/7.png");
				} else if (game_score >= 1200) {
					dataFile.gainTreasure(6);
					imageSheet = new ImageSheet("/Image/Treasure/6.png");
				} else if (game_score >= 1000) {
					dataFile.gainTreasure(5);
					imageSheet = new ImageSheet("/Image/Treasure/5.png");
				} else if (game_score >= 800) {
					dataFile.gainTreasure(4);
					imageSheet = new ImageSheet("/Image/Treasure/4.png");
				} else if (game_score >= 600) {
					dataFile.gainTreasure(3);
					imageSheet = new ImageSheet("/Image/Treasure/3.png");
				} else if (game_score >= 400) {
					dataFile.gainTreasure(2);
					imageSheet = new ImageSheet("/Image/Treasure/2.png");
				} else if (game_score >= 200) {
					dataFile.gainTreasure(1);
					imageSheet = new ImageSheet("/Image/Treasure/1.png");
				} else {
					dataFile.gainTreasure(0);
					imageSheet = new ImageSheet("/Image/Treasure/0.png");
				}

				treasureImage = new Image(imageSheet, 0, 0, Id.GET_WHOLE_SHEET);
				handler.addTile(new Treasure(Id.TREASURE, handler, (GameParameter.WIDTH - LOGO_DEATH_WIDTH * 4 / 5) / 2,
						(GameParameter.WIDTH - LOGO_DEATH_HEIGHT * 4 / 5) / 2 - 180, 200, 200));

				runOnce = true;
			}

			// 頝ogo death��� update()
			for (int i = 0; i < handler.tileLinkedList.size(); i++) {
				Tile tile = handler.tileLinkedList.get(i);
				if (tile.getId() == Id.LOGODEATH || tile.getId() == Id.TREASURE) {
					tile.update();
				}
			}
		}

	}

	/*
	 * �璈��� 蝮賢�����100��umObstacle = 100嚗�
	 * 銝�����銝剖�����憭�2���嚗axObstaclesOnScreen = 3嚗�
	 * 銋����20�停������1����嚗ifficulty = 15嚗�
	 * 
	 * Game.handler.tileLinkedList.size() ���5��
	 */
	public void buildObstacles() {
		int obstacleRange = 500;
		if (numOfObstacles > totalObstacles - 1 * difficulty) { // 95
			maxObstaclesOnScreen = 2;
		} else if (numOfObstacles > totalObstacles - 2 * difficulty) { // 90
			maxObstaclesOnScreen = 3;
		} else if (numOfObstacles > totalObstacles - 4 * difficulty) { // 80
			maxObstaclesOnScreen = 4;
		} else if (numOfObstacles > totalObstacles - 6 * difficulty) { // 70
			maxObstaclesOnScreen = 5;
		} else if (numOfObstacles > totalObstacles - 8 * difficulty) { // 60
			maxObstaclesOnScreen = 6;
		} else {
			maxObstaclesOnScreen = 8;
			obstacleRange = 600;
		}

		int floorCanMove = (Game.FIRST_RUN == true || (maxObstaclesOnScreen - 1) > 4) ? 4 : (maxObstaclesOnScreen - 1);
		int tmp = handler.tileLinkedList.size() - initialNumTile - contra_InsectBullets - deathlogo;
//System.out.println(tmp);
		if (numOfObstacles == 0 && tmp <= 0) {
			deathlogo = 2;
			GAME_NOT_STARTED = true;
//			System.out.println("Win");
		}
		loop1: while (tmp < maxObstaclesOnScreen && numOfObstacles > 0) {
			int randomFloor = rnd.nextInt(floorCanMove) + 1;
			int randomPos = rnd.nextInt(100) + 60;

//			 randomFloor = 1;

			for (Tile tile : handler.tileLinkedList) {
				if (tile.getId() == Id.Tontoko_Obstacle) {
					if (randomFloor == 1 && tile.getY() <= GameParameter.HEIGHT * 1 / 4 - 10) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < obstacleRange) {
							break loop1;
						}
					} // ������蔭憭芰餈停break���
				} else if (tile.getId() == Id.Taiko_Obstacle_RED || tile.getId() == Id.Taiko_Obstacle_BLUE) {
					if (randomFloor == 2 && tile.getY() <= GameParameter.HEIGHT * 2 / 4 - 10) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < obstacleRange)
							break loop1;
					} // ������蔭憭芰餈停break���
				} else if (tile.getId() == Id.ContraInsects) {
					if (randomFloor == 3 && tile.getY() <= GameParameter.HEIGHT * 3 / 4 - 10) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < obstacleRange)
							break loop1;
					} // ������蔭憭芰餈停break���
				} else if (tile.getId() == Id.Dino_Obstacle || tile.getId() == Id.Dino_Obstacle0) {
					if (randomFloor == 4 && tile.getY() <= GameParameter.HEIGHT * 4 / 4 - 10) {
						if ((GameParameter.WIDTH + randomPos) - tile.getX() < obstacleRange)
							break loop1;
					} // ������蔭憭芰餈停break���
				}
			}

			if (GAME_NOT_STARTED == false) {
				numOfObstacles--; // 瘥香������嚗停霈��-1
			}

			int whichObstacle = rnd.nextInt(2);
			switch (randomFloor) {
			case 1:
				handler.addTile(new Tontoko_Obstacle(Id.Tontoko_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
						GameParameter.HEIGHT * 1 / 4 - 34 - Game.FLOOR_HEIGHT, 40, 34));
				break;
			case 2:
				if (whichObstacle == 0)
					handler.addTile(
							new Taiko_Obstacle(Id.Taiko_Obstacle_RED, Game.handler, GameParameter.WIDTH + randomPos,
									GameParameter.HEIGHT * 2 / 4 - 60 - Game.FLOOR_HEIGHT - 65, 60, 60, whichObstacle));
				else
					handler.addTile(
							new Taiko_Obstacle(Id.Taiko_Obstacle_BLUE, Game.handler, GameParameter.WIDTH + randomPos,
									GameParameter.HEIGHT * 2 / 4 - 60 - Game.FLOOR_HEIGHT - 65, 60, 60, whichObstacle));
				break;
			case 3:
				int randomY = rnd.nextInt(106) + 5; // 5 ~ 110
				handler.addTile(
						new Contra_Obstacle_Insects(Id.ContraInsects, Game.handler, Game.contra_Obstacle_Boss.getX(),
								GameParameter.HEIGHT * 3 / 4 - Game.FLOOR_HEIGHT - 40 - randomY, 40, 40));
				break;
			case 4:
				int addY = 0;
				if (whichObstacle == 0) {
					addY = -20; // 憒�憌���蔭銝��40
					handler.addTile(new Dino_Obstacle(Id.Dino_Obstacle, Game.handler, GameParameter.WIDTH + randomPos,
							GameParameter.HEIGHT * 4 / 4 - 60 - Game.FLOOR_HEIGHT + addY, 52, 33, whichObstacle));
				} else {
					handler.addTile(new Dino_Obstacle(Id.Dino_Obstacle0, Game.handler, GameParameter.WIDTH + randomPos,
							GameParameter.HEIGHT * 4 / 4 - 40 - Game.FLOOR_HEIGHT + addY, 40, 40, whichObstacle));
				}
				break;
			default:
				break;
			}
			tmp++;
		}
	}

	public static void gameReset() {
		game_time = 0;
		deathlogo = 0;
		numOfObstacles = totalObstacles;
		maxObstaclesOnScreen = 2;
		life = GameParameter.INIT_LIVES;
//		life = 1;
		game_score = 0;
		game_bonus = 1;
		runOnce = false;
		contra_InsectBullets = 0;
		Item.moveSpeedfloor1 = 3;
		Item.moveSpeedfloor2 = 2;
		Item.moveSpeedfloor3 = 4;
		Item.moveSpeedfloor4 = 6;
		handler.resetLinkedList();
		handler.createStuff();
		playBackgroundMusic("overworld0.wav");
	}

	public static void playBackgroundMusic(String path) {
		@SuppressWarnings("unused")
		Music music = new Music("./Java_Final_Project/res/Music/" + path, Id.Music_Background);
	}

	public static void playSoundEffect(String path) {
		@SuppressWarnings("unused")
		Music music = new Music("./Java_Final_Project/res/Music/" + path, Id.Music_SoundEffect);
	}

	public static void main(String[] args) {
		rnd.setSeed(System.currentTimeMillis()); // 閮剖�� 鈭��車摮�
		dataFile = new DataFile(); // 閮�窄�

		// 撱箇��
		Game game = new Game();
		gameFrame = new JFrame(GameParameter.TITLE_STRING); // 撱箇����
		button = new Button();
		button.addInJFrame(gameFrame);
		gameFrame.add(game); // ��ame���脣閬�銝�
		gameFrame.pack(); // 霈��之撠��ame閮剖���璅�憭�
		gameFrame.setResizable(false); // 霈�����之����
		gameFrame.setLocationRelativeTo(null); // 霈���身����撟葉���
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 霈���������
		gameFrame.setVisible(true); // 霈��＊蝷箏靘�
		game.start(); // ���hread
		playBackgroundMusic("opening.wav");

		// �雿��銝�����
		gameFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dataFile.writeFile();
			}
		});
	}

}
