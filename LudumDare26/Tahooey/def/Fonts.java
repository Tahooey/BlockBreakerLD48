package def;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Fonts {
	
	public static Font TahooeySmall;
	public static Font Tahooey;
	public static Font TahooeyLarge;
	static File font1;
	
	static Fonts loader = new Fonts();
	
	public static void loadFont() throws FontFormatException, IOException{
		try {
			TahooeySmall=Font.createFont(Font.TRUETYPE_FONT, Font().openStream()).deriveFont(Font.BOLD,16);
			Tahooey=Font.createFont(Font.TRUETYPE_FONT, Font().openStream()).deriveFont(Font.BOLD,32);
			TahooeyLarge=Font.createFont(Font.TRUETYPE_FONT, Font().openStream()).deriveFont(Font.BOLD,64);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
	}
	
	public static URL Font() throws IOException, URISyntaxException {
		return new URL("https://dl.dropboxusercontent.com/u/94622838/tahooey.ttf");
	}

}
