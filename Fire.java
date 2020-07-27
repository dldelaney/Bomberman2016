public class Fire

{
    private int x, y;
    private int timer;
    public Fire(int Nx, int Ny)
    {
        timer = 1000;
        x = Nx;
        y = Ny;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int Nx){
        x = Nx;
    }
    public void setY(int Ny){
        y = Ny;
    }
    public void countdown(){
        timer--;
    }
    public int getTimer(){
        return timer;
    }
    public void setTimer(int Nx){
        timer = Nx;
    }
}
