import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RaceTrack extends JPanel implements ActionListener, KeyListener
{
    private final static String imageName1 = "kartRed";  //image file name prefix red kart
    private final static String imageName2 = "kartBlue";  //image file name prefix blue kart
    private ImageIcon kartImages1[];                          //kart 1 image array
    private ImageIcon kartImages2[];                          //kart 2 image array
    private final int totalImages = 16;                 //number of images
    private final int animationDelay = 100;             //animation delay in milliseconds
    private Timer animationTimer;                       //Timer tool for animation
    private Kart kart1;                                 //First kart
    private Kart kart2;                                 //Second kart

    public RaceTrack()
    {
        setLayout(null);                                //suppress default panel layout features
        setBounds(0,0,850,650);
        setBackground(Color.green);
        setFocusable(true);
        kart1 = new Kart(425,500);                             //initialise the first kart object
        kart2 = new Kart(425,550);                             //initialise the second kart object
        kartImages1 = new ImageIcon[totalImages];       //initialise image array
        kartImages2 = new ImageIcon[totalImages];       //initialise image array

        for(int i = 0; i < totalImages; i++)            //load kart images to array
        {
            kartImages1[i] = new ImageIcon(getClass().getResource("images1/"
                    + imageName1 + i + ".png"));
            kartImages2[i] = new ImageIcon(getClass().getResource("images2/"
                    + imageName2 + i + ".png"));
        }
        this.addKeyListener(this);

        StartAnimation();
    }

    public void paintComponent(Graphics g)              //Draw racetrack and current kart locations
    {
        super.paintComponent(g);

        //Draw racetrack
        Color c1 = Color.green;
        g.setColor( c1 );
        g.fillRect( 150, 200, 550, 300 ); // grass
        Color c2 = Color.white;
        g.setColor( c2 );
        g.fillRect( 50, 100, 750, 500 ); // outer edge
        Color c5 = Color.green;
        g.setColor( c5 );
        g.fillRect( 150, 200, 550, 300 ); // inner edge
        Color c3 = Color.yellow;
        g.setColor( c3 );
        g.drawRect( 100, 150, 650, 400 ); // mid-lane marker
        Color c4 = Color.white;
        g.setColor( c4 );
        g.drawLine( 425, 500, 425, 600 ); // start line

        //Draw karts
        kartImages1[kart1.getCurrentImage()].paintIcon(this, g, kart1.getLocation().x, kart1.getLocation().y);
        kartImages2[kart2.getCurrentImage()].paintIcon(this, g, kart2.getLocation().x, kart2.getLocation().y);

        if(animationTimer.isRunning())                  //Only refreshes the screen if the timer is working
        {
            kart1.displaceKart();
            kart2.displaceKart();
        }
    }

    public void StartAnimation()
    {
        if(animationTimer == null)                      //Create timer if timer is not yet created
        {
            animationTimer = new Timer(animationDelay, this);
            animationTimer.start();
        }
        else if(!animationTimer.isRunning())            //Restart timer if it's already running
        {   animationTimer.restart();   }
    }

    public void StopAnimation()
    {
        animationTimer.stop();
    }

    public void actionPerformed(ActionEvent event)
    {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            kart1.increaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            kart1.decreaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            kart1.updateDirection("left");
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            kart1.updateDirection("right");
        }
        else if(e.getKeyCode() == KeyEvent.VK_A)
        {
            kart2.updateDirection("left");
        }
        else if (e.getKeyCode() == KeyEvent.VK_D)
        {
            kart2.updateDirection("right");
        }
        else if (e.getKeyCode() == KeyEvent.VK_S)
        {
            kart2.decreaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_W)
        {
            kart2.increaseSpeed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void collisionDetection()
    {

    }
}