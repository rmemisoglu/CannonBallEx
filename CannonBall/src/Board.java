
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;
    private Alien aliens;
    private boolean ingame;
    private boolean newb=true;
    private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 700;
    private int posx=0;
	private int posy=0;
	private int hit=0;
    private final int DELAY = 15;
    private int Time=2000;


    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        craft = new Craft(B_WIDTH/2, B_HEIGHT-20);

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
    	Random r=new Random();
    	posx=r.nextInt(500)+500;
    	posy=r.nextInt(300)+300;
        aliens = new Alien(posx,posy);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(craft.missile>=0){
            
        

        if (ingame) {
            
            drawObjects(g);
            doDrawing(g);

        } else {

            drawGameOver(g);
        }
        }else{
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        
         ArrayList<Missile> ms = craft.getMissiles();

        for (Missile m : ms) {
            
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX()+20, m.getY(), this);
            }
        }
        
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(craft.angle), craft.getX() + craft.image.getWidth(null) / 2, craft.getY()-30 + craft.image.getHeight(null) / 2);
        g2d.transform(tx);
        
        if (craft.isVisible()) {
            g.drawImage(craft.getImage(), craft.getX(), craft.getY()-30,
                    this);
        }
        

    }
    
    private void drawObjects(Graphics g) {
    	
        //for (Alien a : aliens) {
          //  if (a.isVisible()) {
                g.drawImage(aliens.getImage(), aliens.getX(), aliens.getY(), this);
           // }
        //}

        g.setColor(Color.WHITE);
        g.drawString("TIME: " + Time, B_WIDTH-120, 10);
          g.setColor(Color.WHITE);
        g.drawString("Avlanan Sinek: " + hit, B_WIDTH-120, 20);
    }

    private void drawGameOver(Graphics g) {
         
        String msg1="Tebrikler fasülye 20snde "+ hit +" sinek avladýn ";
        String msg = "Oyun Bitti";
        
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg1, (B_WIDTH - fm.stringWidth(msg1)) / 2,
                B_HEIGHT / 2);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2+40);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateCraft();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    private void inGame() {
        
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateCraft() {

        if (craft.isVisible()) {
            craft.move();
            Time-=1;
        }
    }

    private void updateMissiles() {

        ArrayList<Missile> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);
            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateAliens() {
       /* if (aliens.isEmpty()) {

            ingame = false;
            return;
        }*/

        //for (int i = 0; i < aliens.size(); i++) {

            //Alien a = aliens.get(i);
        
            if (aliens.isVisible()) {
                aliens.move();
            } else if(!aliens.isVisible()&&newb) {
                aliens.setVisible(false);
                
            	new Alien(posx,posy);
                
            }
       // }
    }

    public void checkCollisions() {

        Rectangle r3 = craft.getBounds();
       if(Time<0)
    	   ingame=false;
        //for (Alien alien : aliens) {
            Rectangle r21 = aliens.getBounds();

            if (r3.intersects(r21)) {
                craft.setVisible(false);
                aliens.setVisible(false);
                ingame = false;
            }
        //}
       

        ArrayList<Missile> ms = craft.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            //for (Alien alien : aliens) {
                Rectangle r2 = aliens.getBounds();
                
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    aliens.sound();
                    Random r=new Random();
                    posx=r.nextInt(500)+500;
                    posy=r.nextInt(300)+300;
                    aliens=new Alien(posx,posy);
                    hit++;
                    
                }
            //}
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}