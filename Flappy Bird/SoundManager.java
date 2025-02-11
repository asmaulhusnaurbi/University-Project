import javax.sound.sampled.*;
import java.net.URL;

public class SoundManager {
    private Clip backgroundMusic;
    
   

    public void loadBackgroundMusic() {
        try {
            URL bgMusicURLL = getClass().getResource("/Flappy Bird Theme Song.wav");
            if (bgMusicURLL == null) {
                System.out.println("Error: Background music file not found!");
                return;
            }
            AudioInputStream bgStream = AudioSystem.getAudioInputStream(bgMusicURLL);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(bgStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the background music
            backgroundMusic.start();  // Start the background music
            System.out.println("Background music loaded and playing successfully!");
        } catch (Exception e) {
            System.out.println("Error loading background music:");
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
        
    }

   
}
