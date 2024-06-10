import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import java.util.random.*;


public class SnakeGame extends JPanel implements ActionListener,KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x=x;   
            this.y=y;
        }

    }
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakebody;
    //food
    Tile food;
    //obstacles
    ArrayList<Tile> obstacle;



    Random random;

    int boardwidth;
    int boardheight;
    int tileSize=25;
    //gamelogic

    Timer gameloop;

    int velocityX;
    int velocityY;
    boolean gameOver=false;


    SnakeGame(int boardwidth, int boardheight){
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        System.out.println(boardheight+""+boardwidth);
        this.setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        this.setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead=new Tile(5,5);
        snakebody=new ArrayList<Tile>();
        obstacle=new ArrayList<>();
        for(int i=0;i<10;i++){
            obstacle.add(new Tile(new Random().nextInt(boardheight/tileSize), new Random().nextInt(boardheight/tileSize)));
        }

        food=new Tile(10,10);
        random=new Random();
        placefood();

        velocityX=0;
        velocityY=1;
        gameloop=new Timer(100, this);
        gameloop.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }


    public void draw(Graphics g){
        for(int i=0;i<boardwidth;i+=tileSize){
            g.drawLine(i, 0,i ,boardheight);
            g.drawLine(0, i, boardwidth,i);

        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        //head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize,tileSize);
        //body

        for(int i=0;i<snakebody.size();i++){
            Tile snakePart=snakebody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        }
        //obstacles
        g.setColor(Color.BLUE);
        for(int i=0;i<10;i++){
            Tile obstacles=obstacle.get(i);
            g.fillRect(obstacles.x*tileSize, obstacles.y*tileSize, tileSize, tileSize);
        }

        //score
        g.setFont(new Font("Arial",Font.BOLD,20));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over:"+String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }
        else{
            g.drawString("Score:"+String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }
    }



    public void placefood(){
        
        food.x=random.nextInt(boardwidth/tileSize);
        food.y=random.nextInt(boardheight/tileSize);


    }

    public boolean collision(Tile tile1,Tile tile2){
        return tile1.x==tile2.x&& tile1.y==tile2.y;

    }
    public void move(){
        if(collision(snakeHead,food)){
            snakebody.add(new Tile(food.x,food.y));
            placefood();
        }
        //snakebody

        for(int i=snakebody.size()-1;i>=0;i--){
            Tile snakePart=snakebody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{
                Tile prevSnakePart=snakebody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;

            }
        }


        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;
        System.out.println(snakeHead.x+" "+snakeHead.y);
        for(int i=0;i<snakebody.size();i++){
        
        Tile snakePart=snakebody.get(i);
        //collide with the head
        if(collision(snakeHead,snakePart)){
            gameOver=true;  
        }

        }
        for(int i=0;i<10;i++){
            Tile obstacles=obstacle.get(i);
            if(collision(snakeHead,obstacles)){
                gameOver=true;
            }
        }

        if(snakeHead.x<0||snakeHead.x>this.boardheight/tileSize||snakeHead.y<0||snakeHead.y>this.boardwidth/tileSize){
            gameOver=true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver==true){
            gameloop.stop();
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;

        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT&&velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT&&velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
