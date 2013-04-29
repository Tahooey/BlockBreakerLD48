package def;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public static final String TahooeyMaps = "https://dl.dropbox.com/u/94622838/";
	public static final int SCALE=2;
	public static final int WIDTH=470,HEIGHT=462+28;	
	public static final int SPEED=2;
	public static final String NAME="Block Breaker";
	public static final int release=0;
	public static final Color BACKGROUND=Color.BLACK;
	
	public static Game GAME = new Game();
	public static final int UP=0,DOWN=1,LEFT=2,RIGHT=3,STILL=4;
	
	public Frame(){
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle(NAME);
		setBackground(BACKGROUND);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		add(GAME);
		
		setVisible(true);
	}

}
