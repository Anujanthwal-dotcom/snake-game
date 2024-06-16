/**
 * main
 */
import javax.swing.*;
public class main {
    final static int boardwidth=600;
    final static int boardheight=600;

    public static void main(String[] args) {
        JFrame frame=new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        SnakeGame snakeGame=new SnakeGame(boardwidth,boardheight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
    
    

}

