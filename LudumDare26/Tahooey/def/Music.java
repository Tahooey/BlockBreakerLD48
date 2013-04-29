package def;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

public class Music {
	
	static Music loader = new Music();
	
	public static AudioClip sound1;
	
	public static void load() throws IOException{
		sound1=loadSound("music.wav");
	}
	
	public static AudioClip loadSound(String path) throws IOException {
		AudioClip sound;
		URL url = loader.getClass().getResource(path);
		sound = Applet.newAudioClip(url);
		return sound;
	}

}
