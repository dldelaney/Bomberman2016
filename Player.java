import java.awt.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    public int x, y;
    public boolean inItem, dead = false;
    public int numBombs = 1, radius = 3;
    public String direction = "U/D";
    public Player()
    {
        x = 50;
        y = 50;
    }
    public void moveLeft(){
        x = x - 50;
        direction = "L/R";
    }
    public void moveRight(){
        //have MainGame stop player from moving instead of Player
        x = x + 50;
        direction = "L/R";
    }
    public void moveUp(){
        y = y - 50;
        direction = "U/D";
    }
    public void moveDown(){
        y = y + 50;
        direction = "U/D";
    }
    public int getRadius(){
        return radius;
    }
    public void setX(int Nx){
        x = Nx;
    }
    public void setY(int Ny){
        y = Ny;
    }
}
