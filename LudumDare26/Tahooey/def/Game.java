package def;

import game.*;
import game.Menu;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public boolean gameIsRunning=false,menuIsOpen=true,loaded=false,loading=false;
	public boolean completed=false;
	public int timeRemaining=0;
	
	public int POINTS=0;
	
	public Map MAP = new Map(new File(defaultDirectory()+"map.thu"),new File(defaultDirectory()+"tileDyn.thu"));
	public Mobs MOBS = new Mobs(new File(defaultDirectory()+"mobsOnMap.thu"),new File(defaultDirectory()+"mobsDyn.thu"));
	
	public static int controlledMob=1;
	
	public static int tick=0;

	private Image dbImage;
	public Graphics dbg;
	static final int W = Frame.WIDTH, H = Frame.HEIGHT;
	static final Dimension gameDim = new Dimension(W, H);
	
	public static int mobControlled=0;
	
	public boolean blankScreen=false;

	private Thread game;
	private volatile boolean running = false;
	//private long period = 6 * 1000000;

	public Game() {
		setPreferredSize(gameDim);
		setBackground(Color.WHITE);
		setFocusable(true);
		requestFocus();
		// Handle all key inputs from user
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int KeyCode=e.getKeyCode();
				if(gameIsRunning){
				if(KeyCode==KeyEvent.VK_W){
					//Camera.move(Frame.UP);
					MOBS.MobsOnMap[controlledMob].jump();
				}
				if(KeyCode==KeyEvent.VK_S){
					//Camera.move(Frame.DOWN);
				}
				if(KeyCode==KeyEvent.VK_A){
					Camera.move(Frame.LEFT);
					MOBS.MobsOnMap[controlledMob].move(Frame.LEFT);
				}
				if(KeyCode==KeyEvent.VK_D){
					Camera.move(Frame.RIGHT);
					MOBS.MobsOnMap[controlledMob].move(Frame.RIGHT);
				}
				if(KeyCode==KeyEvent.VK_E){
					MOBS.MobsOnMap[controlledMob].placeBomb();
				}
				}
				if(KeyCode==KeyEvent.VK_O){
					if(!Menu.aboutToPlay){
						Menu.aboutToPlay=true;
					}
				}
				if(KeyCode==KeyEvent.VK_P){
					if(Menu.aboutToPlay&&!loading){
						loadGame();
					}
					if(!loading&&loaded){
						gameIsRunning=true;
						menuIsOpen=false;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int KeyCode=e.getKeyCode();
				if(gameIsRunning){
					if(gameIsRunning){
						if(KeyCode==KeyEvent.VK_W){
							//Camera.move(Frame.UP);
							MOBS.MobsOnMap[controlledMob].isJumping=false;;
						}
						if(KeyCode==KeyEvent.VK_S){
							//Camera.move(Frame.DOWN);
						}
						if(KeyCode==KeyEvent.VK_A){
							Camera.move(Frame.STILL);
							MOBS.MobsOnMap[controlledMob].move(Frame.STILL);
						}
						if(KeyCode==KeyEvent.VK_D){
							Camera.move(Frame.STILL);
							MOBS.MobsOnMap[controlledMob].move(Frame.STILL);
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
	}

	@Override
	public void run() {
		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();
		}
	}
	
	public static String defaultDirectory(){
		String os = System.getProperty("os.name").toUpperCase();
		String folder = "";
		
		String SEP =  System.getProperty("file.separator");

		if(os.contains("MAC")){
			folder = System.getProperty("user.home") + SEP + "Library" + SEP + "Application Support" + SEP + "Tahooey"+SEP+"LD48"+SEP;
		}
		else if(os.contains("WIN")){
			folder = System.getProperty("user.home") + SEP + "AppData" + SEP + "Roaming" + SEP + "Tahooey"+SEP+"LD48"+SEP;
		}
		else {
			folder = System.getProperty("user.home") + SEP + "Tahooey"+SEP+"LD48"+SEP;
		}
		File f = new File(folder);
		if(!f.exists()) f.mkdirs();
		return folder;
	}
	
	public void findControlledMob(){
		for(int i=0;i<MOBS.MobsOnMap.length;i++){
			if(MOBS.MobsOnMap[i].isControlledByPlayer){
				controlledMob=i;
			}
		}
	}

	private void gameUpdate() {
		if (running && game != null) {
			if(menuIsOpen){
				
			}
			if(gameIsRunning&!menuIsOpen&&loaded&!completed){
				CamUpdater.run();
				BackGroundUpdater.run();
				TileUpdater.run();
				MobsUpdater.run();
				TickUpdater.run();
				
				if(tick==10000-1){
					completed=true;
				}
			}
			if(completed){
				Menu.restart();
			}
		}
	}
	
	Thread BackGroundUpdater = new Thread(){
		public void run(){
			BackGround.update();
		}
	};
	
	Thread CamUpdater = new Thread(){
		public void run(){
			Camera.update();
		}
	};
	Thread TileUpdater = new Thread(){
		public void run(){
			MAP.runMap();
		}
	};
	Thread MobsUpdater = new Thread(){
		public void run(){
			MOBS.runMobs();
			Mob.getBlocksOnMap();
		}
	};
	Thread TickUpdater = new Thread(){
		public void run(){
			if(tick<=10000){
				timeRemaining=(10000-tick)/100;
				tick++;
			}
		}
	};

	public void draw(Graphics g) {
		if(menuIsOpen){
			Menu.drawBacking(g);
		}
		if(gameIsRunning&!menuIsOpen&&loaded){
			BackGround.draw(g);
			MAP.drawMap(g);
			MOBS.drawMobs(g);
			g.setColor(Color.black);
			g.drawString("You have "+timeRemaining+" Seconds Left",10,30);
			g.drawString("You have "+POINTS+" POINTS",10,42);
		}
		if(Menu.countingDown){
			Menu.drawCountdown(g);
		}
		//g.fillRect(MOBS.MobsOnMap[0].r.x,MOBS.MobsOnMap[0].r.y,MOBS.MobsOnMap[0].r.width,MOBS.MobsOnMap[0].r.height);
	}

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(W, H);
			if (dbImage == null) {
				System.err.println("dbImage is still null!");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}
		dbg.setColor(Frame.BACKGROUND);
		dbg.fillRect(0, 0, W, H);
		draw(dbg);
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if (dbImage != null && g != null) {
				g.drawImage(dbImage, 0, 0, null);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void loadGame(){
		if(!loaded){
			loading=true;
			MAP.startupMap();
			MOBS.boot();
			findControlledMob();
			loading=false;
		}		
		loaded=true;
	}

	public void addNotify() {
		super.addNotify();
		Menu.getColors();
		try {
			Music.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Fonts.loadFont();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Music.sound1.loop();
		startGame();
	}

	private void startGame() {
		if (game == null || !running) {
			game = new Thread(this);
			running = true;
			game.start();
		}
	}

	public void stopGame() {
		if (running) {
			running = false;
		}
	}

	public void log(String i) {
		System.out.println(i);
	}

}
