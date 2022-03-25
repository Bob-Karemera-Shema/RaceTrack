import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RaceTrack extends JPanel implements ActionListener, KeyListener
{
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
        kart1 = new Kart(425,500, "kartRed");                             //initialise the first kart object
        kart2 = new Kart(425,600,"kartBlue");                             //initialise the second kart object
        kart1.populateImageArray();                                                         //load kart 1 images
        kart2.populateImageArray();                                                         //load kart 2 images
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
        kart1.getCurrentImage().paintIcon(this, g, kart1.getLocation().x, kart1.getLocation().y);
        kart2.getCurrentImage().paintIcon(this, g, kart2.getLocation().x, kart2.getLocation().y);

        if(animationTimer.isRunning())                  //Only refreshes kart locations if timer is running
        {
            kart1.displaceKart();
            kart2.displaceKart();
            collisionDetection();
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
        if(e.getKeyCode() == KeyEvent.VK_UP)                        //increase kart1 speed if up key is pressed
        {
            kart1.increaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)                //decrease kart1 speed if down key is pressed
        {
            kart1.decreaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)                //turn kart1 left if left key is pressed
        {
            kart1.updateDirection("left");
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)               //turn kart1 right if right key is pressed
        {
            kart1.updateDirection("right");
        }
        else if(e.getKeyCode() == KeyEvent.VK_A)                    //turn kart2 left if A key is pressed
        {
            kart2.updateDirection("left");
        }
        else if (e.getKeyCode() == KeyEvent.VK_D)                   //turn kart2 right if D key is pressed
        {
            kart2.updateDirection("right");
        }
        else if (e.getKeyCode() == KeyEvent.VK_S)                   //decrease kart2 speed if S key is pressed
        {
            kart2.decreaseSpeed();
        }
        else if (e.getKeyCode() == KeyEvent.VK_W)                   //increase kart2 speed if W key is pressed
        {
            kart2.increaseSpeed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void collisionDetection()                //collision detection method
    {
        //Collision detection between karts
        if((kart2.getLocation().y >= kart1.getLocation().y && kart2.getLocation().y <= kart1.getLocation().y + 40)
                || (kart2.getLocation().y + 40 >= kart1.getLocation().y && kart2.getLocation().y + 40 <= kart1.getLocation().y + 40))
        {   //if the karts collide vertically
            if(kart1.getLocation().x + 45 >= kart2.getLocation().x && !(kart1.getLocation().x >= kart2.getLocation().x + 45))
            {  //and if the karts collide horizontally
                kart1.stopKart();
                kart2.stopKart();
            }
        }

        //Collision detection between karts and racetrack bounds
        kart1.checkOuterCollision();
        kart1.checkInnerCollision(new Rectangle( 150, 200, 550, 300 ));     //inner edge bounds

        kart2.checkOuterCollision();
        kart2.checkInnerCollision(new Rectangle( 150, 200, 550, 300  ));    //inner edge bounds
    }
}
