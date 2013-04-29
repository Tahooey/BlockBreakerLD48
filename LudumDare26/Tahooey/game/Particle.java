package game;

import java.awt.*;
import java.util.*;

public class Particle {
	
	Random r = new Random();
	
	int x,y,dx,dy,w,h;
	int tickWhenDies;
	
	int tileX,tileY;
	
	Color c;
	
	int ID;
	
	public Particle(int TileX,int TileY,int iD,Color c,int x,int y,int dx,int dy,int w,int h,int timeAlive){
		this.x=x;
		this.y=y;
		this.dx=dx*-1;
		this.dy=dy*-1;
		this.w=w;
		this.h=h;
		
		tileX=TileX;
		tileY=TileY;
		
		ID=iD;
		
		tickWhenDies=def.Game.tick+timeAlive;
		
		this.c=c;
		this.c=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}
	
	public void update(){
		x=x+dx;
		y=y+dy;
	}
	
	public void draw(Graphics g){
		if(def.Game.tick<=tickWhenDies){
			update();
			g.setColor(c);
			g.fillRect(x+def.Camera.x,y+def.Camera.y,w,h);
		}else{
			def.Frame.GAME.MAP.Tiles[tileY][tileX].particles[ID]=null;
		}
	}

}
