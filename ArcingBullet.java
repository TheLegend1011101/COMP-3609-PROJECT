import java.awt.Graphics2D;
import java.awt.Color;

public class ArcingBullet extends Bullet {
    private double initialVelocityX;
    private double initialVelocityY;
    private double gravity = 0.5;
    private double timeElapsed;
    private int startX, startY;

    public ArcingBullet(int startX, int startY, BulletOwner owner, int damage, double initialVelocityX,
            double initialVelocityY) {

        this.startX = startX;
        this.startY = startY;
        this.initialVelocityX = initialVelocityX;
        this.initialVelocityY = initialVelocityY;
        this.timeElapsed = 0;
    }

    @Override
    public void update() {
        timeElapsed += 0.5; // Adjust time increment for desired speed
        x = (int) (startX + initialVelocityX * timeElapsed);
        y = (int) (startY - (initialVelocityY * timeElapsed - 0.5 * gravity * timeElapsed * timeElapsed));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillOval(x, y, 8, 8); // Draw as a small circle
    }

    public boolean isOffScreen() {
        return y < -8 || y > 600 || x < -8 || x > 800;
    }
}