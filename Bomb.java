public class Bomb
{
    private int x = -100;
    private int y = -100;
    public boolean placed = false, exploded = false;
    public int timer;
    public Bomb(int X, int Y)
    {
       x = X;
       y = Y;
       timer = 1500;
       placed = true;
    }
    public void setX(int Nx){
           x = Nx;
    }
    public void setY(int Ny){
           y = Ny;
    }
    public int getX(){
           return x;
    }
    public int getY(){
           return y;
    }
    public boolean checkIfExploded(){
        //If exploded
        if(timer <= 0){
            exploded = true;
            placed = false;
            return true;
        }
        return false;
    }
}