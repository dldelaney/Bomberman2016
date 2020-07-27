import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

/**
 * add to randomItemDrop to
 * have a chance of dropping an 
 * powerup when the block is broken
 * 
 * ?
 * ?make powerups pick-up-able
 * ?by the player(s).
 * ?(only pick-up-able by the player that 'created' them?)
 */
class GraphicsPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{    
    private Font smallFont;
    private Font largeFont;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Image bg;
    private int x = 20;
    private int mouseX, mouseY;
    private int key = 0, p1Radius, p2Radius, radius, key2 = 0;
    public static int bombNumber = 0;
    private String lastMovement;
    private Wall wall;
    private Player p1;
    private Player p2;
    private Item i;
    private Image wallDestructable;
    private Image wallIndestructable;
    private Image sideRed;
    private Image sideBlue;
    private Image frontRed;
    private Image frontBlue;
    private Image fire;
    private Image bomb;
    private Image RadiusPU;
    private Image BombPU;
    private int GameState = 0;
    public static Wall[][] wallLocation = new Wall[10][10];
    public static ArrayList<Bomb> bombs1 = new ArrayList<>();
    public static ArrayList<Bomb> bombs2 = new ArrayList<>();
    public static ArrayList<Fire> fires1 = new ArrayList<>();
    public static ArrayList<Fire> fires2 = new ArrayList<>();
    public ArrayList<Item> items = new ArrayList<>();
    public GraphicsPanel() {
        smallFont = new Font("Arial", Font.BOLD, 20);
        largeFont = new Font("Arial", Font.ITALIC, 30);
        wallDestructable = toolkit.getImage("wallDestructable.png");
        wallIndestructable = toolkit.getImage("wallIndestructable.png");
        fire = toolkit.getImage("fire.png");
        bomb = toolkit.getImage("bomb.png");
        RadiusPU = toolkit.getImage("radiuspowerup.png");
        BombPU = toolkit.getImage("bombpowerup.png");
        sideRed = toolkit.getImage("sideRed.png");
        sideBlue = toolkit.getImage("sideBlue.png");
        frontRed = toolkit.getImage("frontRed.png");
        frontBlue = toolkit.getImage("frontBlue.png");
        this.setPreferredSize(new Dimension(480, 480));
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.requestFocusInWindow();
        wall = new Wall();
        p1 = new Player();
        p2 = new Player();
        p1Radius = 1; //Add data for who placed the bomb
        p2Radius = 1; //Add data for who placed the bomb
        radius = 1;	
        for(int r = 0;r<wallLocation[0].length;r++){
            for(int c = 0;c < wallLocation.length;c++){
                wallLocation[r][c] = new Wall();
                wallLocation[r][c].setX(r*50);
                wallLocation[r][c].setY(c*50);
                if(c == 0 || r == 0 || c == wallLocation.length-1 || r == wallLocation.length-1){
                    wallLocation[r][c].setDestructable(false);
                }
                //Make the top-left corner free of blocks
                if(c == 2 && r == 1 || c == 1 && r == 2 || c == 1 && r == 1){
                    wallLocation[r][c].setAir(true);
                }
                //Make the bottom-right corner free of blocks
                if(c == 7 && r == 8 || c == 8 && r == 7 || c == 8 && r == 8){
                    wallLocation[r][c].setAir(true);
                }
            }
        }
        
        p1.setX(50);
        p1.setY(50);
        p2.setX(400);
        p2.setY(400);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,600,600);

        g.setFont(largeFont);
        
        
        for(int r = 0;r<wallLocation[0].length;r++){
            for(int c = 0;c < wallLocation.length;c++){
                //set color to specific wall types
                if(wallLocation[r][c].isDestructable){
                    g.drawImage(wallDestructable,r*50,c*50,this);
                }
                if(!wallLocation[r][c].isDestructable){
                    g.drawImage(wallIndestructable, r*50,c*50, this);
                }
                if(wallLocation[r][c].isAir){
                    g.setColor(Color.black);
                    g.fillRect(r*50, c*50, 50,50);
                }
               }
              }
              
