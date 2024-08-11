package snake;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Snake extends Application {
  BorderPane pane;
  
  Scene scene;
  
  String direction;
  
  int snakeLength = 5;
  
  int score = 0;
  
  ArrayList<Integer> snakeX = new ArrayList<>();
  
  ArrayList<Integer> snakeY = new ArrayList<>();
  
  VBox controls = new VBox();
  
  int x = 14;
  
  int y = 15;
  
  boolean gameOver = false;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException {
    final Text scoreboard = new Text("Score: " + this.score);
    scoreboard.setFont(new Font("Arial", 40.0D));
    BorderPane.setAlignment(scoreboard, Pos.TOP_LEFT);
    this.pane = new BorderPane(scoreboard);
    this.scene = new Scene(this.pane, 800.0D, 840.0D);
    primaryStage.setTitle("Snake");
    primaryStage.setScene(this.scene);
    primaryStage.show();
    final Tile[][] grid = new Tile[32][32];
    int tileX = 0;
    int tileY = 40;
    for (int y = 0; y < 32; y++) {
      for (int x = 0; x < 32; x++) {
        grid[x][y] = new Tile(tileX, tileY);
        this.pane.getChildren().add(grid[x][y].getTile());
        tileX += 25;
      } 
      tileX = 0;
      tileY += 25;
    } 
    grid[10][15].addSnake();
    this.snakeX.add(Integer.valueOf(10));
    this.snakeY.add(Integer.valueOf(15));
    grid[11][15].addSnake();
    this.snakeX.add(Integer.valueOf(11));
    this.snakeY.add(Integer.valueOf(15));
    grid[12][15].addSnake();
    this.snakeX.add(Integer.valueOf(12));
    this.snakeY.add(Integer.valueOf(15));
    grid[13][15].addSnake();
    this.snakeX.add(Integer.valueOf(13));
    this.snakeY.add(Integer.valueOf(15));
    grid[14][15].addSnake();
    this.snakeX.add(Integer.valueOf(14));
    this.snakeY.add(Integer.valueOf(15));
    grid[20][20].addApple();
    this.direction = "right";
    this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          public void handle(KeyEvent key) {
            if (key.getCode().toString().equals("LEFT"))
              Snake.this.direction = "left"; 
            if (key.getCode().toString().equals("RIGHT"))
              Snake.this.direction = "right"; 
            if (key.getCode().toString().equals("DOWN"))
              Snake.this.direction = "down"; 
            if (key.getCode().toString().equals("UP"))
              Snake.this.direction = "up"; 
          }
        });
    Thread thread = new Thread(new Runnable() {
          public void run() {
            Runnable updater = new Runnable() {
                public void run() {
                  if (!Snake.this.gameOver)
                    if (Snake.this.y < 0 || Snake.this.y > 31 || Snake.this.x < 0 || Snake.this.x > 31) {
                      Snake.this.gameOver = true;
                      JOptionPane.showMessageDialog(new JFrame(), "Game Over", "Snake", 2);
                    } else if (grid[Snake.this.x][Snake.this.y].hasSnake()) {
                      Snake.this.gameOver = true;
                      JOptionPane.showMessageDialog(new JFrame(), "Game Over", "Snake", 2);
                    } else {
                      Snake.this.snakeX.add(Integer.valueOf(Snake.this.x));
                      Snake.this.snakeY.add(Integer.valueOf(Snake.this.y));
                      if (grid[Snake.this.x][Snake.this.y].hasApple()) {
                        int rX, rY;
                        Snake.this.score++;
                        scoreboard.setText("Score: " + Snake.this.score);
                        Snake.this.snakeLength++;
                        grid[Snake.this.x][Snake.this.y].removeApple();
                        grid[Snake.this.x][Snake.this.y].addSnake();
                        Random rand = new Random();
                        do {
                          rX = rand.nextInt(31);
                          rY = rand.nextInt(31);
                        } while (grid[rX][rY].hasSnake());
                        grid[rX][rY].addApple();
                      } else {
                        grid[Snake.this.x][Snake.this.y].addSnake();
                        grid[((Integer)Snake.this.snakeX.remove(0)).intValue()][((Integer)Snake.this.snakeY.remove(0)).intValue()].removeSnake();
                      } 
                    }  
                }
              };
            while (!Snake.this.gameOver) {
              try {
                Thread.sleep(100L);
                if (Snake.this.direction.equals("left"))
                  Snake.this.moveX(-1); 
                if (Snake.this.direction.equals("right"))
                  Snake.this.moveX(1); 
                if (Snake.this.direction.equals("up"))
                  Snake.this.moveY(-1); 
                if (Snake.this.direction.equals("down"))
                  Snake.this.moveY(1); 
              } catch (InterruptedException interruptedException) {}
              Platform.runLater(updater);
            } 
          }
        });
    thread.setDaemon(true);
    thread.start();
  }
  
  public void moveX(int d) {
    if (d > 0) {
      this.x++;
    } else {
      this.x--;
    } 
  }
  
  public void moveY(int d) {
    if (d > 0) {
      this.y++;
    } else {
      this.y--;
    } 
  }
}
