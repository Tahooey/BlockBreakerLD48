package game;

import java.awt.*;
import java.util.*;

import def.Fonts;

public class Menu {
	
	public static boolean aboutToPlay=false;
	
	public static boolean countingDown=false;
	
	public static int CountdownTick=0;
	
	public static Color[][] colors = new Color[16*def.Frame.SCALE][16*def.Frame.SCALE];
	public static Color TitleColor;
	
	public static Random r = new Random();
	
	public static void getColors(){
		for(int i=0;i<colors.length;i++){
			for(int j=0;j<colors[i].length;j++){
				colors[i][j]=new Color(r.nextInt(253)+1,r.nextInt(50)+150,(25)+150);
			}
		}
		TitleColor=Color.BLACK;
	}
	
	public static void drawBacking(Graphics g){
		if(!countingDown){
		for(int i=0;i<colors.length;i++){
			for(int j=0;j<colors[i].length;j++){
				g.setColor(colors[i][j]);
				g.fillRect(i*(Tile.WIDTH*def.Frame.SCALE), j*(Tile.HEIGHT*def.Frame.SCALE), Tile.WIDTH*def.Frame.SCALE, Tile.HEIGHT*def.Frame.SCALE);
			}
		}
		g.setFont(Fonts.TahooeyLarge);
		g.setColor(TitleColor);
		g.drawString("Block", 110, 64);
		g.drawString("Breaker!", 48, 128);
		
		g.setFont(Fonts.Tahooey);		
		g.drawString("Press O To Start", 56, def.Frame.HEIGHT-64);
		
		if(aboutToPlay){
			drawInfoPanel(g);
		}
		}
		
	}
	
	public static void drawCountdown(Graphics g){
		if(countingDown){
			g.setColor(new Color(255,111,2));
			g.fillRect(0,0,def.Frame.WIDTH,def.Frame.HEIGHT);
			g.setFont(Fonts.TahooeySmall);
			g.setColor(Color.BLACK);
			g.drawString((1000-CountdownTick)/100+" seconds till restart!",96,256);
			g.drawString("You got "+def.Frame.GAME.POINTS+" Points!",128,272);
		}
	}
	
	public static Thread countdown = new Thread(){
		public void run(){
			if(CountdownTick<1000){
				countingDown=true;
				CountdownTick++;
			}
		}
	};
	
	public static void restart(){
		countdown.run();
		if(CountdownTick==999){
			CountdownTick=0;
			def.Frame.GAME.loaded=false;
			def.Game.tick=0;
			def.Frame.GAME.POINTS=0;
			def.Frame.GAME.loadGame();
			def.Frame.GAME.completed=false;
			def.Frame.GAME.loaded=true;
			countingDown=false;
		}
	}
	
	public static void drawInfoPanel(Graphics g){
		g.setColor(new Color(255,111,2));
		g.fillRect(48,16,def.Frame.WIDTH-94,def.Frame.HEIGHT-32);
		
		if(def.Frame.GAME.loading){
			g.setColor(Color.black);
			g.setFont(Fonts.Tahooey);
			g.drawString("Loading", 152, 264);
		}
		
		g.setFont(Fonts.TahooeySmall);
		g.setColor(Color.BLACK);
		g.drawString("Welcome To Block Breaker", 64, 48);
		g.drawString("This game was created of LD48", 64, 64);
		g.drawString("Controls E: Place Bomb",64,96);
		g.drawString("Touch bomb to detonate", 64, 114);
		g.drawString("In this game you play a man", 64, 144);
		g.drawString("Made of 2 pixels", 64, 160);
		g.drawString("He Live's in a nice World, A bit", 64, 176);
		g.drawString("Pixelly, but so is he...", 64, 192);
		g.drawString("So yeah, he enjoys his life", 64, 208);
		g.drawString("The Only Problem?", 64, 224);
		g.drawString("Lack of Explosions!", 64, 320);
		g.drawString("BLOW IT UP! ALL OF IT", 64, 336);
		g.drawString("HAAHAHAHHAHAAHHA", 64, 352);
		g.drawString("Oh, yeah did I mention", 64, 368);
		g.drawString("In 100 Seconds", 64, 384);
		g.drawString("HAVE FUN!", 64, 400);
		
		g.drawString("Press P to Play", 160, 432);
	}

}
