import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
   private Clip backgroundClip;

   public SoundManager() {
   }

   public void playBackgroundMusic(String filePath) {
      try {
         InputStream inputStream = this.getClass().getResourceAsStream(filePath);
         if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
         }

         AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
         this.backgroundClip = AudioSystem.getClip();
         this.backgroundClip.open(audioStream);
         this.backgroundClip.loop(-1);
         this.backgroundClip.start();
      } catch (IOException | LineUnavailableException | UnsupportedAudioFileException var4) {
         var4.printStackTrace();
      }

   }

   public void stopBackgroundMusic() {
      if (this.backgroundClip != null && this.backgroundClip.isRunning()) {
         this.backgroundClip.stop();
      }

   }

   public void playSoundEffect(String filePath) {
      try {
         InputStream inputStream = this.getClass().getResourceAsStream(filePath);
         if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
         }

         AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
         Clip clip = AudioSystem.getClip();
         clip.open(audioStream);
         clip.start();
      } catch (IOException | LineUnavailableException | UnsupportedAudioFileException var5) {
         var5.printStackTrace();
      }

   }
}
