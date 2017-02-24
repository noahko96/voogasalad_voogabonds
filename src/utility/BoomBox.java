package utility;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BoomBox {
	
	private static MediaPlayer mp;
	
	public static void playMusic(String mp3Name){
		String initialPath = new File("").getAbsolutePath().replace('\\', '/');
		
		String music = Paths.get(initialPath+"/src/resources/background_music/"+mp3Name).toUri().toString();
		Media hit = new Media(music);
		mp = new MediaPlayer(hit);
		
		mp.play();
	}
	
	public static void playMusicOnLoop(String mp3Name){
		playMusic(mp3Name);
		onRepeat(mp);
	}
	
	public static void playMusicSet(List<String> mp3Names){
		for (String song:mp3Names){
			playMusic(song);
			if (song.equals(mp3Names.get(mp3Names.size()))){
				song = mp3Names.get(0);
			}
		}
		
	}
	
	private static void onRepeat(MediaPlayer mp){
		mp.setOnEndOfMedia(new Runnable() {
		       public void run() {
			         mp.seek(Duration.ZERO);
			       }
			   });
	}

}
