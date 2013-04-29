package game;

import java.awt.*;
import java.util.Random;

import def.Camera;

public class Tile {
	
	public int x,y,finalx,finaly;
	public int ID;
	
	Random ran = new Random();
	
	public Particle[] particles = new Particle[8];
	
	public boolean showRectangle=false;
	
	public boolean hasNotExploded=true;
	
	public int explosionStartTick=0;
	
	public boolean hasStartedDetonation=false;
	public boolean isDetonating=false;
	
	public boolean Visible=true,Collidable=true,Explodable=true,doesExplode=false;;
	
	public Rectangle r = new Rectangle();
	
	public int R,G,B;
	
	public static final int WIDTH=8,HEIGHT=8;
	public int finalw,finalh;
	
	public Color COLOR;
	
	public Tile(Tile i){
		R=i.R;
		B=i.B;
		G=i.G;
		ID=i.ID;
		Visible=i.Visible;
		Collidable=i.Collidable;
		Explodable=i.Explodable;
		doesExplode=i.doesExplode;
		COLOR=i.COLOR;
	}
	
	Thread bombDetonation = new Thread(){
		public void run(){
			if(!hasStartedDetonation){
				for(int i=0;i<particles.length;i++){
					particles[i]=new Particle(x,y,i,new Color(0,0,0),finalx,finaly,ran.nextInt(5)-2,ran.nextInt(4)+1,ran.nextInt(5)+5,ran.nextInt(5)+5,100);
				}
				explosionStartTick=def.Game.tick;
				//System.out.println("Hi");
			}
			detonate();
		}
	};
	
	public void detonate(){
		hasStartedDetonation=true;
		if(def.Game.tick<explosionStartTick+125){
			if(def.Game.tick==explosionStartTick+25){
				COLOR=new Color(255-R,255-G,255-B);
			}else if(def.Game.tick==explosionStartTick+50){
				COLOR=new Color(R,G,B);
			}else if(def.Game.tick==explosionStartTick+75){
				COLOR=new Color(255-R,255-G,255-B);
			}else if(def.Game.tick==explosionStartTick+100){
				COLOR=new Color(R,G,B);
			}else if(def.Game.tick>=explosionStartTick+125-1){
				explode();
				hasNotExploded=false;
			}
		}
	}
	
	public void beExploded(){
		if(ID!=0){
			if(Explodable){
				def.Frame.GAME.POINTS++;
				def.Frame.GAME.MAP.Tiles[y][x]=new Tile(def.Frame.GAME.MAP.allTiles[0]);
				def.Frame.GAME.MAP.Tiles[y][x].x=x;
				def.Frame.GAME.MAP.Tiles[y][x].y=y;
				def.Frame.GAME.MAP.Tiles[y][x].boot();
			}
		}
	}
	
	public void explode(){
		def.Frame.GAME.POINTS-=5;
		getCustomTile(-1,-3).beExploded();
		getCustomTile(0,-3).beExploded();
		getCustomTile(1,-3).beExploded();
		getCustomTile(-2,-2).beExploded();
		getCustomTile(-1,-2).beExploded();
		getCustomTile(0,-2).beExploded();
		getCustomTile(1,-2).beExploded();
		getCustomTile(2,-2).beExploded();
		getCustomTile(-2,-1).beExploded();
		getCustomTile(-1,-1).beExploded();
		getCustomTile(0,-1).beExploded();
		getCustomTile(1,-1).beExploded();
		getCustomTile(2,-1).beExploded();
		getCustomTile(-2,-0).beExploded();
		getCustomTile(-1,-0).beExploded();
		getCustomTile(0,-0).beExploded();
		getCustomTile(1,-0).beExploded();
		getCustomTile(2,-0).beExploded();
		getCustomTile(-2,1).beExploded();
		getCustomTile(-1,1).beExploded();
		getCustomTile(0,1).beExploded();
		getCustomTile(1,1).beExploded();
		getCustomTile(2,1).beExploded();
		getCustomTile(-2,2).beExploded();
		getCustomTile(-1,2).beExploded();
		getCustomTile(0,2).beExploded();
		getCustomTile(1,2).beExploded();
		getCustomTile(2,2).beExploded();
		getCustomTile(-1,3).beExploded();
		getCustomTile(0,3).beExploded();
		getCustomTile(1,3).beExploded();
		def.Frame.GAME.MAP.Tiles[y][x]=new Tile(def.Frame.GAME.MAP.allTiles[0]);
	}
	
