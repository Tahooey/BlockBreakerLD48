package game;

import java.awt.*;

import def.Camera;
import def.Frame;

public class Mob {
	
	public boolean canPlaceBomb=true;
	
	public boolean canJump=true;
	public boolean isJumping=false;
	private Tile TileLaunchedFrom=null;
	
	public boolean overrideLeft=false,overrideRight=false;
	
	public Color HEAD;
	public Color BODY;
	
	static int smallestOnscreenwd = 0, smallestOnscreenhd = 0;
	static int largestOnscreenwd = 0, largestOnscreenhd = 0;

	static int smallestOnscreenwu = 0, smallestOnscreenhu = 0;
	static int largestOnscreenwu = 0, largestOnscreenhu = 0;
	
	public Rectangle ActualR = new Rectangle();
	public Rectangle r = new Rectangle();
	
	public boolean isDetonating=true;
	public boolean canDetonate=true;
	
	public int maxSpeed=10;
	public int acceleratedPace=1;
	
	public boolean accelerating=false,deaccelerting=false;
	
	public final int WIDTH=8,HEIGHT=8;
	public int finalw,finalh;
	
	public int x=0,y=0,finalx,finaly,dx=0,dy=0;
	public boolean isControlledByPlayer;
	
	public Mob(Mob m,String Name,int x,int y, String isControlledByPlayer){
		HEAD=m.HEAD;
		BODY=m.BODY;
		this.x=x;
		this.y=y;
		boolean con=false;
		if(isControlledByPlayer.equals("true")){
			con=true;
		}else{
			con=false;
		}
		this.isControlledByPlayer=con;
	}
	
	public void accelerate(){
		if(accelerating){
			for(int i=0;i<10000;i++){
				acceleratedPace=(i/1000);
				System.out.println(acceleratedPace);
				if(i==10000-1){
					accelerating=false;
				}
			}
		}
	}
	
	public static void getBlocksOnMap(){
		smallestOnscreenwd=Camera.x/(8*def.Frame.SCALE)*-1;
		largestOnscreenwd=smallestOnscreenwd+(def.Frame.WIDTH/(8*def.Frame.SCALE))+2;
		smallestOnscreenhd=Camera.y/(8*def.Frame.SCALE)*-1;
		largestOnscreenhd=smallestOnscreenhd+(def.Frame.HEIGHT/(8*def.Frame.SCALE))+2;
	}
	
	public Mob(int HeadR,int HeadG,int HeadB,int BodyR,int BodyG,int BodyB){
		HEAD=new Color(HeadR, HeadG,HeadB);
		BODY=new Color(BodyR, BodyG,BodyB);
	}
	
	public void setDX(int i){
		dx=i*def.Frame.SPEED;
	}
	
	public void setDY(int i){
		dy=i*def.Frame.SPEED;
	}
	
	public void move(int dir) {
		if (dir == Frame.STILL) {
			accelerating=false;
			setDX(0);
			setDY(0);
		}
		if (dir == Frame.RIGHT) {
			if(canMoveRight()){
				setDX(1);
			}else{
				setDX(0);
			}
		}
		if (dir == Frame.DOWN) {
			setDX(0);
			setDY(1);
		}
		if (dir == Frame.LEFT) {
			if(canMoveLeft()){
				setDX(-1);
			}else{
				setDX(0);
			}
		}
		if (dir == Frame.UP) {
			setDX(0);
			setDY(-1);
		}
	}
	
	public void gravity(){
		if(!isJumping){
			if(canMoveDown()){
					if(!getCustomTile(1,1).Collidable){
						setDY(def.Frame.SPEED/2);
					}else{
						setDY(0);
					}
			}else{
				if(r.intersects(getCustomTile(1,1).r)){
					setDY(0);
				}else{
					setDY(0);
				}
			}
		}
	}
	
	public void jump(){
		if(canJump){
		if(!isJumping){
			if(dy==0){
				TileLaunchedFrom=getTileBelow();
			}
		}
		if(TileLaunchedFrom!=null){
			isJumping=true;
			if(y>=TileLaunchedFrom.y-5){
				setDY(-def.Frame.SPEED/2);
			}else{
				isJumping=false;
				TileLaunchedFrom=null;
			}
		}
		}		
	}
	
