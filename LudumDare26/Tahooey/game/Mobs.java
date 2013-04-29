package game;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Mobs {
	
	public Random r = new Random();
	
	public Mob[] MobTypes;
	public Mob[] MobsOnMap;
	
	public File mobsOnMap;
	public File mobsDyn;
	
	public Mobs(File mobsList,File mobsDyn){
		mobsOnMap = mobsList;
		this.mobsDyn=mobsDyn;
	}
	
	public void boot(){
		if(!mobsDyn.exists()){
			try {
				mobsDyn.createNewFile();
				writeNewMobsDyn();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!mobsOnMap.exists()){
			try {
				mobsOnMap.createNewFile();
				writeMobsOnMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			readMobsDynamics();
			createMobs();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bootMobs();
	}
	
	public void readMobsDynamics() throws FileNotFoundException{
		Scanner s = new Scanner(mobsDyn);
		MobTypes = new Mob[s.nextInt()];
		for(int i=0;i<MobTypes.length;i++){
			MobTypes[i]=new Mob(s.nextInt(),s.nextInt(),s.nextInt(),s.nextInt(),s.nextInt(),s.nextInt());
		}
	}
	
	public void createMobs() throws FileNotFoundException{
		Scanner s = new Scanner(mobsOnMap);
		MobsOnMap = new Mob[s.nextInt()];
		for(int i=0;i<MobsOnMap.length;i++){
			MobsOnMap[i]=new Mob(MobTypes[s.nextInt()],s.next(),s.nextInt(),s.nextInt(),s.next());
		}
	}
	
	public void writeMobsOnMap() throws IOException{
		FileWriter fw = new FileWriter(mobsOnMap);
		
		fw.write("1\r\n0 Player 14 13 true");
		
		fw.flush();
		fw.close();
	}
	
	public void writeNewMobsDyn() throws IOException{
		FileWriter fw = new FileWriter(mobsDyn);
		
		fw.write("1\r\n255 140 58 185 109 145");
		
		fw.flush();
		fw.close();
	}
	
	public void bootMobs(){
		for(int i=0;i<MobsOnMap.length;i++){
			MobsOnMap[i].boot();
		}
	}
	
	public void runMobs(){
		for(int i=0;i<MobsOnMap.length;i++){
			MobsOnMap[i].update();
		}
	}
	
	public void drawMobs(Graphics g){
		for(int i=0;i<MobsOnMap.length;i++){
			MobsOnMap[i].draw(g);
		}
	}

}
