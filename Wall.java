public class Wall
{
    boolean isAir, isDestructable;
    int x,y;
    public Wall()
    {
        isAir = Math.random()<0.1;
        isDestructable = true;
        int x = 0;
        int y = 0;
    }
    public void setAir(boolean isAirN){
        isAir = isAirN;
    }
    public boolean isAir(){
        return isAir;
    }
    public void setDestructable(boolean isDestructableN){
        isDestructable = isDestructableN;
        isAir = false;
    }
    public boolean getDestructable(){
        return isDestructable;
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
}
