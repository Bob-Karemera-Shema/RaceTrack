import javax.swing.*;
import java.awt.*;

public class Kart
{
    private Point location;                             //current kart location
    private float speed;                                //current kart speed
    private int direction;                              //moving kart direction
    private int image = 0;                              //current image index in loop
    private int changeTime = 1;                         //constant change in time  100ms = 1s

    public Kart(int x, int y)
    {
        location = new Point( x, y );          //kart starting position
        speed = 0;                                      //kart starts at rest
        direction = 0;                                  //initial kart direction
    }

    public int getCurrentImage()
    {
        return image;
    }

    public void displaceKart()                          //displace image
    {
        if(direction == 0)
        {
            location.x = (int) (location.x - speed);
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

            if(direction == 0)
            {    direction = 15;    }

            image = direction;
        }
        if(newDirection.equals("right"))
        {
            direction++;

            if(direction == 15)
            {    direction = 0;     }

            image = direction;
        }
    }

    public void increaseSpeed()
    {
        speed += 1;
    }

    public void decreaseSpeed()
    {
        speed -= 1;


        if(speed <= 0)
            speed = 0;
    }

    public Point getLocation()
    {
        return location;
    }
}