	public Tile(int R,int G,int B,int ID,String Visible,String Collidable,String Explodable,String doesExplode){
		boolean vis,coll,explodable,explodes;
		this.R=R;
		this.G=G;
		this.B=B;
		if(Visible.equals("true")){
			vis=true;
		}else{
			vis=false;
		}
		if(Collidable.equals("true")){
			coll=true;
		}else{
			coll=false;
		}
		if(Explodable.equals("true")){
			explodable=true;
		}else{
			explodable=false;
		}
		if(doesExplode.equals("true")){
			explodes=true;
		}else{
			explodes=false;
		}
		this.ID=ID;
		this.Visible=vis;
		this.Collidable=coll;
		this.Explodable=explodable;
		this.doesExplode=explodes;
		COLOR = new Color(R,G,B);
	}
	
	public void boot(){
		finalw=WIDTH*def.Frame.SCALE;
		finalh=HEIGHT*def.Frame.SCALE;
		
		finalx=x*finalw;
		finaly=y*finalh;
		
		r.setSize(finalw,finalh);
		r.setLocation(finalx+def.Camera.x,finaly+def.Camera.y);
	}
	
	public void update(){
		r.setSize(finalw,finalh);
		r.setLocation(finalx+def.Camera.x,finaly+def.Camera.y);
		if(isDetonating&&doesExplode){
			bombDetonation.run();
		}
	}
	
	public void draw(Graphics g){
		if(Visible){
			g.setColor(COLOR);
			g.fillRect(finalx+Camera.x, finaly+Camera.y, finalw, finalh);
		}
		if(showRectangle){
			g.setColor(Color.BLACK);
			g.drawRect(r.x, r.y, r.width, r.height);
			showRectangle=false;
		}
	}
	
	public void drawParticles(Graphics g){
		for(int i=0;i<particles.length;i++){
			if(particles[i]!=null){
				particles[i].draw(g);
			}
		}
	}
	
	public Tile getTileBelow(){
		int yco=y+1,xco=x;
		if(yco>=def.Frame.GAME.MAP.Tiles.length){
			yco=def.Frame.GAME.MAP.Tiles.length-1;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>=def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length-1;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}
	
	public Tile getTileAbove(){
		int yco=y-1,xco=x;
		if(yco>=def.Frame.GAME.MAP.Tiles.length){
			yco=def.Frame.GAME.MAP.Tiles.length-1;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>=def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length-1;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}
	
	public Tile getTileLeft(){
		int yco=y,xco=x-1;
		if(yco>=def.Frame.GAME.MAP.Tiles.length){
			yco=def.Frame.GAME.MAP.Tiles.length-1;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>=def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length-1;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}
	
	public Tile getTileRight(){
		int yco=y,xco=x+1;
		if(yco>=def.Frame.GAME.MAP.Tiles.length){
			yco=def.Frame.GAME.MAP.Tiles.length-1;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>=def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length-1;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}
	
	public void detonationParticles(Graphics g){
		if(hasStartedDetonation){
			int r=R-5,gr=G-5,b=B-5;
			if(r<0){
				r=0;
			}
			if(gr<0){
				gr=0;
			}
			if(b<0){
				b=0;
			}
			for(int i=0;i<8;i++){
				g.setColor(new Color(r,gr,b));
				
			}
		}
	}
	
	public Tile getCustomTile(int xOffset,int yOffset){
		int yco=y+yOffset,xco=x+xOffset;
		if(yco>=def.Frame.GAME.MAP.Tiles.length){
			yco=def.Frame.GAME.MAP.Tiles.length-1;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>=def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length-1;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}

}
