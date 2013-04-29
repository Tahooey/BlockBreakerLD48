package game;

import java.awt.*;
import def.Frame;

public class BackGround {
	
	public static int dx,dy;
	static int offsetX=0,offsetY=0;
	
	static int doubler=0;
	
	public static final Color c1 = new Color(127,212,255);
	public static final Color c2 = new Color(127,170,255);
	
	public static final int WIDTH=8,HEIGHT=8;
	public static int finalw=(WIDTH*def.Frame.SCALE)-1,finalh=(HEIGHT*def.Frame.SCALE)-1;
	
	public static void draw(Graphics g){
		for(int i=-finalw*2;i<(def.Frame.WIDTH/finalw)+(finalw*2);i++){
			for(int j=-finalh*2;j<(def.Frame.HEIGHT/finalh)+(finalh*2);j++){
				if(j%2==0){
					if(i%2==0){
						g.setColor(c2);
					}else{
						g.setColor(c1);
					}
				}else{
					if(i%2==0){
						g.setColor(c1);
					}else{
						g.setColor(c2);
					}
				}
				g.fillRect(i*finalw+offsetX, j*finalh+offsetY, finalw, finalh);
			}
		}
	}
	
	public static void setDX(int i){
		dx=i;
	}
	
	public static void setDY(int i){
		dy=i;
	}
	
	public static void move(int dir) {
		if (dir == Frame.STILL) {
			setDX(0);
			setDY(0);
		}
		if (dir == Frame.LEFT) {
			setDY(0);
			setDX(1);
		}
		if (dir == Frame.UP) {
			setDX(0);
			setDY(1);
		}
		if (dir == Frame.RIGHT) {
			setDX(-1);
			setDY(0);
		}
		if (dir == Frame.DOWN) {
			setDX(0);
			setDY(-1);
		}
	}
	
	public static void update(){
		doubler+=1;
		if(def.Frame.GAME.MOBS.MobsOnMap[0].dx!=0){
				offsetX=offsetX+dx;
				offsetY=offsetY+dy;
		}
		if(offsetX==-finalw*2){
			offsetX=0;
		}
		if(offsetY==-finalh*2){
			offsetY=0;
		}
		if(offsetX==finalw*2){
			offsetX=0;
		}
		if(offsetY==finalh*2){
			offsetY=0;
		}
	}

}
