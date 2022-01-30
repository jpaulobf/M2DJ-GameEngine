package util;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
  
public class Audio {
  
    private Clip clip                           = null;
    private String status                       = null;
    private AudioInputStream audioInputStream   = null;
    private boolean ready                       = false;
  
    public Audio(String filePath, int loop) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (audioInputStream != null) {
            try {
                clip = AudioSystem.getClip();    
            } catch (Exception e) {}

            try {
                clip.open(audioInputStream);
                this.ready = true;
            } catch (Exception e) {
                this.ready = false;
            }
        }
    }

    public void play() {
        play(0);
    }
      
    public void play(int loop) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setMicrosecondPosition(0);
        try {
            Thread.sleep(1);    
        } catch (Exception e) {
        }
        clip.loop(loop);
        clip.start();
        status = "play";
    }

    public boolean isReady() {
        return (this.ready);
    }

    public String getStatus() {
        return (this.status);
    }
}