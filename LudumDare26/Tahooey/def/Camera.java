package def;

import game.BackGround;
import game.Tile;

public class Camera {
	
	public static int x = 0, y=0;
	public static int dx = 0, dy = 0;

	public static void update() {
		x=-Frame.GAME.MOBS.MobsOnMap[Game.mobControlled].finalx+(14*(Tile.WIDTH*def.Frame.SCALE));
		
		x = x + dx;
		y = y + dy;
		if(y<-(Frame.GAME.MAP.Tiles.length*(8*def.Frame.SCALE))+def.Frame.HEIGHT){
			y=-(Frame.GAME.MAP.Tiles.length*(8*def.Frame.SCALE))+def.Frame.HEIGHT;
			BackGround.dy=0;
		}
		if(x<-Frame.GAME.MAP.Tiles[1].length*(8*def.Frame.SCALE)+def.Frame.WIDTH){
			x=-Frame.GAME.MAP.Tiles[1].length*(8*def.Frame.SCALE)+def.Frame.WIDTH;
			BackGround.dx=0;
		}
		if(y>0){
			y=0;
			BackGround.dy=0;
		}
		if(x>0){
			x=0;
			BackGround.dx=0;
		}
	}

	public static void move(int dir) {
		BackGround.move(dir);
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
	
	public static void setX(int i){
		x=Frame.SPEED*i;
	}
	public static void setY(int i){
		y=Frame.SPEED*i;
	}

	public static void setDX(int i) {
		dx = Frame.SPEED*i;
	}

	public static void setDY(int i) {
		dy = Frame.SPEED*i;
	}

}
