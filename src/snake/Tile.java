package snake;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
  private Rectangle background;
  
  private Rectangle tile;
  
  private StackPane stack = new StackPane();
  
  private boolean hasSnake;
  
  private boolean hasApple;
  
  private int xLoc;
  
  private int yLoc;
  
  public Tile(int x, int y) {
    this.xLoc = x;
    this.yLoc = y;
    this.background = new Rectangle(x, y, 100.0D, 100.0D);
    this.tile = new Rectangle((x + 5), (y + 5), 100.0D, 100.0D);
    this.background.setFill(Color.BLACK);
    this.stack.setLayoutX((x + 50));
    this.stack.setLayoutY((y + 50));
    this.stack.getChildren().addAll(new Node[] { this.background, this.tile });
  }
  
  public StackPane getTile() {
    return this.stack;
  }
  
  public int getX() {
    return this.xLoc;
  }
  
  public int getY() {
    return this.yLoc;
  }
  
  public void addSnake() {
    this.hasSnake = true;
    this.tile.setFill(Color.GREEN);
  }
  
  public void removeSnake() {
    this.hasSnake = false;
    this.tile.setFill(Color.BLACK);
  }
  
  public boolean hasSnake() {
    return this.hasSnake;
  }
  
  public void addApple() {
    this.hasApple = true;
    this.tile.setFill(Color.RED);
  }
  
  public void removeApple() {
    this.hasApple = false;
    this.tile.setFill(Color.BLACK);
  }
  
  public boolean hasApple() {
    return this.hasApple;
  }
}