              //Player 1
              for(int n = 0;n<bombs1.size();n++){
                  //bomb when timer going down
                g.drawImage(bomb, bombs1.get(n).getX(),bombs1.get(n).getY(), this);
                if(bombs1.get(n).checkIfExploded()){
                    explode1(bombs1.get(n));
                    bombs1.remove(n);
                    n--;
                }
            }
            
            //Player 2
            for(int n = 0;n<bombs2.size();n++){
                  //bomb when timer going down
                g.drawImage(bomb, bombs2.get(n).getX(),bombs2.get(n).getY(), this);
                if(bombs2.get(n).checkIfExploded()){
                    explode2(bombs2.get(n));
                    bombs2.remove(n);
                    n--;
                }
            }
            
            //FIRES
            for(int n = 0;n<fires1.size();n++){
                if(fires1.get(n).getX() == p1.x && fires1.get(n).getY() == p1.y){
                    p1.dead = true;
              }
              if(fires1.get(n).getX() == p2.x && fires1.get(n).getY() == p2.y){
                    p2.dead = true;
              }
                if(fires1.get(n).getTimer() > 0){
               g.drawImage(fire, fires1.get(n).getX(), fires1.get(n).getY(), this);
               fires1.get(n).countdown();
              }
              else{
                  fires1.remove(n);
                  n--;
              }
            }
            for(int n = 0;n<fires2.size();n++){
              if(fires2.get(n).getX() == p2.x && fires2.get(n).getY() == p2.y){
                    p2.dead = true;
              }
                if(fires2.get(n).getTimer() > 0){
               g.setColor(Color.orange);
               g.drawImage(fire, fires2.get(n).getX(), fires2.get(n).getY(), this);
               fires2.get(n).countdown();
              }
              else{
                  fires2.remove(n);
                  n--;
              }
            }
                
            //ITEM
            for(int n = 0;n<items.size();n++){
                if(items.get(n).getType() == "bomb"){
                    g.drawImage(BombPU, items.get(n).getX(), items.get(n).getY(), this);
                }
                if(items.get(n).getType() == "radius"){
                    g.drawImage(RadiusPU, items.get(n).getX(), items.get(n).getY(), this);
                }
                //check if the player has 'picked up' an item
                if(items.get(n).getX() == p1.x && items.get(n).getY() == p1.y){
                    if(items.get(n).getType() == "bomb"){
                        p1.numBombs++;
                    }
                    if(items.get(n).getType() == "radius"){
                        p1.radius++;
                    }
                    items.get(n).setPlaced(false);
                }
                
                if(items.get(n).getX() == p2.x && items.get(n).getY() == p2.y){
                    if(items.get(n).getType() == "bomb"){
                        p2.numBombs++;
                    }
                    if(items.get(n).getType() == "radius"){
                        p2.radius++;
                    }
                    items.get(n).setPlaced(false);
                }
                
                if(!items.get(n).isPlaced()){
                    items.remove(n);
                    n--;
                }
            }
            
              //Wall Hit Detection P1
                if(key == 37 && (p1.x/50)-1 >=0 && wallLocation[(p1.x/50)-1][p1.y/50].isAir){
                  //if hit the key and not at the edge and the wall directly to the left is air
                  p1.moveLeft();
                }
                if(key == 38 && (p1.y/50)-1 >=0 && wallLocation[(p1.x/50)][(p1.y/50)-1].isAir){
                  p1.moveUp();
                }
                if(key == 39 && (p1.x/50)+1 >=0 && wallLocation[(p1.x/50)+1][p1.y/50].isAir){
                  p1.moveRight();
                }
                if(key == 40 && (p1.y/50)+1 >=0 && wallLocation[(p1.x/50)][(p1.y/50)+1].isAir){
                  p1.moveDown();
                }  
                
                
                //Wall Hit Detection P2
                //A
                if(key2 == 'a' && (p2.x/50)-1 <=9 && wallLocation[(p2.x/50)-1][p2.y/50].isAir){
                  //if hit the key and not at the edge and the wall directly to the left is air
                  //move left
                  p2.moveLeft();
                }
                //W
                if(key2 == 'w' && (p2.y/50)-1 <=9 && wallLocation[(p2.x/50)][(p2.y/50)-1].isAir){
                  p2.moveUp();
                }
                //D
                if(key2 == 'd' && (p2.x/50)+1 <=9 && wallLocation[(p2.x/50)+1][p2.y/50].isAir){
                  p2.moveRight();
                }
                //S
                if(key2 == 's' && (p2.y/50)+1 <=9 && wallLocation[(p2.x/50)][(p2.y/50)+1].isAir){
                  p2.moveDown();
                }  
                