	public void boot(){
		finalw=WIDTH*def.Frame.SCALE;
		finalh=HEIGHT*def.Frame.SCALE;
		
		finalx=x*finalw;
		finaly=y*finalh;
		
		r.setSize(finalw+(def.Frame.SPEED*2),(finalh*2)+(def.Frame.SPEED*2));
		r.setLocation(finalx+Camera.x,finaly+Camera.y);
	}
	
	public void update(){
		if((finalx+def.Camera.x)>=-finalw){
			if((finalx+def.Camera.x<def.Frame.WIDTH)){
				if(dy==0){
					isJumping=false;
				}
				if(!canMoveRight()){
					if(dx==1*def.Frame.SPEED){
						dx=0;
					}
				}
				if(!canMoveLeft()){
					if(dx==-1*def.Frame.SPEED){
						dx=0;
					}
				}
				finalx=finalx+dx;
				finaly=finaly+dy;
				x=finalx/finalw;
				y=finaly/(finalh);
				ActualR.setSize(finalw,(finalh*2));
				ActualR.setLocation(finalx,finaly);
				r.setSize(finalw+(def.Frame.SPEED*2),(finalh*2)+(def.Frame.SPEED*2));
				r.setLocation(finalx+Camera.x-def.Frame.SPEED,finaly+Camera.y-(def.Frame.SPEED));
				if(isDetonating){
					getTileBodyLeft().isDetonating=true;
					getTileBodyRight().isDetonating=true;
				}
				if(isJumping){
					jump();
				}					
				gravity();
			}
		}
		
		//accelerate();
	}
	
	public void placeBomb(){
		if(x!=0){
				if(y!=0){
						if(def.Frame.GAME.MAP.Tiles[y+1][x].Explodable){
							def.Frame.GAME.MAP.Tiles[y+1][x]=new Tile(def.Frame.GAME.MAP.allTiles[3]);
							def.Frame.GAME.MAP.Tiles[y+1][x].x=x;
							def.Frame.GAME.MAP.Tiles[y+1][x].y=y+1;
							def.Frame.GAME.MAP.Tiles[y+1][x].boot();
						}
					}
				}
	}
	
	public boolean canMoveLeft(){
		if(getTileHeadLeft().Collidable){
			if(getTileHeadLeft().r.intersects(r)){
				return false;
			}
		}
		if(getTileBodyLeft().Collidable){
			if(getTileHeadLeft().r.intersects(r)){
				return false;
			}
		}
		if(getTileBelow().Collidable){
			if(getTileBelow().r.intersects(ActualR)){
				return false;
			}
		}
		return true;
	}
	
	public boolean canMoveRight(){
		if(getTileBodyRight().Collidable){
			if(getTileBodyRight().r.intersects(r)){
				return false;
			}
		}
		if(getTileBodyRight().Collidable){
			if(getTileHeadRight().r.intersects(r)){
				return false;
			}
		}
		if(getTileBelow().Collidable){
			if(getTileBelow().r.intersects(ActualR)){
				return false;
			}
		}
		return true;
	}
	
	public boolean canMoveDown(){
		if(!isJumping){
			if(getTileBelow().Collidable){
				if(getTileBelow().r.intersects(r)){
					return false;
				}
			}
			if(getCustomTile(-1,2).Collidable){
				if(getCustomTile(1,-2).r.intersects(r)){
					return false;
				}
			}
			if(getCustomTile(1,2).Collidable){
				if(getCustomTile(1,2).Collidable){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Tile getTileBelow(){
		int yco=y+2,xco=x;
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
	public Tile getTileHeadLeft(){
		int yco=y,xco=x;
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
	
	public Tile getTileBodyLeft(){
		int yco=y+1,xco=x;
		if(yco>=def.Frame.GAME.MAP.Tiles.length-1){
			yco=def.Frame.GAME.MAP.Tiles.length;
		}
		if(yco<0){
			yco=0;
		}
		if(xco<0){
			xco=0;
		}
		if(xco>def.Frame.GAME.MAP.Tiles[0].length){
			xco=def.Frame.GAME.MAP.Tiles[0].length;
		}
		return def.Frame.GAME.MAP.Tiles[yco][xco];
	}
	public Tile getTileHeadRight(){
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
	public Tile getTileBodyRight(){
		int yco=y+1,xco=x+1;
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
	
	public void draw(Graphics g){
		g.setColor(HEAD);
		g.fillRect(finalx+def.Camera.x, finaly+def.Camera.y, finalw, finalh);
		g.setColor(BODY);
		g.fillRect(finalx+def.Camera.x, finaly+finalh+def.Camera.y, finalw, finalh);
	}

}
