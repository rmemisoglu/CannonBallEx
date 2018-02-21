import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Alien extends Sprite {

    private final int INITIAL_X = 1000;
    private boolean xD;
    private boolean yD;
    private int count;
    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("images\\bug.png");
        getImageDimensions();
    }
 
    public void move() {
    	Random r=new Random();
        if (x < 0) {
            x += 10;
        }
        if(y<0) {
        	y+=10;
        }
        x -= 1;
    	xD=r.nextBoolean();
    	yD=r.nextBoolean();
    	
    	/*if(xD==false) {
    		x-=4;
    	}
    	else if(xD==true) {
    		x+=4;
    	}
    	if(yD==false) {
    		y-=5;
    	}
    	else if(yD==true) {
    		y+=4;
    	}*/
    	
        
        }
    public void sound() {
    	try {
            // Open an audio input stream.
        	File soundFile = new File("sounds\\explosion.wav");
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