                if(key == 17){
                  placeBomb1();
                }
                
                if(key2 == ' '){
                  placeBomb2();
                }
                
                g.setColor(Color.black);
              generalTimerCountdown();
              
              //Draw the player
              if(p1.direction == "U/D"){
                  g.drawImage(frontRed, p1.x, p1.y, this);
                }
              if(p1.direction == "L/R"){
                  g.drawImage(sideRed, p1.x, p1.y, this);
                }
                
                if(p2.direction == "L/R"){
                  g.drawImage(sideBlue, p2.x, p2.y, this);
                }
                if(p2.direction == "U/D"){
                  g.drawImage(frontBlue, p2.x, p2.y, this);
                }
              
              key = 0;
              key2 = 'm';
              //Win Condition
              if(p1.dead == true){
                  g.setColor(Color.black);
                  g.fillRect(0,0,600,600);
                  g.setColor(Color.green);
                  g.drawString("Player 2 wins!", 200,200);
                }
                
              if(p2.dead == true){
                g.setColor(Color.black);
                g.fillRect(0,0,600,600);
                g.setColor(Color.green);
                g.drawString("Player 1 wins!", 200,200);
              }
              
    }
    
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void mouseDragged(MouseEvent e){}
    public void keyPressed(KeyEvent e){
        key = e.getKeyCode();
        key2 = e.getKeyChar();
    }
    
    public void keyTyped(KeyEvent e){
        key = e.getKeyCode();
        key2 = e.getKeyChar();
    }
    
    public void placeBomb1(){
        if(p1.numBombs > bombs1.size()){
        bombs1.add(new Bomb(p1.x, p1.y));
       }
    }
    
    public void placeBomb2(){
        if(p2.numBombs > bombs2.size()){
        bombs2.add(new Bomb(p2.x, p2.y));
       }
    }
    
    public void generalTimerCountdown(){
        for(Bomb b:bombs1){
            if(b.placed){
            b.timer--;
        }
       }
       for(Bomb b:bombs2){
            if(b.placed){
            b.timer--;
        }
       }
    }
    
    public void randomItemDrop(int X, int Y){
         items.add(new Item(X,Y));

    }
    
    public void explode1(Bomb b){
        boolean stop1 = false, stop2 = false, stop3 = false, stop4 = false;
        int x = b.getX();
        int y = b.getY();
        //how many times the loop goes through => radius
        fires1.add(new Fire(x,y));
        for(int n = 1;n<p1.getRadius()+1;n++){
            
            if(!stop1 && wallLocation[x/50][y/50+n].getDestructable() == true && wallLocation[x/50][y/50+n].isAir() == false){
                wallLocation[x/50][y/50+n].setAir(true);
                fires1.add(new Fire(x,y+n*50));
                randomItemDrop(x,y+n*50);
                stop1 = true;
            }
            else if(!stop1 && wallLocation[x/50][y/50+n].getDestructable() == false){
                stop1 = true;
            }
            else{
               if(!stop1){
                 fires1.add(new Fire(x,y+n*50));
                }
            }
            
            if(!stop2 && wallLocation[x/50][y/50-n].getDestructable() == true && wallLocation[x/50][y/50-n].isAir() == false){
                wallLocation[x/50][y/50-n].setAir(true);
                fires1.add(new Fire(x,y-n*50));
                randomItemDrop(x,y-n*50);
                stop2 = true;
            }
            else if(!stop2 && wallLocation[x/50][y/50-n].getDestructable() == false){
                stop2 = true;
            }
            else{
               if(!stop2){
                 fires1.add(new Fire(x,y-n*50));
                }
            }
            
            if(!stop3 && wallLocation[x/50+n][y/50].getDestructable() == true && wallLocation[x/50+n][y/50].isAir() == false){
                wallLocation[x/50+n][y/50].setAir(true);
                fires1.add(new Fire(x+n*50,y));
                randomItemDrop(x+n*50,y);
                stop3 = true;
            }
            else if(!stop3 && wallLocation[x/50+n][y/50].getDestructable() == false){
                stop3 = true;
            }
            else{
               if(!stop3){
                 fires1.add(new Fire(x+n*50,y));
                }
            }
            
            if(!stop4 && wallLocation[x/50-n][y/50].getDestructable() == true && wallLocation[x/50-n][y/50].isAir() == false){
                wallLocation[x/50-n][y/50].setAir(true);
                fires1.add(new Fire(x-n*50,y));
                randomItemDrop(x-n*50,y);
                stop4 = true;
            }
            else if(!stop4 && wallLocation[x/50-n][y/50].getDestructable() == false){
                stop4 = true;
            }
            else{
               if(!stop4){
                 fires1.add(new Fire(x-n*50,y));
                }
            }
        }
    }
    
   public void explode2(Bomb b){
        boolean stop1 = false, stop2 = false, stop3 = false, stop4 = false;
        int x = b.getX();
        int y = b.getY();
        //how many times the loop goes through => radius
        fires1.add(new Fire(x,y));
        for(int n = 1;n<p2.getRadius()+1;n++){
            
            if(!stop1 && wallLocation[x/50][y/50+n].getDestructable() == true && wallLocation[x/50][y/50+n].isAir() == false){
                wallLocation[x/50][y/50+n].setAir(true);
                fires1.add(new Fire(x,y+n*50));
                randomItemDrop(x,y+n*50);
                stop1 = true;
            }
            else if(!stop1 && wallLocation[x/50][y/50+n].getDestructable() == false){
                stop1 = true;
            }
            else{
               if(!stop1){
                 fires1.add(new Fire(x,y+n*50));
                }
            }
            
            if(!stop2 && wallLocation[x/50][y/50-n].getDestructable() == true && wallLocation[x/50][y/50-n].isAir() == false){
                wallLocation[x/50][y/50-n].setAir(true);
                fires1.add(new Fire(x,y-n*50));
                randomItemDrop(x,y-n*50);
                stop2 = true;
            }
            else if(!stop2 && wallLocation[x/50][y/50-n].getDestructable() == false){
                stop2 = true;
            }
            else{
               if(!stop2){
                 fires1.add(new Fire(x,y-n*50));
                }
            }
            
            if(!stop3 && wallLocation[x/50+n][y/50].getDestructable() == true && wallLocation[x/50+n][y/50].isAir() == false){
                wallLocation[x/50+n][y/50].setAir(true);
                fires1.add(new Fire(x+n*50,y));
                randomItemDrop(x+n*50,y);
                stop3 = true;
            }
            else if(!stop3 && wallLocation[x/50+n][y/50].getDestructable() == false){
                stop3 = true;
            }
            else{
               if(!stop3){
                 fires1.add(new Fire(x+n*50,y));
                }
            }
            
            if(!stop4 && wallLocation[x/50-n][y/50].getDestructable() == true && wallLocation[x/50-n][y/50].isAir() == false){
                wallLocation[x/50-n][y/50].setAir(true);
                fires1.add(new Fire(x-n*50,y));
                randomItemDrop(x-n*50,y);
                stop4 = true;
            }
            else if(!stop4 && wallLocation[x/50-n][y/50].getDestructable() == false){
                stop4 = true;
            }
            else{
               if(!stop4){
                 fires1.add(new Fire(x-n*50,y));
                }
            }
        }
    }
    public void keyReleased(KeyEvent e){}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}