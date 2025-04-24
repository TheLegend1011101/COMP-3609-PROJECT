import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
    private int x, y;
    private int width = 50, height = 50;
    private int speed = 5;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    public Player() {
        // this.x = 300;
        // this.y = 450;
        this.x = 100; // Example initial position
        this.y = 100;
        this.width = 50;
        this.height = 50;
    }

    public void update() {
        if (movingLeft && x > 0)
            x -= speed;
        if (movingRight && x < 550)
            x += speed;
        if (movingUp && y > 0)
            y -= speed;
        if (movingDown && y < 500)
            y += speed;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void moveLeft() {
        this.x -= 10; // Adjust the value as needed for movement speed
    }

    public void moveRight() {
        this.x += 10; // Adjust the value as needed for movement speed
    }

    public void moveUp() {
        this.y -= 10; // Adjust the value as needed for movement speed
    }

    public void moveDown() {
        this.y += 10; // Adjust the value as needed for movement speed
    }

    public Bullet shoot() {
        return new Bullet(x + width / 2, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
