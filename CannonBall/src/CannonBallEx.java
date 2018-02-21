import java.awt.EventQueue;
import javax.swing.JFrame;

public class odev extends JFrame {

    public odev() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Shoot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                odev ex = new odev();
                ex.setVisible(true);
            }
        });
    }
}