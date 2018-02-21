import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Craft extends Sprite {
    private double angle1;
    public double angle=-90;
    public int missile=20;
    
    
    private ArrayList<Missile> missiles;
    

    public Craft(int x, int y) {
        super(x, y);

        initCraft();
    }

    private void initCraft() {
        
        missiles = new ArrayList<>();
        loadImage("images\\cannon0.png");
        getImageDimensions();
    }

    public void move() {

         angle+=angle1;

    }

    public ArrayList getMissiles() {
        return missiles;
    }
    
    

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
       


        if (key == KeyEvent.VK_LEFT) {
            angle1 = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            angle1 = 1;
        }

    }
    
    public void keyReleased(KeyEvent e) {
        

        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            angle1 = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            angle1 = 0;
        }


    }

    public void fire() {
        
        //missile--;
        if(missile>=0)
            missiles.add(new Missile(x+width/4, y, angle));
        try {
            // Open an audio input stream.
        	File soundFile = new File("sounds\\laser_projectile.wav");
        	AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }
    

  
}
