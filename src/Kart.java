import javax.swing.*;
import java.awt.*;

public class Kart extends Component {
    private Point location;                             //current kart location
    private float speed;                                //current kart speed
    private int direction;                              //moving kart direction
    private int imageIndex = 0;                         //current image index in loop
    private ImageIcon[] kartImages;                     //kart 2 image array
    private String kartColor;                           //kart color attribute
    private final int totalImages = 16;                 //number of images

    public Kart(int x, int y, String kartColor)
    {
        location = new Point( x, y );                   //kart starting position
        speed = 0;                                      //kart starts at rest
        direction = 0;                                  //initial kart direction
        this.kartColor = kartColor;                     //Assign kart color
        kartImages = new ImageIcon[totalImages];       //initialise image array
    }

    public void populateImageArray()
    {
        //Load images to display the kart as it moves on the screen
        if(kartColor.equals("kartRed"))
        {
            loadKartImages(1);
        }

        if(kartColor.equals("kartBlue"))
        {
            loadKartImages(2);
        }
    }

    public void loadKartImages(int folderNumber)
    {
        String message = "Kart Images not found";
        try
        {
            for (int i = 0; i < totalImages; i++)            //load kart images to array
            {
                kartImages[i] = new ImageIcon(getClass().getResource("images" + folderNumber + "/"
                        + kartColor + i + ".png"));
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,"Images not found",
                    "Loading error",JOptionPane.ERROR_MESSAGE); //inform user
        }
    }

    public ImageIcon getCurrentImage()
    {
        //return current kart image
        return kartImages[imageIndex];
    }

    public void displaceKart()
    {
        //displace kart image
        if(direction == 0)
        {
            location.x = (int) (location.x - 2 * speed);
        }
        else if(direction == 1)
        {
            location.x = (int) (location.x - 1.5 * speed);
            location.y = (int) (location.y - 0.5 * speed);
        }
        else if (direction == 2)
        {
            location.x = (int) (location.x - 1 * speed);
            location.y = (int) (location.y - 1 * speed);
        }
        else if (direction == 3)
        {
            location.x = (int) (location.x - 0.5 * speed);
            location.y = (int) (location.y - 1.5 * speed);
        }
        else if (direction == 4)
        {
            location.y = (int) (location.y - 2 * speed);
        }
        else if (direction == 5)
        {
            location.x = (int) (location.x + 0.5 * speed);
            location.y = (int) (location.y - 1.5 * speed);
        }
        else if (direction == 6)
        {
            location.x = (int) (location.x + 1 * speed);
            location.y = (int) (location.y - 1 * speed);
        }
        else if (direction == 7)
        {
            location.x = (int) (location.x + 1.5 * speed);
            location.y = (int) (location.y - 0.5 * speed);
        }
        else if (direction == 8)
        {
            location.x = (int) (location.x + 2 * speed);
        }
        else if (direction == 9)
        {
            location.x = (int) (location.x + 1.5 * speed);
            location.y = (int) (location.y + 0.5 * speed);
        }
        else if (direction == 10)
        {
            location.x = (int) (location.x + 1 * speed);
            location.y = (int) (location.y + 1 * speed);
        }
        else if (direction == 11)
        {
            location.x = (int) (location.x + 0.5 * speed);
            location.y = (int) (location.y + 1.5 * speed);
        }
        else if (direction == 12)
        {
            location.y = (int) (location.y + 2 * speed);
        }
        else if (direction == 13)
        {
            location.x = (int) (location.x - 0.5 * speed);
            location.y = (int) (location.y + 1.5 * speed);
        }
        else if (direction == 14)
        {
            location.x = (int) (location.x - 1 * speed);
            location.y = (int) (location.y + 1 * speed);
        }
        else if (direction == 15)
        {
            location.x = (int) (location.x - 1.5 * speed);
            location.y = (int) (location.y + 0.5 * speed);
        }
    }

    public void updateDirection(String newDirection)
    {
        if(newDirection.equals("left"))
        {
            direction--;

            if(direction < 0)
            {    direction = 15;    }

            imageIndex = direction;
        }
        if(newDirection.equals("right"))
        {
            direction++;

            if(direction > 15)
            {    direction = 0;     }

            imageIndex = direction;
        }
    }

    public void increaseSpeed()
    {
        speed += 1;
    }           //increase kart speed

    public void decreaseSpeed()                           //decrease kart speed
    {
        speed -= 1;
    }

    public Point getLocation()
    {
        return location;
    }       //return current kart location

    public void stopKart()
    {
        speed = 0;
    }                 //set kart speed to zero

    public Rectangle getBounds(){                         //return kart bounds
        return new Rectangle(getLocation().x,getLocation().y,50,50);
    }

    public void setLocationX(int newX) { location.x = newX; }           //set kart X coordinate to new coordinate

    public void setLocationY(int newY) { location.y = newY; }           //set kart Y coordinate to new coordinate

    public void checkOuterCollision()                    //check whether kart collides with outer bound
    {
        //( 50, 100, 750, 500 ) outer edge
        if(getLocation().x < 50)
        {
            setLocationX(50);
            stopKart();
        }

        if(getLocation().x > 750)
        {
            setLocationX(750);
            stopKart();
        }

        if(getLocation().y < 100)
        {
            setLocationY(100);
            stopKart();
        }

        if(getLocation().y > 550)
        {
            setLocationY(550);
            stopKart();
        }
    }

    public void checkInnerCollision(Rectangle innerBound)
    {
        if(getBounds().intersects(innerBound))
        {
            stopKart();
        }
    }
}
