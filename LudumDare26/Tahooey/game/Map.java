package game;

import java.awt.*;
import java.io.*;
import java.util.*;
import def.Camera;

public class Map {
	
	static int smallestOnscreenwd = 0, smallestOnscreenhd = 0;
	static int largestOnscreenwd = 0, largestOnscreenhd = 0;

	static int smallestOnscreenwu = 0, smallestOnscreenhu = 0;
	static int largestOnscreenwu = 0, largestOnscreenhu = 0;
	
	File world;
	File TileDynamics;
	public Tile[] allTiles;
	public int[][] TilesID;
	public Tile[][] Tiles;
	
	public Map(File world,File Tiledynamics){
		this.world=world;
		TileDynamics=Tiledynamics;
	}
	
	public void startupMap(){
		try {
			boot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void boot() throws IOException{
		if(!world.exists()){
			world.createNewFile();
			buildNewMap(0,2048,32);
		}
		if(!TileDynamics.exists()){
			TileDynamics.createNewFile();
			buildNewTileDynamics();
		}
		readWorld();
		readTileDynamics();
		convertToMap();
		bootTiles();
		
	}
	
	public void bootTiles(){
		for(int i=0;i<Tiles.length;i++){
			for(int j=0;j<Tiles[i].length;j++){
				Tiles[i][j].x=j;
				Tiles[i][j].y=i;
				Tiles[i][j].boot();
				
			}
		}
	}
	
	public void readTileDynamics() throws FileNotFoundException{
		Scanner s = new Scanner(TileDynamics);
		allTiles=new Tile[s.nextInt()];
		for(int i=0;i<allTiles.length;i++){
			allTiles[i]=new Tile(s.nextInt(),s.nextInt(),s.nextInt(),i,s.next(),s.next(),s.next(),s.next());
		}
	}
	
	public void buildNewTileDynamics() throws IOException{
		FileWriter fw = new FileWriter(TileDynamics);
		fw.write("6\r\n0 0 0 false false true false\r\n56 192 255 true true true false\r\n255 170 43 true true true false\r\n0 0 0 true false false true\r\n163 25 32 true true false false\r\n163 25 32 true true false false");
		
		fw.flush();
		fw.close();
	}
	
	public void convertToMap(){
		for(int i=0;i<Tiles.length;i++){
			for(int j=0;j<Tiles[i].length;j++){
				Tiles[i][j]=new Tile(allTiles[TilesID[i][j]]);
			}
		}
	}
	
	public void readWorld() throws FileNotFoundException{
		Scanner s = new Scanner(world);
		TilesID = new int[s.nextInt()][s.nextInt()];
		Tiles = new Tile[TilesID.length][TilesID[0].length];
		for(int i=0;i<TilesID.length;i++){
			for(int j=0;j<TilesID[i].length;j++){
				TilesID[i][j]=s.nextInt();
			}
		}
	}
	
	public void buildNewMap(int ID,int W,int H) throws IOException{
		FileWriter fw = new FileWriter(world);
		fw.write(H+" "+W+"\r\n");
		for(int i=0;i<H;i++){
			for(int j=0;j<W;j++){
				if(i==H-2){
					fw.write("4 ");
				}else if(j==0){
					fw.write("4 ");
				}else if(j==W-1){
					fw.write("4 ");
				}else if(i==0){
					fw.write("4 ");
				}else{
					if(i>=15){
						if(i%2==0){
							if(j%2==0){
								fw.write((ID+2)+" ");
							}else{
								fw.write((ID+1)+" ");
							}
						}else{
							if(j%2==0){
								fw.write((ID+1)+" ");
							}else{
								fw.write((ID+2)+" ");
							}
						}
					}else{
						fw.write((ID)+" ");
					}
				}
			}
			fw.write("\r\n");
		}
		
		fw.flush();
		fw.close();
	}
	
	public void runMap(){
		int finw=0,finh=0;
		
		smallestOnscreenwd=Camera.x/(8*def.Frame.SCALE)*-1;
		largestOnscreenwd=smallestOnscreenwd+(def.Frame.WIDTH/(8*def.Frame.SCALE))+2;
		smallestOnscreenhd=Camera.y/(8*def.Frame.SCALE)*-1;
		largestOnscreenhd=smallestOnscreenhd+(def.Frame.HEIGHT/(8*def.Frame.SCALE))+2;
		
		for(int h=smallestOnscreenhd;h<largestOnscreenhd;h++){
			for(int w=smallestOnscreenwd;w<largestOnscreenwd;w++){
				finw=w;
				finh=h;
				if(h<=-1){
					finh=0;
				}
				if(w<=-1){
					finw=0;
				}
				if(h>=Tiles.length){
					finh=Tiles.length-1;
				}
				if(w>=Tiles[0].length){
					finw=Tiles[0].length-1;
				}
				Tiles[finh][finw].update();
			}
		}
	}
	
	public void drawMap(Graphics g){
		int finw=0,finh=0;
		
		for(int h=smallestOnscreenhd;h<largestOnscreenhd;h++){
			for(int w=smallestOnscreenwd;w<largestOnscreenwd;w++){
				finw=w;
				finh=h;
				if(h<=-1){
					finh=0;
				}
				if(w<=-1){
					finw=0;
				}
				if(h>=Tiles.length){
					finh=Tiles.length-1;
				}
				if(w>=Tiles[0].length){
					finw=Tiles[0].length-1;
				}
				Tiles[finh][finw].draw(g);
				Tiles[finh][finw].drawParticles(g);
			}
		}
	}

}
