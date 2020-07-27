public class Item
{
    private int x, y;
    private String type;
    private String[] types = {"radius","bomb"};
    private boolean isPlaced = false;
    //type can be speed, radius, or bomb
    public Item(int Nx, int Ny)
    {
        if(Math.random() < 0.1 && !GraphicsPanel.wallLocation[x/50][y/50].isDestructable){
        x = Nx;
        y = Ny;
        type = types[(int)(Math.random()*2)];
        isPlaced = true;
       }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean isPlaced(){
        return isPlaced;
        
    }
    
    public String getType(){
        return type;
    }
    
    public void setPlaced(boolean p){
        isPlaced = p;
    }
}