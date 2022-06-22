import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RaceTrack extends JPanel implements ActionListener, KeyListener
{
    private final int animationDelay = 100;             //animation delay in milliseconds
    private Timer animationTimer;                       //Timer tool for animation
    private Kart kart1;                                 //First kart
    private Kart kart2;                                 //Second kart
    private Frame parent;                               //Frame containing panel
    private JLabel raceBoardTitle;                           //Race information title label
    private JLabel ownKartLaps;                           //Race information label
    private JLabel foreignKartLaps;                           //Race information label
    private JButton exitButton;                         //Exit button

    public RaceTrack(Frame parent)
    {
        setLayout(null);                                //suppress default panel layout features
        setBounds(0,0,850,650);
        setBackground(Color.green);
        setFocusable(true);
        this.parent = parent;
        kart1 = new Kart(425,500, "Red");                             //initialise the first kart object
        kart2 = new Kart(425,550,"Blue");                             //initialise the second kart object
        kart1.populateImageArray();                                                         //load kart 1 images
        kart2.populateImageArray();                                                         //load kart 2 images
        this.addKeyListener(this);

        raceBoardTitle = new JLabel("Race Information");
        raceBoardTitle.setBounds(50,600,100,50);

        ownKartLaps = new JLabel("Kart " + kart1.getKartColor() + " laps:");
        ownKartLaps.setBounds(50,650,100,50);

        foreignKartLaps = new JLabel("Kart " + kart2.getKartColor() + " laps:");
        foreignKartLaps.setBounds(50,700,100,50);

        exitButton = new JButton("Exit");
        exitButton.setBounds(800,30,100,50);
        exitButton.setBackground(Color.white);
        exitButton.addActionListener(this);

        add(raceBoardTitle);
        add(ownKartLaps);
        add(foreignKartLaps);
        add(exitButton);

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
        Color c3 = Color.black;
        g.setColor( c3 );
        g.drawRect( 100, 150, 650, 400 ); // mid-lane marker
        Color c4 = Color.black;
        g.setColor( c4 );
        g.drawLine( 425, 500, 425, 600 ); // start line

        //Draw karts
        kart1.getCurrentImage().paintIcon(this, g, kart1.getLocation().x, kart1.getLocation().y);
        kart2.getCurrentImage().paintIcon(this, g, kart2.getLocation().x, kart2.getLocation().y);
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
        if(event.getSource() == animationTimer)
        {
            repaint();        //Call repaint function to update display
            updateRaceInformation();       //update race information displayed on the screen
            kart1.displaceKart();         //update kart position
            kart2.displaceKart();         //update kart position
            checkWinner();                 //check whether a kart has won the raceBlue
            collisionDetection();           // detect collisions (between karts and with edges)
        }
        if(event.getSource() == exitButton)
        {
            StopAnimation();
            endGame();
        }
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

    private void updateRaceInformation()
    {
        kart1.updateLaps();
        kart2.updateLaps();
        ownKartLaps.setText("Kart " + kart1.getKartColor() + " Laps: " + kart1.getLapCounter());
        foreignKartLaps.setText("Kart " + kart2.getKartColor() + " Laps: " + kart2.getLapCounter());
    }

    public void collisionDetection()                //collision detection method
    {
        collisionBetweenKarts();
        collisionWithBoundaries();
    }

    public void collisionBetweenKarts()
    {
        //Collision detection between karts
        if((kart2.getLocation().y >= kart1.getLocation().y && kart2.getLocation().y <= kart1.getLocation().y + 40)
                || (kart2.getLocation().y + 40 >= kart1.getLocation().y && kart2.getLocation().y + 40 <= kart1.getLocation().y + 40))
        {   //if the karts collide vertically
            if(kart1.getLocation().x + 45 >= kart2.getLocation().x && !(kart1.getLocation().x >= kart2.getLocation().x + 45))
            {  //and if the karts collide horizontally
                kart1.stopKart();
                kart2.stopKart();
                StopAnimation();
                JOptionPane.showMessageDialog(this, "Karts crashed into each other!" +
                        " No winner for this race", "Collision Detected", JOptionPane.INFORMATION_MESSAGE);
                endGame();
            }
        }
    }
    public void collisionWithBoundaries() {
        //Collision detection between karts and racetrack bounds
        if(kart1.checkOuterCollision())
        {
            StopAnimation();        //Stop animation as soon as a crash is detected
            JOptionPane.showMessageDialog(parent, "Kart" + kart1.getKartColor() + " crashed!" +
                    " Kart" + kart2.getKartColor() + " wins.", "Collision Detected", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }

        if(kart1.checkInnerCollision(new Rectangle( 150, 200, 550, 300 )))     //inner edge bounds
        {
            StopAnimation();
            JOptionPane.showMessageDialog(parent, "Kart" + kart1.getKartColor() + " crashed!" +
                    " Kart" + kart2.getKartColor() + " wins.", "Collision Detected", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }
        if(kart2.checkOuterCollision())
        {
            StopAnimation();
            JOptionPane.showMessageDialog(parent, "Kart" + kart2.getKartColor() + " crashed!" +
                    " Kart" + kart1.getKartColor() + " wins.", "Collision Detected", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }

        if(kart2.checkInnerCollision(new Rectangle( 150, 200, 550, 300 )))     //inner edge bounds
        {
            StopAnimation();
            JOptionPane.showMessageDialog(parent, "Kart" + kart2.getKartColor() + " crashed!" +
                    " Kart" + kart1.getKartColor() + " wins.", "Collision Detected", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }
    }

    private void checkWinner()
    {
        //the first kart to complete three laps wins the race
        if (kart1.getLapCounter() == 4 && kart2.getLapCounter() < 4)
        {
            JOptionPane.showMessageDialog(this, "Kart" + kart1.getKartColor() + " wins!"
                        , "Race Finished", JOptionPane.INFORMATION_MESSAGE);
            StopAnimation();
        }


        if (kart1.getLapCounter() < 4 && kart2.getLapCounter() == 4)
        {
            JOptionPane.showMessageDialog(this, "Kart" + kart2.getKartColor() + " wins!"
                        , "Race Finished", JOptionPane.INFORMATION_MESSAGE);
            StopAnimation();
        }

        if (kart1.getLapCounter() == 4 && kart2.getLapCounter() == 4)
        {
            JOptionPane.showMessageDialog(this, "The race ends in a tie. No winner!"
                        , "Race Finished", JOptionPane.INFORMATION_MESSAGE);
            StopAnimation();
        }
    }

    public void endGame()
    {
        //Close Frame containing panel
        parent.ParentCloseMe();
    }
}
